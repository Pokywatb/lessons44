package ru.ifmo.base.lesson21;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class LambdaAndVars {
    private static int staticInt = 12;

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Сидней", 200);
        map.put("Лондон", 260);
        map.put("Париж", 376);

        //BiConsumer - void accept(T t, U u);
        BiConsumer<String, Integer> biConsumer = (key, value) -> System.out.println(key + ":" + value);
//        map.forEach(biConsumer);
//        map.computeIfAbsent();
//        map.merge();
        



        int localInt = 10;
        Data localObj = new Data("Текстовые данные");

        // в качестве имени аргумента нельзя использовать существующие имена объектов

        Accumulator plusFive = x -> {
            System.out.println("localInt =" + localInt);
            // return LocalInt + 5;
            // LocalInt = 100; Нельзя изменить значение локальных переменных
            // внутри лямбды

            System.out.println("localObj = " + localObj);
            localObj.setText("новые данные");

            staticInt = 1000;
            System.out.println("staticInt = " + staticInt);
            return x + 5;

        };

    }
}


@FunctionalInterface
interface Accumulator{
    int getInt(int someInt);
}

class Data {
    private String text;

    public Data(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}