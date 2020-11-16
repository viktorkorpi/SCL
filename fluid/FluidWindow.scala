package fluid
import introprog.PixelWindow
import introprog.PixelWindow.Event
import java.awt.Color

case class FluidWindow(fluid: Fluid, N: Int, s: Int){
    val HN = N / 2
    val CN = HN + HN / 2
    val w = new PixelWindow(N / 2 * s - 1, N / 2 * s - 1, "Fluid Simulation")
    var t = 0.0

    def update(): Unit = {
        for(cy <- -1 to 1; cx <- -1 to 1) fluid.addDensity((CN) + cx, (CN) + cy)(200)
        fluid.addVelocity(CN, CN)(Math.cos(t), Math.sin(t))
        fluid.step
        t += 0.1
    }

    def drawDensity(): Unit = {
        for(i <- 0 until N; j <- 0 until N){
            val C = limit(fluid.density(i)(j), 0, 255).toInt
            w.fill(i * s - (HN * s), j * s - (HN * s), s, s, new Color(C, C, C))
        }
    }

    def drawVelocity(): Unit = {
        val velRes = 4
        for(i <- 0 until N by velRes; j <- 0 until N by velRes){
            val x = limit(fluid.velocityX(i)(j), 0, velRes * 8).toInt
            val y = limit(fluid.velocityY(i)(j), 0, velRes * 8).toInt
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
        //drawVelocity
        val elapsed = System.currentTimeMillis - t0
        //println(elapsed)
        Thread.sleep((NextStepDelay - elapsed) max 0)
    }

    def limit(x: Double, min: Double, max: Double): Double = if(x >= max) max else if(x <= min) min else x
}