package ru.ifmo.base.multithreading.lesson25;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

public class BlockingQueue {
    public static void main(String[] args) {
        // при пустом конструкторе нет предела для добавления потока put


        LinkedBlockingDeque<Signal> signals = new LinkedBlockingDeque<>(12);// в скобках максимальный размер очереди, тип данных int
        // если для очереди задан лимит, то метод put заставит поток ждать пока из очереди не будут удалены элементы.

        new Thread(new WriteSignals(signals)).start();

        new Thread(new ReadSignals(signals)).start();

        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<>(100); // нужно указать колличество элементов которые может принять очередь
        ArrayBlockingQueue<String> strings2 = new ArrayBlockingQueue<>(100, true); // флаг справедливости, по умолчанию false, если несколько put/get потоков
        // для выстраивания этих потоков в очередь(чтобы не простаивали),
        // третий конструктор добавляет ссылку на коллекци.

//        SynchronousQueue - каждая операция добавления находится в ожидании соответствующей оперции удаления (добавили - удалили)

        // DelayQueue - в отдельном файле.

    }
}

class WriteSignals implements Runnable{
    private LinkedBlockingDeque<Signal> signals;

    public WriteSignals(LinkedBlockingDeque<Signal> signals){
        this.signals = signals;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Write" + Thread.currentThread().getState());

            try {
                Thread.sleep(5000);
                Signal signal = Signal.getSignal();
                signals.put(signal);
                System.out.println("Write Signal" + signal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class ReadSignals implements Runnable{
    private LinkedBlockingDeque<Signal> signals;

    public ReadSignals(LinkedBlockingDeque<Signal> signals) {
        this.signals = signals;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Read" + Thread.currentThread().getState());

            try {
                System.out.println("Read Signal" + signals.take());
                // take удаляет элемент из очереди и возвращает его, не позволит получить элемент из очереди, если там ничего нет
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// флаг поменяется и поток остановится
                e.printStackTrace();
            }
        }
    }
}


class Signal {
    enum Priority {
        HIGH, MEDIUM, LOW;
        public static Priority getPriority(int ord){
            for (Priority priority: values()){
                if (ord == priority.ordinal()){
                    return priority;
                }
            }
            throw new AssertionError("wrong ordinal");
        }
    }

    private Priority priority;
    private int code;

    public Signal(Priority priority, int code) {
        this.priority = priority;
        this.code = code;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "priority=" + priority +
                ", code=" + code +
                '}';
    }

    public static Signal getSignal(){
        Random random = new Random();
        return new Signal(
                Priority.getPriority(random.nextInt(Priority.values().length)),
                random.nextInt(30)
        );
    }
}