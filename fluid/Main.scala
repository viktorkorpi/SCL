package fluid

object Main {
    //Global variables
    val iterations = 16
    val resolution = 256
    val scale = 2
    val timeStep = 0.2
    val diffusion = 0.0
    val viscosity = 0.0000001

    def main(args: Array[String]): Unit = {
        val window = FluidWindow(new Fluid(timeStep, diffusion, viscosity, resolution, iterations), resolution, scale)
        window.loop
        println("finished")
    }
}