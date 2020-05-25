package ru.ifmo.base.lesson17.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Properties;


public class PropertiesClass {
    public static void main(String[] args) {
        // в корне проекта (на одном уровне с src и out)
        // создаем директорию sources

        // правой кнопкой мыши -> Mark Directory as
        // -> Resources Root

        //в директории sources файл example.properties

        // объект типа Properties основан на хэш таблице
        // используется для хранения пар: ключ - значение,
        // при этом тип данных ключей - String
        // тип данных значений - String
        Properties properties = new Properties();

        try (InputStream input = PropertiesClass.class
                .getClassLoader()
                .getResourceAsStream("src/sources/example.properties")){
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // получение значений по ключам!
        System.out.println(properties.getProperty("key")); // value
        System.out.println(properties.getProperty("age")); // 23
        System.out.println(properties.getProperty("data")); // null
        System.out.println(properties
                .getProperty("data", "default")); // default

        // добавить пару ключ - значение
        properties.setProperty("ip", "97.167.23.9");


        //
        PropertiesLoader loader1 =
                PropertiesLoader.getPropertiesLoader("example.properties");

        PropertiesLoader loader2 =
                PropertiesLoader.getPropertiesLoader("example.properties");



        Comparator comp = new SomeComp();

        Object a = 12;
        Object b = 122;

        int res = comp.compare(a, b);
    }
}


class SomeComp implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1, o2);
    }
}