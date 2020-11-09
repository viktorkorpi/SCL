package fluid

case class Grid(data: Array[Array[Vektor]]){
    def apply(x: Int, y: Int): Vektor = data(x)(y)
}
object Grid{
    def fill[T](dim: (Int, Int))(value: Vektor): Grid = Grid(Array.fill(dim._1, dim._2)(value))
}