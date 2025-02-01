Topic: Java reflection

Implement the "Run tests" application.

· Create a set of annotations: @Test, @BeforeSuite, @AfterSuite, which can be added to methods, @Test to regular
methods, @BeforeSuite and @AfterSuite only to static ones.

· For the @Test annotation, add the parameter priority (int from 1 to 10 inclusive). If the parameter is not specified
explicitly, it is equal to 5.

· Create a class with a set of methods and mark them with the created annotations.

· Create a TestRunner class with a static method runTests(Class c).

· Running runTests(Class c) results in the following:

o The specified class is loaded into memory

o It is checked that there is no more than one method with @BeforeSuite annotations

o It is checked that there is no more than one method with @AfterSuite annotations

o The method with @BeforeSuite annotation is executed, if any

o You group methods with @Test annotations according to their priority. The methods with the highest priority are
executed first.

o The method with @AfterSuite annotation is executed, if any

· * You can add @BeforeTest and @AfterTest annotations, methods with such annotations should be executed before and
after each test.

· * Add the @CsvSource annotation parameter, which is a string. When the test method is run, this string should be
parsed, each element of the string corresponds to the argument type and the argument method executed with the data from
the specified string.

For example: @CsvSource("10, Java, 20, true")

public void testMethod(int a, String b, int c, boolean d) { .. }