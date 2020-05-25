package ru.ifmo.base.lesson19.serialization;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Pupil pupil1 = new Pupil();
        pupil1.setAge(7);
        pupil1.setName("pupil1");
        pupil1.setGroup(new Group("Химия", 12));
        pupil1.learn();

        Pupil pupil2 = new Pupil();
        pupil2.setAge(8);
        pupil2.setName("pupil2");
        pupil2.setGroup(new Group("Биология", 22));
        pupil2.learn();

        Director director = new Director("Super Speech");
        director.setAge(44);
        director.setName("director");
        director.setRating(2);


        // сериализация - передать объект в последовательность байт
        // десереализация - последовательность байт в объект


        // чтобы объект записать в файл - класс на основе которолго создан объект должен имплементировать интерфейс Serializable или Externalizable
        // наличие Serializable указывает на то, что сериализовать нужно все поля клласса.(по умолчанию сериализует все поля класса), у нас есть возможность исключать некоторые из них
        // если имплементировать Serializable у дочернего класса, то сериализуются свойства только дочернего класса.
        // Externalizable - ничего не сериализует по умолчанию, нужно самим перечислить какие поля сериализовать.
        //

        //у всех классов есть версия. тип long. она тоже записывается в файл. Версия меняется при модифицировании клласса
        //при чтении срравнивается версия, если версии не совпадают - десериализация ломается
        // если предполагается изменение структуры класса, версию можно установить в ручную private static final long serialVersionUID =
        //тогда новые поля добавлятьсся будут только по дефолту


        File file = new File("school.bin");

        System.out.println("до записи " + pupil1);
        System.out.println("до записи " + pupil2);

        objectToFile(file, pupil1);

        Pupil pupilFromFile1 = (Pupil) getObjectFromFile(file);
        System.out.println("после записи" + pupilFromFile1);
        System.out.println(pupil1.equals(pupilFromFile1));

        objectToFile(file, pupil2);

        Pupil pupilFromFile2 = (Pupil) getObjectFromFile(file);
        System.out.println("после записи" + pupilFromFile2);
        System.out.println(pupil2.equals(pupilFromFile2));

        objectToFile(file, director);

        Director directorFromFile  = (Director) getObjectFromFile(file);
        System.out.println("после записи " + directorFromFile);


    }

    private static void objectToFile(File file, Object obj) {
        try (FileOutputStream fileOutput = new FileOutputStream(file); ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
            //ObjectOutputStream - преобразует объект в последовательность байт.

            objectOutput.writeObject(obj);

        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static Object getObjectFromFile(File file) {

        Object obj = null;
        // чтение из файла
        try (FileInputStream fileInput = new FileInputStream(file);
             // десериализация
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            obj = objectInput.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("файл не найден");
            ;
        } catch (IOException e) {
            System.out.println("проблема в момент чтения");
            ;
        } catch (ClassNotFoundException e) {
            System.out.println("проблема в момент чтения");
            ;
        }

        return obj;
    }


}
