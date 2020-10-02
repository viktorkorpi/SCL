import base._

object ProjectName{
    val window = new Window((1000, 1000), "teeeest")
    def main(args: Array[String]): Unit = {
        //code to execute once
        window.loop()
    }
}

class Window(size: (Int, Int), title: String) extends Base(size, title){
    override def toDoEachLoop(): Unit = {
        //code to execute every loop
    }

    override def handleInput(key: String): Unit = {
        //code to handle input keys
    }
}