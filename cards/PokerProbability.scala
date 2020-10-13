package cards

object PokerProbability {
    def register(n: Int, deck: Deck): Vector[Int] = {
        val tempArr = Array.fill(n)(0)
        for(i <- 0 until n) {
            tempArr(i) = newHand(deck).category
            progressBar(((i / n) * 100).toInt)
        }
        count(tempArr)
    }

    def newHand(deck: Deck): Hand = {
        deck.shuffle
        Hand.from(deck)
    }

    def progressBar(progress: Int): Unit = print(s"\r[Progress][${"■" * progress}${"-" * (100 - progress)}][$progress%]")

    def count(arr: Array[Int]): Vector[Int] = Array.range(0, 10).distinct.map(x => arr.count(_== x)).toVector

    def main(args: Array[String]): Unit = { 
        val n = scala.io.StdIn.readLine("number of iterations: ").toInt
        val deck = Deck.full() 
        progressBar(0)
        val frequencies = register(n, deck)

        println("")
        for(i <- Hand.Category.values) {
            val name = Hand.Category.Name.english(i).capitalize
            val percentages = frequencies(i).toDouble / n * 100
            println(f"$name%16s $percentages%10.6f%%")
        }
    }
}