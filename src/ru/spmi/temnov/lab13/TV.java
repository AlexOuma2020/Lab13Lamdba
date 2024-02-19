package ru.spmi.temnov.lab13;

public final class TV extends Appliances{
    private final int screen;
    TV(String name, int screen){
        super(name);
        this.screen = screen;
    }
    @Override
    public void show(){
        System.out.printf("Телевиор компании %s с диагональю %d''\n", name, screen);
    }
}