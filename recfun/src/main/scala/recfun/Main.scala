package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = if (c == 0 || c == r) 1 else  pascal(c-1, r-1) + pascal(c, r-1)

  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      @tailrec
      def balance(bal: Int, chars: List[Char]): Boolean = (bal, chars) match {
        case (b, _) if b < 0 => false
        case (_, c) if c.isEmpty => true
        case (b, '(' :: rest) => balance(b + 1, rest)
        case (b, ')' :: rest) => balance(b - 1, rest)
        case (b, _ :: rest) => balance(b, rest)
      }

      (chars.count(_ == '('), chars.count(_ == ')')) match {
        case (0, 0) => true
        case (l, r) if (l != r) => false
        case (l, r) if (l == r) => balance(0, chars)
      }
    }
  
  /**
    * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = (coins, money) match {
        case (Nil, _)  => 0
        case (_, m) if m < 0 => 0
        case (_, m) if m == 0 => 1
        case (c, m) => countChange(m - c.head, c) + countChange(m, c.tail)
    }
  }
