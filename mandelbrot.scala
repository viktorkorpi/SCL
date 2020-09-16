import java.awt.Color
import scala.util.control.Breaks._

object Mandelbrot{
  def main(args: Array[String]): Unit = {
    val window = new introprog.PixelWindow(800, 800)
    val t1 = System.currentTimeMillis()
    for(y <- 0 until window.height){
      for(x <- 0 until window.width){
        var col = 255 - calcColor(((x.toDouble - (window.width / 2)) / window.width) * 3 - 0.3, ((y.toDouble - (window.height / 2)) / window.height) * 3, 255)
        window.setPixel(x, y, new Color(col, col, col))
      }
    } 
    val t2 = System.currentTimeMillis()
    println(s"It took ${t2-t1} ms to calculate the mandelbrot,\nat a resolution of ${window.width}x${window.height} \nwith a max iteration value of 255")
  }

  def calcColor(a: Double, b: Double, iter: Int): Int = {
    var x = 0.0
    var y = 0.0
    var i = 0
    breakable{ 
      while(i < iter){
        i += 1
        var x2 = Math.pow(x, 2)
        var y2 = Math.pow(y, 2)
        y = 2 * x * y + b
        x = x2 - y2 + a
        if(x2 + y2 > 4) break
      }
    }
    i
  }
}
