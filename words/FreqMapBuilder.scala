package nlp

class FreqMapBuilder {
  private val register = scala.collection.mutable.Map.empty[String, Int]
  def toMap: Map[String, Int] = register.toMap
  def add(s: String): Unit = if(register.contains(s)) register(s) += 1 else register += s -> 1
}
object FreqMapBuilder {
  def apply(xs: String*): FreqMapBuilder = {
    val freqMap = new FreqMapBuilder
    xs.foreach(word => freqMap.add(word))
    freqMap
  }
}