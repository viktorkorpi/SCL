object irritext2{
    var rnd = new scala.util.Random()
    var p = 0
    
    def main(args: Array[String]): Unit = {
        sayMult(Array(
            "Welcome to PROGRESSION\n(Press ENTER to continue)", 
            "Your goal is to fill the progressbar to 100%"
        ))

        progressBar(p, p)

        sayMult(Array(
            "\nAs you can see, it is currently at 0%", 
            "So, how do you fill it you ask?", 
            "Well it is quite simple, here I will give you a hand with the first 10%"
        ))

        progressBar(p, p + 10)

        sayMult(Array(
            "\nSee?", 
            "What?", 
            "You do not understand how to fill the bar?", 
            "Just add to or multiply the bar"
        ))

        while(p < 90) fill()

        say("\nHmm, that went to fast let me bring that back down again")

        recessBar(p, 0)

        say("\nLet us do a guessing game instead")

        if(boolQ("You guess my number and I will give you %, sound good?", "y/n")){
            println("Now guess my number!")
            while(!guess(io.StdIn.readLine.toLowerCase)) println("That was wrong, try again")
            progressBar(p, p + 40)

            say("There you go, that was correct, have some progress\nNow guess again :)")
            while(!guess(io.StdIn.readLine.toLowerCase)) println("That was wrong, try again")
            progressBar(p, p + 40)
            say("Another correct answer...")
        }
        else {
            say("Fine, wanna se the progressbar go crazy instead?")
            for(i <- 0 to 1080) crazyBar(Math.abs(Math.sin(i * Math.PI / 180) * 99).toInt)
        }
        say("\nEh, I am bored, you win, bye")
        progressBar(p, 100)
    }

    def guess(input: String): Boolean = if(input.toIntOption.getOrElse(-1) == rnd.nextInt(1000)) true else false   

    def fill(): Unit = {
        val in = io.StdIn.readLine()
        if(in.startsWith("+")) progressBar(p, p + 1)
        else if(in.startsWith("*")) progressBar(p, p * 2)
    }

    def boolQ(msg: String, options: String): Boolean = { 
        println(s"$msg ($options)")
        io.StdIn.readLine().toLowerCase.startsWith(s"${options(0)}")
    }

    def say(msg: Any): Unit = { 
        print(msg) 
        while(io.StdIn.readLine != ""){}
    }

    def sayMult(msgArr: Array[String]): Unit = { 
        msgArr.foreach({ msg =>
            print(msg) 
            while(io.StdIn.readLine != ""){}
        })
    }

    def progressBar(previous_progress: Int, progress: Int): Unit = {
        for(i <- previous_progress to progress) {
            print(s"\r[Progress][${"■" * i}${"-" * (100 - i)}][$i%]")
            Thread.sleep(75)
        }
        p = progress
    }

    def recessBar(previous_progress: Int, progress: Int): Unit = {
        for(i <- progress to previous_progress) {
            print(s"\r[Progress][${"■" * (100 - i)}${"-" * (i)}][${100 - i}%]")
            Thread.sleep(75)
        }
        p = progress
    }

    def crazyBar(progress: Int): Unit = {
        print(s"\r[Progress][${"■" * (progress)}${"-" * (100 - progress)}][${progress}%]")
        Thread.sleep(5)
    }
}