import doodle.core._
import doodle.image._
import doodle.image.syntax.all._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import cats.effect.unsafe.implicits.global

object Chapter_8_Fractals {

  // Fractals.

  def chessboard(count: Int): Image = {
    val blackSquare = Image.rectangle(10, 10).fillColor(Color.black)
    val redSquare = Image.rectangle(10, 10).fillColor(Color.red)

    count match {
      case 0 => (redSquare.beside(blackSquare)).above(blackSquare.beside(redSquare))
      case n => {
        val unit = chessboard(n - 1)
        (unit.beside(unit).above(unit.beside(unit)))
      }
    }
  }

  def sierpinski(count: Int): Image = {
    val triangle = Image.triangle(10, 10).strokeColor(Color.blue.spin((count*40).degrees))
    count match {
      case 0 => triangle.above(triangle.beside(triangle))
      case n => {
        val unit = sierpinski(n - 1)
        unit.above(unit.beside(unit))
      }
    }
  }

  def main(args: Array[String]): Unit = {

    sierpinski(3).draw()
    //chessboard(0).draw() // Not sure the base finishes properly

  }
}
