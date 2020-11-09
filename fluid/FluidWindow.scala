package fluid
import introprog.PixelWindow
import introprog.PixelWindow.Event
import java.awt.Color

case class FluidWindow(fluid: Fluid){
    val N = Main.resolution
    val s = Main.scale
    val w = new PixelWindow(N * s, N * s, "Fluid Simulation")
    var t = 0.0

    def draw(): Unit = {
        drawDensity
        //drawVelocity
    }

    def update(): Unit = {
        for(cy <- -1 to 1; cx <- -1 to 1) fluid.addDensity((N / 2) + cx, (N / 2) + cy)(500)
        //fluid.addVelocity(N / 2, N / 2)(Math.cos(t.toRadians) / 10, Math.sin(t.toRadians) / 10)
        fluid.step
        draw
        t += 0.01
    }

    def drawDensity(): Unit = {
        for(i <- 0 until N; j <- 0 until N){
            val C = stretch(fluid.density(i)(j), -1000, 1000, 0, 254).toInt
            w.fill(i * s, j * s, s, s, new Color(C, C, C))
        }
    }

    def handleClick(pos: (Int, Int)): Unit = {
        if(w.isInside(pos._1, pos._2)) fluid.addDensity((pos._1 / s).toInt, (pos._2 / s).toInt)(500)
    }

    var quit = false
    val EventMaxWait = 1
    val NextStepDelay = 200
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
        draw
        val elapsed = System.currentTimeMillis - t0
        println(elapsed)
        Thread.sleep((NextStepDelay - elapsed) max 0)
    }

    def stretch(p: Double, A: Double, B: Double, C: Double, D: Double): Double = {
        val scale = (D - C) / (if(B - A != 0) B - A else 0.00001)
        val offset = -A * (D - C) / (B - A) + C
        p * scale + offset
    }
}