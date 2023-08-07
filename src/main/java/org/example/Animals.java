package org.example;

public abstract class Animals {
    protected static int id;
    protected String animal_name;
    public  String animal_cmd;
    public String animal_birthday;
    protected int groupe_id;
    protected String species;
    protected String groupe_name;
    static {id = 18;}
    public Animals() {
        id++;
    }
    public Animals(String type, String species, String name, String animal_birthday) {
        this.animal_name = name;
        this.animal_birthday = animal_birthday;
        this.animal_cmd = "None";
        this.groupe_name = type;
        this.species = species;
        id++;
    }
}
