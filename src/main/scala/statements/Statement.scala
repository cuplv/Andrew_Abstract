trait Statement:
  def execute[T](using evaluator: Evaluator[T])(state: State[T]): State[T] =
    evaluator.execute(this, state)
end Statement
