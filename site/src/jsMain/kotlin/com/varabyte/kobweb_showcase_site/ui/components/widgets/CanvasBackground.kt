package com.varabyte.kobweb_showcase_site.ui.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
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
    Modifier
        .position(Position.Fixed)
        .top(0.px).left(0.px)
        .width(100.vw).height(100.vh)
        .styleModifier {
            property("z-index", zIndex.toString())
            property("pointer-events", "none")
        }

// Install a single mousemove listener on window that writes to plain JS properties.
// Called once from the first composable that needs it; safe to call multiple times
private fun ensureMouseTracking() {
    js("""
        if (!window.__cbMouseInit) {
            window.__cbMouseX = -1000;
            window.__cbMouseY = -1000;
            window.addEventListener('mousemove', function(e) {
                window.__cbMouseX = e.clientX;
                window.__cbMouseY = e.clientY;
            });
            window.__cbMouseInit = true;
        }
    """)
}

@Composable
fun CanvasBackground() {
    DisposableEffect(Unit) {
        ensureMouseTracking()
        onDispose { }
    }

    // Layer 0 : Cobweb
    Canvas(attrs = canvasLayerModifier(0).toAttrs()) {
        DisposableEffect(Unit) {
            val canvas = scopeElement
            val ctx = canvas.getContext("2d").unsafeCast<CanvasRenderingContext2D>()
            var w = window.innerWidth.toDouble()
            var h = window.innerHeight.toDouble()
            canvas.width = w.toInt(); canvas.height = h.toInt()

            val SPOKES    = 16
            val RINGS     = 8
            val STIFFNESS = 0.032
            val DAMPING   = 0.87
            val ATTRACT_R = 220.0
            val ATTRACT_F = 0.18

            val totalNodes = SPOKES * RINGS + 1
            val ox = DoubleArray(totalNodes); val oy = DoubleArray(totalNodes)
            val nx = DoubleArray(totalNodes); val ny = DoubleArray(totalNodes)
            val vx = DoubleArray(totalNodes); val vy = DoubleArray(totalNodes)
            val pinned = BooleanArray(totalNodes)

            fun centerIdx() = totalNodes - 1

            fun spokeAnchorPos(s: Int): Pair<Double, Double> {
                val angle = s.toDouble() / SPOKES * 2 * PI
                val cx = w * 0.5; val cy = h * 0.5
                val dx = cos(angle); val dy = sin(angle)
                val tx = if (dx > 1e-9) (w - cx) / dx else if (dx < -1e-9) -cx / dx else Double.MAX_VALUE
                val ty = if (dy > 1e-9) (h - cy) / dy else if (dy < -1e-9) -cy / dy else Double.MAX_VALUE
                val t  = minOf(tx, ty) * 0.96
                return Pair(cx + dx * t, cy + dy * t)
            }

            fun buildWeb() {
                val cx = w * 0.5; val cy = h * 0.5
                val ci = centerIdx()
                ox[ci] = cx; oy[ci] = cy; nx[ci] = cx; ny[ci] = cy; pinned[ci] = false
                for (s in 0 until SPOKES) {
                    val (tx, ty) = spokeAnchorPos(s)
                    for (r in 0 until RINGS) {
                        val t = (r + 1).toDouble() / RINGS
                        val idx = r * SPOKES + s
                        ox[idx] = cx + (tx - cx) * t; oy[idx] = cy + (ty - cy) * t
                        nx[idx] = ox[idx]; ny[idx] = oy[idx]
                        vx[idx] = 0.0; vy[idx] = 0.0
                        pinned[idx] = (r == RINGS - 1)
                    }
                }
            }
            buildWeb()

            val onResize = { _: Event ->
                w = window.innerWidth.toDouble(); h = window.innerHeight.toDouble()
                canvas.width = w.toInt(); canvas.height = h.toInt()
                buildWeb()
            }
            window.addEventListener("resize", onResize)

            var animId = 0
            fun drawCobweb() {
                ctx.clearRect(0.0, 0.0, w, h)

                // Read mouse from window JS properties (shared with firefly layer)
                val mx = js("window.__cbMouseX").unsafeCast<Double>()
                val my = js("window.__cbMouseY").unsafeCast<Double>()

                // Physics
                val ci = centerIdx()
                for (i in 0 until totalNodes) {
                    if (pinned[i]) continue
                    vx[i] += (ox[i] - nx[i]) * STIFFNESS
                    vy[i] += (oy[i] - ny[i]) * STIFFNESS
                    val adx = mx - nx[i]; val ady = my - ny[i]
                    val ad  = sqrt(adx * adx + ady * ady)
                    if (ad < ATTRACT_R && ad > 1.0) {
                        val proximity = (ATTRACT_R - ad) / ATTRACT_R  // 0..1
                        vx[i] += (adx / ad) * proximity * ATTRACT_F
                        vy[i] += (ady / ad) * proximity * ATTRACT_F
                    }
                    vx[i] *= DAMPING; vy[i] *= DAMPING
                    nx[i] += vx[i]; ny[i] += vy[i]
                }

                val cx = nx[ci]; val cy = ny[ci]

                // Draw spokes
                for (s in 0 until SPOKES) {
                    var px = cx; var py = cy
                    for (r in 0 until RINGS) {
                        val idx = r * SPOKES + s
                        val curX = nx[idx]; val curY = ny[idx]
                        val tension = sqrt((curX - ox[idx]).pow(2) + (curY - oy[idx]).pow(2)) / 20.0
                        // White-silver base, green tint grows with tension
                        val alpha = (0.45 + tension * 0.2).coerceIn(0.0, 0.7)
                        val greenBoost = (tension * 180).toInt().coerceIn(0, 180)
                        val r_ = (210 - greenBoost / 2).coerceIn(30, 210)
                        val g_ = (210 + greenBoost / 2).coerceIn(180, 255)
                        val b_ = (200 - greenBoost).coerceIn(50, 200)
                        ctx.strokeStyle = "rgba($r_, $g_, $b_, $alpha)"
                        ctx.lineWidth = if (r < RINGS / 2) 0.5 else 0.85
                        ctx.beginPath(); ctx.moveTo(px, py); ctx.lineTo(curX, curY); ctx.stroke()
                        px = curX; py = curY
                    }
                }

                // Draw ring strands (Bezier, slight inward curve)
                for (r in 0 until RINGS) {
                    val baseAlpha = 0.10 + (r.toDouble() / RINGS) * 0.30
                    for (s in 0 until SPOKES) {
                        val sNext = (s + 1) % SPOKES
                        val idx1 = r * SPOKES + s; val idx2 = r * SPOKES + sNext
                        val x1 = nx[idx1]; val y1 = ny[idx1]
                        val x2 = nx[idx2]; val y2 = ny[idx2]
                        val tension = sqrt(
                            ((x1 - ox[idx1]) + (x2 - ox[idx2])).pow(2) / 4 +
                                    ((y1 - oy[idx1]) + (y2 - oy[idx2])).pow(2) / 4
                        ) / 15.0
                        val alpha = (baseAlpha + tension * 0.15).coerceIn(0.04, 0.6)
                        val greenBoost = (tension * 120).toInt().coerceIn(0, 120)
                        ctx.strokeStyle = "rgba(${200 - greenBoost / 3}, ${220 + greenBoost / 3}, ${195 - greenBoost / 2}, $alpha)"
                        ctx.lineWidth = 0.55
                        val midX = (x1 + x2) / 2; val midY = (y1 + y2) / 2
                        val cpX = midX + (cx - midX) * 0.07; val cpY = midY + (cy - midY) * 0.07
                        ctx.beginPath(); ctx.moveTo(x1, y1)
                        ctx.quadraticCurveTo(cpX, cpY, x2, y2); ctx.stroke()
                    }
                }

                // Dew-drop dots at intersections
                ctx.fillStyle = "rgba(200, 255, 215, 0.30)"
                for (r in 0 until RINGS) {
                    for (s in 0 until SPOKES) {
                        val idx = r * SPOKES + s
                        ctx.beginPath()
                        ctx.arc(nx[idx], ny[idx], if (r == 0) 1.2 else 0.8, 0.0, PI * 2)
                        ctx.fill()
                    }
                }

                animId = window.requestAnimationFrame { drawCobweb() }
            }
            drawCobweb()

            onDispose {
                window.cancelAnimationFrame(animId)
                window.removeEventListener("resize", onResize)
            }
        }
    }

    // Layer 1 : Fireflies
    Canvas(attrs = canvasLayerModifier(1).toAttrs()) {
        DisposableEffect(Unit) {
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

            val NUM_FLIES    = 55
            val REPEL_RADIUS = 120.0

            class Firefly {
                var x = Random.nextDouble(0.0, w)
                var y = Random.nextDouble(h * 0.3, h * 1.1)
                var vx = Random.nextDouble(-0.25, 0.25)
                var vy = -Random.nextDouble(0.3, 0.7)
                var alpha = Random.nextDouble(0.0, 0.8)
                var alphaDelta = Random.nextDouble(0.003, 0.008) * if (Random.nextBoolean()) 1 else -1
                val radius = Random.nextDouble(2.0, 4.5)
                val rc = (220 + Random.nextInt(35))
                val gc = (185 + Random.nextInt(50))
                val bc = (30  + Random.nextInt(50))

                fun reset() {
                    x = Random.nextDouble(0.0, w); y = h + Random.nextDouble(10.0, 40.0)
                    vx = Random.nextDouble(-0.25, 0.25); vy = -Random.nextDouble(0.3, 0.7)
                    alpha = 0.0; alphaDelta = Random.nextDouble(0.003, 0.008)
                }
            }

            val flies = Array(NUM_FLIES) { Firefly() }
            var animId = 0

            fun drawFireflies() {
                ctx.clearRect(0.0, 0.0, w, h)
                val mx = js("window.__cbMouseX").unsafeCast<Double>()
                val my = js("window.__cbMouseY").unsafeCast<Double>()

                for (fly in flies) {
                    val dx = fly.x - mx; val dy = fly.y - my
                    val dist = sqrt(dx * dx + dy * dy)
                    if (dist < REPEL_RADIUS && dist > 1.0) {
                        val f = ((REPEL_RADIUS - dist) / REPEL_RADIUS) * 1.8
                        fly.vx += (dx / dist) * f * 0.08; fly.vy += (dy / dist) * f * 0.08
                    }
                    val speed = sqrt(fly.vx * fly.vx + fly.vy * fly.vy)
                    if (speed > 1.4) { fly.vx = fly.vx / speed * 1.4; fly.vy = fly.vy / speed * 1.4 }
                    fly.vy = fly.vy * 0.97 - 0.012
                    fly.x += fly.vx; fly.y += fly.vy
                    val heightFactor = (fly.y / h).coerceIn(0.0, 1.0)
                    fly.alpha = (fly.alpha + fly.alphaDelta).coerceIn(0.0, 1.0)
                    if (fly.alpha >= 1.0 || fly.alpha <= 0.0) fly.alphaDelta = -fly.alphaDelta
                    val drawAlpha = fly.alpha * heightFactor

                    if (drawAlpha > 0.01) {
                        val grad = ctx.createRadialGradient(fly.x, fly.y, 0.0, fly.x, fly.y, fly.radius * 3)
                        grad.addColorStop(0.0, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, $drawAlpha)")
                        grad.addColorStop(0.4, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, ${drawAlpha * 0.5})")
                        grad.addColorStop(1.0, "rgba(${fly.rc}, ${fly.gc}, ${fly.bc}, 0)")
                        ctx.beginPath(); ctx.arc(fly.x, fly.y, fly.radius * 3, 0.0, PI * 2)
                        ctx.fillStyle = grad; ctx.fill()
                        ctx.beginPath(); ctx.arc(fly.x, fly.y, fly.radius, 0.0, PI * 2)
                        ctx.fillStyle = "rgba(255, 255, 200, $drawAlpha)"; ctx.fill()
                    }
                    if (fly.y < -20 || fly.x < -20 || fly.x > w + 20) fly.reset()
                }
                animId = window.requestAnimationFrame { drawFireflies() }
            }
            drawFireflies()

            onDispose {
                window.cancelAnimationFrame(animId)
                window.removeEventListener("resize", onResize)
            }
        }
    }
}
