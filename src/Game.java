package src;

import java.util.*;

class Energy {
    private int amount;
    public Energy(int amount_) {
        this.amount = amount_;
    }
    public void change(int change_) {
        this.amount += change_;
    }
    public int get() {
        return this.amount;
    }
}

class Spaceship {
    private String name;
    private int capacity;
    private List<Technology> technologies;
    public Spaceship(String name_, int capacity_, List<Technology> technologies_) {
        this.name = name_;
        this.capacity = capacity_;
        this.technologies = technologies_;
    }
    public int energy() {
        return this.capacity;
    }
}

class Planet {
    String name;
    int maintenance;
    List<Technology> technologies_needed;
    public Planet(String name_, int maintenance_, List<Technology> technologies_needed_) {
        this.name = name_;
        this.maintenance = maintenance_;
        this.technologies_needed = technologies_needed_;
    }
}

class Colony {
    private String name;
    private Spaceship spaceship;
    private Energy energy;
    private List<Technology> technologies;
    private Planet planet;
    private boolean active;
    public Colony(String name_, Spaceship spaceship_, List<Technology> technologies_, Planet planet_) {
        this.spaceship = spaceship_;
        this.name = name_;
        this.technologies = technologies_;
        this.planet = planet_;
        this.energy = spaceship_.energy();
        this.active = true;
    }
    public void research(Technology technology_) {
        technologies.addLast(technology_);
    }
    public hold_out_a_move() {
        for (int i = 0; i < technologies.size(); i++) {

        }
    }
}

class Technology {
    public String name;
    public int cost;
    public Technology(String name_, int cost_) {
        this.name = name_;
        this.cost = cost_;
    }
}

class EnergyTechnology extends Technology {
    private int gain;
    public EnergyTechnology(String name_, int cost_, int gain_) {
        this.name = name_;
        this.cost = cost_;
        this.gain = gain_;
    }
    public int obtain() {
        return this.gain;
    }
}

public class Game {
    
}
