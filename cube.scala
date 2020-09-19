import java.awt.Color

object cube{
    val window = new introprog.PixelWindow(500, 500)
    var points = Vector.fill(1000)(new Point)

    def main(args: Array[String]): Unit = {
        for(i <- 0 to 1000){
            Thread.sleep(10)
            window.clear
            points.foreach(p => {
                p.moveZ
                p.project
                p.draw
            })
        }
    }

    class Point{
        var rnd = new scala.util.Random

        var x = (rnd.nextDouble - 0.5) * window.width
        var y = (rnd.nextDouble - 0.5) * window.height
        var z = rnd.nextDouble
        var xp = 0
        var yp = 0

        var c = new Color(100 + rnd.nextInt(155), 100 + rnd.nextInt(155), 100 + rnd.nextInt(155), 255)

        var scale = 0.0
        var fow = window.width * 0.8
        var s = 10

        def moveZ(): Unit = {
            this.z += 0.01
        }

        def project(): Unit = {
            this.scale = this.fow / (this.fow + Math.abs(Math.sin(this.z) * window.width))
            this.xp = ((this.x * this.scale) + window.width / 2).toInt
            this.yp = ((this.y * this.scale) + window.height / 2).toInt
            this.c = new Color(this.c.getRed, this.c.getGreen, this.c.getBlue, (255 * scale).toInt)
        }

        def draw(): Unit = {
            window.fill(this.xp, this.yp, (this.s * this.scale).toInt, (this.s * this.scale).toInt, this.c)
        }
    }
}