package life

case class Life(cells: Matrix[Boolean]) {
    def apply(row: Int, col: Int): Boolean = if(
        row < Main.dim._1 && 
        row >= 0 && 
        col < Main.dim._2 && 
        col >= 0 && 
        cells(row, col)
    ) true else false
    def updated(row: Int, col: Int, value: Boolean): Life = Life(cells.updated(row, col)(value))
    def toggled(row: Int, col: Int): Life = Life(cells.updated(row, col)(!apply(row, col))) 
    def nbrOfNeighbours(row: Int, col: Int): Int = {
        var neighbours = 0
        for(cy <- -1 to 1){
            for(cx <- -1 to 1){
                if(!(cx == 0 && cy == 0)) {
                    if(apply(row + cx, col + cy)) neighbours += 1
                }
            }
        }
        neighbours
    }
    def evolved(rule: (Int, Int, Life) => Boolean = Life.defaultRule): Life = {
        var nextGeneration = Life.empty(cells.dim)
        cells.foreachIndex( (r, c) => {
            nextGeneration.updated(r, c, rule(r, c, this))
        })
        nextGeneration
    }
    /**Radseparerad text där 0 är levande cell och - är död cell.*/
    override def toString = ???
} 
object Life {
    def empty(dim: (Int, Int)): Life = Life(Matrix(Vector.fill(dim._1)(Vector.fill(dim._2)(false))))
    def random(dim: (Int, Int)): Life = Life(Matrix(Vector.fill(dim._1)(Vector.tabulate(dim._2)(x => if(Math.random > 0.7) true else false))))
    def defaultRule(row: Int, col: Int, current: Life): Boolean = {
        var neighbours = current.nbrOfNeighbours(row, col)
        if((neighbours == 3 || neighbours == 2 ) && current.cells(row, col)) true
        else if(neighbours == 3 && !current.cells(row, col)) true
        else false
    }
}
