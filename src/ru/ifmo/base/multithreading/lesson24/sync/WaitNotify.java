package ru.ifmo.base.multithreading.lesson24.sync;

import java.util.ArrayList;

public class WaitNotify {
    public static void main(String[] args) {
        //часть потоков кладет данные в хранилище, другая часть потоков забирает данные, обычно через конкаренс`

        BookStorage bookStorage = new BookStorage();
        new Thread(new PutBook(bookStorage)).start();
        //new Thread(new PutBook(bookStorage)).start();
        
        new Thread(new GetBook(bookStorage)).start();
        //new Thread(new GetBook(bookStorage)).start();

    }
}

class Book{}

class BookStorage{ //хранилище, будем добавлять и получать из него
    private ArrayList<Book> books = new ArrayList<>();

    public synchronized void putBook() throws InterruptedException {
        //потоки будут класть книгу в хранилище
        System.out.println("PUT_BOOK: начало выполнения");

        while(books.size() > 5){
            notify();// будит любой поток, у которого ранее был вызван метод wait. либо все потоки notifyAll вызывается в синхронизированных блоках или методах
            System.out.println("PUT_BOOK: ожидание. Книг: " + books.size());
            wait();//освобождает монитор объекта ( перестает быть захваченным текущем потоком), поток в котором был вызван wait
            // переходит в состояние ожидания до тех пор пока не будет разбужен методом notify. вызывается в синхронизированных блоках или методах
            //  поток, который усыпился методом wait может проснуться сам по себе.
        }
        books.add(new Book());
        System.out.println("PUT_BOOK: добавил 1 книгу. Книг: " + books.size());


    }

    public synchronized void getBook() throws InterruptedException{
        System.out.println("GET_BOOK: начало выполнения");
        while(books.size() < 1){
            notify();
            System.out.println("GET_BOOK: ожидание. Книг: " + books.size());
            wait();
        }
        books.remove(0);
        System.out.println("GET_BOOK: забрал 1 книгу. Книг: " + books.size());
    }
}

class PutBook implements Runnable{

    private BookStorage bookStorage;


    public PutBook(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(Thread.currentThread().getName() + "готовит данные для передачи");

            try {
                Thread.sleep(700);
                bookStorage.putBook();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class GetBook implements Runnable{
    private BookStorage bookStorage;


    public GetBook(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    public void run() {
        while (true){

            try {
                bookStorage.getBook();
                System.out.println(Thread.currentThread().getName() + "обрабатываети данные после получение");
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}