package ru.ifmo.base.lesson21;

public class Lambda {
    public static void main(String[] args) {
      // лямбда - это реализация метода интерфейса
      // интерфейс должен быть и метод должжен быть создан
      // для работы с лямбдой у интерфейса должен быть один абстрактный метод и любое кол-во дефолтных.
        // реализацию абстрактного метода можно описать через лямбда выражение.
        // интерфейсы у которых только один абстрактный метод называются функциональными интерфейсамми

        // реализация метода с сохранением в переменную
       // Operation operation = реализация абстрактного метода интерфейса
        // приннимаемые аргументы: должно быть столько же, сколько в методе
        // можно не заключать в скобки(), если аргумент только один.
        // во всех остальных случаях они должны быть.
        // тип данных аргументов можно не указывать, он берется из контекста метода.
        // реализация метода:
        // если реализация в одну инструкцию и предполагается возвращать значение, то {} указывать не ннужно + return по умолчанию
        // если инструкций больше 1, то {} использовать обязательно
        // метод по умолчанию ничего не возвращает

        Operation plus = (a, b) -> a + b;
        Operation division = (a, b) -> {
            if (b == 0) throw new IllegalArgumentException("0");
            return a / b;
        };

        System.out.println(Calculator.calculate(3, 67, plus));
        System.out.println(Calculator.calculate(80, 2, division));

        //использование лямбда без сохранения в переменную
        System.out.println(Calculator.calculate(4, 4, (a, b)-> a - b));
    }
}

interface Operation{
    double execute(double a, double b);
}

class Calculator {
    public static double calculate(double a, double b, Operation operation){
        return operation.execute(a, b);
    }
}