package com.example.spacecolonizations.model.mission;

import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Barracks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Rescue extends Mission {
    private ArrayList<Crew> crewMembers;
    private int timeRequire;
    private long startTime;
    private transient boolean isStarted = false;

    public Rescue(String missionName){
        super(missionName);
        if (Statistics.getInstance().getShipKills() >= 3) {
            this.numCrew = 3;
        } else {
            this.numCrew = 2;
        }

        this.crewMembers = new ArrayList<>();
        this.timeRequire = 30;
        // Mission is NOT added to CrewManager here anymore to avoid duplication during load.
        // It should be added explicitly by the creator (e.g., RescueActivity).
    }
    
    @Override
    public String getMissionType(){
        return "Rescue";
    }

    public int getTimeRequire(){
        return timeRequire;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void damageCrew(){
        for (Crew c : crewMembers){
            double dice = Math.random()*100;
            double dice2 = (Math.random()*40)+40;
            if (dice<=1){
                c.loseHealth(c.getMaxHealthPoints());
            } else if (dice<=51) {
                c.loseHealth((int) ((c.getMaxHealthPoints())*(dice2/100)));
            }
        }
    }
    
    public void start(){
        if (isStarted) return;
        isStarted = true;
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long remaining = Math.max(0, timeRequire - elapsed);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            returnCrew();
        }, remaining, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public ArrayList<Crew> returnCrew(){
        damageCrew();
        ArrayList<Crew> tempCrew = new ArrayList<>(crewMembers);
        boolean anyAlive = false;
        for (Crew crew : crewMembers){
            if (crew.getHealthPoints() > 0){
                anyAlive = true;
                break;
            }
        }
        
        // Use field directly to avoid base Mission class side effects if we want custom reward logic
        this.Complete = anyAlive;
        Statistics stats = Statistics.getInstance();
        if (anyAlive) {
            stats.setNumSuccessfulMissions(stats.getNumSuccessfulMissions() + 1);
            Wallet.getInstance().addBalance(150); // Rescue specific reward
        } else {
            stats.setNumFailedMissions(stats.getNumFailedMissions() + 1);
        }

        // Return living crew to Barracks and reset canWork status
        for (int i = crewMembers.size() - 1; i >= 0; i--) {
            Crew crew = crewMembers.get(i);
            if (crew.getHealthPoints() > 0) {
                crew.receiveExp(200F);
                crew.setCanWork(true);
                Barracks.getInstance().assignCrew(crew);
            } else {
                crew.setCanWork(false);
                // If they died on mission, they stay out of crewList?
                // Actually removeCrew should have been called if they die.
                // But damageCrew just reduces HP.
                if (crew.getHealthPoints() <= 0) {
                    CrewManager.removeCrew(crew);
                }
            }
        }
        
        crewMembers.clear();
        CrewManager.removeRescueMission(this);
        
        // Auto-save the state so the JSON file reflects the return
        CrewManager.saveTOFile(null); 

        return tempCrew;
    }

    public void addCrew(Crew crew){
        if (crewMembers.size() >= numCrew || !crew.getCanWork()){
            return;
        }

        if (crew.getCurrentStation() != null){
            crew.getCurrentStation().removeCrew(crew);
            crew.setCurrentStation(null);
        }

        crewMembers.add(crew);
        crew.setCanWork(false);
    }

    public void removeCrew(Crew crew){
        crewMembers.remove(crew);
        crew.setCanWork(crew.getHealthPoints() > 0);
        if (crew.getHealthPoints() > 0) {
            Barracks.getInstance().assignCrew(crew);
        }
    }

    public List<Crew> getCrewMembers(){
        return crewMembers;
    }
}
