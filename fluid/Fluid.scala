package fluid

class Fluid(dt: Double, diff: Double, visc: Double, res: Int, iter: Int){
    val N = res
    val N1 = N - 1
    val N2 = N - 2
    val s =          Array.fill(N, N)(0.0)
    val density =    Array.fill(N, N)(0.0)
    val velocityX =  Array.fill(N, N)(0.0)
    val velocityY =  Array.fill(N, N)(0.0)
    val velocity0X = Array.fill(N, N)(0.0)
    val velocity0Y = Array.fill(N, N)(0.0)

    def addDensity (x: Int, y: Int)(amount: Double): Unit = density(x)(y)  = density(x)(y)  + amount
    def addVelocity(x: Int, y: Int)(amountX: Double, amountY: Double): Unit = {
        velocityX(x)(y) += amountX
        velocityY(x)(y) += amountY
    }

    def step(): Unit = {
        diffuse(1, velocity0X, velocityX, visc, dt)
        diffuse(2, velocity0Y, velocityY, visc, dt)
        project(velocity0X, velocity0Y, velocityX, velocityY)

        advect(1, velocityX, velocity0X, velocity0X, velocity0Y, dt)
        advect(2, velocityY, velocity0Y, velocity0X, velocity0Y, dt)
        project(velocityX, velocityY, velocity0X, velocity0Y)

        diffuse(0, s, density, diff, dt)
        advect(0, density, s, velocityX, velocityY, dt)
    }

    def advect(b: Int, d: Array[Array[Double]], d0: Array[Array[Double]], xs: Array[Array[Double]], ys: Array[Array[Double]], dt: Double): Unit = {
        for (j <- 1 until N; i <- 1 until N) {
            var x = i - dt * (N2) * xs(i)(j)
            var y = j - dt * (N2) * ys(i)(j)
            if (x < 0.5) x = 0.5
            if (x > N2 + 0.5) x = N2 + 0.5
            if (y < 0.5) y = 0.5
            if (y > N2 + 0.5) y = N2 + 0.5
            val x0 = Math.floor(x)
            val x1 = x - x0
            val x2 = 1.0 - x1
            val y0 = Math.floor(y)
            val y1 = y - y0
            val y2 = 1.0 - y1
            d(i)(j) = 
                x2 * (y2 * d0(x0.toInt)(y0.toInt) + y1 * d0(x0.toInt)((y0 + 1.0).toInt)) + 
                x1 * (y2 * d0((x0 + 1.0).toInt)(y0.toInt) + y1 * d0((x0 + 1.0).toInt)((y0 + 1.0).toInt))
        }
        setBoundries(b, d)
    }


    def project(xs: Array[Array[Double]], ys: Array[Array[Double]], p: Array[Array[Double]], div: Array[Array[Double]]): Unit = {
        for (j <- 1 until N1; i <- 1 until N1) {
            div(i)(j) = (-0.5 * (xs(i + 1)(j) - xs(i - 1)(j) + ys(i)(j + 1) - ys(i)(j - 1))) / N
            p(i)(j) = 0
        }

        setBoundries(0, div)
        setBoundries(0, p)
        linearSolve(0, p, div, 1, 6)

        for (j <- 1 until N1; i <- 1 until N1) {
            xs(i)(j) -= 0.5 * (p(i + 1)(j) - p(i - 1)(j)) * N
            ys(i)(j) -= 0.5 * (p(i)(j + 1) - p(i)(j - 1)) * N
        }

        setBoundries(1, xs)
        setBoundries(2, ys)
    }

    def diffuse(b: Int, xs: Array[Array[Double]], xs0: Array[Array[Double]], diff: Double, dt: Double): Unit = {
        val a = dt * diff * (N2) * (N2)
        linearSolve(b, xs, xs0, a, 1 + (4 * a))
    }

    def setBoundries(b: Int, xs: Array[Array[Double]]): Unit = {
        for (i <- 1 until N1) {
            xs(i)(0) = if(b == 2) xs(i)(1) else -xs(i)(1)
            xs(i)(N1) = if(b == 2) xs(i)(N2) else -xs(i)(N2)
        }

        for (j <- 1 until N1) {
            xs(0)(j) = if(b == 1) xs(1)(j) else -xs(1)(j)
            xs(N1)(j) = if(b == 1) xs(N2)(j) else -xs(N2)(j)
        }
        
        xs(0)(0) = 0.5 * (xs(1)(0) + xs(0)(1))
        xs(0)(N1) = 0.5 * (xs(1)(N1) + xs(0)(N2))
        xs(N1)(0) = 0.5 * (xs(N2)(0) + xs(N1)(1))
        xs(N1)(N1) = 0.5 * (xs(N2)(N1) + xs(N1)(N2))
    }

    def linearSolve(b: Int, xs: Array[Array[Double]], xs0: Array[Array[Double]], a: Double, c: Double): Unit = {
        val cR = 1.0 / c
        for (t <- 0 until iter) {
            for (j <- 1 until N1; i <- 1 until N1) xs(i)(j) = (xs0(i)(j) + a * (xs(i + 1)(j) + xs(i - 1)(j) + xs(i)(j + 1) + xs(i)(j - 1))) * cR
            setBoundries(b, xs)
        }
    }
}
