package ru.ifmo.base.multithreading.lesson26;

import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadsPool {
    public static void main(String[] args) {
        // пул потоков
        // пулу потоков передаются задачи на выполнение
        // пул распределяет задачи между потоками
        // если задач больше, новую задачу берет первый освободившийся поток
        // пулы потоков не для бесконечных задач

        // если какой то поток вылетел с необработанный эксепшном, исползовать его дальше будет невозможно, по сути поток исчезает из пула.

        // связанные задачи передовать в порядке необходимом для корректного выполнения


        // создаем пул
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);// в скобках количество потоков

        // передаем задачи на выполнение
        for (int i = 0; i < 10 ; i++) {
            fixedPool.execute(new RunnableTask("fixedpool yf 2 потока"));
        }
        //fixedPool.shutdown();
        // метод ждет пока выполнятся все задачи из очереди, но новых не принимает. после этого метода повторно обратиться к пулу уже нельзя,  будет эксепшн

       // List<Runnable> runnables = fixedPool.shutdownNow();
//        System.out.println(runnables);
        // прекращает выполнение задач сразу, и возвращает список незавершенных задач

        // возвращение пула на один поток
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        singleThread.execute(new RunnableTask("singleThread"));
        singleThread.execute(new RunnableTask("singleThread"));
        fixedPool.shutdown();

        // отложенное выполнение
        ScheduledExecutorService dService = Executors.newSingleThreadScheduledExecutor();
        dService.schedule(new RunnableTask("dService"), 5, TimeUnit.SECONDS);// выполнение через указанное время
        dService.shutdown();

        ScheduledExecutorService everyFiveSecondService = Executors.newSingleThreadScheduledExecutor();
        everyFiveSecondService.scheduleAtFixedRate(
                new RunnableTask("everyFiveSecondService"),// сначалаа указали задачу
                0, //первое выполнение
                5,// период повторения
                TimeUnit.SECONDS
                // период отсчитывается от начала выполнения задачи, если не успела выполниться первая, запуск второй все равно произойдет.
        );
        everyFiveSecondService.shutdown();

        ScheduledExecutorService everySecondFromEnd = Executors.newSingleThreadScheduledExecutor();
        everySecondFromEnd.scheduleWithFixedDelay(
                new RunnableTask("everySecondFromEnd"),
                0,
                1,// сколько надо ждать, после ВЫПОЛНЕНИЯ предыдущей задачи
                TimeUnit.SECONDS
        );
        everySecondFromEnd.shutdown();

    }
}

//создаем задачи

class RunnableTask implements Runnable {

    private String poolName;

    public RunnableTask(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public void run() {
        System.out.println("Поток: " + Thread.currentThread().getName() + "из пула: " + poolName);
    }
}
