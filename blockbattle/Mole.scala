package blockbattle

class Mole(val name: String, var pos: Pos, var dir: (Int, Int), val color: java.awt.Color, val keyControl: KeyControl){
    var points = 0
    override def toString = s"Mole[name=$name, pos=$pos, dir=$dir, points=$points]"
    def setDir(key: String): Unit = dir = keyControl.direction(key)
    def reverseDir(): Unit = dir = (dir._1 * -1, dir._2 * -1)
    def move(): Unit = pos = nextPos
    def nextPos: Pos = pos.moved(dir)
    def outside: Unit = {
        if( pos.x == Game.windowSize._1 || 
            pos.y == Game.windowSize._2 || 
            pos.x == -1                 || 
            pos.y == Game.grassRange.start
        ) reverseDir
    }
}