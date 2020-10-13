package cards

case class Hand(cards: Vector[Card]) {
    import Hand._

    def tally: Vector[Int] = {
        val tempArr = Array.fill(14)(0)
        cards.foreach(card => tempArr(card.rank) += 1)
        tempArr.toVector
    }

    def ranksSorted: Vector[Int] = cards.map(_.rank).sorted.toVector

    def isFlush:         Boolean = cards.length > 0 && cards.forall(_.suit == cards(0).suit)
    def isStraight:      Boolean = {
        def isInSeq(xs: Vector[Int]): Boolean = xs.length > 1 && (0 to xs.length - 2).forall(i => xs(i) == xs(i + 1) - 1)
        isInSeq(ranksSorted) ||  // special case with ace interpreted as 14:
        (ranksSorted(0) == 1) && isInSeq(ranksSorted.drop(1) :+ 14)
    }  
    def isRoyal:         Boolean = {
        val temp = cards.map(card => card.rank)
        if(temp.contains(13) && temp.contains(1)) true else false
    }
    def isStraightFlush: Boolean = isStraight && isFlush
    def isFour:          Boolean = tally.contains(4)
    def isFullHouse:     Boolean = tally.contains(3) && tally.contains(2)
    def isThrees:        Boolean = tally.contains(3)
    def isTwoPair:       Boolean = tally.count(_ == 2) == 2
    def isOnePair:       Boolean = tally.contains(2)

    def category: Int = 
        if(isStraight && isFlush && isRoyal) Category.RoyalFlush
        else if(isStraight && isFlush)       Category.StraightFlush
        else if(isFour)                      Category.Fours
        else if(isThrees && isOnePair)       Category.FullHouse
        else if(isFlush)                     Category.Flush
        else if(isStraight)                  Category.Straight
        else if(isThrees)                    Category.Threes            
        else if(isTwoPair)                   Category.TwoPair
        else if(isOnePair)                   Category.OnePair
        else                                 Category.HighCard
}       
object Hand {
    def apply(cardSeq: Card*): Hand = new Hand(cardSeq.toVector)

    def from(deck: Deck): Hand = new Hand(deck.peek(5))

    def removeFrom(deck: Deck): Hand = new Hand(deck.remove(5))

    object Category {
        val RoyalFlush = 0
        val StraightFlush = 1
        val Fours = 2
        val FullHouse = 3 
        val Flush = 4
        val Straight = 5
        val Threes = 6
        val TwoPair = 7
        val OnePair = 8
        val HighCard = 9
        val values = RoyalFlush to HighCard

        object Name {
            val english = Vector("royal flush", "straight flush", "four of a kind", "full house","flush", "straight", "three of a kind", "two pairs", "pair", "high card")
            val swedish = Vector("royal flush", "färgstege", "fyrtal", "kåk", "färg","stege", "tretal", "två par", "par", "högt kort")
        }
    }
}