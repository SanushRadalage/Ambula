package com.intpro.decepticons;
public class Item {
    private String name;
    private boolean isChecked;

    public Item(){

    }

    public Item(String name, boolean isChecked){
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
