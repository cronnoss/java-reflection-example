### Topic: Java reflection

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

### Topic: Java Stream

All tasks must be done in one line.

1 Implement the removal of all duplicates from the sheet

2 Find the 3rd largest number in the list of integers (example: 5 2 10 9 4 3 10 1 13 => 10)

3 Find the 3rd largest "unique" number in the list of integers (example: 5 2 10 9 4 3 10 1 13 => 9, unlike the previous
task, here different 10s are considered one number)

4 There is a list of objects of the Employee type (name, age, position), it is necessary to get a list of names of the 3
oldest employees with the position of "Engineer", in descending order of age

5 There is a list of objects of the Employee type (name, age, position), calculate the average age of employees with the
position of "Engineer"

6 Find the longest word in the list

7 There is a string with a set of words in lower case, separated by a space. Build a hash map that will store pairs:
word - how many times it occurs in the input string

8 Print the lines from the list to the console in order of increasing word length, if the words are the same length,
then the alphabetical order should be preserved

9 There is an array of lines, each of which contains a set of 5 lines separated by a space, find the longest among all
the words, if there are several such words, get any of them