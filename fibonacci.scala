object Fibonacci{
  def main(args: Array[String]): Unit = {
    var x = 1
    var px = 0
    for (i <- 0 to 50){
      var tx = x
      x += px
      px = tx
      println(s"$i $x")
    }
  }
}