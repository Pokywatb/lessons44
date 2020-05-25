package ru.ifmo.base.lesson20;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

public class NIOBuffer {
    public static void main(String[] args) {

        //Java NIO (New IO aka Non-blocking IO) - предназначенн для работы с вводом-выводм.
        // Неблокирующий
        // Асинхронный
        // Буфер-ориентированный

        //Есть набор классов для работы с файловой системой и набор классов для работы с вводом-выводом
        //каналы, буферы, селекторы.- основные составляющие пакета NIO
        // в NIO нет in и out каналов, есть просто каналы, которые подходят и для чтения и для записи. Действия в канале выполняются асинхронно
        // одновременно может происходить и чтение и запись.
        // буфер используется в любом случае. Данные и на вход и на выход сначала попадают в буфер

        // Виды каналов:
        // - FileChannel - работа с файлами
        // - DatagramChannel - udp протокол
        // - SocketChannel - tcp протокол
        // - ServerSocketChannel - ждет входящих соединений, после чего устанавливает связь между клиентским и серверным SocketChannel

        // Виды буферов.
        // при создании буфера - внутри создается массив(с типом данных по типу буфера)
        // ByteBuffer
        // CharBuffer
        // DoubleBuffer
        // FloatBuffer
        // IntBuffer
        // LongBuffer
        // ShortBuffer

        // свойсва буфера:
        // capacity - емкость (не меняется после установки)
        // position - текущая позоция в буфере (изначально 0)
        // limit - до какого значения можно читать/писать данные (изначально равен capacity)
        // limit изначально равен емкости и больше емкости быть не может, но может быть меньше емкости.



        //Каналы:
        // канал может быть использован и для записи и для чтения
        // чтение и запись может происходить асинхронно
        // каналы всегда пишут в буфер и читают из буфера
        // Каналы могут быть созданы на основе:
        // - FileChannel
        // - DatagramChannel
        // - SocketChannel
        // - ServerSocketChannel

        // Буферы могут быть:
        // ByteBuffer
        // CharBuffer
        // DoubleBuffer
        // FloatBuffer
        // IntBuffer
        // LongBuffer
        // ShortBuffer

        // свойсва буфера:
        // capacity - емкость (не меняется после установки)
        // position - текущая позоция в буфере (изначально 0)
        // limit - до какого значения можно читать/писать данные (изначально равен capacity)

        ByteBuffer buffer = ByteBuffer.allocate(16);// создание буфера с указанием размера массива allocate()
        assert buffer.position() == 0;
        assert buffer.capacity() == 16;
        assert buffer.limit() == 1655; // лимит равен емкости т.к. бувер новый.
        assert buffer.remaining() == 16; // разница между position и limit

        // Увеличивает позицию на 4. т.к. int занимает 4 байта
        buffer.putInt(100);

        assert buffer.position() == 4;
        assert buffer.remaining() == 12;

        // Увеличивает позицию на 8. т.к. double занимает 8 байт
        buffer.putDouble(100.25);

        assert buffer.position() == 12;
        assert buffer.remaining() == 4;
        // после добавления int и double позиция сместилась на 12. Последние 4 ячейки не заняты.
        // если просто читать из этого буфера, то чтение начнется с позиции до лимита( т.е. с 12 до лимита)

        // Устанавливает лимит на место позиции, сбрасывает позицию в 0 (для чтения из буфера)
        // Сначала усстанавливает лимит на позицию, потом переносит позицию на 0. Получается чтение с 0 до места где была позиция до использования flip
        buffer.flip();

        assert buffer.position() == 0;
        assert buffer.limit() == 12;
        assert buffer.remaining() == 12;

        // чтение смещщает позицию на колличество прочитанных позиций.

        // Увеличивает позицию на 4.
        int anInt = buffer.getInt();

        assert buffer.position() == 4;
        assert buffer.remaining() == 8;

        // Увеличивает позицию на 8.
        double aDouble = buffer.getDouble();

        assert buffer.position() == 12;
        assert buffer.remaining() == 0;

        // Сбрасывает позицию в 0 (limit остается на прежнем месте - для повторного чтения)
        buffer.rewind();

        assert buffer.position() == 0;
        assert buffer.limit() == 12;
        assert buffer.remaining() == 12;

        // Увеличивает позицию на 4.
        assert anInt == buffer.getInt();
        // Увеличивает позицию на 8.
        assert aDouble == buffer.getDouble();

        // Сбрасывает позицию в 0, ставит лимит, равный емкости буфера (для записи в буфер)
        buffer.clear();// ничего не удаляет из буфера, только переставляет позицию в 0 и лимит в емкость.

        assert buffer.position() == 0;
        assert buffer.capacity() == 16;
        assert buffer.limit() == 16;
        assert buffer.remaining() == 16;


        //TODO buffer.compact();
        // копирует все непрочитанные данные в начало буфера. Лимит равен емкости буфера
        // (для записи в буфер после непрочитанных данных)


//        ByteBuffer(7)
//        34
//        544545
//        position(2)
//
//
//        capacity(7) limit(7)


    }
}