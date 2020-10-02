package blockbattle
import introprog.PixelWindow

class BlockWindow(val nbrOfBlocks: (Int, Int), val title: String = "BLOCK WINDOW", val blockSize: Int = 14) {
    val pixelWindow = new PixelWindow(nbrOfBlocks._1 * blockSize, nbrOfBlocks._2 * blockSize, title)
    def setBlock(pos: Pos, color: java.awt.Color): Unit = {
        pixelWindow.fill(
            pos.x * blockSize, 
            pos.y * blockSize, 
            blockSize, 
            blockSize, 
            color
        )
    }
    def setRectangle(pos1: Pos, pos2: Pos, color: java.awt.Color): Unit = {
        pixelWindow.fill(
            pos1.x * blockSize, 
            pos1.y * blockSize, 
            (pos2.x - pos1.x) * blockSize, 
            (pos2.y - pos1.y) * blockSize, 
            color
        )
    }
    def getBlock(pos: Pos): java.awt.Color = {
        val px = pos.x * blockSize + blockSize / 2
        val py = pos.y * blockSize + blockSize / 2
        if(pixelWindow.isInside(px, py)) pixelWindow.getPixel(px, py)
        else java.awt.Color.black
    }
    def write(text: String, pos: Pos, color: java.awt.Color, textSize: Int = blockSize): Unit = {
        pixelWindow.drawText(
            text, 
            pos.x * blockSize, 
            pos.y * blockSize, 
            color, 
            textSize
        )
    }
    def nextEvent(maxWaitMillis: Int = 10): BlockWindow.Event.EventType  = {
        import BlockWindow.Event._
        pixelWindow.awaitEvent(maxWaitMillis)
        pixelWindow.lastEventType
        match {
            case PixelWindow.Event.KeyPressed => KeyPressed(pixelWindow.lastKey)
            case PixelWindow.Event.WindowClosed => WindowClosed
            case _ => Undefined
        }
    }
} 
object BlockWindow {
    def delay(millis: Int): Unit = Thread.sleep(millis)
    object Event {
        trait EventType
        case class KeyPressed(key: String) extends EventType 
        case object WindowClosed extends EventType
        case object Undefined extends EventType
    }
}