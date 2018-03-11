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

    public long getProtein() {
        return protein;
    }

    public void setProtein(long protein) {
        this.protein = protein;
    }

    public long getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(long carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public long getSugar() {
        return sugar;
    }

    public void setSugar(long sugar) {
        this.sugar = sugar;
    }

    public long getFat() {
        return fat;
    }

    public void setFat(long fat) {
        this.fat = fat;
    }

    public long getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(long saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public long getFibre() {
        return fibre;
    }

    public void setFibre(long fibre) {
        this.fibre = fibre;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "Intake{" +
            "protein=" + protein +
            ", carbohydrate=" + carbohydrate +
            ", sugar=" + sugar +
            ", fat=" + fat +
            ", saturatedFat=" + saturatedFat +
            ", fibre=" + fibre +
            ", energy=" + energy +
            '}';
    }
}
