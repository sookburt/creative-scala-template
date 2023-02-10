import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.turtle._
import doodle.turtle.Instruction._
import doodle.image.syntax.all.ImageOps
import doodle.image.syntax.image.ImageOps
import cats.effect.unsafe.implicits.global

object Chapter_12_TurtleGraphicsAlgebra {

  def tinySquareInstructions = {
    val instructions: List[Instruction] =
      List(forward(10), turn(90.degrees),
        forward(10), turn(90.degrees),
        forward(10), turn(90.degrees),
        forward(10))
    instructions
  }

  def polygon(sides: Int, sideLength: Double): Image = {
    val rotation = Angle.one / sides

    def iter(n: Int): List[Instruction] =
      n match {
        case 0 => Nil
        case n => turn(rotation) :: forward(sideLength) :: iter(n - 1)
      }

    Turtle.draw(iter(sides))
  }

  def squareSpiral(steps: Int, distance: Double, angle: Angle, increment: Double): Image = {
    def iter(n: Int, distance: Double): List[Instruction] = {
      n match {
        case 0 => Nil
        case n => forward(distance) :: turn(angle) :: iter(steps - 1, distance + increment)
      }
    }
    Turtle.draw(iter(steps, distance))
  }

  val stepSize = 10
  // stepSize: Int = 10

  val oneBranch = List(
    forward(100),
    branch(turn(45.degrees), forward(stepSize), noop),
    branch(turn(-45.degrees), forward(stepSize), noop))

  val drawOneBranch = Turtle.draw(oneBranch)

  /* L-system.
    - an initial seed to start the growth; and
    - rewrite rules, which specify how the growth occurs.
  */

  def rule(i: Instruction): List[Instruction] =
    i match {
      case Forward(_) => List(forward(stepSize), forward(stepSize))
      case NoOp =>
        List(branch(turn(45.degrees), forward(stepSize), noop),
          branch(turn(-45.degrees), forward(stepSize), noop))
      case other => List(other)
    }

  def rewrite(instructions: List[Instruction], rule: Instruction => List[Instruction]) :List[Instruction] =
    instructions.flatMap { i =>
      i match {
        case Branch(i) =>
          List(branch(rewrite(i, rule): _*))
        case other =>
          rule(other)
      }
    }

  def iterate(steps: Int,
              seed: List[Instruction],
              rule: Instruction => List[Instruction]): List[Instruction] =
    steps match {
      case 0 => seed
      case n => iterate(n - 1, rewrite(seed, rule), rule)
    }

  def polygonWithRange(sides: Int, sideLength: Double): Image =
    {
      val rotation = Angle.one/sides
      Turtle.draw((0 to sides).toList.flatMap{ x => List(turn(rotation), forward(sideLength))})
    }

  def main(args: Array[String]): Unit = {
    // Turtle.draw(tinySquareInstructions).draw()
    // polygon(5, 20.0).draw() // nothing drawn to screen
    // squareSpiral(2, 2, 48.degrees, 10).draw() // stack overflow
    // drawOneBranch.draw()
    Turtle.draw(iterate(5, oneBranch, rule)).draw()
    polygonWithRange(15, 30).draw()
  }
}
