package ru.ifmo.base.multithreading.lesson23;

import java.util.ArrayList;
import java.util.EventListener;

public class SomeClass {
    public static void main(String[] args) {
        //анонимные классы
        // можно создать только на основе интерфейса или абстрактного класса

        //создание объекта на основе класса
        Eventlistener firstListener = new Eventlistener() {//отсюда
            @Override
            public void greenEvent(int code) {
                System.out.println("реакция на firstlistener" + code);

            }

            @Override
            public void yellowEvent(int code) {
                System.out.println("реакция на firstlistener" + code);

            }

            @Override
            public void redEvent(int code) {
                System.out.println("реакция на firstlistener" + code);

            }
        };// до сюда - анонимный класс

        Eventlistener secondListener = new Eventlistener() {
            @Override
            public void greenEvent(int code) {

            }

            @Override
            public void yellowEvent(int code) {

            }

            @Override
            public void redEvent(int code) {

            }
        };
        System.out.println(firstListener.getClass().getName());
        System.out.println(secondListener.getClass().getName());

    }
}



interface Eventlistener{
    void greenEvent(int code);
    void yellowEvent(int code);
    void redEvent(int code);
}

class StateClass{
    //хотим сделать так, чтобы при изменении состояния StateClass другие классы (FirstListener, SecondListener)
    // получили об этом информацию:
    // у получающих инфо должен быть общий интерфейс
    // создаем список получателей
    private ArrayList<EventListener> eventListeners = new ArrayList<>(); //этих будет уведомлять

    public void addListener(EventListener listener){
        eventListeners.add(listener);
    }
    public void removeListener (EventListener listener){
        eventListeners.remove(listener);
    }

   //
}