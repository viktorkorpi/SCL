import java.awt.Color

object sphere{
  val radius = 200
  val points = Vector.fill(1000)(new Point)
  val window = new introprog.PixelWindow(radius * 3, radius * 3)

  def main(args: Array[String]): Unit = {
    for(i <- 0 to 180){
      window.clear
      points.foreach(p => {
        p.spin
        p.draw
      })
      Thread.sleep(50)
    }
  }

  class Point{
    var rnd = new scala.util.Random
    var phi = rnd.nextDouble * Math.PI
    var the = rnd.nextDouble * Math.PI * 2
    var x = radius * Math.cos(this.phi) * Math.sin(this.the)
    var y = radius * Math.sin(this.phi) * Math.sin(this.the)
    var z = radius * Math.cos(this.phi) 
    var c = 255

    def spin(): Unit = {
      this.the += 0.005
      this.phi += 0.005
      x = radius * Math.cos(this.phi) * Math.sin(this.the)
      y = radius * Math.sin(this.phi) * Math.sin(this.the)
      z = radius * Math.cos(this.phi)
      //this.c = (255 * (this.z + radius) / (radius * 2)).toInt
    }

    def draw(): Unit = window.setPixel(this.x.toInt + window.width / 2, this.y.toInt + window.height / 2, new Color(this.c, this.c, this.c))
  }
}