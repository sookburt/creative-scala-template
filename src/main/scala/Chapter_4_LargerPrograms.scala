import doodle.core._
import doodle.image._
import doodle.image.syntax.all._
import doodle.image.syntax.core._
import doodle.java2d._
import doodle.reactor._
import scala.concurrent.duration._
import cats.effect.unsafe.implicits.global

// To use this example:
//
// 1. run `sbt`
// 2. run the `run` command within `sbt`
object Chapter_4_LargerPrograms {

  //val basic = Image.circle(100).fillColor(Color.darkSlateBlue).strokeColor(Color.indianRed).draw()

  val target =  Image
        .circle(100)
        .fillColor(Color.green)
        .on(Image.circle(200).fillColor(Color.white))
        .on(Image.circle(300).fillColor(Color.green))
  val stand = Image.rectangle(6, 20).fillColor(Color.brown)
        .above(Image.rectangle(20, 6).fillColor(Color.brown))
        .above(Image.rectangle(80, 25).noStroke.fillColor(Color.green))

  //val archeryTarget = target.above(stand).draw()

  // -------------------------------
  // 4.3 exercise = A Street Scene

  val road = Image.rectangle(40, 5).fillColor(Color.black)
  val tree = Image.circle(20).fillColor(Color.green)
    .above(Image.rectangle(5, 15).fillColor(Color.brown))
  // note door is floating in the square of the house.
  val house = Image.rectangle(5, 8).fillColor(Color.black)
    .on(Image.rectangle(15, 15).fillColor(Color.red))
      .below(Image.triangle(15, 15).fillColor(Color.darkRed))


  val image =
    Image
      .circle(100)
      .fillColor(Color.red)
      .on(Image.circle(200).fillColor(Color.aquamarine))
      .on(Image.circle(300).fillColor(Color.steelBlue))

  val archeryTarget =
    Image
      .circle(100)
      .fillColor(Color.red)
      .on(Image.circle(200).fillColor(Color.white))
      .on(Image.circle(300).fillColor(Color.red))
      .above(Image.rectangle(6,20).fillColor(Color.brown))
      .above(Image.rectangle(20, 6).fillColor(Color.brown))
      .above(Image.rectangle(80, 25).noStroke.fillColor(Color.green))

  val animation =
    Reactor
      .init(-200)
      .withOnTick(x => x + 1)
      .withStop(x => x > 200)
      .withTickRate(20.millis)
      .withRender { x =>
        val y = x.degrees.sin * 200
        val planet = Image.circle(20.0).noStroke.fillColor(Color.seaGreen)
        val moon = Image
          .circle(5.0)
          .noStroke
          .fillColor(Color.slateGray)
          .at((x * 10).degrees.cos * 50, (x * 10).degrees.sin * 50)

        moon.on(planet).at(x, y)
      }

  val frame = Frame.size(600, 600)

  def main(args: Array[String]): Unit = {
     //image.draw()
     archeryTarget.draw()

     //Comment out the above and uncomment the below to display the animation
     //animation.run(frame)
  }
}
