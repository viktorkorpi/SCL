object irritext2{
    def main(args: Array[String]): Unit = {
        if(hiddenChoice("Vill du gå höger eller vänster?") == 2) println("där är en dörr")
    }

    def hiddenChoice(msg: String): Int = {
        println(msg)
        val input = io.StdIn.readLine.toLowerCase()
        if(input.startsWith("f")){
            println("du gick fram")
            1
        }
        else if(input.startsWith("h")){
            println("du gick höger")
            2
        }
        else{
            println("du gick vänster")
            3
        }
    }
}