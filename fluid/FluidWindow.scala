package fluid
import introprog.PixelWindow
import introprog.PixelWindow.Event
import java.awt.Color

case class FluidWindow(fluid: Fluid, N: Int, s: Int){
    val w = new PixelWindow(N * s, N * s, "Fluid Simulation")
    var t = 0.0

    def update(): Unit = {
        for(cy <- -1 to 1; cx <- -1 to 1) fluid.addDensity((N / 2) + cx, (N / 2) + cy)(500)
        fluid.addVelocity(N / 2, N / 2)(Math.cos(t.toRadians), Math.sin(t.toRadians))
        fluid.step
        t += 0.01
    }

    def drawDensity(): Unit = {
        for(i <- 0 until N; j <- 0 until N){
            val C = limit(fluid.density(i)(j), 0, 255).toInt
            w.fill(i * s, j * s, s, s, new Color(C, C, C))
        }
    }

    def drawVelocity(): Unit = {
        val velRes = 1
        for(i <- 0 until N by velRes; j <- 0 until N by velRes){
            val x = limit(fluid.velocityX(i)(j), 0, velRes * 2).toInt
            val y = limit(fluid.velocityY(i)(j), 0, velRes * 2).toInt
            w.line(i * s, j * s, x + i * s, y + j * s, java.awt.Color.RED)
        }
    }

    def handleClick(pos: (Int, Int)): Unit = if(w.isInside(pos._1, pos._2)) fluid.addDensity((pos._1 / s).toInt, (pos._2 / s).toInt)(500)

    var quit = false
    val EventMaxWait = 1
    val NextStepDelay = 100
    def loop(): Unit = while(!quit) {
        val t0 = System.currentTimeMillis 
        w.awaitEvent(EventMaxWait)
        while(w.lastEventType != PixelWindow.Event.Undefined) {
            w.lastEventType match{ 
                case Event.MousePressed => handleClick(w.lastMousePos)
                case Event.WindowClosed => quit = true
                case _ =>
            }
            w.awaitEvent(EventMaxWait)
        } 
        update
        drawDensity
        val elapsed = System.currentTimeMillis - t0
        println(elapsed)
        Thread.sleep((NextStepDelay - elapsed) max 0)
    }

    def limit(x: Double, min: Double, max: Double): Double = if(x >= max) max else if(x <= min) min else x
}