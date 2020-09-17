object irritext2{
    var rnd = new scala.util.Random()
    var p = 0
    def main(args: Array[String]): Unit = {
        print("\u001b[2J")
        say("Welcome to PROGRESSION\n(Press ENTER to continue)")
        say("Your goal is to fill the progressbar to 100%")
        progressBar(p, p)
        say("As you can see, it is currently at 0%")
        say("So, how do you fill it you ask?")
        say("Well it is quite simple, here I will give you a hand with the first 10%")
        progressBar(p, p + 10)
        say("See?")
        Thread.sleep(100)
        say("What?")
        say("You do not understand how to fill the bar?")
        say("Come on, just fill it already")
        while(p < 100) fill()
    }

    def fill(): Unit = {
        val in = io.StdIn.readLine()
        if(in.startsWith("+")) progressBar(p, p + 1)
        else if(in.startsWith("*")) progressBar(p, p * 2)
    }

    def boolQ(msg: String, options: String): Boolean = { 
        say(s"$msg ($options)")
        io.StdIn.readLine().toLowerCase.startsWith(s"${options(0)}")
    }

    def say(msg: Any): Unit = { 
        print(msg) 
        while(io.StdIn.readLine() != ""){}
    }

    def progressBar(previous_progress: Int, progress: Int): Unit = {
        for(i <- previous_progress to progress) {
            print(s"\r[Progress][${"■" * i}${"-" * (100 - i)}][$i%]")
            Thread.sleep(75)
        }
        println("")
        p = progress
    }
}