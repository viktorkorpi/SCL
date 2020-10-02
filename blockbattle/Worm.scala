package blockbattle

class Worm(var pos: Pos = Pos(0, 0)){
    val rnd = new scala.util.Random()
    def teleport: Unit = pos = Pos(
        rnd.nextInt(Game.windowSize._1), 
        Game.grassRange.last + rnd.nextInt(Game.windowSize._2 - Game.grassRange.last)
    )
}