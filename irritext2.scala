object irritext2{
    var rnd = new scala.util.Random()
    def main(args: Array[String]): Unit = {
        for(i <- 0 to 100) {
            print(s"\r[Loading][${"■" * i + "-" * (100 - i)}][$i%]")
            Thread.sleep(100)
        }
        say("")
        say("*Welcome Recruit, please report to the bridge immediately!*")
        say("You are in a filthy grey room")
        say("You are laying in a gray metall bed")
        say("It's cold and quiet")
        if(booleanQuestion("Do you want to get up?", "y/n")){
            say("You get up and look around")
            say("There is another bed, some random items and a large metall door")
            say("You walk over to the door")
            say("There is a code lock on the door, you will have to guess the code")
            codeLock()
            say("The door opens, it was never locked...")
            say("Outside the door you find a long dark hallway")
            say("Down the left of the hallway there is a turn and you can see some light, to the right there is only darkness...")
            if(booleanQuestion("Which way do you want to go?", "l/r")){
                
            }
        } else say("You fall back to sleep")
    }

    def codeLock(): Unit = {
        val input = io.StdIn.readLine.toLowerCase()
        if(input.startsWith("o")) say("You try to open the door")
        else if(input.toIntOption.getOrElse(0) == rnd.nextInt(10000)) {
            say("The lamp on the lock turned green but the there is no sound from the door")
            say("You try to open the door")
        }
        else {
            say("The locks lamp blinks red, you will have to guess again")
            codeLock()
        }
    }

    def booleanQuestion(msg: String, options: String): Boolean = {
        say(s"$msg ($options)")
        io.StdIn.readLine().toLowerCase.startsWith("y")
    }

    def say(msg: Any): Unit = { Thread.sleep(1500); println(msg) }
}