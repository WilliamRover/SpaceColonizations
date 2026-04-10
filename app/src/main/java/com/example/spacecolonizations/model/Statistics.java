package com.example.spacecolonizations.model;

import java.util.HashMap;

public class Statistics {
    private static Statistics instance;
    private int shipKills;
    private int numDeadCrews;
    private int numLivingCrews;
    private int numTotalCrews;
    private int numSuccessfulMissions;
    private int numFailedMissions;


    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    private Statistics() {
        this.shipKills = 0;
        this.numDeadCrews = 0;
        this.numLivingCrews = 6;
        this.numTotalCrews = 6;
        this.numSuccessfulMissions = 0;
        this.numFailedMissions = 0;
    }

    //Getters
    public int getNumDeadCrews() {
        return numDeadCrews;
    }

    public int getNumLivingCrews() {
        return numLivingCrews;
    }

    public int getNumTotalCrews() {
        return numTotalCrews;
    }

    public int getNumFailedMissions() {
        return numFailedMissions;
    }

    public int getNumSuccessfulMissions() {
        return numSuccessfulMissions;
    }

    public int getShipKills() {
        return shipKills;
    }

    /**
     * Get all statistics in a hashmap for the use of save file
     * @return a hashmap of all statistics
     */
    public HashMap<String, Integer> getStatistics() {
        HashMap<String, Integer> stats = new HashMap<>();

        stats.put("shipKills", shipKills);
        stats.put("numDeadCrews", numDeadCrews);
        stats.put("numLivingCrews", numLivingCrews);
        stats.put("numTotalCrews", numTotalCrews);
        stats.put("numSuccessfulMissions", numSuccessfulMissions);
        stats.put("numFailedMissions", numFailedMissions);

        return stats;
    }

    public void resetStatistics() {
        this.shipKills = 0;
        this.numDeadCrews = 0;
        this.numLivingCrews = 6;
        this.numTotalCrews = 6;
        this.numSuccessfulMissions = 0;
        this.numFailedMissions = 0;
    }

    // Setters
    public void setShipKills(int shipKills) {
        this.shipKills = shipKills;
    }

    public void setNumDeadCrews(int numDeadCrews) {
        this.numDeadCrews = numDeadCrews;
    }

    public void setNumLivingCrews(int numLivingCrews) {
        this.numLivingCrews = numLivingCrews;
    }

    public void setNumTotalCrews(int numTotalCrews) {
        this.numTotalCrews = numTotalCrews;
    }

    public void setNumFailedMissions(int numFailedMissions) {
        this.numFailedMissions = numFailedMissions;
    }

    public void setNumSuccessfulMissions(int numSuccessfulMissions) {
        this.numSuccessfulMissions = numSuccessfulMissions;
    }
}
