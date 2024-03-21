class State[T <: Domain]():
  var variables = collection.mutable.Map[String, T]()

  // var intervals = collection.mutable.Map[String, Interval | TwoDInterval]()

//perhaps have state hold the total interval of each variable across the whole program?
