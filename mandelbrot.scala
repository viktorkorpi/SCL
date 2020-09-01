import java.awt.Color
object Mandelbrot{
  def main(args: Array[String]): Unit = {
    val w = new introprog.PixelWindow(500, 500)

    //Fibonacci
    /*
    var x = 1
    var px = 0
    for (i <- 0 to 20){
      var tx = x
      x += px
      px = tx
      println(i + " " + x)
    }
    */

    //Mandelbrot
    for(y <- 0 to w.height - 1){
      for(x <- 0 to w.width - 1){
        w.setPixel(x, y, java.awt.Color.white)
        var a = ((x.toDouble - (w.width / 2)) / w.width) * 3 - 0.3
        var b = ((y.toDouble - (w.height / 2)) / w.height) * 3
        var max_iterations = 255
        var cx = 0.0
        var cy = 0.0
        for(i <- 0 to max_iterations){
          var tx = cx
          cx = cx * cx - cy * cy + a
          cy = 2 * tx * cy + b
          if(cx * cx + cy * cy > 4) w.setPixel(x, y, new Color(i, i, i))
        }
      }
    } 
  }
}
