package snake
import introprog.BlockGame

abstract class SnakeGame(title: String) extends BlockGame(
  title, dim = (50, 30), blockSize = 15, background = Colors.Background,
  framesPerSecond = 50, messageAreaHeight = 3
) {
  var entities: Vector[Entity] = Vector.empty

  var players: Vector[Player] = Vector.empty

  sealed trait State
  case object Starting extends State
  case object Playing  extends State
  case object GameOver extends State
  case object Quitting extends State

  var state: State = Starting

  def enterStartingState(): Unit = {
    println("Starting!\nTryck space för start!")
    clearWindow()
    state = Starting
  }

  def enterPlayingState(): Unit = {
    println("Playing!")
    clearWindow()
    entities.foreach(_.draw)
    state = Playing
  }

  def enterGameOverState(): Unit = {
    println("Game Over!")
    state = GameOver
  }

  def enterQuittingState(): Unit = {
    println("Goodbye!")
    pixelWindow.hide()
    state = Quitting
  }

  def randomFreePos(): Pos = {
    var pos = Pos.random(Dim(dim))
    var occupied = true
    while(occupied){
      pos = Pos.random(Dim(dim))
      occupied = entities.forall(!_.isOccupyingBlockAt(pos))
    }
    pos
  }

  override def onKeyDown(key: String): Unit = {
    println(s"""key "$key" pressed""")
    state match {
      case Starting => if (key == " ") enterPlayingState()
      case Playing => players.foreach(_.handleKey(key))
      case GameOver =>
        if (key == " ") enterPlayingState()
        else if(key == "Escape") enterQuittingState()
      case _ =>
    }
  }

  override def onClose(): Unit = enterQuittingState()

  def isGameOver(): Boolean

  override def gameLoopAction(): Unit = ??? //Vad som ska hända varje gång skärmen uppdateras

  def startGameLoop(): Unit = {
    pixelWindow.show()
    enterStartingState()
    gameLoop(stopWhen = state == Quitting)
  }

  def play(playerNames: String*): Unit
}