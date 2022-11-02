import doodle.core._
import doodle.image._
import doodle.image.syntax.all._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import cats.effect.unsafe.implicits.global

object Chapter_9_HigherOrderFunctions {

  val dot = Image.circle(5).strokeWidth(3).strokeColor(Color.crimson)
  val squareDots =
    dot.at(0,0)
      .on(dot.at(0, 100))
      .on(dot.at(100, 100))
      .on(dot.at(100, 0))

  val hexagon = Image.regularPolygon(5, 40)

  def originAndBoundingBox: Image = {
    val c = Image.circle(40)
    val c1 = c.beside(c.at(10,10)).beside(c.at(10, -10)).debug
    val c2 = c.debug.beside(c.at(10,10).debug).beside(c.at(10, -10).debug)
    val c3 = c.debug.beside(c.debug.at(10, 10)).beside(c.debug.at(10, -10))
    c1.above(c2).above(c3)
  }

  // function literal fun:
  val squaresInput = (x: Int) => x * x
  // (x: Int) => x * x

  val spin15 = (c: Color) => c.spin(15.degrees)

  val dupX4AndRotate90 = (i: Image) => i.beside(i.rotate(90.degrees))
    .beside(i.rotate(180.degrees))
    .beside(i.rotate(270.degrees))

  val convertAngleToPoint = (angle: Angle) =>
    Point.cartesian((angle * 7).cos * angle.cos, (angle * 7).cos * angle.sin)

  def main(args: Array[String]): Unit = {
    
    //squareDots.draw()
    //originAndBoundingBox.draw()
    //dupX4AndRotate90(hexagon).draw()


  }
}
