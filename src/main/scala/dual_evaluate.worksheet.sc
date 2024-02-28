object Basics {
  sealed trait Expression
  final case class Number(n: Int) extends Expression
  final case class Addition(a: Expression, b: Expression) extends Expression

  def evaluate(e: Expression): Int = e match {
    case Number(n)      => n
    case Addition(a, b) => evaluate(a) + evaluate(b)
  }

  val test = Addition(Number(1), Number(1))
}
Basics.evaluate(Basics.test)

object BasicsWithAbstract {
  trait Expression
  trait AbstractExpression extends Expression
  type Interval = scala.collection.immutable.Range

  final case class Number(n: Int) extends Expression
  final case class Addition(a: Expression, b: Expression) extends Expression
  final case class AbstractNumber(n: Interval) extends Expression

  extension (a: Int | Interval) {
    def +(other: Int | Interval): Int | Interval = (a, other) match {
      case (a: Int, b: Int)           => a + b
      case (a: Interval, b: Interval) => a + b
      case _                          => null
    }
  }

  def evaluate(e: Expression): Int | Interval = e match {
    case Number(n)         => n
    case Addition(a, b)    => evaluate(a) + evaluate(b)
    case AbstractNumber(n) => n
  }

  val test1 =
    Addition(AbstractNumber(Range(1, 2)), AbstractNumber(Range(4, 7)))
  val test2 = Addition(Number(1), Number(2))
}
// BasicsWithAbstract.evaluate(BasicsWithAbstract.test1)
BasicsWithAbstract.evaluate(BasicsWithAbstract.test2)

object Attempt1 {
  case class Interval(a: Int, b: Int) {
    def +(other: Interval): Interval = Interval(a + other.a, b + other.b)
  }

  type Ret = Int | Interval

  trait Expression {
    def evaluate(): Ret
  }

  trait Plus[T](a: T, b: T) {
    def evalPlus(): Ret = (a, b) match {
      case (a: Int, b: Int)           => a + b
      case (a: Interval, b: Interval) => a + b
      case _                          => null
    }
  }

  class RealPlus[RT <: Int](a: RT, b: RT)
      extends Expression
      with Plus[RT](a, b) {
    def evaluate(): Ret = evalPlus()
  }

  class AbstPlus[AT <: Interval](a: AT, b: AT)
      extends Expression
      with Plus[AT](a, b) {
    def evaluate(): Ret = evalPlus()
  }

  val test1 = RealPlus[Int](2, 4)
  val test2 = AbstPlus[Interval](Interval(1, 2), Interval(2, 2))
}
Attempt1.test1.evaluate()
Attempt1.test2.evaluate()
//Problem with attempt1 is that it is backwards, need to find a way to reverse the order of shared extensions
//In attempt1 I wanted to pursure the dual extension of traits, similar to interfaces in java

object Attempt2 {
  case class Interval(a: Int, b: Int) {
    def +(other: Interval): Interval = Interval(a + other.a, b + other.b)
  }
  type Ret = Int | Interval
  extension (a: Ret) {
    def +(b: Ret): Ret = (a, b) match {
      case (a: Int, b: Int)           => a + b
      case (a: Interval, b: Interval) => a + b
      case _                          => null
    }
  }
  abstract trait Expression
  abstract trait AbstractExpression // (v: T)

  case class Number(v: Int) extends Expression

  case class AbstractNumber(v: Interval) extends AbstractExpression

  case class Addition[T](a: T, b: T) extends Expression with AbstractExpression

  def evaluate(e: Expression | AbstractExpression): Ret = e match {
    case Number(v) => v
    // wanted to do:
    // case e:Addition[Expression] => ... but scala loses generic types at runtime
    case e: Addition[_] =>
      (e.a, e.b) match {
        case (a: Expression, b: Expression) => evaluate(a) + evaluate(b)
        case (a: AbstractExpression, b: AbstractExpression) =>
          evaluate(a) + evaluate(b)
      }
    case AbstractNumber(v) => v
    case _                 => null
  }
  val test = Addition[Number](Number(1), Number(1))
  val test2 = Addition[AbstractExpression](
    AbstractNumber(Interval(1, 2)),
    AbstractNumber(Interval(3, 5))
  )

}
Attempt2.evaluate(Attempt2.test)
Attempt2.evaluate(Attempt2.test2)
//In attempt 2 I continued the extend _ with _ idea, but made a more unified evaluate expression

object Thoughts {
  abstract trait StateType
  trait Concrete extends StateType
  trait Abstract extends StateType
  class State[T <: StateType]

  trait Expression
  // this function takes a state, either concrete or abstract and runs it through any expression
  // lowkey may be cheating but who knows
  def evaluate[T <: StateType](e: Expression, s: State[T]): State[T] = ???

}

//Attempt 2
/* object Attempt2 {
  case class Interval(a: Int, b: Int) {
    def +(other: Interval): Interval = Interval(a + other.a, b + other.b)
  }

  type Ret = Int | Interval
  trait Expression {
    def evaluate(): Ret
  }
  def evaluate[T](e: Expression): Ret = ???
  trait RealPlus[T]
  trait AbstPlus[T]
  case class Plus[T](a: T, b: T) extends RealPlus[T] with AbstPlus[T] {}
} */
