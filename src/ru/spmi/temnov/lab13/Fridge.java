package ru.spmi.temnov.lab13;

public final class Fridge extends Appliances{
    private final int shelf_num;
    Fridge(String name, int shelf_num){
        super(name);
        this.shelf_num = shelf_num;
    }
    @Override
    public void show(){
        System.out.printf("Холодильник компании %s с %d полками внутри\n", name, shelf_num);
    }
}
