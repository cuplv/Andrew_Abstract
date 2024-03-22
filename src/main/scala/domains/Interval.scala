case class Interval(
    low: Int | Infinity,
    lowInc: Boolean,
    high: Int | Infinity,
    highInc: Boolean
) extends Operable:
  override def toString(): String =
    (if lowInc then "[" else "(") + low.toString() + ", " + high
      .toString() + (if highInc then "]" else ")")

  def *(num: Int): Interval =
    if (num == 0) {
      throw new Exception("Multiplying Interval by 0 is not allowed")
    }
    Interval(
      high match {
        case h: Int      => h * num
        case h: Infinity => h * (num / (num.abs))
      },
      highInc,
      low match {
        case l: Int      => l * num
        case l: Infinity => l * (num / (num.abs))
      },
      lowInc
    )

  def *(other: Interval): Interval =
    var low = this.low
    var high = this.high
    var lowInc = this.lowInc
    var highInc = this.highInc
    // lower bound
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity]) {
      low = Infinity(true)
      lowInc = false
    } else {
      low = this.low.asInstanceOf[Int] * other.low.asInstanceOf[Int]
      lowInc = this.lowInc && other.lowInc
    }

    // upper bound
    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity]) {
      high = Infinity(false)
      highInc = false
    } else {
      high = this.high.asInstanceOf[Int] * other.high.asInstanceOf[Int]
      highInc = this.highInc && other.highInc
    }

    Interval(low, lowInc, high, highInc)

  def union(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity])
    then
      min = Infinity(true)
      minInc = false
    else
      if (this.low.asInstanceOf[Int] < other.low.asInstanceOf[Int])
      then
        min = this.low
        minInc = this.lowInc
      else if (this.low.asInstanceOf[Int] > other.low.asInstanceOf[Int])
        min = other.low
        minInc = other.lowInc
      else
        min = this.low
        minInc = this.lowInc && other.lowInc
      end if
    end if

    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity])
    then
      max = Infinity(false)
      maxInc = false
    else
      if (this.high.asInstanceOf[Int] > other.high.asInstanceOf[Int])
      then
        max = this.high
        maxInc = this.highInc
      else if (this.high.asInstanceOf[Int] < other.high.asInstanceOf[Int])
        max = other.high
        maxInc = other.highInc
      else
        max = this.high
        maxInc = this.highInc && other.highInc
      end if
    end if

    Interval(min, minInc, max, maxInc)
  def negate(other: Interval): Interval = {
    // returns the stuff left over after a union
    if (this.low == other.low && this.high == other.high) then Bottom()
    else if (this.low == other.low) then
      Interval(other.high, !other.highInc, this.high, this.highInc)
    else if (this.high == other.high) then
      Interval(this.low, this.lowInc, other.low, !other.lowInc)
    else if (this.low < other.low) then
      Interval(this.low, this.lowInc, other.low - 1, true)
    else if (this.high > other.high) then
      Interval(other.high - -1, true, this.high, this.highInc)
    else Bottom()
  }
  def +(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity])
    then
      min = Infinity(true)
      minInc = false
    else
      min = this.low.asInstanceOf[Int] + other.low.asInstanceOf[Int]
      minInc = this.lowInc && other.lowInc
    end if

    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity])
    then
      max = Infinity(false)
      maxInc = false
    else
      max = this.high.asInstanceOf[Int] + other.high.asInstanceOf[Int]
      maxInc = this.highInc && other.highInc
    end if

    Interval(min, minInc, max, maxInc)
  def +(other: Int): Interval =
    this + Interval(other, true, other, true)
  def -(other: Interval): Interval =
    this + other * -1
  def rand(max: Int): Interval =
    Interval(0, true, max, true)
  // returns the interval that is less than the other interval
  def <(other: Int): Interval =
    if (this.high < other) then this
    else if (this.low > other) then Bottom()
    else Interval(this.low, this.lowInc, other - 1, true)
  def >(other: Int): Interval =
    if (this.low > other) then this
    else if (this.high < other) then Bottom()
    else Interval(other + 1, true, this.high, this.highInc)
  def <(other: Interval): Interval =
    // returns the part of the interval that is true for the given operator
    if (this.high < other.low) then this
    else if (this.low > other.high) then Bottom()
    else Interval(this.low, this.lowInc, other.low - 1, true)
  def >(other: Interval): Interval =
    if (this.low > other.high) then this
    else if (this.high < other.low) then Bottom()
    else Interval(other.high - -1, true, this.high, this.highInc)
  // not sure if i am doing this that well
  def ==(other: Interval): Interval = {
    if (this.low == other.low && this.high == other.high) then this
    else Bottom()
  }

  def +(other: Operable): Operable = other match {
    case other: Interval => this + other
  }
  def *(other: Operable): Operable = other match {
    case other: Interval => this * other
  }
  def -(other: Operable): Operable = other match {
    case other: Interval => this - other
  }
class Bottom() extends Interval(0, false, 0, false):

  override def toString(): String = "\u22A5" // ⊥
  override def *(num: Int): Interval = this
  override def *(other: Interval): Interval = this
  override def union(other: Interval): Interval = other
  override def +(other: Interval): Interval = this
  override def -(other: Interval): Interval = this
  def +(other: Bottom): Interval = this

class Top() extends Interval(Infinity(true), true, Infinity(false), true):
  override def toString(): String = "\u22A4" // ⊤(not T)
  override def *(num: Int): Interval = this
  override def *(other: Interval): Interval = this
  override def union(other: Interval): Interval = this
  override def +(other: Interval): Interval = this
  override def -(other: Interval): Interval = this

class Identity() extends Interval(0, true, 0, true) {
  override def toString(): String = "Identity"
  override def *(other: Interval): Interval = other
  override def union(other: Interval): Interval = other
  override def +(other: Interval): Interval = other
  override def -(other: Interval): Interval = other

}

case class Infinity(neg: Boolean):
  override def toString(): String = if neg then "-Infinity" else "Infinity"
  def *(num: Int): Infinity = if num < 0 then Infinity(!neg) else this
  def <(num: Int): Boolean = if neg then true else false
  def >(num: Int): Boolean = if neg then false else true
  def >(num: Infinity): Boolean = if neg then false else true
  def <(num: Infinity): Boolean = if neg then true else false
  def ==(num: Infinity): Boolean = neg == num.neg
  def ==(num: Int): Boolean = false

//Two D Interval(removed because useless)
/* case class TwoDInterval(x: Interval, y: Interval) extends Domain:
override def toString(): String =
  "(" + x.toString() + ", " + y.toString() + ")"
def union(other: TwoDInterval): TwoDInterval =
  TwoDInterval(x union other.x, y union other.y)
def +(other: TwoDInterval): TwoDInterval =
  TwoDInterval(x + other.x, y + other.y)
def -(other: TwoDInterval): TwoDInterval =
  TwoDInterval(x - other.x, y - other.y)
def *(other: Interval): TwoDInterval = TwoDInterval(x * other, y * other)
def *(num: Concrete): TwoDInterval = TwoDInterval(x * num, y * num)
 */

extension (x: Int | Infinity)
  def <(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x < y
    case (x: Int, y: Int)           => x < y
    case (x: Infinity, y: Int)      => x < y
    case (x: Int, y: Infinity)      => x < y
    case _                          => false // should be unreachable
  }
  def >(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x > y
    case (x: Int, y: Int)           => x > y
    case (x: Infinity, y: Int)      => x > y
    case (x: Int, y: Infinity)      => x > y
    case _                          => false // should be unreachable
  }
  def ==(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x == y
    case (x: Int, y: Int)           => x == y
    case (x: Infinity, y: Int)      => false
    case (x: Int, y: Infinity)      => false
    case _                          => false // should be unreachable
  }
  def <=(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x < y
    case (x: Int, y: Int)           => x <= y
    case (x: Infinity, y: Int)      => x < y
    case (x: Int, y: Infinity)      => x < y
    case _                          => false // should be unreachable
  }
  def >=(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x > y
    case (x: Int, y: Int)           => x >= y
    case (x: Infinity, y: Int)      => x > y
    case (x: Int, y: Infinity)      => x > y
    case _                          => false // should be unreachable
  }
  def -(y: Int | Infinity): Int | Infinity = (x, y) match {
    case (x: Infinity, y: Infinity) => null // how tf does one know?
    case (x: Int, y: Int)           => x - y
    case (x: Infinity, y: Int)      => Infinity(x.neg)
    case (x: Int, y: Infinity)      => Infinity(!y.neg)
    case _                          => 0 // should be unreachable
  }
  /*
  couldn't get scala to like this because of type erasure.
  def +(y: Int | Infinity): Int | Infinity = (x, y) match {
    case (x: Infinity, y: Infinity) => null // how tf does one know?
    case (x: Int, y: Int)           => x + y
    case (x: Infinity, y: Int)      => Infinity(x.neg)
    case (x: Int, y: Infinity)      => Infinity(y.neg)
    case _                          => 0 // should be unreachable
  } */
implicit object IntervalEvaluator extends Evaluator[Interval] {
  def evaluate(expr: Expression, state: State[Interval]): Interval =
    expr match {
      case Number(value)        => Interval(value, true, value, true)
      case Rand(max)            => Interval(0, true, max, true)
      case Variable(identifier) => state.variables(identifier)
      case Addition(left, right, sub) =>
        if (sub)
          left.evaluate[Interval](state) - right.evaluate[Interval](state)
        else left.evaluate[Interval](state) + right.evaluate[Interval](state)
      case Conditional(a, comp, b) => {
        val aEval = a.evaluate[Interval](state)
        val bEval = b.evaluate[Interval](state)
        comp match {
          case LessThan    => aEval < bEval
          case GreaterThan => aEval > bEval
          case Equals      => aEval == bEval
        }
      }
      case stmt: While_Statement => Bottom() // while_interval(state, stmt)
      case _                     => Bottom()
    }

  // doesn't mean run the program, it means evaluate a Statement
  def execute(stmt: Statement, state: State[Interval]): State[Interval] =
    stmt match {
      case Executable(body)      => body(state).asInstanceOf[State[Interval]]
      case stmt: While_Statement => while_interval(state, stmt)
      case _                     => null.asInstanceOf[State[Interval]]
    }

  def while_interval(
      state: State[Interval],
      stmt: While_Statement
  ): State[Interval] = {
    // this maps each position to the next possible ones
    val topologicalMap = Map(
      0 -> List(1),
      1 -> List(3, 2),
      2 -> List(0, 3),
      3 -> List() // No outgoing transitions from position 4
    )

    // maps each position to the state there
    var intervals = collection.mutable.Map(
      0 -> state,
      1 -> State[Interval](),
      2 -> State[Interval](),
      3 -> State[Interval]()
    )
    // need some way to keep track of what was changed
    var oldIntervals = intervals.clone()

    // what happens at each position
    val transfers =
      Map(
        0 -> (() => {
          var together = intervals(0) union intervals(2)
          for ((k, v) <- together.variables) {
            var high = v.high
            var low = v.low
            var highInc = v.highInc
            var lowInc = v.lowInc
            // for now just if it has increased, then assume it will always increase
            if (v.high > intervals(1).variables.getOrElse(k, v).high) {
              high = Infinity(false)
              highInc = false
            }
            // same with decrease
            if (v.low < intervals(1).variables.getOrElse(k, v).low) {
              low = Infinity(true)
              lowInc = false
            }
            together.variables(k) = Interval(low, lowInc, high, highInc)
          }
          intervals(0) = together
        }), // merges 0 and 2 if possible(with fake wideing operator)
        1 -> (() => {
          intervals(1) =
            stmt.splitInterval(intervals(0))._2 // 2 is the true branch
        }), // splits 0 at b, 1 holds true branch
        2 -> (() =>
          intervals(2) = stmt.execBody[Interval](intervals(1))
        ), // call the executable with 1
        3 -> (() => {
          intervals(3) =
            stmt.splitInterval(intervals(0))._1 // 1 is false branch

        }) // holds false branch
      )

    // queue to keep track of the positions
    var queue =
      scala.collection.mutable.Queue(1) // start the queue with position one
    var position = 0

    // makes everything look nicer
    println(Console.GREEN + "Start of Anaylsis" + Console.RESET)
    println("--------------------")
    println("Current position: " + position)
    println("Current intervals" + intervals.toString())
    println("Current queue: " + queue.toString())

    // loop to go through queue
    while (queue.nonEmpty) {
      position = queue.dequeue()

      transfers(position)()
      // at the end, if there was a change we add next places onto new queue
      if (!intervals.equals(oldIntervals)) {
        oldIntervals = intervals.clone()
        topologicalMap(position).foreach(queue.enqueue(_))
      }
      println("--------------------")
      println("Current position: " + position)
      println("Current intervals" + intervals.toString())
      println("Current queue: " + queue.toString())
    }

    // if position 3 is bottom, then we try to call 3 one time to see if there is any change
    if (position != 3 && intervals(3).isEmpty()) {
      transfers(3)()
      println("--------------------")
      println("Current position: " + position)
      println("Current intervals" + intervals.toString())
      println("Current queue: " + queue.toString())
    }

    println("--------------------")
    println(Console.GREEN + "End of Analysis" + Console.RESET)
    println("--------------------")

    // return the interval at the end
    return intervals(3)
  }
}

extension (x: State[Interval])
  def union(y: State[Interval]): State[Interval] = {
    val newState = State[Interval]()
    for ((k: String, v: Interval) <- x.variables) {
      newState.variables(k) = v union y.variables.getOrElse(k, Identity())
    }
    for ((k: String, v: Interval) <- y.variables) {
      if (!x.variables.contains(k)) {
        newState.variables(k) = v
      }
    }
    newState
  }

extension (stmt: While_Statement)
  // takes an interval and splits it over the conditional returning (false, true) branches
  // for now only works when one variable is used in the conditional
  def splitInterval(
      state: State[Interval]
  ): (State[Interval], State[Interval]) = {
    val cond = stmt.getCond()

    (cond.a, cond.b) match {
      case (a: Variable[_], b: Variable[_]) => null
      case (
            a: Variable[_],
            b: Expression
          ) => {
        var trueBranch = state.clone()
        var falseBranch = state.clone()
        trueBranch.variables(a.identifier) =
          trueBranch.variables(a.identifier) union b.evaluate[Interval](state)
        falseBranch.variables(a.identifier) = falseBranch.variables(
          a.identifier
        ) negate b.evaluate[Interval](state)
        (falseBranch, trueBranch)
      } // split a over b with cond.comp
      case (
            a: Expression,
            b: Variable[_]
          ) => {
        var trueBranch = state.clone()
        var falseBranch = state.clone()
        trueBranch.variables(b.identifier) =
          trueBranch.variables(b.identifier) union a.evaluate[Interval](state)
        falseBranch.variables(b.identifier) = falseBranch.variables(
          b.identifier
        ) negate a.evaluate[Interval](state)
        (falseBranch, trueBranch)
      } // split b over a with cond.comp
      case (a: Expression, b: Expression) =>
        null // don't want to do infinite loops

    }

  }
