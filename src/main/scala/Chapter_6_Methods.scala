import doodle.core._
import doodle.image._
import doodle.image.syntax.all._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import scala.concurrent.duration._
import cats.effect.unsafe.implicits.global
import scala.collection.mutable.ArrayBuffer

object Chapter_6_Methods {

  case class HistogramBar(val delivered: Int, val numRecipients:Int) {}

  def barCreator(record: HistogramBar):Image = {
    Image.rectangle(10, record.delivered).fillColor(Color.blue.spin((record.numRecipients/1000).degrees))
  }

  def setupData(): ArrayBuffer[HistogramBar] = {

    val data = new ArrayBuffer[HistogramBar]()

    data += HistogramBar(63.28.toInt, 1892051)
    data += HistogramBar(85.67.toInt, 881381)
    data += HistogramBar(95.31.toInt, 288286)
    data += HistogramBar(94.15.toInt, 886214)
    data += HistogramBar(94.29.toInt, 558151)
    data += HistogramBar(79.6.toInt, 888289)
    data += HistogramBar(90.88.toInt, 542379)
    data += HistogramBar(88.78.toInt, 895883)
    data += HistogramBar(73.66.toInt, 556439)
    data += HistogramBar(59.88.toInt, 1908051)

    data
  }

  def main(args: Array[String]): Unit = {

    var graph: Image = Image.rectangle(1,1)
    val data = setupData()

    for (i:HistogramBar <- data)
      graph = graph.beside(barCreator(i))
      graph = graph.beside(Image.rectangle(1,1))

    val output = graph.above(Image.rectangle(220,2).fillColor(Color.black))
    output.draw(); // TODO: get it laying on the baseline somehow.
  }
}

