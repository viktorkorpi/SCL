package life

case class Life(cells: Matrix[Boolean]) {
    def apply(row: Int, col: Int): Boolean = {
        if(row < Main.dim._1 && row >= 0 && col < Main.dim._2 && col >= 0){
            cells(row, col)
        } else false
    }
    def updated(row: Int, col: Int, value: Boolean): Life = Life(cells.updated(row, col)(value))
    def toggled(row: Int, col: Int): Life = Life(cells.updated(row, col)(!cells(row, col))) 
    def nbrOfNeighbours(row: Int, col: Int): Int = {
        var neighbours = 0
        for(cy <- -1 to 1; cx <- -1 to 1){
            if(!(cx == 0 && cy == 0)) {
                if(this(row + cx, col + cy)) neighbours += 1
            }
        }
        neighbours
    }
    def evolved(rule: (Int, Int, Life) => Boolean = Life.defaultRule): Life = {
        var nextGeneration = Life.empty(cells.dim)
        cells.foreachIndex((r, c) => nextGeneration = nextGeneration.updated(r, c, rule(r, c, this)))
        nextGeneration
    }
    override def toString = cells.toString
} 
object Life {
    def empty(dim: (Int, Int)): Life = Life(Matrix.fill(dim)(false))
    def full(dim: (Int, Int)): Life = Life(Matrix.fill(dim)(true))
    def random(dim: (Int, Int)): Life = Life(Matrix(Vector.fill(dim._1, dim._2)(Math.random > 0.7)))
    def defaultRule(row: Int, col: Int, current: Life): Boolean = {
        var neighbours = current.nbrOfNeighbours(row, col)
        if((neighbours == 3 || neighbours == 2 ) && current.cells(row, col)) true
        else if(neighbours == 3 && !current.cells(row, col)) true
        else false
    }
}
