package cards

class Deck private(val initCards: Vector[Card]){
    private var cards: Array[Card] = initCards.toArray

    private var rnd = new scala.util.Random

    def reset(): Unit = cards = initCards.toArray

    def apply(i: Int): Card = cards(i)

    def toVector: Vector[Card] = cards.toVector

    override def toString: String = cards.mkString(" ")

    def peek(n: Int): Vector[Card] = cards.take(n).toVector

    def remove(n: Int): Vector[Card] = {
        val init = peek(n)
        cards = cards.drop(n)
        init
    }

    def prepend(moreCards: Card*): Unit = cards = moreCards.toArray ++ cards

    def swap(a: Int, b: Int): Unit = {
        val temp = cards(a)
        cards(a) = cards(b)
        cards(b) = temp
    }

    def shuffle(): Unit = for(i <- cards.length - 1 to 0 by -1) swap(i, rnd.nextInt(i + 1))
} 
object Deck {
    def empty: Deck = new Deck(Vector())

    def apply(cards: Seq[Card]): Deck = new Deck(cards.toVector)

    def full(): Deck = {
        var tempSeq = new Array[Card](52) 
        for(suit <- Card.suitRange) for(rank <- Card.rankRange) tempSeq(((suit - 1) * Card.rankRange.last) + rank - 1) = Card(rank, suit)
        apply(tempSeq)
    }
}