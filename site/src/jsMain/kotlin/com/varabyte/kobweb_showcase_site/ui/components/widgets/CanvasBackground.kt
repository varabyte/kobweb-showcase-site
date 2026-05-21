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
import org.w3c.dom.events.MouseEvent
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun CanvasBackground() {
    // Render the Canvas directly via Compose HTML instead of raw DOM manipulation.
    // This ensures it mounts correctly within the tree.
    Canvas(
        attrs = Modifier
            .position(Position.Fixed)
            .top(0.px).left(0.px)
            .width(100.vw).height(100.vh)
            .styleModifier {
                property("z-index", "0")
                property("pointer-events", "none")
            }
            .toAttrs()
    ) {
        DisposableEffect(Unit) {
            val canvas = scopeElement
            val ctx = canvas.getContext("2d").unsafeCast<CanvasRenderingContext2D>()

            var w = window.innerWidth.toDouble()
            var h = window.innerHeight.toDouble()
            canvas.width = w.toInt()
            canvas.height = h.toInt()

            val mouse = doubleArrayOf(-1000.0, -1000.0)

            val onResize = { _: Event ->
                w = window.innerWidth.toDouble()
                h = window.innerHeight.toDouble()
                canvas.width = w.toInt()
                canvas.height = h.toInt()
            }
            window.addEventListener("resize", onResize)

            val onMouseMove = { e: Event ->
                val me = e.unsafeCast<MouseEvent>()
                mouse[0] = me.clientX.toDouble()
                mouse[1] = me.clientY.toDouble()
            }
            window.addEventListener("mousemove", onMouseMove)

            // Interactive particle physics
            class Particle(
                var x: Double, var y: Double,
                var vx: Double, var vy: Double
            ) {
                val baseRadius = Random.nextDouble(1.0, 2.5)
            }

            val numParticles = 80
            val particles = Array(numParticles) {
                Particle(
                    x = Random.nextDouble(0.0, w),
                    y = Random.nextDouble(0.0, h),
                    vx = Random.nextDouble(-0.5, 0.5),
                    vy = Random.nextDouble(-0.5, 0.5)
                )
            }

            var animId = 0

            fun draw() {
                ctx.clearRect(0.0, 0.0, w, h)

                val mx = mouse[0]
                val my = mouse[1]
                val mouseRadius = 150.0

                // MakoGreen base
                ctx.fillStyle = "#00FF87"

                for (i in 0 until numParticles) {
                    val p = particles[i]

                    // Mouse repulsion force
                    val dx = p.x - mx
                    val dy = p.y - my
                    val dist = sqrt(dx * dx + dy * dy)

                    if (dist < mouseRadius) {
                        val force = (mouseRadius - dist) / mouseRadius
                        p.vx += (dx / dist) * force * 0.8
                        p.vy += (dy / dist) * force * 0.8
                    }

                    // Friction / speed limit
                    val speed = sqrt(p.vx * p.vx + p.vy * p.vy)
                    if (speed > 1.5) {
                        p.vx = (p.vx / speed) * 1.5
                        p.vy = (p.vy / speed) * 1.5
                    }

                    p.x += p.vx
                    p.y += p.vy

                    // Edge wrapping (smoother than bouncing)
                    if (p.x < 0) p.x = w
                    if (p.x > w) p.x = 0.0
                    if (p.y < 0) p.y = h
                    if (p.y > h) p.y = 0.0

                    // Draw dot
                    ctx.beginPath()
                    ctx.arc(p.x, p.y, p.baseRadius, 0.0, PI * 2)
                    ctx.fill()
                }

                // Connect nearby particles
                ctx.lineWidth = 1.0
                for (i in 0 until numParticles) {
                    val p1 = particles[i]
                    for (j in i + 1 until numParticles) {
                        val p2 = particles[j]
                        val dx = p1.x - p2.x
                        val dy = p1.y - p2.y
                        val dist = sqrt(dx * dx + dy * dy)

                        if (dist < 130.0) {
                            val alpha = 1.0 - (dist / 130.0)
                            ctx.strokeStyle = "rgba(0, 255, 135, ${alpha * 0.4})"
                            ctx.beginPath()
                            ctx.moveTo(p1.x, p1.y)
                            ctx.lineTo(p2.x, p2.y)
                            ctx.stroke()
                        }
                    }
                }

                animId = window.requestAnimationFrame { draw() }
            }

            draw()

            onDispose {
                window.cancelAnimationFrame(animId)
                window.removeEventListener("resize", onResize)
                window.removeEventListener("mousemove", onMouseMove)
            }
        }
    }
}
