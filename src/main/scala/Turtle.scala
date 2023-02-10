//import doodle.core._
//import doodle.core.PathElement._
//import doodle.image.Image
//import doodle.image.Image.openPath
//import doodle.turtle.Instruction._
//import doodle.algebra._
//
//object Turtle {
//
//  sealed abstract class Instruction extends Product with Serializable
//
//  final case class Forward(distance: Double) extends Instruction
//
//  final case class Turn(angle: Angle) extends Instruction
//
//  final case class Branch(instructions: List[Instruction]) extends Instruction
//
//  final case class NoOp() extends Instruction
//
//  final case class TurtleState(location: Vec, heading: Angle)
//
//
//  def draw(instructions: List[Instruction]): Image = {
//    def iterate(state: TurtleState, instructions: List[Instruction]): List[PathElement] =
//      instructions match {
//        case Nil =>
//          Nil
//        case i :: is =>
//          val (newState, elements) = process(state, i)
//          elements ++ iterate(newState, is)
//      }
//
//    def process(state: TurtleState, instruction: Instruction): (TurtleState, List[PathElement]) = {
//      import PathElement._
//      instruction match {
//        case Forward(d) =>
//          val nowAt = state.at + Vec.polar(d, state.heading)
//          val element = lineTo(nowAt.toPoint)
//          (state.copy(at = nowAt), List(element))
//        case Turn(a) =>
//          val nowHeading = state.heading + a
//          (state.copy(heading = nowHeading), List())
//        case Branch(is) =>
//          val branchedElements = iterate(state, is)
//          (state, moveTo(state.at.toPoint) :: branchedElements)
//        case NoOp() =>
//          (state, List())
//      }
//    }
//    openPath(iterate(TurtleState(Vec.zero, Angle.zero), instructions))
//  }
//}
