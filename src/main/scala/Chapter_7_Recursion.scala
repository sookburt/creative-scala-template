import doodle.core.*
import doodle.image.*
import doodle.image.syntax.all.*
import doodle.image.syntax.core.*
import doodle.java2d.*
import doodle.reactor.*
import cats.effect.unsafe.implicits.global

import java.awt

object Chapter_7_Recursion {

  val aBox = Image.square(20).fillColor(Color.royalBlue)

  val aStar = Image.star(5,20, 10).strokeColor(Color.royalBlue)

  // the long winded way.
  val oneBox = aBox
  val twoBoxes = aBox.beside(oneBox)
  val threeBoxes = aBox.beside(twoBoxes)

  // recursion
  def boxes(count: Int):Image = {
    count match {
      case 0 => Image.empty
      case n => aBox.beside(boxes(n - 1))
    }
  }

  def stackedBoxes(count: Int):Image = {
    count match {
      case 0 => Image.empty
      case n => aBox.above(stackedBoxes(n - 1))
    }
  }

  def failingMatch(count: Int): String = {
    count match {
      case 0 => "one"
      case 1 => "two"
      case 2 => "three"
      case 3 => "four"
    }
  }

  def alternatingRow(count: Int):Image = {

    if(count == 0) Image.empty
    else if (count % 2 == 0) aBox.beside(alternatingRow(count - 1))
    else aStar.beside(alternatingRow(count - 1))
  }

  def funRow(count: Int): Image = {
    count match {
      case 0 => Image.empty
      case n => Image.star((5*count),(20*count), (10*count)).strokeColor(Color.royalBlue.spin((count*40).degrees)).beside(funRow(n - 1))
    }
  }

  def cross(count: Int): Image = {
    val target = Image.circle(5).noStroke.fillColor(Color.yellow).on(Image.circle(15).noStroke.fillColor(Color.green).on(Image.circle(25).noStroke.fillColor(Color.blue)))
    count match {
      case 0 => Image.regularPolygon(6, 10.0).strokeColor(Color.red).strokeWidth(5)
      case n => {
        val unit = cross(n - 1)
        target.above(((target.beside(unit.beside(target)))).above(target))
      }
    }
  }

  def main(args: Array[String]): Unit = {
    //threeBoxes.draw()
    //boxes(10).draw()
    //stackedBoxes(4).draw()
    //alternatingRow(5).draw()
    //println(failingMatch(3))
    //funRow(5).draw()
    cross(2).draw()
  }
}
