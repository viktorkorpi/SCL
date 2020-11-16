package fluid

object Main {
    //Global variables
    val iterations = 16
    val resolution = 32
    val scale = 16

    def main(args: Array[String]): Unit = {
        val window = FluidWindow(new Fluid(0.2, 0, 0.0000001, resolution, iterations), resolution, scale)
        window.loop
        println("finished")
    }
}