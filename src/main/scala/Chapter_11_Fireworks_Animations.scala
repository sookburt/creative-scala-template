import doodle.core.*
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import concurrent.duration.DurationInt
import cats.effect.unsafe.implicits.global

object Chapter_11_Fireworks_Animations {

    val image =
      Image
        .circle(100)
        .fillColor(Color.red)
        .on(Image.circle(200).fillColor(Color.aquamarine.darken(0.2.normalized)))
        .on(Image.circle(300).fillColor(Color.steelBlue))

    val animation =
      Reactor
        .init(-500)
        .withOnTick(x => x + 1)
        .withStop(x => x > 800)
        .withTickRate(20.millis)
        .withRender { x =>
          val y = x.degrees.sin * 300
          val planet = Image.circle(20.0).noStroke.fillColor(Color.seaGreen)
          val moon = Image
            .circle(5.0)
            .noStroke
            .fillColor(Color.hotpink)
            .at((x * 10).degrees.cos * 50, (x * 10).degrees.sin * 50)

          moon.on(planet).at(x, y)
        }

    val frame = Frame.size(2000, 600)

    def main(args: Array[String]): Unit = {
      animation.run(frame)
    }

}
