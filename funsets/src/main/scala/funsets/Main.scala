package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))

  val s1 = union(union(union(singletonSet(1), singletonSet(10)), singletonSet(-1)), singletonSet(1000))
  printSet(s1)

  printSet(map(s1, _ -1))
}
