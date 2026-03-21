package model;

public class FriendlyShip {
    private int hullStrength;
    private int energy;

    public FriendlyShip(int hullStrength, int energy) {
        this.hullStrength = hullStrength;
        this.energy = energy;
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
        if (hullStrength <= 0) {
            this.hullStrength = 0;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
