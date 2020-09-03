import java.awt.Color
import introprog.PixelWindow.Event

object Life{
    var block_size = 1
    val w = new introprog.PixelWindow(500, 500)
    var rnd = new scala.util.Random()
    var grid = Array.tabulate(w.width / block_size)(n => Array.tabulate(w.height / block_size)(n => rnd.nextInt(2)))
    var next = Array.fill(w.width / block_size)(Array.fill(w.height / block_size)(0))

  def loop(): Unit = {
    var quit = false
    while (!quit) {
    while (w.lastEventType != introprog.PixelWindow.Event.Undefined) {
      w.lastEventType match {
        case Event.MousePressed => handleClick(w.lastMousePos)
        case Event.WindowClosed => quit = true
        case _ =>
      }
    }
  } 
} 

  def handleClick(pos: (Int, Int)): Unit = {
    calc()
    draw()
  }

  def main(args: Array[String]): Unit = {
    draw()
    loop()
  }

  def draw(){
      for(x <- 0 to grid.length - 1){
          for(y <- 0 to grid(x).length - 1){
            if(grid(x)(y) == 1) w.fill(x * block_size, y * block_size, block_size, block_size, new Color(0, 0, 0))
            else w.fill(x * block_size, y * block_size, block_size, block_size, new Color(255, 255, 255))  
          }
      }
  }

  def calc(){
    for(x <- 0 to grid.length - 1){
        for(y <- 0 to grid(x).length - 1){
             var points = 0
            for(cy <- -1 to 1){
                for(cx <- -1 to 1){
                    if(!(cx == 0 && cy == 0)){
                        var nx = (x + cx + w.width / block_size) % w.width / block_size
                        var ny = (y + cy + w.height / block_size) % w.height / block_size
                        if(grid(nx)(ny) == 1) points += 1
                    }
                }
            }
        
            if((points == 3 || points == 2 ) && grid(x)(y) == 1) next(x)(y) = 1
            else if(points == 3 && grid(x)(y) == 0) next(x)(y) = 1
            else next(x)(y) = 0
        }
    }
    grid = next
  }
}
