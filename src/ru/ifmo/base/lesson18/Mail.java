package ru.ifmo.base.lesson18;

import java.io.*;
import java.util.Arrays;

public class Mail {
    public static void main(String[] args) {

        File file = new File("src/oneoneone.txt");

        try {
            System.out.println(file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writeToFileWithBuffer(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            writeToFile(file, "rrr", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readFromFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.length());


        File file2 = new File("src/oneonetwo.txt");
        File file3 = new File("src/oneonethree.txt");
        File file4 = new File("src/oneonefour.txt");
        File file5 = new File("src/oneonefive.txt");
        try {
            System.out.println(file2.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(file3.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();

        }


        try {
            split(file, file2, file3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream fin = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(file2);
             FileOutputStream fos2 = new FileOutputStream(file3);) {
            byte[] buffer = new byte[fin.available()];
            // считываем буфер
            fin.read(buffer, 0 , buffer.length / 2);
            fos.write(buffer, 0, buffer.length / 2);
            fin.read(buffer, 0, buffer.length);
            fos2.write(buffer, 0, buffer.length);


        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

        //catch(IndexOutOfBoundsException ee){}


    }


    private static void writeToFileWithBuffer(File file) throws IOException {
        // file <---- [buffer] <----  java app
        try (FileOutputStream fileOutput = new FileOutputStream(file);
             BufferedOutputStream bufferOut = new BufferedOutputStream(fileOutput)) {
            // размер буфера по умолчанию 8192 байта, размер буфера можно
            // установить через констркутор new BufferedOutputStream(fileOutput, size)

            // задача FileOutputStream - запись в файл
            // задача BufferedOutputStream - накапливать данные в буфер
            // после того, как буфер заполнится, объект BufferedOutputStream
            // вызывает метод write у того объекта типа OutputStream, который
            // был передан в конструктор,
            // в данном случае у объекта fileOutput и уже объект fileOutput
            // осуществляет запись данных в файл

            // BufferedOutputStream - декоратор (обертка, дополнительный функционал)
            // для любого OutputStream

            for (int i = 0; i < 100; i++) {
                bufferOut.write((i + " ").getBytes());
            }
        }
    }

    private static void writeToFile(File file, String data, boolean append)
            throws IOException {
        // строка data должна быть записана в файл file
        // если append - true - запись в конец файла
        // если append - false - запись в начало файла
        // try-with-resources - доступен, начиная с 7 java
        // file <---------  java app
        try (FileOutputStream fileOutput = new FileOutputStream(file, append)) {
            byte[] bytes = data.getBytes();
            fileOutput.write(bytes);
        }
    }

    public static String readFromFileToByteArray(File file)
            throws IOException {
        String string = null;

        try (FileInputStream fileInput = new FileInputStream(file);
             ByteArrayOutputStream arrOut = new ByteArrayOutputStream()) {

            byte[] buf = new byte[293]; // 10 20

            int data;

            while ((data = fileInput.read(buf)) > 0) {
                System.out.println(data);
                System.out.println(Arrays.toString(buf));
                arrOut.write(buf, 0, data);
                //System.out.println(Arrays.toString(arrOut.toByteArray()));
            }
            System.out.println(arrOut.toString());
            //arrOut.toString()
            string = new String(arrOut.toByteArray());

        }

        return string;
    }


    public static void split(File file, File file2, File file3) throws IOException {
        try (FileInputStream fileInput = new FileInputStream(file); ByteArrayOutputStream arrOut = new ByteArrayOutputStream()) {
            byte[] buf = new byte[(int) file.length()];
            int data;

            while ((data = fileInput.read(buf)) > 0) {
                arrOut.write(buf, 0, data);
            }

        }


    }


}


