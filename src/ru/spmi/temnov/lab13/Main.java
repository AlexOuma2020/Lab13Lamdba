package ru.spmi.temnov.lab13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private void countAll(Countable2 countable, String name){
        countable.count(name);
    }//метод для ссылки на метод
    private TV[] tv = new TV[5];
    private Fridge[] fridge = new Fridge[5];
    Main(){
        for (int i = 0; i < 5; ++i){
            tv[i] = new TV(RandomGenerator.getRandomComp(),RandomGenerator.getRandomScreen());
            fridge[i] = new Fridge(RandomGenerator.getRandomComp(), RandomGenerator.getRandomShelf());
        }
    }
    public void setTV(TV[] tvs){
        tv = tvs;
    }

    public void setFridge(Fridge[] fr){
        fridge = fr;
    }
    private void print(){
        System.out.println("Список телевизоров: ");
        for (TV value : tv) value.show();
        System.out.println("\nСписок холодильников: ");
        for (Fridge value : fridge) value.show();
        System.out.println();
    }
    private boolean find(String inp){
        for (String comp: RandomGenerator.getAll()){
            if (comp.equals(inp))
                return true;
        }
        return false;
    }
    private String inputCompany() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
    public String menu() {//без понятия как сделать тест
        String needed = null;
        boolean excep;
        do {
            excep = true;
            System.out.print("Введите название фирмы, количество товаров которой хотите узнать {LG, Haier, Sharp, Samsung, Bosch, Siemens, Hitachi}: ");
            try {
                needed = inputCompany();
            } catch (IOException e) {
                excep = false;
                System.out.println("Неверный формат ввода!");
            }
            if (!find(needed)) {
                System.out.println("Несуществующая фирма " + needed);
                excep = false;
            }
        } while (!excep);
        return needed;
    }

    private int count(String needed, boolean option){//вычисление ВП
        if (option){//если true
            Appliances.zeroize();
            System.out.println("Задание 1. Лямбда-выражения");
            Countable countable = (app) -> {if(app.getName().equals(needed)) Appliances.incrNum();};//лямбда выражение
            for (int i = 0; i < tv.length; ++i){
                countable.count(tv[i]);
                countable.count(fridge[i]);
            }
        }
        else{
            Appliances.zeroize();
            System.out.println("2 задание. Ссылка на метод");
            for (int i = 0; i < tv.length; ++i){
                countAll(tv[i]::countApp, needed);//ссылка на метод
                countAll(fridge[i]::countApp, needed);
            }
        }
        return Appliances.getNum();
    }

    public TV[] getTV(){
        return tv;
    }

    public Fridge[] getFridge(){
        return fridge;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.print();
        String firm = (m.menu());
        System.out.printf("Товаров заданной фирмы = %d\n", m.count(firm, true));
        System.out.printf("Товаров заданной фирмы = %d", m.count(firm, false));
    }
}
