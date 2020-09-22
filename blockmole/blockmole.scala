package blockmole
import introprog._

object Color {
    val black  = new Color(  0,   0,   0)
    val mole   = new Color( 51,  51,   0)
    val soil   = new Color(153, 102,  51)
    val tunnel = new Color(204, 153, 102)
    val grass  = new Color( 25, 130,  35)
}

object BlockWindow {
  // Har ett introprog.PixelWindow och ritar blockgrafik
} 

object Mole { 
    // Representerar en blockmullvad som kan grävadefdig(): Unit = println("Här ska det grävas!")
}
    
object Main {
  def drawWorld(): Unit = println("Ska rita ut underjorden!")
  
  def main(args: Array[String]): Unit = {
    drawWorld()
    Mole.dig()
  }
}
