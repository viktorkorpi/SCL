package life

case class Matrix[T](data: Vector[Vector[T]]) {
    val dim: (Int, Int) = Main.dim
    require(data.forall(row => row.length == dim._2))
    def apply(row: Int, col: Int): T = data(row)(col)
    def updated(row: Int, col: Int)(value: T): Matrix[T] = Matrix(data.updated(row, data(row).updated(col, value)))
    def foreachIndex(f: (Int, Int) => Unit): Unit = for(row <- 0 until dim._1) for(col <- 0 until dim._2) f(row, col)
    override def toString = ???
}
object Matrix {
    def fill[T](dim: (Int, Int))(value: T): Matrix[T] = Matrix[T](Vector.fill(dim._1, dim._2)(value))
}