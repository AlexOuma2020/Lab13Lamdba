package ru.spmi.temnov.lab13;

public class RandomGenerator {
    private static final String[] company = new String[]{"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"};
    public static String getRandomComp(){
        return company[(int)(Math.random() * company.length)];
    }
    public static int getRandomScreen(){
        return (int) (Math.random() * 18) + 22;
    }
    public static int getRandomShelf(){
        return (int) (Math.random() * 4) + 3;
    }
    public static String[] getAll(){
        return company;
    }
}