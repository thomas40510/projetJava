package com.apogee.dev.DuoVaders;

public class Player implements Ship{
    private double health;
    private double fireMode;
    private double speed;
    private double damage;
    private double shield;
    private double shieldRegen;
    private double shieldRegenDelay;
    private double position;

public Player(double health, double fireMode, double speed, double damage, double shield, double shieldRegen, double shieldRegenDelay, double position) {
        this.health = health;
        this.fireMode = fireMode;
        this.speed = speed;
        this.damage = damage;
        this.shield = shield;
        this.shieldRegen = shieldRegen;
        this.shieldRegenDelay = shieldRegenDelay;
        this.position = position;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public double getFireMode() {
        return fireMode;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public double getShield() {
        return shield;
    }

    @Override
    public double getShieldRegen() {
        return shieldRegen;
    }

    @Override
    public double getShieldRegenDelay() {
        return shieldRegenDelay;
    }

    public double getPosition() {
        return position;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setFireMode(double fireMode) {
        this.fireMode = fireMode;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setShield(double shield) {
        this.shield = shield;
    }

    public void setShieldRegen(double shieldRegen) {
        this.shieldRegen = shieldRegen;
    }

    public void setShieldRegenDelay(double shieldRegenDelay) {
        this.shieldRegenDelay = shieldRegenDelay;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public String toString(){
        return "Player{\tHealth: " + health + ",\n"
                + " \tFireMode: " + fireMode + ",\n"
                + " Speed: " + speed + ",\n"
                + " Damage: " + damage + ",\n" + " Shield: " + shield + ",\n"
                + " ShieldRegen: " + shieldRegen + ",\n"
                + " ShieldRegenDelay: " + shieldRegenDelay + ",\n"
                + " Position: " + position + "}";
    }

}
