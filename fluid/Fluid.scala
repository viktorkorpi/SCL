package fluid

class Fluid(dt: Double, diff: Double, visc: Double){
    val density = Array.fill(Main.size._1, Main.size._2)(0.0)
    val velocity = Array.fill(Main.size._1, Main.size._2)(Vektor(0.0, 0.0))
    val velocity0 = Array.fill(Main.size._1, Main.size._2)(Vektor(0.0, 0.0))

    def addDensity(x: Int, y: Int)(amount: Double): Unit = density(x)(y) = density(x)(y) + amount
    def addVelocity(x: Int, y: Int)(amount: Vektor): Unit = velocity(x)(y) = velocity(x)(y) + amount

    def step(): Unit = {
        /*
        diffuse(velocity0, velocity, visc, dt)
        project()
        advect()
        project()
        diffuse()
        advect()
        */
    }

    def setBoundriesVelocity(arr: Array[Array[Vektor]]){
        for(i <- 1 until Main.size._2) {
            arr(i)(0).y = -arr(i)(1).y 
            arr(i)(Main.size._2 - 1).y = -arr(i)(Main.size._2 - 2).y 
        }

        for(i <- 1 until Main.size._1) {
            arr(0)(i).x = -arr(1)(i).x 
            arr(Main.size._1 - 1)(i).x = -arr(Main.size._1 - 2)(i).x
        }

        arr(0)(0) = arr(1)(0) * 0.5 + arr(0)(1)
        arr(0)(Main.size._2 - 1) = arr(1)(Main.size._2 - 1) * 0.5 + arr(0)(Main.size._2 - 2)
        arr(Main.size._1 - 1)(0) = arr(Main.size._2 - 2)(0) * 0.5 + arr(0)(1)
        arr(0)(0) = arr(1)(0) * 0.5 + arr(0)(1)
    }

    def setBoundriesDesity(arr: Array[Array[Double]]){
        arr(0)(0) = arr(1)(0) * 0.5 + arr(0)(1)
        arr(0)(Main.size._2 - 1) = arr(1)(Main.size._2 - 1) * 0.5 + arr(0)(Main.size._2 - 2)
        arr(Main.size._1 - 1)(0) = arr(Main.size._2 - 2)(0) * 0.5 + arr(0)(1)
        arr(0)(0) = arr(1)(0) * 0.5 + arr(0)(1)
    }

    def linearSolve()
}
