package com.mac.nutritio.web.rest.util;

public class Intake {
    private long protein;
    private long carbohydrate;
    private long sugar;
    private long fat;
    private long saturatedFat;
    private long fibre;
    private long energy;

    public Intake() {
        this.protein = 0;
        this.carbohydrate = 0;
        this.sugar = 0;
        this.fat = 0;
        this.saturatedFat = 0;
        this.fibre = 0;
        this.energy = 0;
    }

    public void addProtein(long amount) {
        this.protein += amount;
    }

    public void addCarbohydrate(long amount) {
        this.carbohydrate += amount;
    }

    public void addSugar(long amount){
        this.sugar += amount;
    }

    public void addFat(long amount) {
        this.fat += amount;
    }

    public void addSaturatedFat(long amount) {
        this.saturatedFat += amount;
    }

    public void addFibre(long amount) {
        this.fibre += amount;
    }

    public void addEnergy(long amount) {
        this.energy += amount;
    }
}
