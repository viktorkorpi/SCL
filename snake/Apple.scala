package snake

class Apple extends CanTeleport {
  def draw(): Unit = ???
  def erase(): Unit = ???
  def move(): Unit = ???
  def game: SnakeGame = ???
  def teleportAfterSteps: Int = ???
  def isOccupyingBlockAt(p: Pos): Boolean = ???
}