package ru.spmi.temnov.lab13;

public abstract class Appliances{//суперкласс
    protected String name;
    protected static int num = 0;//значение ВП
    public static void zeroize(){
        num = 0;
    }//обнуление значения ВП
    Appliances(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public static void incrNum(){//инкременирования числа для ВП
        ++num;
    }

    public static int getNum(){
        return num;
    }

    abstract public void show();
    public void countApp(String needed){//метод расчета
        if(this.name.equals(needed))
            num++;
    }
}
