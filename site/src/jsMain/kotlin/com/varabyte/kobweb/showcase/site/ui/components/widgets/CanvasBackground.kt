package com.varabyte.kobweb.showcase.site.ui.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Canvas
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.events.Event
import kotlin.math.*
import kotlin.random.Random

private fun canvasLayerModifier(zIndex: Int): Modifier =
    Modifier.position(Position.Fixed).top(0.px).left(0.px).width(100.vw).height(100.vh).styleModifier {
            property("z-index", zIndex.toString())
            property("pointer-events", "none")
        }

private fun ensureMouseTracking() {
    js(
        """
        if (!window.__cbMouseInit) {
            window.__cbMouseX = -1000;
            window.__cbMouseY = -1000;
            window.addEventListener('mousemove', function(e) {
                window.__cbMouseX = e.clientX;
                window.__cbMouseY = e.clientY;
            });
            window.__cbMouseInit = true;
        }
    """
    )
}

// Flat data structure for the physics engine, representing coordinates in matrices
private class WebNode(var ox: Double, var oy: Double, val spokeIdx: Int, val ringIdx: Int) {
    var nx = ox
    var ny = oy
    var vx = 0.0
    var vy = 0.0
    var pinned = false
}

// Deterministic pseudo-random number generator for fixed geometry layout
private fun pseudoRandom(seed: Double): Double {
    val x = sin(seed) * 10000.0
    return x - floor(x)
}

@Composable
fun CanvasBackground() {
    val isLight = ColorMode.current.isLight

    DisposableEffect(Unit) {
        ensureMouseTracking()
        onDispose { }
    }

    // Layer 0 : Cobweb
    Canvas(attrs = canvasLayerModifier(0).toAttrs()) {
        DisposableEffect(isLight) {
            val canvas = scopeElement
            val ctx = canvas.getContext("2d").unsafeCast<CanvasRenderingContext2D>()

            // State variables stored outside build loop to prevent closure allocation issues on resize
            var w = window.innerWidth.toDouble()
            var h = window.innerHeight.toDouble()
            canvas.width = w.toInt(); canvas.height = h.toInt()

            val SPOKES = 21
            val RINGS = 34
            val STIFFNESS = 0.02
            val DAMPING = 0.85

            // Matrix lists containing arrays of varying lengths
            var physicsNodes = arrayOf<WebNode>()
            var spokesList = arrayOf<Array<WebNode>>()
            var ringsList = arrayOf<Array<WebNode>>()

            fun buildWeb() {
                // Origin ring offset
                val cx = w * 0.15
                val cy = h * 0.15
                val maxR = sqrt((w - cx) * (w - cx) + (h - cy) * (h - cy)) * 1.1

                val nodesList = mutableListOf<WebNode>()
                val tempSpokes = Array(SPOKES) { mutableListOf<WebNode>() }
                val tempRings = Array(RINGS) { mutableListOf<WebNode>() }

                val angles = DoubleArray(SPOKES)
                for (i in 0 until SPOKES) {
                    val baseAngle = (i.toDouble() / SPOKES) * 2 * PI
                    // Deterministic offset to break perfect radial symmetry
                    val offset = sin(i * 12.345) * 0.15
                    angles[i] = baseAngle + offset
                }

                for (r in 0 until RINGS) {
                    // Hub represents the dense inner rings. Outer rings form the sticky spiral.
                    val isHub = r < 6

                    for (s in 0 until SPOKES) {
                        // Hub is solid. Main web has missing segments pseudo-randomly for organic look.
                        val exists = if (isHub) true else {
                            val dropOff = 0.05 + (r.toDouble() / RINGS) * 0.35
                            pseudoRandom(r * 45.67 + s * 89.12) > dropOff
                        }

                        if (!exists) continue

                        // Distances mimic real orb web: tight hub, free zone, expansive sticky spiral
                        val baseDist = if (isHub) {
                            12.0 + r * 5.0
                        } else {
                            val p = (r - 5).toDouble() / (RINGS - 6)
                            60.0 + maxR * p.pow(1.5)
                        }

                        // Shape distortion to make it an irregular polygon rather than a circle
                        val angle = angles[s]
                        val stretch = 1.0 + 0.25 * sin(angle * 2.0 + 1.0) + 0.15 * cos(angle * 3.0)

                        // Slight coordinate jitter
                        val jitter = if (isHub) 0.0 else (pseudoRandom(
                            r * 11.0 + s * 13.0
                        ) - 0.5) * (baseDist * 0.04)

                        val finalDist = baseDist * stretch + jitter
                        val nx = cx + cos(angle) * finalDist
                        val ny = cy + sin(angle) * finalDist

                        val node = WebNode(
                            nx,
                            ny,
                            s,
                            r
                        )
                        // Pin anchor lines and the very center
                        node.pinned = (r == RINGS - 1 || r == 0)

                        nodesList.add(node)
                        tempSpokes[s].add(node)
                        tempRings[r].add(node)
                    }
                }

                // Finalize matrices
                physicsNodes = nodesList.toTypedArray()
                spokesList = tempSpokes.map { it.toTypedArray() }.toTypedArray()
                ringsList = tempRings.map { it.toTypedArray() }.toTypedArray()
            }
            buildWeb()

            val onResize = { _: Event ->
                w = window.innerWidth.toDouble(); h = window.innerHeight.toDouble()
                canvas.width = w.toInt(); canvas.height = h.toInt()
                buildWeb()
            }
            window.addEventListener("resize", onResize)

            var animId = 0
            var lastFrameTime = 0.0
            val targetFPS = 60.0
            val frameInterval = 1000.0 / targetFPS

            fun drawCobweb(now: Double) {
                animId = window.requestAnimationFrame { drawCobweb(it) }
                val delta = now - lastFrameTime
                if (delta < frameInterval) return
                lastFrameTime = now - (delta % frameInterval)

                ctx.clearRect(0.0, 0.0, w, h)
                val mx = js("window.__cbMouseX").unsafeCast<Double>()
                val my = js("window.__cbMouseY").unsafeCast<Double>()

                // Minimal node physics step
                for (i in physicsNodes.indices) {
                    val node = physicsNodes[i]
                    if (node.pinned) continue
                    node.vx += (node.ox - node.nx) * STIFFNESS
                    node.vy += (node.oy - node.ny) * STIFFNESS

                    val adx = mx - node.nx
                    val ady = my - node.ny
                    val ad = sqrt(adx * adx + ady * ady)
                    // Increased repulsion radius from 150 to 220 for larger mouse impact
                    if (ad < 220.0 && ad > 1.0) {
                        val proximity = (220.0 - ad) / 220.0
                        node.vx += (adx / ad) * proximity * 0.05
                        node.vy += (ady / ad) * proximity * 0.05
                    }

                    node.vx *= DAMPING; node.vy *= DAMPING
                    node.nx += node.vx; node.ny += node.vy
                }

                // Render Styles
                val alphaMult = if (isLight) 2.0 else 0.8
                val rColor = if (isLight) 100 else 180
                val gColor = if (isLight) 140 else 200
                val bColor = if (isLight) 120 else 180

                // Draw Radii (Spokes)
                ctx.lineWidth = if (isLight) 1.0 else 0.5
                for (spoke in spokesList) {
                    if (spoke.size < 2) continue
                    ctx.beginPath()
                    ctx.moveTo(spoke[0].nx, spoke[0].ny)
                    for (i in 1 until spoke.size) {
                        ctx.lineTo(spoke[i].nx, spoke[i].ny)
                    }
                    ctx.strokeStyle = "rgba($rColor, $gColor, $bColor, ${(0.3 * alphaMult).coerceIn(0.0, 0.7)})"
                    ctx.stroke()
                }

                // Draw Spiral (Rings)
                for (r in 0 until RINGS) {
                    val ring = ringsList[r]
                    if (ring.size < 2) continue
                    val isHub = r < 6

                    ctx.beginPath()
                    ctx.moveTo(ring[0].nx, ring[0].ny)

                    for (i in 1 until ring.size) {
                        val n1 = ring[i - 1]
                        val n2 = ring[i]

                        val spokeGap = n2.spokeIdx - n1.spokeIdx
                        if (spokeGap > 2) {
                            ctx.moveTo(n2.nx, n2.ny)
                            continue
                        }

                        if (isHub) {
                            ctx.lineTo(n2.nx, n2.ny)
                        } else {
                            val midX = (n1.nx + n2.nx) / 2
                            val midY = (n1.ny + n2.ny) / 2
                            val dist = sqrt((n2.nx - n1.nx).pow(2) + (n2.ny - n1.ny).pow(2))
                            // Increased sag gravity effect for web realism
                            val sagAmt = dist * 0.28
                            ctx.quadraticCurveTo(midX, midY + sagAmt, n2.nx, n2.ny)
                        }
                    }

                    // Attempt to close the ring smoothly
                    val first = ring[0]
                    val last = ring[ring.size - 1]
                    val wrapGap = (SPOKES - last.spokeIdx) + first.spokeIdx
                    if (wrapGap <= 2) {
                        if (isHub) {
                            ctx.lineTo(first.nx, first.ny)
                        } else {
                            val midX = (last.nx + first.nx) / 2
                            val midY = (last.ny + first.ny) / 2
                            val dist = sqrt((first.nx - last.nx).pow(2) + (first.ny - last.ny).pow(2))
                            ctx.quadraticCurveTo(midX, midY + dist * 0.28, first.nx, first.ny)
                        }
                    }

                    val alpha = if (isHub) 0.4 else 0.15 + (r.toDouble() / RINGS) * 0.25
                    ctx.strokeStyle = "rgba($rColor, $gColor, $bColor, ${(alpha * alphaMult).coerceIn(0.0, 0.8)})"
                    ctx.lineWidth = if (isLight) 0.8 else 0.4
                    ctx.stroke()

                    // Dew droplets on intersections for sticky spiral
                    if (!isHub) {
                        ctx.fillStyle = if (isLight) "rgba(0, 150, 100, 0.40)" else "rgba(200, 255, 215, 0.30)"
                        for (node in ring) {
                            ctx.beginPath()
                            ctx.arc(node.nx, node.ny, 1.2, 0.0, PI * 2)
                            ctx.fill()
                        }
                    }
                }
            }
            animId = window.requestAnimationFrame { drawCobweb(it) }

            onDispose {
                window.cancelAnimationFrame(animId)
                window.removeEventListener("resize", onResize)
            }
        }
    }

    // Layer 1 : Fireflies
    Canvas(attrs = canvasLayerModifier(1).toAttrs()) {
        DisposableEffect(isLight) {
            val canvas = scopeElement
            val ctx = canvas.getContext("2d").unsafeCast<CanvasRenderingContext2D>()
            var w = window.innerWidth.toDouble()
            var h = window.innerHeight.toDouble()
            canvas.width = w.toInt(); canvas.height = h.toInt()

            val onResize = { _: Event ->
                w = window.innerWidth.toDouble(); h = window.innerHeight.toDouble()
                canvas.width = w.toInt(); canvas.height = h.toInt()
            }
            window.addEventListener("resize", onResize)

            val NUM_FLIES = 40
            val REPEL_RADIUS = 120.0

            class Firefly {
                var x = Random.nextDouble(0.0, w)
                var y = Random.nextDouble(h * 0.3, h * 1.1)
                var vx = Random.nextDouble(-0.25, 0.25)
                var vy = -Random.nextDouble(0.3, 0.7)
                var alpha = Random.nextDouble(0.0, 0.8)
                var alphaDelta = Random.nextDouble(0.003, 0.008) * if (Random.nextBoolean()) 1 else -1
                val radius = Random.nextDouble(2.0, 4.5)

                val rc = if (isLight) 255 else (220 + Random.nextInt(35))
                val gc = if (isLight) 160 + Random.nextInt(80) else (185 + Random.nextInt(50))
                val bc = if (isLight) Random.nextInt(40) else (30 + Random.nextInt(50))

                fun reset() {
                    x = Random.nextDouble(0.0, w); y = h + Random.nextDouble(10.0, 40.0)
                    vx = Random.nextDouble(-0.25, 0.25); vy = -Random.nextDouble(0.3, 0.7)
                    alpha = 0.0; alphaDelta = Random.nextDouble(0.003, 0.008)
                }
            }

            val flies = Array(NUM_FLIES) { Firefly() }
            var animId = 0
            var lastFrameTime = 0.0
            val targetFPS = 60.0
            val frameInterval = 1000.0 / targetFPS

            fun drawFireflies(now: Double) {
                animId = window.requestAnimationFrame { drawFireflies(it) }
                val delta = now - lastFrameTime
                if (delta < frameInterval) return
                lastFrameTime = now - (delta % frameInterval)

                ctx.clearRect(0.0, 0.0, w, h)
                val mx = js("window.__cbMouseX").unsafeCast<Double>()
                val my = js("window.__cbMouseY").unsafeCast<Double>()

                for (fly in flies) {
                    val dx = fly.x - mx
                    val dy = fly.y - my
                    val dist = sqrt(dx * dx + dy * dy)
                    if (dist < REPEL_RADIUS && dist > 1.0) {
                        val f = ((REPEL_RADIUS - dist) / REPEL_RADIUS) * 1.8
                        fly.vx += (dx / dist) * f * 0.08; fly.vy += (dy / dist) * f * 0.08
                    }
                    val speed = sqrt(fly.vx * fly.vx + fly.vy * fly.vy)
                    if (speed > 1.4) {
                        fly.vx = fly.vx / speed * 1.4; fly.vy = fly.vy / speed * 1.4
                    }
                    fly.vy = fly.vy * 0.97 - 0.012
                    fly.x += fly.vx; fly.y += fly.vy
                    val heightFactor = (fly.y / h).coerceIn(0.0, 1.0)
                    fly.alpha = (fly.alpha + fly.alphaDelta).coerceIn(0.0, 1.0)
                    if (fly.alpha >= 1.0 || fly.alpha <= 0.0) fly.alphaDelta = -fly.alphaDelta

                    val alphaMultiplier = if (isLight) 1.8 else 1.0
                    val drawAlpha = (fly.alpha * heightFactor * alphaMultiplier).coerceIn(0.0, 1.0)

                    if (drawAlpha > 0.01) {
                        val grad = ctx.createRadialGradient(fly.x, fly.y, 0.0, fly.x, fly.y, fly.radius * 3)
                        grad.addColorStop(0.0, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, $drawAlpha)")
                        grad.addColorStop(0.4, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, ${drawAlpha * 0.5})")
                        grad.addColorStop(1.0, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, 0)")
                        ctx.beginPath(); ctx.arc(fly.x, fly.y, fly.radius * 3, 0.0, PI * 2)
                        ctx.fillStyle = grad; ctx.fill()

                        val coreR = 255
                        val coreG = if (isLight) 230 else 255
                        val coreB = if (isLight) 150 else 200
                        ctx.beginPath(); ctx.arc(fly.x, fly.y, fly.radius, 0.0, PI * 2)
                        ctx.fillStyle = "rgba($coreR, $coreG, $coreB, $drawAlpha)"; ctx.fill()
                    }
                    if (fly.y < -20 || fly.x < -20 || fly.x > w + 20) fly.reset()
                }
            }
            animId = window.requestAnimationFrame { drawFireflies(it) }

            onDispose {
                window.cancelAnimationFrame(animId)
                window.removeEventListener("resize", onResize)
            }
        }
    }
}
