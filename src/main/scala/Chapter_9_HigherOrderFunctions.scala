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

  val parametricCircle = (angle: Angle) => Point.polar(200, angle)

  val triangle = Image
    .triangle(10, 10)
    .fillColor(Color.limeGreen)
    .strokeColor(Color.lawngreen)

  def spiral(samples: Int, curve: Angle => Image): Image = {
    val step = Angle.one / samples

    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n => curve(angle).on(loop(n - 1))
      }
    }
    loop (samples)
  }
  def sample(samples: Int, dot: Image, curve: Angle => Point): Image = {
    // Angle.one is one complete turn. I.e. 360 degrees
    val step = Angle.one / samples

    def loop(count: Int): Image = {
      val angle = step * count
      count match {
        case 0 => Image.empty
        case n =>
          dot.at(curve(angle)).on(loop(n - 1))
      }
    }
    loop(samples)
  }

  /* shapes ------------------------- */
  def concentricShapes(count: Int, singleShape: Int => Image): Image =
    count match {
      case 0 => Image.empty
      case n => singleShape(n).on(concentricShapes(n - 1, singleShape))
    }

  def rainbowCircle(n: Int) = {
    val color = Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)
    val shape = Image.circle(50 + n * 12)
    shape.strokeWidth(10).strokeColor(color)
  }

  def fadingTriangle(n: Int) = {
    val color = Color.blue.fadeOut((1 - n / 20.0).normalized)
    val shape = Image.triangle(100 + n * 24, 100 + n * 24)
    shape.strokeWidth(10).strokeColor(color)
  }

  def rainbowSquare(n: Int) = {
    val color = Color.blue.desaturate(0.5.normalized).spin((n * 30).degrees)
    val shape = Image.rectangle(100 + n * 24, 100 + n * 24)
    shape.strokeWidth(10).strokeColor(color)
  }

  val answer =
    concentricShapes(10, rainbowCircle)
      .beside(
        concentricShapes(10, fadingTriangle)
          .beside(concentricShapes(10, rainbowSquare))
      )
  /* curves ------------------------ */
  def rose(k: Int): Angle => Point =
    (angle: Angle) => Point((angle * k).cos * 200, angle)

  def lissajous(a: Int, b: Int, offset: Angle): Angle => Point =
    (angle: Angle) =>
      Point(100 * ((angle * a) + offset).sin, 100 * (angle * b).sin)
  def epicycloid(a: Int, b: Int, c: Int): Angle => Point =
    (angle: Angle) =>
      (Point(75, angle * a).toVec + Point(32, angle * b).toVec + Point(15, angle * c).toVec).toPoint

  /* ----------------------------- */

  def main(args: Array[String]): Unit = {
    
    //squareDots.draw()

    //originAndBoundingBox.draw()

    //dupX4AndRotate90(hexagon).draw()

    //spiral(25, (angle: Angle) =>
      //Image.triangle(10, 10).at(Point((Math.exp(angle.toTurns) - 1) * 200, angle))).draw()

    //sample(90, triangle, parametricCircle).draw()
    sample(90, triangle, rose(5)).draw()
    answer.draw()

    //println("x="+(45.degrees.cos * 1.0))
    //println("y="+(45.degrees.sin * 1.0))

  }
}
