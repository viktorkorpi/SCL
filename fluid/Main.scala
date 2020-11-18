package fluid

object Main {
    //Global settings
    val iterations = 16
    val resolution = 128
    val scale = 4
    val timeStep = 0.2
    val diffusion = 0.0
    val viscosity = 0.0

    def main(args: Array[String]): Unit = {
        val window = FluidWindow(new Fluid(timeStep, diffusion, viscosity, resolution, iterations), resolution, scale)
        window.loop
    }
}