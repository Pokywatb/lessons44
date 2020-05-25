package ru.ifmo.base.lesson22;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamBase {
    public static void main(String[] args) {
         /*Stream API - набор методов для работы с данными, как с потоком
         позволяет представить различные наботы данных в виде потока
         И далее: сортировать их, фильтровать, осуществлять поиск по различным критериям,
         кроме этого позволяет создавать новые потоки, создавать коллекции и мапы на основе потока данных*/
        // Stream НЕ ХРАНИТ ДАННЫЕ Для сохранения данных из Stream нужно использовать специальные методы

         /*Для работы с потоками данных:
         1. получить данные в виде потока - объект типа Stream
         2. выполнить промежуточные операции с потоком данных
         (промежуточные операции обрабатывают данные и возвражают Stream объект)
         3. выполнить терминальную (конечную) операцию
         (терминальная операция обрабатывает данные и завершает работу стрима)
         Без терминальной операции промежуточные операции не начнут выполняться!!!
         Например, получили объект stream
         промежуточные операции
         stream.операция1() - вернет преобразованный объект stream
               .операция2() - вернет преобразованный объект stream
               .операция3()  - вернет преобразованный объект stream
               .терминальнаяОперация(); // запускает промежуточные операции, данные обрабатываются, стрим закрывается
         терминальные forEach / findFirst / findAny / xxxMatch / min / max / collect*/

         /*методы получения Stream объектов:
         из коллекций collection.stream();
         из массива Arrays.stream(arr);
         из файла Files.lines(path_to_file);
         из строки string.chars();
         используя builder:
         Stream.builder().add(obj1).add(obj2).add(objN).build();
         Stream.of(1, 4, 7); любой набор данных*/


        Stream<Integer> integerStream = Stream.of(6, 8, 300, -44, 0, -111);// создали массив и открыли как поток
        integerStream.filter(num -> num < 0).map(num -> num * 10).limit(2).forEach(System.out::println);
        // forEach терминальная операция
                // limit берет указанное колличество элементов, остальные удаляет
                //map проводит с элементом операцию и втсавляет на место предыдущего
                //filter оставит в стриме только те элементы, которые удовлетворяют условию
// filter - промежутачная операция, чтото делает и возвращает массив как поток
        //filter принимает на вход предикат

        integerStream = Stream.of(45, 67, 45, -500, 0, -500, 120, 45);
        integerStream.distinct().sorted().forEach(System.out::println);
                // sorted отсортирует по возрастанию
                //distinct - оставляет только уникальные элементы

        //терминальные операции
        // anyMatch - хотя - бы один \ allMatch - все \ noneMatch - ни один
        // все эти опериации принимают предикат и возвращают true-false

        integerStream = Stream.of(6, 8, 300, -44, 0, -111, -6);
        System.out.println(integerStream.anyMatch(num -> num == 300));
        integerStream = Stream.of(6, 8, 300, -44, 0, -111, -6);
        System.out.println(integerStream.allMatch(num -> num > 0));
        integerStream = Stream.of(6, 8, 300, -44, 0, -111, -6);
        System.out.println(integerStream.noneMatch(num -> num > 5000));

        String[] stringArr = {"cat", "dog", "pig"};

        //еще терминальные операции
        //findFirst - получение первого элемента
        //findAny - получение Любого элемента
        // возвращают объект типа Optional<T> - null safe container

        String s = Arrays.stream(stringArr).findFirst().get();
        System.out.println(Arrays.stream(stringArr).findAny());
        s = Arrays.stream(stringArr).findFirst().orElse("default");
        System.out.println(s);
        boolean isElemPresent = Arrays.stream(stringArr).findFirst().isPresent();

        Arrays.stream(stringArr).skip(1).filter(s1 -> s1.endsWith("g")).forEach(System.out::println);
                //skip выкидывает указанное число первых элемментов из стрима



        ArrayList<User> users = new ArrayList<>();
        users.add(new User("qwe", 34));
        users.add(new User("asd", 56));
        users.add(new User("zxc", 18));
        users.add(new User("qwe", 34));
        users.add(new User("zxc", 22));


//        массив пользователей младше 30
        User[] usersArr = users.stream().filter(user -> user.getAge() < 30).toArray(User[]::new);
                //toArray - для собиранния массива из стрима без аргументов вернет объект типа Object[]

        System.out.println(Arrays.toString((usersArr)));

        List<User> userList = users.stream().filter(user -> user.getAge() > 30).peek(user -> user.setActive(true)).collect(Collectors.toList());
                // peek - метод для изменения свойства объекта
        // collect - если из стрима собираем не массив

        Set<User> userSet = users.stream().filter(user -> user.getAge() > 20).collect(Collectors.toSet());

        ArrayList<User> userArrayList = users.stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toCollection(ArrayList::new));

        //терминальнные операции
        //min/max
        //возвращают объект типа Optional<T>
        ///принимают на вход компаратор

        User minUser = users.stream().min(Comparator.comparing(User::getAge)).orElse(new User("default user", 11));
        System.out.println("min user" + minUser);

        String[] colors = {"red", "black", "orange", "yellow", "green"};
        Map<String, Integer> colorMap = Arrays.stream(colors).collect(Collectors.toMap(Function.identity(), String::length, (item1, item2) -> item1));

        HashMap<String, Integer> colorHashMap = Arrays.stream(colors).collect(Collectors.toMap(Function.identity(), String::length, (item1, item2) -> item1, HashMap :: new));

        List<String> list1 = Arrays.asList("33", "33", "55");
        List<String> list2 = Arrays.asList("111", "34", "11");
        List<String> list3 = Arrays.asList("0", "-33", "155");

        Stream.of(list1, list2, list3).flatMap(elem -> elem.stream().distinct().sorted()).forEach(System.out::println);

        List<Integer> stringList = Stream.of("33", "55", "77").flatMap(elem -> Stream.of(Integer.parseInt(elem))).collect(Collectors.toList());
        // каждый элемент стрима преобразуется в новый стрим, а потом собирается в общий





    }
}