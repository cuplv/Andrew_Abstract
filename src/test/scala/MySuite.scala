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
}
