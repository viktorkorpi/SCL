package blockbattle
import introprog.PixelWindow
import java.awt.{Color => JColor}

object Main {
  def main(args: Array[String]): Unit = {
    val game = new Game("Left player!", "Right player!")
    game.start
  }
}

object Game {
    val blockSize = 14
    val skyRange = 0 to 9
    val windowSize = (30, 50)
    val windowTitle = "EPIC BLOCK BATTLE"
    val grassRange = skyRange.last + 1 to skyRange.last + 1  
    
    object Color { 
        val black  = new JColor(  0,   0,   0)
        val mole   = new JColor( 51,  51,   0)
        val soil   = new JColor(153, 102,  51)
        val tunnel = new JColor(204, 153, 102)
        val grass  = new JColor( 25, 130,  35)
        val sky    = new JColor(140, 190, 255) 
    }

    def backgroundColorAtDepth(y: Int): JColor = if(skyRange.contains(y)) Color.sky else if(grassRange.contains(y)) Color.grass else Color.soil
} 

class Game( val leftPlayerName: String  = "LEFT", val rightPlayerName: String = "RIGHT") {
    import Game._
    val window = new BlockWindow(windowSize, windowTitle, blockSize)
    val leftMole: Mole  = new Mole(leftPlayerName, Pos(0, grassRange.last + 1), (1, 0), Color.mole, KeyControl("a", "d", "w", "s"))
    val rightMole: Mole = new Mole(rightPlayerName, Pos(windowSize._1 - 1, windowSize._2 - 1), (-1, 0), Color.mole, KeyControl("j", "l", "i", "k"))

    def drawWorld(): Unit = for(y <- 0 to windowSize._2) for(x <- 0 to windowSize._1) window.setBlock(Pos(x, y), backgroundColorAtDepth(y))

    def eraseBlocks(x1: Int, y1: Int, x2: Int, y2: Int): Unit = ???

    def update(mole: Mole): Unit = {
        window.setBlock(mole.nextPos, mole.color)
        window.setBlock(mole.pos, Color.tunnel)  
        mole.move() 
        if(mole.pos.x == windowSize._1 || mole.pos.y == windowSize._2 || mole.pos.x == 0 || mole.pos.y == grassRange.start) mole.reverseDir
    } 

    var quit = false
    val delayMillis = 80
    def gameLoop(): Unit = {
        while(!quit) {
            val t0 = System.currentTimeMillis
            handleEvents()
            update(leftMole) 
            update(rightMole)
            window.write(s"${leftMole.name}: ${leftMole.points}", Pos(0, 0), JColor.black, blockSize)
            window.write(s"${rightMole.name}: ${rightMole.points}", Pos(0, 3), JColor.black, blockSize)
            val elapsedMillis = (System.currentTimeMillis - t0).toInt
            Thread.sleep((delayMillis - elapsedMillis) max 0)
        }
    }
   
    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while(e != BlockWindow.Event.Undefined) {
            e match{
                case BlockWindow.Event.KeyPressed(key) => if(leftMole.keyControl.has(key)) leftMole.setDir(key) else if(rightMole.keyControl.has(key)) rightMole.setDir(key)
                case BlockWindow.Event.WindowClosed => quit = true
            }
            e = window.nextEvent()
        }
    }


    def start(): Unit = {
        println("Start digging!")
        println(s"$leftPlayerName ${leftMole.keyControl}")
        println(s"$rightPlayerName ${rightMole.keyControl}")
        drawWorld()
        gameLoop()
    }
}

class BlockWindow(val nbrOfBlocks: (Int, Int), val title: String = "BLOCK WINDOW", val blockSize: Int = 14) {
    val pixelWindow = new PixelWindow(nbrOfBlocks._1 * blockSize, nbrOfBlocks._2 * blockSize, title)
    def setBlock(pos: Pos, color: java.awt.Color): Unit = pixelWindow.fill(pos.x * blockSize, pos.y * blockSize, blockSize, blockSize, color)
    def getBlock(pos: Pos): java.awt.Color = Game.backgroundColorAtDepth(pos.y)
    def write(text: String, pos: Pos, color: java.awt.Color, textSize: Int = blockSize): Unit = pixelWindow.drawText(text, pos.x * blockSize, pos.y * blockSize, color, textSize)
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

case class Pos(x: Int, y: Int) {
    def moved(delta: (Int, Int)): Pos = ???
}

case class KeyControl(left: String, right: String, up: String, down: String){
    override def toString = s"Keys(left: $left, right: $right, up: $up, down: $down)"
    def direction(key: String): (Int, Int) = if(key == left) -1 -> 0 else if(key == right) 1 -> 0 else if(key == up) 0 -> -1 else if(key == down) 0 -> 1 else 0 -> 0
    def has(key: String): Boolean = if(key == left || key == right || key == up || key == down) true else false
}

class Mole(val name: String, var pos: Pos, var dir: (Int, Int), val color: java.awt.Color, val keyControl: KeyControl){
    var points = 0
    override def toString = s"Mole[name=$name, pos=$pos, dir=$dir, points=$points]"
    def setDir(key: String): Unit = dir = keyControl.direction(key)
    def reverseDir(): Unit = dir = (dir._1 * -1, dir._2 * -1)
    def move(): Unit = pos = nextPos
    def nextPos: Pos = Pos(pos.x + dir._1, pos.y + dir._2)
}