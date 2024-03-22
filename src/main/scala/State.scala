class State[T]():
  var variables = collection.mutable.Map[String, T]()
  def isEmpty(): Boolean = variables.isEmpty

  override def clone(): State[T] = this.clone()

  // var intervals = collection.mutable.Map[String, Interval | TwoDInterval]()

//perhaps have state hold the total interval of each variable across the whole program?
