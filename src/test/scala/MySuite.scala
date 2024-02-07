// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  /* test("1+1") {
    val test = Addition(Number(1), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 2
    assertEquals(obtained, expected)
  }

  test("1+1+1") {
    val test = Addition(Addition(Number(1), Number(1)), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 3
    assertEquals(obtained, expected)
  }

  test("Hello+World") {
    val test = Addition(MyString("Hello, "), MyString("World"));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = "Hello, World"
    assertEquals(obtained, expected)
  }

  test("true+false") {
    val test = Addition(Bool(true), Bool(false));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = true
    assertEquals(obtained, expected)
  }

  test("If(true){1}else{0}") {
    val test = If_Statement(Bool(true), Number(1), Number(0));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 1
    assertEquals(obtained, expected)
  } */

  test("Abstract analysis of 1+1") {
    val test = Addition(Number(1), Number(1));
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(2, true, 2, true)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()") {
    val test = Rand();
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")

    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()+1") {
    val test = Addition(Rand(), Number(1));
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")

    val obtained = test.abstract_evaluate()
    val expected = Interval(1, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()+Rand()") {
    val test = Addition(Rand(), Rand());
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()-Rand()") {
    val test = Addition(Rand(), Rand(),sub = true);
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    val obtained = test.abstract_evaluate()
    val expected = Interval(Infinity(true), false, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  //can't test a while loop without having modifiable variables
  //because otherwise it does nothing or it goes forever
}