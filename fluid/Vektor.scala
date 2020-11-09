package fluid

case class Vektor(var x: Double, var y: Double){
    def +(other: Vektor): Vektor = Vektor(x + other.x, y + other.y)
    def *(other: Double): Vektor = Vektor(x * other, y * other)
}
