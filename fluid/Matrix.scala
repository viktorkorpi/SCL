package fluid

case class Matrix[T](data: Vector[Vector[T]]) {
    require(data.forall(row => row.length == Main.dim._2))
    def apply(row: Int, col: Int): T = data(row)(col)
    def updated(row: Int, col: Int)(value: T): Matrix[T] = Matrix(data.updated(row, data(row).updated(col, value)))
    def foreachIndex(f: (Int, Int) => Unit): Unit = for(r <- 0 until Main.dim._1; c <- 0 until Main.dim._2) f(r, c)
}
object Matrix {
    def fill[T](dim: (Int, Int))(value: T): Matrix[T] = Matrix[T](Vector.fill(dim._1, dim._2)(value))
}
