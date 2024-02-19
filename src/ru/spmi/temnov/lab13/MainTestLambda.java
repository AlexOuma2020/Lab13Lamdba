package ru.spmi.temnov.lab13;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainTestLambda {
    private void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void getTVTest(){
        TV tv = new TV("Siemens", 29);
        assertEquals("Siemens",tv.getName());
        System.out.println("Геттер TV работает корректно\n");
    }
    @Test
    void getFridgeTest(){
        Fridge fr = new Fridge("LG", 4);
        assertEquals("LG",fr.getName());
        System.out.println("Геттер Fridge работает корректно\n");
    }
    @Test
    void numTest(){
        Fridge fr = new Fridge("Samsung", 6);
        TV tv = new TV("Bosch", 30);
        assertEquals(0, Appliances.getNum());
        System.out.println("Геттер num работает корректно");
        Appliances.incrNum();
        assertEquals(1, Appliances.getNum());
        System.out.println("incrNum работает корректно");
        tv.countApp("Samsung");
        fr.countApp("Samsung");
        assertEquals(2, Appliances.getNum());
        System.out.println("countApp работает корректно");
        Appliances.zeroize();
        assertEquals(0, Appliances.getNum());
        System.out.println("zeroize работает корректно");
    }
    @Test
    void randGetRandomCompanyTest(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertTrue((Boolean)method.invoke(m, RandomGenerator.getRandomComp()));
            System.out.println("Случайное значение массива company работает корректно\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void companyGetAllTest(){
        assertArrayEquals(new String[]{"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"}, RandomGenerator.getAll());
        System.out.println("Получение массива фирм работает корректно\n");
    }

   @Test
    public void showTest(){
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        TV tv= new TV("LG", 27);
        Fridge fr= new Fridge("Samsung", 7);
        tv.show();
        assertEquals("Телевиор компании LG с диагональю 27''\n",  outputStreamCaptor.toString());
        System.setOut(standardOut);
        System.out.println("Вывод информации о телевизоре корректен " + outputStreamCaptor.toString());
        System.setOut(new PrintStream(outputStreamCaptor));
        fr.show();
        assertEquals("Телевиор компании LG с диагональю 27''\nХолодильник компании Samsung с 7 полками внутри\n",  outputStreamCaptor.toString());
        System.setOut(standardOut);
        System.out.println("Вывод информации о холдильнике корректен " + outputStreamCaptor.toString());
    }


    @Test
    public void testFound1(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(true, method.invoke(m, "LG"));
            System.out.println("Товар фирмы LG существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound2(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, "Qwerty"));
            System.out.println("Товара фирмы lG не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound3(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, ""));
            System.out.println("Товара фирмы без названия не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest1(){
        Main m = new Main();
        provideInput("Haier");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertEquals("Haier", output);
            System.out.println("Ввод Haier подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest2(){
        Main m = new Main();
        provideInput("");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertNull(output);
            System.out.println("Ввод пустой строки подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private HashMap<String, Integer> counter(Main m){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String st: RandomGenerator.getAll()){
            Integer num = 0;
            for (TV app: m.getTV())
                if (app.getName().equals(st))
                    ++num;
            for (Fridge app: m.getFridge())
                if (app.getName().equals(st))
                    ++num;
            hashMap.put(st, num);
        }
        return hashMap;
    }

    @Test
    public void countTestNotRandom() throws RuntimeException {//неслучайные значения
        System.out.println("Заданные значения!\n");
        Main m = new Main();

        TV[] tv = new TV[]{
                new TV("Hitachi",RandomGenerator.getRandomScreen()),
                new TV("Haier",RandomGenerator.getRandomScreen()),
                new TV("Sharp",RandomGenerator.getRandomScreen()),
                new TV("Samsung",RandomGenerator.getRandomScreen()),
                new TV("Siemens",RandomGenerator.getRandomScreen())
        };
        m.setTV(tv);

        Fridge[] fridge = new Fridge[]{
                new Fridge("Samsung",RandomGenerator.getRandomShelf()),
                new Fridge("Samsung",RandomGenerator.getRandomShelf()),
                new Fridge("Hitachi",RandomGenerator.getRandomShelf()),
                new Fridge("Bosch",RandomGenerator.getRandomShelf()),
                new Fridge("Hitachi",RandomGenerator.getRandomShelf())
        };
        m.setFridge(fridge);

        try {
            Method method = Main.class.getDeclaredMethod("print");
            method.setAccessible(true);
            method.invoke(m, null);

            method = Main.class.getDeclaredMethod("count", String.class, boolean.class);//лямбда
            method.setAccessible(true);

            int n = (int)method.invoke(m, "LG", true);
            assertEquals(0, n);
            System.out.printf("Количество товаров фирмы LG равно %d\n", n);

            n = (int)method.invoke(m, "Siemens", true);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Siemens равно %d\n", n);

            n = (int)method.invoke(m, "Sharp", true);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Sharp равно %d\n", n);

            n = (int)method.invoke(m, "Haier", true);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Haier равно %d\n", n);

            n = (int)method.invoke(m, "Hitachi", true);
            assertEquals(3, n);
            System.out.printf("Количество товаров фирмы Hitachi равно %d\n", n);

            n = (int)method.invoke(m, "Samsung", true);
            assertEquals(3, n);
            System.out.printf("Количество товаров фирмы Samsung равно %d\n", n);

            n = (int)method.invoke(m, "Bosch", true);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Bosch равно %d\n\n", n);

////////////ссылка на метод
            n = (int)method.invoke(m, "LG", false);
            assertEquals(0, n);
            System.out.printf("Количество товаров фирмы LG равно %d\n", n);

            n = (int)method.invoke(m, "Siemens", false);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Siemens равно %d\n", n);

            n = (int)method.invoke(m, "Sharp", false);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Sharp равно %d\n", n);

            n = (int)method.invoke(m, "Haier", false);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Haier равно %d\n", n);

            n = (int)method.invoke(m, "Hitachi", false);
            assertEquals(3, n);
            System.out.printf("Количество товаров фирмы Hitachi равно %d\n", n);

            n = (int)method.invoke(m, "Samsung", false);
            assertEquals(3, n);
            System.out.printf("Количество товаров фирмы Samsung равно %d\n", n);

            n = (int)method.invoke(m, "Bosch", false);
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Bosch равно %d\n\n", n);
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

   @Test
    public void countTest() throws RuntimeException {//случайные значения
        System.out.println("Случайные значения!\n");
        Main m = new Main();
        try {
            HashMap<String, Integer> num = counter(m);

            Method method = Main.class.getDeclaredMethod("print");
            method.setAccessible(true);
            method.invoke(m, null);

            method = Main.class.getDeclaredMethod("count", String.class, boolean.class);
            method.setAccessible(true);

            for (String st: RandomGenerator.getAll()){//лямбда
                assertEquals(num.get(st), method.invoke(m, st, true));
                System.out.printf("Количество товаров фирмы %s равно %d\n", st, num.get(st));
            }
            System.out.println();

            for (String st: RandomGenerator.getAll()){//ссылка на метод
                assertEquals(num.get(st), method.invoke(m, st, false));
                System.out.printf("Количество товаров фирмы %s равно %d\n", st, num.get(st));
            }
            System.out.println();
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
