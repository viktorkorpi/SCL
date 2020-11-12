package fluid

object Main {
    //Global variables
    val iterations = 16
    val resolution = 128
    val scale = 8

    def main(args: Array[String]): Unit = {
        val window = FluidWindow(new Fluid(0.0002, 0, 0.0000001))
        window.loop
        println("finished")
    }
}