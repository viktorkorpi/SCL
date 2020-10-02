package base

class Base(val size: (Int, Int) = (400, 400), val title: String = "Window"){
    val window = new Window(size, title)
    var quit = false
    val delayMillis = 80
    def loop(): Unit = {
        while(!quit) {
            val t0 = System.currentTimeMillis
            handleEvents
            toDoEachLoop
            val elapsedMillis = (System.currentTimeMillis - t0).toInt
            Thread.sleep((delayMillis - elapsedMillis) max 0)
        }
    }
  
    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while(e != Window.Event.Undefined) {
            e match{
                case Window.Event.KeyPressed(key) => handleInput(key)
                case Window.Event.WindowClosed => quit = true
            }
            e = window.nextEvent()
        }
    }   

    def handleInput(key: String): Unit = {}

    def toDoEachLoop(): Unit = {}
} 

import introprog.PixelWindow
class Window(val size: (Int, Int), val title: String) {
    val w = new PixelWindow(size._1, size._2, title)
    def nextEvent(maxWaitMillis: Int = 10): Window.Event.EventType  = {
        import Window.Event._
        w.awaitEvent(maxWaitMillis)
        w.lastEventType
        match {
            case PixelWindow.Event.KeyPressed => KeyPressed(w.lastKey)
            case PixelWindow.Event.WindowClosed => WindowClosed
            case _ => Undefined
        }
    }
} 
object Window {
    def delay(millis: Int): Unit = Thread.sleep(millis)
    object Event {
        trait EventType
        case class KeyPressed(key: String) extends EventType 
        case object WindowClosed extends EventType
        case object Undefined extends EventType
    }
}