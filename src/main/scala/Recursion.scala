object Recursion {

  /*
  * This was an exercise to investigate map and prepare us for the flatmap which we will need to do for Chapter 12
  * and introduce us to Type Variables (known as generics in other languages)
  * */
  def reverse[A](list: List[A]): List[A] = {

    def iter(list: List[A], reversed: List[A]): List[A] =
      list match {
        case Nil => reversed
        case hd :: tl => iter(tl, hd :: reversed) // also, is this tail recursion?
      }

    iter(list, Nil)
  }

  def reverseString(s: String) = reverse(s.toList).mkString("")

  def double[A](in: List[A]): List[A] =
    in.flatMap { x => List(x, x) }

  def nothing[A](in: List[A]): List[A] =
    in.flatMap {x => List.empty}

  def main(args: Array[String]): Unit = {

    // reversing a list of integers
    println(reverse(List(1, 2, 3, 5)))
    // reversing a list of chars
    println(reverse(List("a", "b", "c", "d")))
    // reversing a string
    println(reverseString("ABCD123"))
    // or could convert to list first... and pass directly into reverse()
    println(reverse("ABCD123".toList))
    // reverse list
    println(reverse(List("ABCD", "EFGH", "IJKL")))
    // reverse strings in each list
    println(List("ABCD", "EFGH", "IJKL").map(reverseString(_)))
    // reverse both list and strings in each
    println(reverse(List("ABCD", "EFGH", "IJKL").map(reverseString(_))))

    println(double(List(1,2,3)))
    println(double(List("a", "b", "c")))
    println(nothing(List(1,2,3)))
  }
}
