package life 
import introprog.PixelWindow
import introprog.PixelWindow.Event
object LifeWindow {
    val EventMaxWait = 1
    var NextGenerationDelay = 200
    val blockSize = 20
    object Color {
        val UnderPopulated = java.awt.Color.cyan  
        val OverPopulated  = java.awt.Color.red   
        val WillBeBorn     = new java.awt.Color(40, 0, 0)  
        val Alive          = new java.awt.Color(242, 128, 161)
        val Dead           = java.awt.Color.black
        val Borders        = java.awt.Color.white
    }
} 
class LifeWindow(rows: Int, cols: Int){
    import LifeWindow._
    var life = Life.random(rows, cols)
    val window: PixelWindow = new PixelWindow(rows * blockSize + 1, cols * blockSize + 1, "Life")
    var quit = false
    var play = false 
    def drawGrid(): Unit = {
        life.cells.foreachIndex(drawCell(_, _))
        drawLines
    }
    def drawCell(row: Int, col: Int): Unit = {
        window.fill(row * blockSize, col * blockSize, blockSize, blockSize, if(life.cells(row, col)) Color.Alive else Color.Dead)
    } 
    def drawLines(): Unit = {
        for(x <- 0 to (cols) * blockSize by blockSize) window.line(x, 0, x, cols * blockSize, Color.Borders)
        for(y <- 0 to (rows) * blockSize by blockSize) window.line(0, y, rows * blockSize, y, Color.Borders)
    }
    def update(newLife: Life): Unit = {
        val oldLife = life
        life = Life.empty((rows, cols))
        life.cells.foreachIndex{ ??? }
    }
    def handleKey(key: String): Unit = {
        println(key)
        if(key == "Enter") life = life.evolved(Life.defaultRule)
        else if(key == " "){

        }
        else if(key.toLowerCase == "r") life = Life.random(rows, cols)
        else if(key == "Backspace") life = Life.empty(rows, cols)
    }
    def handleClick(pos: (Int, Int)): Unit = life = life.toggled((pos._1 / blockSize).toInt, (pos._2 / blockSize).toInt)
    def loopUntilQuit(): Unit = while(!quit) {
        val t0 = System.currentTimeMillis
        if(play) update(life.evolved()) 
        window.awaitEvent(EventMaxWait)
        drawGrid
        while(window.lastEventType != PixelWindow.Event.Undefined) {
            window.lastEventType match{ 
                case Event.KeyPressed => handleKey(window.lastKey)
                case Event.MousePressed => handleClick(window.lastMousePos)
                case Event.WindowClosed => quit = true
                case _ =>
            }
            window.awaitEvent(EventMaxWait)
        } 
        val elapsed = System.currentTimeMillis - t0
        Thread.sleep((NextGenerationDelay - elapsed) max 0)
    }
    def start(): Unit = { 
        drawGrid()
        loopUntilQuit() 
    }
}