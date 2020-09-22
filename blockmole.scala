package blockmole
import introprog.PixelWindow
import java.awt.{Color => JColor}

object Color {
    val black  = new JColor(  0,   0,   0)
    val mole   = new JColor( 51,  51,   0)
    val soil   = new JColor(153, 102,  51)
    val tunnel = new JColor(204, 153, 102)
    val grass  = new JColor( 25, 130,  35)
    val sky    = new JColor(140, 190, 255)
}

object BlockWindow {
  val windowSize = (30, 50) 
  val blockSize  = 10      
  val window = new PixelWindow(blockSize * windowSize._1, blockSize * windowSize._2, "Digging Blockmole")

  type Pos = (Int, Int)

  def block(pos: Pos)(color: JColor = JColor.gray): Unit = {
    val x = blockSize * pos._1
    val y = blockSize * pos._2
    window.fill(x, y, blockSize, blockSize, color)
  }

  def rectangle(leftTop: Pos)(size: (Int, Int))(color: JColor = JColor.gray): Unit = {
    for(y <- 0 to size._2){
      for(x <- 0 to size._1){
        block(leftTop._1 + x,leftTop._2 + y)(color)
      }
    }
  }

  val maxWaitMillis = 10 
  def waitForKey(): String = {
    window.awaitEvent(maxWaitMillis)
    while(window.lastEventType != PixelWindow.Event.KeyPressed) {
      window.awaitEvent(maxWaitMillis)
    }
    println(s"KeyPressed: " + window.lastKey)
    window.lastKey
  }
} 

object Mole { 
  def dig(): Unit = {
    var x = BlockWindow.windowSize._1 / 2
    var y = BlockWindow.windowSize._2 / 2
    var quit = false
    while(!quit) {
      BlockWindow.block(x, y)(Color.mole)
      val key = BlockWindow.waitForKey()
      BlockWindow.block(x, y)(if(y > 9) Color.tunnel else Color.grass)
      if(key == "w") y -= 1
      else if(key == "a") x -= 1
      else if(key == "s") y += 1 
      else if(key == "d") x += 1
      else if(key == "q") quit = true
      if(x >= BlockWindow.windowSize._1) x -= 1
      if(y >= BlockWindow.windowSize._2) y -= 1
      if(x < 0) x += 1
      if(y < 6) y += 1
    }
  }
}
    
object Main {
  def drawWorld(): Unit = {
    BlockWindow.rectangle(0, 0)(size = (30, 6))(Color.sky)
    BlockWindow.rectangle(0, 6)(size = (30, 4))(Color.grass)
    BlockWindow.rectangle(0, 10)(size = (30, 40))(Color.soil)
  }
  
  def main(args: Array[String]): Unit = {
    drawWorld()
    Mole.dig()
  }
}
