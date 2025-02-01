package com.cronnoss.java.reflection.example;

public class MyTests {

    @BeforeSuite
    public static void setup() {
        System.out.println("Before Suite");
    }

    @AfterSuite
    public static void teardown() {
        System.out.println("After Suite");
    }

    @BeforeTest
    public static void beforeTest() {
        System.out.println("Before each test");
    }

    @AfterTest
    public static void afterTest() {
        System.out.println("After each test");
    }

    @Test(priority = 10)
    public static void testHighPriority() {
        System.out.println("High priority test");
    }

    @Test
    public static void testDefaultPriority() {
        System.out.println("Default priority test");
    }

    @CsvSource("10, Java, 20, true")
    @Test(priority = 8)
    public static void testWithCsv(int a, String b, int c, boolean d) {
        System.out.printf("CSV Test: a=%d, b=%s, c=%d, d=%b\n", a, b, c, d);
    }
}
