package ru.ifmo.base.multithreading.lesson25;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQ {

    public static void main(String[] args) {
        //    DelayQueue - для добавления элементов в очередь - элементы должны имплементировать интерфейс Delay

        DelayQueue<Actions> actions = new DelayQueue<>();
        actions.put((new Actions(LocalDateTime.now().plusMinutes(2),() ->{
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("now()");
        })));
        actions.put(new Actions(LocalDateTime.of(2020, 5, 20, 17, 31), ()->{
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();

                System.out.println("old");
            }}));

        while (true) {
            try {
                new Thread(actions.take().getRunnable()).start();// take вернет объект типа action
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}




class Actions implements Delayed {

    // будут храниться упорядлоченными во времени

    private final Runnable runnable;
    private final LocalDateTime dateTime;

    public Runnable getRunnable() {
        return runnable;
    }

    public Actions(LocalDateTime dateTime, Runnable runnable) {
        this.runnable = runnable;
        this.dateTime = dateTime;


    }

    @Override
    public long getDelay(TimeUnit unit) {// показывает можно извлекать или нет
        return unit.convert(Duration.between(LocalDateTime.now(), dateTime).toSeconds(), TimeUnit.SECONDS);
    }
    // если метод возвр 0 или -, значит можно извлекать
    // все просроченные и текущие задачи сможем извлечь
    // будем получать объект типа Action


    @Override
    public int compareTo(Delayed o) {// принимает на вход объект типа Delayed. Нужно привести к одному типу и сравнить по времени
        return dateTime.compareTo(((Actions) o).dateTime);
    }
}
