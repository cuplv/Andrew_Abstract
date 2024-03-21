case class Interval(
    low: Int | Infinity,
    lowInc: Boolean,
    high: Int | Infinity,
    highInc: Boolean
) extends Operable:
  override def toString(): String =
    (if lowInc then "[" else "(") + low.toString() + ", " + high
      .toString() + (if highInc then "]" else ")")

  def *(other: Concrete): Interval =
    val num = other.value
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
  def +(other: Concrete): Interval =
    this + Interval(other.value, true, other.value, true)
  def -(other: Interval): Interval =
    this + other * Concrete(-1)
  def rand(max: Int): Interval =
    Interval(0, true, max, true)
  // returns the interval that is less than the other interval
  def <(other: Concrete): Interval =
    if (this.high < other.value) then this
    else if (this.low > other.value) then Bottom()
    else Interval(this.low, this.lowInc, other.value - 1, true)
  def >(other: Concrete): Interval =
    if (this.low > other.value) then this
    else if (this.high < other.value) then Bottom()
    else Interval(other.value + 1, true, this.high, this.highInc)

class Bottom() extends Interval(0, false, 0, false):
  override def toString(): String = "\u22A5" // ⊥
  override def *(num: Int): Interval = this
  override def *(other: Interval): Interval = this
  override def union(other: Interval): Interval = other
  override def +(other: Interval): Interval = this
  override def -(other: Interval): Interval = this

class Top() extends Interval(Infinity(true), true, Infinity(false), true):
  override def toString(): String = "\u22A4" // ⊤(not T)
  override def *(num: Int): Interval = this
  override def *(other: Interval): Interval = this
  override def union(other: Interval): Interval = this
  override def +(other: Interval): Interval = this
  override def -(other: Interval): Interval = this

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
