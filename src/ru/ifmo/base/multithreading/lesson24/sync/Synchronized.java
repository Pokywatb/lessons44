package ru.ifmo.base.multithreading.lesson24.sync;

import java.util.ArrayList;

public class Synchronized {
    public static void main(String[] args) {
        Counter counter = new Counter();

        ArrayList<IncrementThread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            threads.add(new IncrementThread(counter));
        }
        for (IncrementThread thread: threads){
            thread.start();
        }

        for (IncrementThread thread: threads){
            try {
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("counter =" + counter.getCounter());

    }
}

class Counter {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    //public void increment() {
    public synchronized void increment(){
        // поток держит монитор объекта, для того объекта, у которого вызывается метод
        // нельзя делать синхронизированные конструкторы, и нельзя использовать синхронизацию со свойствами

        counter++;
    }
}

class IncrementThread extends Thread {
    private Counter counter;

    public IncrementThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
//            synchronized блок на объекте counter
            // synchronized блокирует объект так, чтобы с ним одновременно мог работать только один поток
           // synchronized (counter)
            {
            counter.increment();}


        }
    }

}
