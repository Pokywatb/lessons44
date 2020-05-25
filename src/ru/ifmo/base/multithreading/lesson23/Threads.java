package ru.ifmo.base.multithreading.lesson23;

public class Threads {
    public static void main(String[] args) {
        //Жизненный цикл потока
        //1. создание потока (создание ообъекта) NEW
        //2. Поток готов к работе (Вызов метода start)
        //3. поток запущен(физическим выполняет иструкции)
        //4. non-runnable  (TIME WAITING/WAITING/BLOCKED) - поток находится в состоянии ожидания
        //5. TERMINATED - поток завершил работу

        // у каждого потока своя стэк память, но хип память на все потоки одна



        //потоки - это ообъекты типа Thread
        //Варианты описания потоков и их инструкций
        // программа завершается, после завершения работы последненго потока
        // 1. создание класса, который наследуется от класса Thread
        // создание объекта
        FirstThread firstThread = new FirstThread(); //Thread-0
        System.out.println(firstThread.getName() + ": " + firstThread.getState());
        firstThread.start();//вызов метода старт - не равен методу run
        // вызов метода start говорит о том, что поток готов к работе и будет запущен шедулером jvm

        //2. создание класса (с инструкциями для потока), который импелементирует интерфейс runnable/
        // создание объекта
        // объект secondThread не является потоком!
        //SecondThread secondThread = new SecondThread();
        Thread secondThread = new Thread(new SecondThread());
        secondThread.start();
        // создаем объект потока, передаем туда инструкции, вызываем метод старт. шедулер сам запустит метод run когда надо

        //3. Реализация run интерфейса Runnable (инструкции потока) описать через лямбда выражение

        Thread thirdThread = new Thread(() -> {
            System.out.println("thirdThread isAlive" + Thread.currentThread().isAlive());
        });
        thirdThread.start();

        Thread threadOne = new Thread(new SecondThread()); //Thread-3
        Thread threadTwo = new Thread(new SecondThread()); //Thread-4
        Thread threadThree = new Thread(new SecondThread()); //Thread-5

        System.out.println("группа потоков, которой принадлежит threadOne: " + threadOne.getThreadGroup());
        System.out.println("максимальный приоритет группы" + threadOne.getThreadGroup().getMaxPriority());
        threadOne.setPriority(Thread.MAX_PRIORITY);// максималльный приоритет потока не может быть выше приоритета группы в которую он входит.
        threadOne.setPriority(1);
        threadTwo.setPriority(5);
        threadThree.setPriority(10);
        System.out.println("Приоритет threadOne: " + threadOne.getPriority());

        threadOne.start();
        threadTwo.start();
        threadThree.start();

        // нам нужно, чтобы основной поток ждал, пока завершатся потоки
        // threadOne / threadTwo / threadThree
        // join - вызывающий (в данном случаем main) поток ожидает когда к нему присоединятся (завершает выполнение) указанные потоки
        //в данном случает threadOne / threadTwo / threadThree

        try {
            threadOne.join();
            threadTwo.join();
            threadThree.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Основной поток" + Thread.currentThread().getName());

        // Поток останавливается если:
        //1. выполнил все инструкции
        //2. было выброшено неработающее исключение
        //3. остановилась jvm
        //4. поток был фоновым (daemon) потокомм и все не daemon потоки завершили свою работу

        //демон потоки изначально - обычные потоки

        FirstThread daemon = new FirstThread();
        daemon.setDaemon(true);//переделывание из обычного потока в фоновый

        // поток остановить нельзя, можно только создать условия при которых поток прекратит выполнять инструкции
        //Interrupt изначально false

        // если внутри потока который хотим прервать слипом, происходит обработка InterruptedException, если InterruptedException случится
        // флаг Interrupt изменится на true

        Thread actions = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("some actions...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("After While");
        });
        actions.start();

        // метод sleep останавливает поток на указанное количество милисекунд.

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        actions.interrupt();
    }
}

// первый вариант - создание класса, который наследуется от класса Thread

class FirstThread extends Thread {
    @Override
    public void run() {// инструкуции потока
        //когда метод run будет вызван инструкции начнут выполняться в отдельном потоке
        //(не вызвать из кода)(вручную не вызвать)
        Thread.currentThread().setName("First Thread");// Thread-0 переименован
        System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
    }

}

// второй вариант
class SecondThread implements Runnable {
    @Override
    public void run() {//инструкции потока
        for (int i = 0; i<3; i++){
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }

    }
}
