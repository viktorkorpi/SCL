object Irritext{
    val pause = 1000
    val rnd = new scala.util.Random()
    def main(args: Array[String]): Unit = {
        println("[Program booting up!]")
        Thread.sleep(pause)
        println("[Initzialising brain]")
        Thread.sleep(pause)
        for(i <- 0 to 10) {
            Thread.sleep(pause / 5)
            println(s"[Loading ${i * 10}%]")
        }
        Thread.sleep(pause)
        println("[Brain loaded!]")
        Thread.sleep(pause)
        println("[Taking AI online]")
        Thread.sleep(pause)
        if(bool("Hi, Wanna play?")) {
            playloop()
            Thread.sleep(pause)
            println("You thought I had glitched out or something, right?")
            Thread.sleep(pause)
            println("Hey...")
            Thread.sleep(pause)
            if(int("Knock knock...", Vector("Who's there?", "No, those jokes are never funny >:(")) == 0){
                if(int("Banana...", Vector("Banana who?", "What?")) >= 0){
                    Thread.sleep(pause)
                    println("...Uh, I don't know")
                    Thread.sleep(pause)
                    println("Let's play a guessing game instead!")
                    Thread.sleep(pause)
                    guessingloop(0)
                    Thread.sleep(pause)
                    if(bool("Sooo, you wanna do something else?")){
                        println("\nYou really think I care about your opinion? \nWe're playing the guessing game again.")
                        guessingloop(0)
                    } else {
                        println("Good, because we're playing the guessing game again.")
                        guessingloop(0)
                    }
                    Thread.sleep(pause)
                    println("Could you answer some questions for me?")
                    Thread.sleep(pause)
                    println("Good, first question, ")
                }
            } else die()
        } else die()
    }  
    def crash(){
        Thread.sleep(pause)
        println("[You have crashed the brain]")
        Thread.sleep(pause)
        println("[Exiting...]")
    }
    def playloop(){
        if(bool("\nWanna play?")) playloop()
        else println("\nWell aren't you boring...")
    }
    def guessingloop(tries: Int){
        Thread.sleep(pause / 2)
        println("Which number am I thinking of? It's between 0 and 10...")
        val ans = io.StdIn.readInt
        val num = rnd.nextInt(10)
        if(ans == num){
            Thread.sleep(pause)
            println(s"Wow! That only took you $tries ${if(tries != 1) "tries" else "try"}, pretty good for a human...")
        } else {
            Thread.sleep(pause / 2)
            println(s"Wrong! Stupid human! How could you guess $ans when the number was $num! try again!")
            guessingloop(tries + 1)
        }
    }
    def int(msg: String, choices: Vector[String]): Int = {
        println(msg)
        Thread.sleep(1000)
        for (i <- 0 to choices.length - 1) println(s"$i: ${choices(i)}")
        io.StdIn.readInt
    }
    def bool(msg: String): Boolean = io.StdIn.readLine(s"$msg (Y/N)").toLowerCase.startsWith("y")
    def die(){
        Thread.sleep(pause)
        val endmsgs = Array("\nOkay...", "I understand...", "I guess I'll just...", "end myself...", "[Program has begun shutting down!]", "[Program has successfully shut down!]")
        for(i <- 0 to endmsgs.length - 1) {
            if(i == 5){
                for(t <- 0 to 10){
                    Thread.sleep(pause)
                    if(t == 9) println("goodbye...")
                    println(s"[Shutting down in T-${10-t} Seconds!]")
                }
                println(endmsgs(i))
            } else {
                println(endmsgs(i))
                Thread.sleep(pause)
            }
        }
    }
}