import java.awt.Color
import scala.util.control.Breaks._
object Mandelbrot{
  def main(args: Array[String]): Unit = {
    val w = new introprog.PixelWindow(800, 800)
    var max_iterations = 255
    for(y <- 0 to w.height - 1){
      for(x <- 0 to w.width - 1){
        var a = ((x.toDouble - (w.width / 2)) / w.width) * 3 - 0.3
        var b = ((y.toDouble - (w.height / 2)) / w.height) * 3
        var cx = 0.0
        var cy = 0.0
        breakable{ 
          for(i <- 0 to max_iterations){
            var tx = cx
            var cx2 = Math.pow(cx, 2)
            var cy2 = Math.pow(cy, 2)
            cx = cx2 - cy2 + a
            cy = 2 * tx * cy + b
            if(cx2 + cy2 > 4) {
              w.setPixel(x, y, new Color(i, i, i))
              break
            }
          }
        }
      }
    } 
  }
}
