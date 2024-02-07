// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("1+1") {
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
  }

  //can't test a while loop without having modifiable variables
  //because otherwise it does nothing or it goes forever
}