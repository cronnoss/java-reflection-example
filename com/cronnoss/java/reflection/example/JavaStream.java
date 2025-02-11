package com.cronnoss.java.reflection.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaStream {

    public static void main(String[] args) {

        // 1. Removing duplicates from the list
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> uniqueList = list.stream().distinct().collect(Collectors.toList());
        System.out.println(uniqueList);

        // 2. Find the 3rd largest number
        List<Integer> list1 = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        Optional<Integer> thirdMax = list1.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst();
        System.out.println(thirdMax.orElse(null));

        // 3. Find the 3rd largest unique number
        List<Integer> list2 = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        Optional<Integer> thirdMaxUnique = list2.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst();
        System.out.println(thirdMaxUnique.orElse(null));

        // 4. Get a list of names of the 3 oldest employees with the position of "Engineer"
        List<Employee> employees = List.of(
                new Employee("Alice", 25, "Engineer"),
                new Employee("Bob", 30, "Manager"),
                new Employee("Charlie", 35, "Engineer"),
                new Employee("David", 40, "Manager"),
                new Employee("Eve", 45, "Engineer"),
                new Employee("Frank", 50, "Sales"),
                new Employee("Grace", 55, "Engineer"),
                new Employee("Helen", 60, "Engineer"),
                new Employee("Ivan", 65, "Sales"),
                new Employee("John", 70, "Manager")
        );
        List<String> top3Engineers = employees.stream()
                .filter(e -> e.getPosition().equals("Engineer"))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(top3Engineers);

        // 5. Average age of engineers
        double averageAge = employees.stream()
                .filter(e -> e.getPosition().equals("Engineer"))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
        System.out.println(averageAge);

        // 6. Find the longest word in the list
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry", "fig", "grape", "done");
        String longestWord = words.stream().max(Comparator.comparingInt(String::length)).orElse("");
        System.out.println(longestWord);

        // 7. Count the number of occurrences of each word in the string
        String input = "apple banana cherry date apple banana cherry date apple banana date grape";
        Map<String, Long> wordCount = List.of(input.split(" ")).stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        System.out.println(wordCount);

        // 8. Print the lines in order of increasing word length
        List<String> sortedWords = words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        System.out.println(sortedWords);

        // 9. Find the longest word in the array
        List<String> lines = List.of(
                "apple banana cherry date elderberry",
                "fig grape done apple banana cherry",
                "date apple banana cherry date grape",
                "apple banana cherry date journalism fig grape done"
        );
        String longestWordInArray = lines.stream()
                .flatMap(line -> List.of(line.split(" ")).stream())
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println(longestWordInArray);
    }

}
