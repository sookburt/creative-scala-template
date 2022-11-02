import doodle.core._
import doodle.image._
import doodle.image.syntax.all._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import cats.effect.unsafe.implicits.global

object Chapter_8_Fractals {

  /**
   * Moving basecase into it's own method so that we only create it once, not each recursion.
   * @param count
   * @return
   */
  def chessboard(count: Int): Image = {
    val baseCase = {
      println("printing chessboard basecase.")
      val blackSquare = Image.rectangle(10, 10).fillColor(Color.black)
      val redSquare = Image.rectangle(10, 10).fillColor(Color.red)
      (redSquare.beside(blackSquare)).above(blackSquare.beside(redSquare))
    }

    def loop(count: Int): Image = {
      count match {
        case 0 => baseCase
        case n => {
          val unit = loop(n - 1)
          (unit.beside(unit).above(unit.beside(unit)))
        }
      }
    }

    loop(count)
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

  def growingBoxes(count: Int, size: Int): Image = {
    count match {
      case 0 => Image.empty
      case n => Image.square(size).noStroke.fillColor(Color.blue.spin((n*40).degrees)).beside(growingBoxes(n-1, size+10));
    }
  }

  def colorChangeBoxes(count: Int, color: Int): Image = {
    count match {
      case 0 => Image.empty
      case n => (Image.square(50)
                  .strokeColor(Color.blue.spin((color+10).degrees))
                  .strokeWidth(10)
                  .fillColor(Color.blue.spin((color-10).degrees)))
                  .beside(colorChangeBoxes(n-1, color+10))
    }
  }

  def circle(size: Int, color: Color): Image = {
    Image.circle(size).strokeWidth(3.0).strokeColor(color).noFill
  }
  def plainConcentricCircles(count: Int, size: Int, color: Color): Image = {
    count match {
      case 0 => Image.empty
      case n => plainConcentricCircles(n-1, size+15, color)
                  .on(circle(size, color))
    }
  }
  def rainbowConcentricCircles(count: Int, size: Int, color: Color): Image = {
    count match {
      case 0 => Image.empty
      case n => {
        rainbowConcentricCircles(n - 1, size + 15, color.spin((n + 10).degrees))
          .on(circle(size, color))
      }
    }
  }

  def fadeConcentricCircles(count: Int, size: Int, color: Color): Image = {
    count match {
      case 0 => Image.empty
      case n => fadeConcentricCircles(n-1, size + 15, color.spin((n + 8).degrees).fadeOutBy(0.05.normalized))
                .on(circle(size, color))
    }
  }

  def main(args: Array[String]): Unit = {

    //fadeConcentricCircles(20, 100, Color.mediumBlue).draw()
    //rainbowConcentricCircles(20, 100, Color.mediumBlue).draw()
    //plainConcentricCircles(20, 100, Color.mediumBlue).draw()
    //colorChangeBoxes(5, 10).draw()
    //growingBoxes(7, 10).draw()
    //sierpinski(3).draw()
    chessboard(0).draw() 

  }
}
