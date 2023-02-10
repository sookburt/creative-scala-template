import doodle.core.*
import doodle.image.*
import doodle.image.syntax.all.*
import doodle.image.syntax.core.*
import doodle.java2d.*
import doodle.random._
import cats.effect.unsafe.implicits.global

// Graham Oakland's output

object ParametricNoise {
  def rose(k: Int): Angle => Point =
    (angle: Angle) => {
      Point.cartesian((angle * k).cos * angle.cos, (angle * k).cos * angle.sin)
    }

  def scale(factor: Double): Point => Point =
    (pt: Point) => {
      Point.polar(pt.r * factor, pt.angle)
    }

  def perturb(point: Point): Random[Point] =
    for {
      x <- Random.normal(0, 10)
      y <- Random.normal(0, 10)
    } yield Point.cartesian(point.x + x, point.y + y)

  def smoke(r: Normalized): Random[Image] = {
    val alpha = Random.normal(0.5, 0.1)
    val hue = Random.double.map(h => (h * 0.1).turns)
    val saturation = Random.double.map(s => s * 0.8)
    val lightness = Random.normal(0.4, 0.1)
    val color =
      for {
        h <- hue
        s <- saturation
        l <- lightness
        a <- alpha
      } yield Color.hsla(h, s, l, a)
    val c = Random.normal(5, 5) map (r => Image.circle(r))

    for {
      circle <- c
      line <- color
    } yield circle.strokeColor(line).noFill
  }

  def point(
             position: Angle => Point,
             scale: Point => Point,
             perturb: Point => Random[Point],
             image: Normalized => Random[Image],
             rotation: Angle
           ): Angle => Random[Image] = { (angle: Angle) => {
    val pt = position(angle)
    val scaledPt = scale(pt)
    val perturbed = perturb(scaledPt)

    val r = pt.r.normalized
    val img = image(r)

    for {
      i <- img
      pt <- perturbed
    } yield (i at pt.toVec.rotate(rotation))
  }
  }

  def iterate(step: Angle): (Angle => Random[Image]) => Random[Image] = {
    (point: Angle => Random[Image]) => {
      def iter(angle: Angle): Random[Image] = {
        if (angle > Angle.one)
          Random.always(Image.empty)
        else
          for {
            p <- point(angle)
            ps <- iter(angle + step)
          } yield (p on ps)
      }

      iter(Angle.zero)
    }
  }

  val image: Random[Image] = {
    val pts =
      for (i <- 28 to 360 by 39) yield {
        iterate(1.degrees) {
          point(
            rose(5),
            scale(i),
            perturb _,
            smoke _,
            i.degrees
          )
        }
      }

    val picture = pts.foldLeft(Random.always(Image.empty)) { (accum, img) =>
      for {
        a <- accum
        i <- img
      } yield (a on i)
    }

    val background = (Image.rectangle(650, 650).fillColor(Color.black))

    picture map {
      _ on background
    }
  }
    def main(args: Array[String]): Unit = {
      image.run.draw()
    }
}