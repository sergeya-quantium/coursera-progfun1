package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements common to all sets") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s2, s3)
      val r = intersect(s, t)

      assert(contains(r, 2), "In both")
      assert(!contains(r, 1), "Only s")
      assert(!contains(r, 3), "only t")
    }
  }

  test("diff contains only elements of one set which are not in other") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      val r = diff(s, s4)

      assert(contains(r, 1))
      assert(contains(r, 2))
      assert(contains(r, 3))
      assert(!contains(r, 4))
    }
  }

  test("filter contains only elements of a set satisfying predicate") {
    new TestSets {
      val s = union(union(union(s1, s2), s3), s4)
      val r = filter(s, _ > 2)

      assert(!contains(r, 1), "1 < 2 should not be in result")
      assert(!contains(r, 2), "2 <= 2 should not be in result")
      assert(contains(r, 3), "3 > 2 should not be in result")
      assert(contains(r, 4), "4 > 2 should not be in result")
    }
  }

  test("for all tests") {
    new TestSets {

      val set1 = union(union(singletonSet(1000), singletonSet(-1000)), singletonSet(0))

      assert(forall(set1, (x) => x <= 1000 && x >= -1000), "[-1000, 1000]")
      assert(forall(set1, _ != 2), "!= 2")
      assert(!forall(set1, _ == 0), "== 0 should fail")
      assert(!forall(set1, _ == -1000), "== -1000 should fail")
    }
  }

  test("exists tests") {
    new TestSets {

      val set1 = union(union(union(singletonSet(1000), singletonSet(-1000)), singletonSet(0)), singletonSet(-1))

      assert(!exists(set1, (x) => x > 1000 || x < -1000), "any numbers in (-Inf, -1000) (1000, Inf) do not exist")
      assert(!exists(set1, _ == 2), "!= 2")
      assert(exists(set1, _ > 0), "there are numbers > 0")
      assert(exists(set1, _ == 1000), "1000 is part of the set")
      assert(!exists(set1, _ == 999), "999 is not part of the set")
    }
  }

  test("map tests") {
    new TestSets {

      val set1 = union(union(union(singletonSet(1), singletonSet(10)), singletonSet(-1)), singletonSet(1000))
      val set2 = FunSets.toString(map(set1, _ - 1))
      assert(set2 == "{-2,0,9,999}")
    }
  }

}
