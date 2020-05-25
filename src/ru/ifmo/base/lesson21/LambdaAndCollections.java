package ru.ifmo.base.lesson21;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class LambdaAndCollections {
    public static void main(String[] args) {

        Student student1 = new Student("Tom", 25, "Canada");
        Student student2 = new Student("Tim", 33, "Russia");
        Student student3 = new Student("Alex", 19, "China");
        Student student4 = new Student("Robert", 36, "Russia");
        Student student5 = new Student("Paul", 30, "China");

        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        University university = new University(students);

        // студенты из России
        Predicate<Student> russian = student -> student.getCountry().trim().equalsIgnoreCase("Russia");
        // студенты старше 30 лет
        Predicate<Student> over30 = student -> student.getAge() > 30;
        // студенты из России старше 30 лет
        Predicate<Student> fromRussiaOver30 = student -> russian.and(over30).test(student);

        System.out.println( university.getFilteredStudents(russian));
        System.out.println( university.getFilteredStudents(over30));
        System.out.println( university.getFilteredStudents(fromRussiaOver30));


        System.out.println("---Comparing.comparing---");
        Comparator<Student> byName = (std1, std2) -> std1.getName().compareTo(std2.getName());

        byName = Comparator.comparing(Student::getName);
        // через :: указывается ссылка на метод. Ссылка на метод getName
        students.sort(byName);
        Comparator<Student> byAge = Comparator.comparing(Student::getAge);
        students.sort(byAge.thenComparing(byName));
        Collections.reverse(students);

        //Consumer - void accept(T t)
        students.forEach(System.out::println);
        students.forEach(student -> student.setAge(student.getAge()+10));
        students.forEach(System.out::println);

        //Predicate "China"
        students.removeIf(x -> x.getCountry().equals("China"));
        students.removeIf(student -> "china".equalsIgnoreCase(student.getName()));
        students.forEach(System.out::println);

    }
}

class University {
    private List<Student> studentList;

    public University(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Student> getFilteredStudents(Predicate<Student> filter) {
        List<Student> students = new ArrayList<>();

        for (Student student : studentList) {
            if (filter.test(student)) students.add(student);
        }

        return students;
    }

}


class Student {
    private String name;
    private int age;
    private String country;

    public Student(String name, int age, String country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                '}';
    }
}