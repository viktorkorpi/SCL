package life 
import introprog.PixelWindow
import introprog.PixelWindow.Event
object LifeWindow {
    val EventMaxWait = 1
    var NextGenerationDelay = 0
    val blockSize = 20
    object Color {
        val UnderPopulated = java.awt.Color.cyan  
        val OverPopulated  = java.awt.Color.red   
        val WillBeBorn     = new java.awt.Color(40, 0, 0)  
        val Alive          = new java.awt.Color(242, 128, 161)
        val Dead           = java.awt.Color.black
        val Borders        = new java.awt.Color(150, 150, 150)
    }
} 
class LifeWindow(rows: Int, cols: Int){
    import LifeWindow._
    var life = Life.random(rows, cols)
    val window: PixelWindow = new PixelWindow(rows * blockSize + 1, cols * blockSize + 1, "Life", Color.Borders)
    var quit = false
    var play = false 
    def drawGrid(): Unit = life.cells.foreachIndex(drawCell(_, _))
    def drawCell(row: Int, col: Int): Unit = window.fill(row * blockSize + 1, col * blockSize + 1, blockSize - 1, blockSize - 1, getColor(row, col))
    def getColor(row: Int, col: Int): java.awt.Color = {
        val n = life.nbrOfNeighbours(row, col)
        if(n > 3 && life.cells(row, col)) Color.OverPopulated
        else if(n < 2 && life.cells(row, col)) Color.UnderPopulated
        else if(n == 3 && !life.cells(row, col)) Color.WillBeBorn
        else if(life.cells(row, col)) Color.Alive 
        else Color.Dead
    }
    def update(newLife: Life): Unit = life = newLife
    def handleKey(key: String): Unit = {
        if(key == "Enter") update(life.evolved())
        else if(key == " ") play = !play
        else if(key.toLowerCase == "r") update(Life.random(rows, cols))
        else if(key == "Backspace") update(Life.empty(rows, cols))
        else if(key.toLowerCase == "p") println(life)
    }
    def handleClick(pos: (Int, Int)): Unit = life = life.toggled((pos._1 / blockSize).toInt, (pos._2 / blockSize).toInt)
    def loopUntilQuit(): Unit = while(!quit) {
        val t0 = System.currentTimeMillis
        if(play) update(life.evolved()) 
        window.awaitEvent(EventMaxWait)
        while(window.lastEventType != PixelWindow.Event.Undefined) {
            window.lastEventType match{ 
                case Event.KeyPressed => handleKey(window.lastKey)
                case Event.MousePressed => handleClick(window.lastMousePos)
                case Event.WindowClosed => quit = true
                case _ =>
            }
            window.awaitEvent(EventMaxWait)
        } 
        drawGrid
        val elapsed = System.currentTimeMillis - t0
        Thread.sleep((NextGenerationDelay - elapsed) max 0)
    }
    def start(): Unit = { 
        drawGrid()
        loopUntilQuit() 
    }
}