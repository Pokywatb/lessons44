package ru.ifmo.base.multithreading.lesson25;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadSafeCollections {
    public static void main(String[] args) {
        // потокобезопасные коллекции - не нужно синхронизировать
        // очень медленные (использовать при крайней необходимости)
        // Vector
        // Stack
        // Hashtable

        // однопоточные коллекции и мапы - не ориентированы на многопоточные среды
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        // метод synchronizedList, synchronizedSet, synchronizedMap создает из однопоточной коллекции - многопоточную
        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        // потокобезопасные коллекции и мапы из пакета java.util.concurrent.
        CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();
        // исп, если происходят не очень часты вставки
        books.addIfAbsent(new Book("Java", 1800 ));
        books.addIfAbsent(new Book("Java", 1800 ));
        System.out.println(books);

        new Thread(new WriteThread(books)).start();
        new Thread(new ReadThread(books)).start();

        // write -> [] read (перебор хранилища)
        // put -> [] get (извлечение данных)


        CopyOnWriteArraySet s;// на каждую операцию вставки и удаления будет создавать копию.
        ConcurrentSkipListSet s1; // Будут храниться в отсортированном порядке

        ConcurrentHashMap m1; //Блокируется только кусок мапы, не упорядоченные элементы, потокобезопасна, основана на хэш-таблице
        ConcurrentSkipListMap m2; //Блокируется только кусок мапы, данные будут упорядочены

    }
}

class  WriteThread implements Runnable {
    private CopyOnWriteArrayList<Book> books;

    public WriteThread(CopyOnWriteArrayList<Book> books) {
        this.books = books;
    }


    @Override
    public void run() {
        for (int i = 0; i < 50 ; i++) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            books.addIfAbsent(Book.getBook());

        }
    }
}

class ReadThread implements Runnable {
    private CopyOnWriteArrayList<Book> books;

    public ReadThread(CopyOnWriteArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Введите название книги");
            String title = scanner.nextLine();
            for (Book book: books){
                if(title.equalsIgnoreCase(book.getTitle())){
                    System.out.println(book);
                    books.remove(book);
                    /// при CopyOnWrite на каждую операцию операцию вставки и удаления создается копия
                }
            }

        }

    }
}


class Book{
    private String title;
    private int pageCount;

    public Book(String title, int pageCount) {
        this.title = title;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (pageCount != book.pageCount) return false;
        return getTitle() != null ? getTitle().equals(book.getTitle()) : book.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + pageCount;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }

    public static Book getBook(){
        Random random = new Random();
        String[] titles = {"Алфавит", "Сказки", "Колобок", "Репка"};
        return new Book(titles[random.nextInt(titles.length)],
                random.nextInt(2000)); // до 2000 страниц
    }
}