package model;

import java.util.List;

public class Pokemon {
    private String name;
    private List<String> types;
    private int attack, defense, speed, hp;
    private double height, weight;
    private boolean isLegendary;
    private String spriteUrl;

    // Konstruktor
    public Pokemon(String name, List<String> types, int attack, int defense,
                   int speed, int hp, double height, double weight,
                   boolean isLegendary, String spriteUrl) {
        this.name = name;
        this.types = types;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.hp = hp;
        this.height = height;
        this.weight = weight;
        this.isLegendary = isLegendary;
        this.spriteUrl = spriteUrl;
    }

    // Gettery
    public String getName() { return name; }
    public List<String> getTypes() { return types; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public int getHp() { return hp; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public boolean isLegendary() { return isLegendary; }
    public String getSpriteUrl() { return spriteUrl; }
}
