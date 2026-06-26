package ch.noseryoung.tamagotchi;

public class Pet {

    private int energy;
    private int appetite;
    private int happiness;

    public Pet(int energy, int appetite, int happiness) {
        this.energy = energy;
        this.appetite = appetite;
        this.happiness = happiness;
    }

    public void increaseEnergy(int n) {
        energy = Math.min(energy + n, 100);
    }

    public void decreaseEnergy(int n) {
        energy = Math.max(energy - n, 0);
    }

    public void increaseAppetite(int n) {
        appetite = Math.min(appetite + n, 100);
    }

    public void decreaseAppetite(int n) {
        appetite = Math.max(appetite - n, 0);
    }

    public void increaseHappiness(int n) {
        happiness = Math.min(happiness + n, 100);
    }

    public void decreaseHappiness(int n) {
        happiness = Math.max(happiness - n, 0);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getAppetite() {
        return appetite;
    }

    public void setAppetite(int appetite) {
        this.appetite = appetite;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
}