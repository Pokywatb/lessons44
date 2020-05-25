package ru.ifmo.base.multithreading.lesson26;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {
    public static void main(String[] args) {
        //  возвращение данных из потока
        // задачи должны быть с Callable интерфейсом
        // сначала подготовливаем контейнеры Future.
        // данные из контейнера получаем методом .get
        // .get будет ждать указанное количество времени или когда в контейнере чтото появится

        // создаем пул потоков
        ExecutorService service = Executors.newFixedThreadPool(2);
        // делаем задачу
        Callable<Unit> unitCallable = new UnitCreator();


        // готовим контейнеры
        ArrayList<Future<Unit>> futures = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            // передаем задачу на выполнение пулу потоков/ submit т.к. надо получить данные
            Future<Unit> future = service.submit(unitCallable);
            // submit возвращает объект типа future (берет задачу на выполнение и возвращает связанный с этой задачей контейнер)
            futures.add(future);
        }

        service.shutdown();

//        for (Future<Unit> future: futures){
//            System.out.println("Waiting Future...");
//            try {
//                System.out.println("Unit " + future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        // ждет указанное кол-во времени

        for (Future<Unit> future: futures){
            System.out.println("Waiting Future...");
            try {
                System.out.println("Unit " + future.get((long)(Math.random()*30000), TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("не смог дожэаться юнита");;
            }
        }

        ExecutorService service2 = Executors.newFixedThreadPool(2);

        List<Callable<Unit>> taskList = new ArrayList<>();
        taskList.add(unitCallable);
        taskList.add(unitCallable);
        taskList.add(unitCallable);

        try {
            List<Future<Unit>> futureList = service2.invokeAll(taskList);
            service2.shutdown();

            //invokeAll берет список задач на выполнение, когда все выполнятся - даннные появятся в объектах типа future
            for(Future<Unit> future: futureList){
               // future.cancel(true\false) - прерывает текущую задачу
                // future.isCancelled() - проверяет была ли отменена
                // future.isDone() - вернет true, если задача нормельно завершилась
                // service.isShutdown() -

                System.out.println("Unit from service2" + future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class UnitCreator implements Callable<Unit> {

    @Override
    public Unit call() {
        Random random = new Random(new Date().getTime());
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Unit unit = new Unit();
        unit.setName(Thread.currentThread().getName());
        unit.setHealth(random.nextInt(20) + 1);
        unit.setAttackScore(random.nextInt(15) + 1);
        return unit;
    }

}


class Unit {
    private String name;
    private int health;
    private int attackScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackScore() {
        return attackScore;
    }

    public void setAttackScore(int attackScore) {
        this.attackScore = attackScore;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attackScore=" + attackScore +
                '}';
    }
}
