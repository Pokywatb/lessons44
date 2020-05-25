package ru.ifmo.base.multithreading.lesson26;

import java.util.concurrent.*;

public class TstPool {
    public static void main(String[] args) {
        ExecutorService pool = new ThreadPoolExecutor(
                2, // изначальное количество потоков
                5, // маскимальное количество потоков
                10, // сколько хранить простаивающий поток
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>() // передаем ссылку на очередь для задач, можно уже с задачами
        );

    }
}

//если хотим создать свой собственный пул, то он должен наследоваться от ThreadPoolExecutor
class SomeExecutor extends ThreadPoolExecutor {
    public SomeExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("before task");

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("after task");
    }
}