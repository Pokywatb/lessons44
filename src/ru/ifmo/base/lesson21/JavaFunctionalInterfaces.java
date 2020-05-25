package ru.ifmo.base.lesson21;

import java.util.function.Function;
import java.util.function.Predicate;

public class JavaFunctionalInterfaces {
    public static void main(String[] args) {
        // функциональные интеррфейсы
        // интерфейс Predicate<T>
        // методы Predicate<T>
//        boolean test(T t); предпологает проверку T на какое либо условие
        // boolean or (Predicate p) - или
        // boolean and (Predicate p) - и
        // boolean negate (Predicate p) - не

        Predicate<Integer> pos = num -> num > 0;
        Predicate<Integer> neg = num -> num < 0;
        Predicate<Integer> isEven = num -> num % 2 == 0;
        Predicate<Integer> notEven = num -> num % 2 != 0;

        System.out.println(isEven.test(11));
        System.out.println(neg.test(11));

        System.out.println(pos.and(isEven).test(45));
        System.out.println(pos.and(isEven).test(22));

        //Function<T, R> принимает один тип данных и возвращает другой
        // R apply(T t)
        // T и R могут быть одинаковыми
        // default
        // andThen(Function after)
        // compose(Function before)

        Function<Integer, Double> tenPercent = num -> num * 0.1;
        Function<Integer, Integer> plusTen = num -> num + 10;
        Function<Integer, Integer> plusFive = num -> num + 5;

        System.out.println(plusFive.apply(34));

        double res = plusTen.andThen(plusFive).andThen(tenPercent).apply(16);
        System.out.println("res = " + res);

        // принимает Integer возвращает String - int + $

        Function<Integer, String> dollar = num -> num + "$";

        Predicate<Integer> cond = num -> num > 0;
        Function<Integer, Integer> func = getFunction(cond, plusFive, plusTen);
        System.out.println(func.apply(5));


    }



    static Function<Integer, Integer>
    getFunction(Predicate<Integer> cond, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return num -> cond.test(num) ? function1.apply(num) : function2.apply(num);

//        Function<Integer, Integer> res = null;
//        if (cond) res = function1;
//        else res = function2;
//        return res;
    }
}
