package ru.ifmo.base.lesson22;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsExample {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("qwe", "Apple", 2000, 19));
        employees.add(new Employee("asd", "Yandex", 3000, 38));
        employees.add(new Employee("zxc", "Google", 2500, 49));
        employees.add(new Employee("vbn", "Google", 3000, 22));
        employees.add(new Employee("tyu", "Apple", 1500, 22));

        System.out.println("Группировка по названию компании");
        Map<String, List<Employee>> byCompany = employees.stream().collect(Collectors.groupingBy(Employee::getCompany));
        byCompany.entrySet().forEach(System.out::println);

        System.out.println("Группировка по условию (22 года)");

        Map<Boolean, List<Employee>> by22 = employees.stream().collect(Collectors.partitioningBy(employee -> employee.getAge() == 22));
        by22.entrySet().forEach(System.out::println);

        System.out.println("Названия компаний в алфавитном порядке");
        List<String> companiesName = employees.stream().map(Employee::getCompany).distinct().sorted().collect(Collectors.toList());
                //employees.stream().map(Employee::getCompany) = employee -> employee.getCompany()


        System.out.println(" зп - список сотрудников ArrayList");
        Map<Integer, ArrayList<Employee>> salaryyMap = employees.stream().collect(Collectors.groupingBy(Employee::getSalary, Collectors.toCollection(ArrayList::new)));

        System.out.println("Название компании - средняя зп");
        Map<String, Double> compAver = employees.stream().collect(Collectors.groupingBy(Employee::getCompany, Collectors.averagingDouble(Employee::getSalary)));

                //averagingDouble() - принимает на вход функцию описывающую как считать среднее
        compAver.entrySet().forEach(System.out::println);

        System.out.println("название компании: зп - список сотрудников");
        Map<String, Map<Integer, List<Employee>>> companySalary = employees.stream().collect(Collectors.groupingBy(Employee::getCompany, Collectors.groupingBy(Employee::getSalary)));
        companySalary.entrySet().forEach(System.out::println);

    }
}
