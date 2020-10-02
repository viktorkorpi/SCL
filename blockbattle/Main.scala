package blockbattle

object Main {
  def main(args: Array[String]): Unit = {
    val game = new Game(
      if(args.length > 0) args(0) else "Left player", 
      if(args.length > 1) args(1) else "Right player",
      if(args.length > 2) args(2).toInt else 4
    )
    game.start
  }
}