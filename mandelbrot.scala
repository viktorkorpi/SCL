import java.awt.Color
import scala.util.control.Breaks._

object Mandelbrot{
  	def main(args: Array[String]): Unit = {
  	  	val w = new introprog.PixelWindow(600, 600, "Mandelbrot")
  	  	val iterations = 255 * 1
  	  	val t1 = System.currentTimeMillis()
  	  	for(y <- 0 until w.height; x <- 0 until w.width){
  	  	    val col = (calcColor(((x.toDouble - (w.width / 2)) / w.width) * 3 - 0.3, ((y.toDouble - (w.height / 2)) / w.height) * 3, iterations) / Math.round(iterations / 255)).toInt
  	  	    if(col > 20) w.setPixel(x, y, new Color(col, col, col))
  	  	} 
  	  	val t2 = System.currentTimeMillis()
  	  	println(s"It took ${t2-t1} ms to calculate and draw the mandelbrot,\nat a resolution of ${w.width}x${w.height} \nwith a max iteration value of $iterations")
  	}

	def calcColor(a: Double, b: Double, iter: Int): Int = {
		var x = 0.0
		var y = 0.0
		var i = 0
		breakable{ 
		  	while(i < iter){
		  	  	var x2 = Math.pow(x, 2)
		  	  	var y2 = Math.pow(y, 2)
		  	  	y = 2 * x * y + b
		  	  	x = x2 - y2 + a
		  	  	if(x2 + y2 > 4) break
		  	  	i += 1
		  	}
		}
		i
  	}
}
