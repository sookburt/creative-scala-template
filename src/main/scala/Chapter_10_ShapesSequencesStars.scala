import doodle.core.*
import doodle.image.*
import doodle.image.syntax.all.*
import doodle.image.syntax.core.*
import doodle.java2d.*
import doodle.reactor.*
import doodle.core.PathElement.*
import cats.effect.unsafe.implicits.global
import doodle.core.Color.{paleTurquoise, turquoise}
import doodle.core.Point.polar
import scala.math.BigDecimal

import scala.collection.immutable.Nil.tail

object Chapter_10_ShapesSequencesStars {

  val triangle =
    List(
      lineTo(Point(50, 100)),
      lineTo(Point(100, 0)),
      lineTo(Point(0, 0))
    )
  val curve =
    List(curveTo(Point(50, 100), Point(100, 100), Point(150, 0)))

  def style(image: Image): Image =
    image.strokeWidth(6.0)
      .strokeColor(Color.royalBlue)
      .fillColor(Color.skyBlue)

  val openPaths =
    style(Image.openPath(triangle).beside(Image.openPath(curve)))
  val closedPaths =
    style(Image.closedPath(triangle).beside(Image.closedPath(curve)))
  val paths = openPaths.above(closedPaths)

/*----------------------------------------------------------------------*/

  def curvyPolygons(radius: Int, start: Angle, increment: Angle): PathElement = {
    curveTo(
      polar(radius * .8, start + (increment * .3)),
      polar(radius * 1.2, start + (increment * .6)),
      polar(radius, start + increment)
    )
  }

  val triangle2 = Image.closedPath(List(
    moveTo(polar(50, 0.degrees)),
    curvyPolygons(50, 0.degrees, 120.degrees),
    curvyPolygons(50, 120.degrees, 120.degrees),
    curvyPolygons(50, 240.degrees, 120.degrees)
  ))

  val square = Image.closedPath(List(
    moveTo(polar(50, 45.degrees)),
    curvyPolygons(50, 45.degrees, 90.degrees),
    curvyPolygons(50, 135.degrees, 90.degrees),
    curvyPolygons(50, 225.degrees, 90.degrees),
    curvyPolygons(50, 315.degrees, 90.degrees)
  ))
  val pentagon = Image.closedPath(List(
      moveTo(polar(50, 72.degrees)),
      curvyPolygons(50, 72.degrees, 72.degrees),
      curvyPolygons(50, 144.degrees, 72.degrees),
      curvyPolygons(50, 216.degrees, 72.degrees),
      curvyPolygons(50, 288.degrees, 72.degrees),
      curvyPolygons(50, 360.degrees, 72.degrees)
    ))

    val spacer =
      Image.rectangle(10, 100).noStroke.noFill

    def style2(image: Image): Image =
      image.strokeWidth(6.0).strokeColor(paleTurquoise).fillColor(turquoise)

    val image = style2(triangle2).beside(spacer).beside(style2(square)).beside(spacer).beside(style2(
      pentagon))

  def sayHi(length: Int): List[String] =
    length match {
      case 0 => Nil
      case n => "Hi" :: sayHi(n - 1)
  }

  def ones(n: Int): List[Int] = {
    n match {
      case 0 => Nil
      case m => 1 :: ones(m -1)
    }
  }

  def descending(n: Int): List[Int] = {
    n match {
      case 0 => Nil
      case m => m :: descending(m - 1)
    }
  }

  def ascending(n: Int): List[Int] = {
    def inter(n: Int, counter: Int): List[Int] = {
      n match {
        case 0 => Nil
        case n => counter :: inter(n - 1, counter + 1)
      }
    }
    inter(n, 1)
  }

  def fill[A](n: Int, a: A): List[A] = {
    n match {
      case 0 => Nil
      case n => a :: fill(n - 1, a)
    }
  }

  def double(l: List[Int]): List[Int] = {
    l match {
      case Nil => Nil
      case head :: tail => (head*2) :: double(tail)
    }
  }

  def product(l: List[Int]): Int = {
    l match {
      case Nil => 1
      case head :: tail => head * product(tail)
    }
  }

  def contains[A](list: List[A], item: A): Boolean = {
    list match {
      case Nil => false
      case head :: tail => head == item || contains(tail, item)
    }
  }

  def reverse[A](list: List[A]): List[A] = {
    def inter(list: List[A], reversed: List[A]): List[A] = {
      list match {
        case Nil => reversed
        case head :: tail => inter(tail, head :: reversed)
      }
    }
    inter(list, Nil)
  }

  /****************** map *********************/

  def mapIncrement(list: List[Int]): List[Int] =
    list.map(x => x + 1)

//  def fill[A](n: Int, a: A): List[A] =
//    n match {
//      case 0 => Nil
//      case n => a :: fill(n - 1, a)
//  }

  def fill[A](n: List[Int], a: A): List[A] =
    n.map(x => a)
  fill(List(1,1,1), "Hi")

  /****************** until ******************/

  0 until 10
  // res6: Range = Range(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

  // can increment by something other than 1
  0 until 10 by 2
  // res7: Range = Range(0, 2, 4, 6, 8)

  // can also map over using until
  (0 until 3) map (x => x + 1)
  // res8: collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3)

  // so using the until Range, we can do:
  def fillWithUntil[A](n: Int, a: A): List[A] =
    (0 until n).toList.map(x => a)
  fillWithUntil(3, "Hi")
  // res12: List[String] = List("Hi", "Hi", "Hi")

  // no ranges with doubles
  // 0.0 to 10.0 by 1.0
  // error: No warnings can be incurred under -Xfatal-warnings.

  // Alternatively we can write a Range using BigDecimal
  BigDecimal(0.0) to 10.0 by 1.0
  //BigDecimal has methods doubleValue and intValue to get Double and Int values respectively.
  BigDecimal(10.0).doubleValue()
  // res16: Double = 10.0
  BigDecimal(10.0).intValue()
  // res17: Int = 10

  def mapOnes(n: Int): List[Int] =
    (0 until n).toList.map(x => 1)
  mapOnes(3)
  // res20: List[Int] = List(1, 1, 1)

  def mapDescending(n: Int): List[Int] =
    (n until 0 by -1).toList
  mapDescending(0)
  // res24: List[Int] = List()
  mapDescending(3)
  // res25: List[Int] = List(3, 2, 1)

  def mapAscending(n: Int): List[Int] =
    (0 until n).toList.map(x => x + 1)
  mapAscending(0)
  // res29: List[Int] = List()
  mapAscending(3)
  // res30: List[Int] = List(1, 2, 3)

  def mapDouble(list: List[Int]): List[Int] =
    list map (x => x * 2)
  mapDouble(List(1, 2, 3))
  // res34: List[Int] = List(2, 4, 6)
  mapDouble(List(4, 9, 16))
  // res35: List[Int] = List(8, 18, 32)

  /***************** to ******************/

  // Another method inclusive of the end of the range:
  1 until 5
  // res37: Range = Range(1, 2, 3, 4)
  1 to 5
  // res38: Range.Inclusive = Range.Inclusive(1, 2, 3, 4, 5)

  // the Range constructed with `until` is a half-open interval, while the range constructed with
  // `to` is an open interval.

  /***************** polygons and stars ******************/

  def polygon(sides: Int, size: Int, initialRotation: Angle): Image = {
    import Point._
    import PathElement._
    val step = (Angle.one / sides).toDegrees.toInt
    val path =
      (0 to 360 by step).toList.map { deg =>
        lineTo(polar(size, initialRotation + deg.degrees))
      }
    Image.closedPath(moveTo(polar(size, initialRotation)) :: path)
  }

  def style(img: Image, hue: Angle): Image = {
    img.
      strokeColor(Color.hsl(hue, 1.0, 0.25)).
      fillColor(Color.hsl(hue, 1.0, 0.75))
  }

  def allAbove(imgs: List[Image]): Image =
    imgs match {
      case Nil => Image.empty
      case hd :: tl => hd above allAbove(tl)
  }

  def star(p: Int, n: Int, radius: Double): Image =
    ???


  def main(args: Array[String]): Unit = {

    //paths.draw()
    //image.draw()
    //println(sayHi(5))
    println(ones(5))
    println(descending(5))
    println(ascending(5))
    println(fill(5, "Hi!"))
    println(fill(2, Color.darkRed))
    println(double(List(1,2,3)))
    println(product(List(1,2,3)))
    println(contains(List(1,2,3), 3))
    println(contains(List(1,2,3), 0))
    println(reverse(List(1, 2, 3)))
    println(reverse(List("one", "two", "three")))
    println(mapIncrement(List(1,2,3)))
    polygon(15, 60, 45.degrees).draw()


  }
}
