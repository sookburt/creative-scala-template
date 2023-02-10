import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.image.syntax.all.ImageOps
import cats.effect.unsafe.implicits.global
import doodle.random._

object Chapter_13_Composition_of_Generative_Art {

  def concentricCircles(n: Int, size: Int, color: Color): Image =
    n match {
      case 0 => Image.circle(size).fillColor(color)
      case n => Image.circle(size).fillColor(color).on(concentricCircles(n-1, size + 15, color.spin(15.degrees)))
    }

  val randomDouble: Random[Double] = Random.double

  val randomAngle: Random[Angle] = Random.double.map(x => x.turns)

  def randomColor(s: Double, l: Double): Random[Color] =
    randomAngle.map(hue => Color.hsl(hue, s, l)) // not pure tho!

  def randomCircle(r: Double, color: Random[Color]): Random[Image] =
    color.map(fill => Image.circle(r).fillColor(fill))

  val randomPastel = randomColor(0.7,0.7)

  // Substitution: Random[Image] flatMap (Random[Image] map (Image => Image))
  // F[A] map (A => B) = F[B] - type equations for map
  // F[A] flatMap (A => F[B]) = F[B] - type equation for flatmap
  // Random[Image] flatMap (Random[Image]) - type equation for map
  // Random[Image] - apply the equation for flatMap yielded
  def randomConcentricCircles(count: Int, size: Int): Random[Image] =
    count match {
      case 0 => Random.always(Image.empty)
      case n => randomCircle(size, randomPastel).flatMap { circle =>
        randomConcentricCircles(n - 1, size + 15).map { circles =>
          circle on circles
        }
      }
    }

  val circles = randomConcentricCircles(5, 10)

  val programOne =
    circles.flatMap( c1 =>
      circles.flatMap( c2 =>
        circles.map( c3 =>
        c1.beside(c2).beside(c3).beside(Image.square(5))
        )
      )
    )

  val programTwo =
    circles.map { c => c beside c beside c}

  def rowOfBoxes(count: Int): Image =
    val box = Image.rectangle(30, 30)
      .strokeWidth(5)
      .strokeColor(randomColor(0.6, 0.5).run)
      .fillColor(randomColor(0.5, 0.6).run)
    count match {
      case 0 => box
      case n => box.beside(rowOfBoxes(n - 1))
    }

  def step(current: Point): Random[Point] = {
    val start = Random.always(Point.zero)
    val drift = Point(current.x + 10, current.y)
    val noise = Random.normal(0.0, 5.0) flatMap( x =>
      Random.normal(0.0, 5.0) map( y =>
        Vec(x, y)))
    noise.map(vec => drift + vec)
  }

  def render(point: Point): Image = ???
  def walk(steps: Int): Random[Image] = ???





  def main(args: Array[String]): Unit = {

//    concentricCircles(15, 10, randomColor(0.4, 0.5).run).draw()
//    randomCircle(15, randomColor(0.4, 0.5)).run.draw()
//    randomConcentricCircles(8, 10).run.draw()
//
//    println(s"Random Double: ${randomDouble.run}")
//    println(s"Random Double: ${randomDouble.run}")
//
//    println(s"Random Angle: ${randomAngle.run}")
//    println(s"Random Angle: ${randomAngle.run}")

//    programOne.run.draw()
//    programTwo.run.draw()

    rowOfBoxes(5).draw()


  }
}
