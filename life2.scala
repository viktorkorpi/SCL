import base._

object Life2 {
    val window = new WindowLife((400, 400), "Life")
    def main(args: Array[String]): Unit = {
        draw()
        window.loop()
    }

    var block_size = 5
    var rnd = new scala.util.Random()
    var grid = Array.tabulate(window.w.width / block_size)(n => Array.tabulate(window.w.height / block_size)(n => rnd.nextInt(2)))
    var next = emptyArr()

    def emptyArr(): Array[Array[Int]] = {
        Array.fill(window.w.width / block_size)(Array.fill(window.w.height / block_size)(0))
    }

    def draw(): Unit = {
        for(x <- 0 to grid.length - 1){
            for(y <- 0 to grid(x).length - 1){
                if(grid(x)(y) == 1) window.w.fill(x * block_size, y * block_size, block_size, block_size, java.awt.Color.black)
                else window.w.fill(x * block_size, y * block_size, block_size, block_size, java.awt.Color.white)  
            }
        }
    }

    def calc(): Unit = {
        for(x <- 0 to grid.length - 1){
            for(y <- 0 to grid(x).length - 1){
                var points = 0
                for(cy <- -1 to 1){
                    for(cx <- -1 to 1){
                        if(!(cx == 0 && cy == 0)){
                            var nx = (x + cx + (window.w.width / block_size)) % (window.w.width / block_size)
                            var ny = (y + cy + (window.w.height / block_size)) % (window.w.height / block_size)
                            if(grid(nx)(ny) == 1) points += 1
                        }
                    }
                }
                if((points == 3 || points == 2 ) && grid(x)(y) == 1) next(x)(y) = 1
                else if(points == 3 && grid(x)(y) == 0) next(x)(y) = 1
                else next(x)(y) = 0
            }
        }
        grid = next
    }
}

class WindowLife(size: (Int, Int), title: String) extends Base(size, title){
    override def toDoEachLoop(): Unit = {
        Life2.next = Life2.emptyArr()
        Life2.calc()
        Life2.draw()
    }

    override def handleInput(key: String): Unit = {
        //code to handle input keys
    }
}