package blockbattle
import java.awt.{Color => JColor}

object Game {
    val blockSize = 14
    val maxScore = 30000
    val skyRange = 0 to 9
    val windowSize = (30, 50)
    val windowTitle = "EPIC BLOCK BATTLE"
    val grassRange = skyRange.last + 1 to skyRange.last + 2 
    
    object Color { 
        val black  = new JColor(  0,   0,   0)
        val mole1  = new JColor( 51,  51,   0)
        val mole2  = new JColor( 91,  91,   0)
        val soil   = new JColor(153, 102,  51)
        val tunnel = new JColor(204, 153, 102)
        val grass  = new JColor( 25, 130,  35)
        val sky    = new JColor(140, 190, 255) 
        val worm   = new JColor(255, 150, 150)
    }

    def backgroundColorAtDepth(y: Int): JColor = if(skyRange.contains(y)) Color.sky else if(grassRange.contains(y)) Color.grass else Color.soil
} 
class Game( val leftPlayerName: String  = "LEFT", val rightPlayerName: String = "RIGHT", val wormsAmount: Int = 4) {
    import Game._
    val window = new BlockWindow(windowSize, windowTitle, blockSize)
    val leftMole: Mole  = new Mole(
        leftPlayerName, 
        Pos(1, grassRange.last + 2), 
        (1, 0), 
        Color.mole1, 
        KeyControl("a", "d", "w", "s")
    )
    val rightMole: Mole = new Mole(
        rightPlayerName, 
        Pos(windowSize._1 - 2, windowSize._2 - 2), 
        (-1, 0), 
        Color.mole2,
        KeyControl("Left", "Right", "Up", "Down")
    )
    val worms = Vector.tabulate(wormsAmount)(x => new Worm)

    def drawWorld(): Unit = eraseBlocks(0, 0, windowSize._1, windowSize._2)

    def eraseBlocks(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
        for(y <- y1 to y2){
            for(x <- x1 to x2){
                window.setBlock(Pos(x, y), backgroundColorAtDepth(y))
            }
        }
    }

    def checkNextPos(mole: Mole): Unit = {
        val c = window.getBlock(mole.nextPos) 
        if(c == Color.soil) mole.points += 100
        else if(c == Color.mole1 || c == Color.mole2) if(winner) mole.points += 100000
        worms.foreach(w => {
            if(w.pos == mole.nextPos){
                w.teleport
                mole.points += 1000
            }
        })
    }

    def update(mole: Mole): Unit = {
        checkNextPos(mole)
        if(mole.points > maxScore) quit = true 
        mole.outside
        window.setBlock(mole.nextPos, mole.color)
        window.setBlock(mole.pos, Color.tunnel) 
        mole.move() 
    } 

    def winner: Boolean = if(Math.random > 0.5) true else false

    def hud: Unit = {
        window.setRectangle(Pos(0, 0), Pos(windowSize._1, skyRange.last), Color.sky)
        window.write(s"${leftMole.name}: ${leftMole.points}", Pos(0, 0), JColor.black, blockSize)
        window.write(s"${rightMole.name}: ${rightMole.points}", Pos(0, 3), JColor.black, blockSize)
    }

    def updateThings: Unit = {
        worms.foreach(w => window.setBlock(w.pos, Color.worm))
        update(leftMole) 
        update(rightMole)
    }

    def gameOver: Unit = {
        window.write(
            s"${if(leftMole.points > rightMole.points) leftMole.name else rightMole.name} has won, game over!", 
            Pos(0,skyRange.length / 2), 
            JColor.black, 
            blockSize
        )
    }

    var quit = false
    val delayMillis = 80
    def gameLoop(): Unit = {
        while(!quit) {
            val t0 = System.currentTimeMillis
            handleEvents
            updateThings
            hud
            val elapsedMillis = (System.currentTimeMillis - t0).toInt
            Thread.sleep((delayMillis - elapsedMillis) max 0)
        }
        gameOver
    }
   
    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while(e != BlockWindow.Event.Undefined) {
            e match{
                case BlockWindow.Event.KeyPressed(key) => {
                    if(leftMole.keyControl.has(key)) leftMole.setDir(key) 
                    else if(rightMole.keyControl.has(key)) rightMole.setDir(key)
                }
                case BlockWindow.Event.WindowClosed => quit = true
            }
            e = window.nextEvent()
        }
    }

    def start(): Unit = {
        println("Start digging!")
        println(s"$leftPlayerName ${leftMole.keyControl}")
        println(s"$rightPlayerName ${rightMole.keyControl}")
        worms.foreach(w => w.teleport)
        drawWorld()
        gameLoop()
    }
}