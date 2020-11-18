package snake

class TwoPlayerGame extends SnakeGame("Two Player Snake"){
  def isGameOver(): Boolean = ???
  // ormar och ev. äpple, bananer etc

  def play(playerNames: String*): Unit = ???  // ska överskugga play i SnakeGame
}