package com.example.spacecolonizations.model.crewmate;

import android.content.Context;
import android.util.Log;

import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.mission.Rescue;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CrewManager {
    private static final String TAG = "CrewManager";
    private static List<Crew> crewList;
    private static final String saveFileName = "crew_data.ser";
    private static List<Station> stations;
    private static List<Rescue> rescueMissions;
    private static HashMap<String, Integer> statistics;


    /**
     * To be called when the app starts
     * @param context
     */
    public static void loadFromFile(Context context) {
        File file = new File(context.getFilesDir(), saveFileName);
        int loadedBalance = -1;

        if (!file.exists()) {
            crewList = new ArrayList<>();
            stations = new ArrayList<>();
            rescueMissions = new ArrayList<>();
            loadedBalance = 100;
            Statistics.getInstance();
            return;
        }

        try (
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

            HashMap<String, Object> data = (HashMap<String, Object>) objectInputStream.readObject();
            crewList = (List<Crew>) data.get("crewList");
            stations = (List<Station>) data.get("stationList");
            rescueMissions = (List<Rescue>) data.get("rescueMissionList");
            loadedBalance = (int) data.get("balance");

            statistics = (HashMap<String, Integer>) data.get("gameStatistics");
            if  (statistics != null){
                setStatistics(statistics);
            }

            Log.d(TAG, "crew_data.ser loaded");


        } catch (FileNotFoundException e) {
            crewList = new ArrayList<>();
            stations = new ArrayList<>();
            rescueMissions = new ArrayList<>();
            Log.w(TAG, "crew_data.ser not found", e);

        } catch (IOException e) {
            Log.w(TAG, "IO Exception occurred", e);

        } catch (ClassNotFoundException e) {
            crewList = new ArrayList<>();
            stations = new ArrayList<>();
            rescueMissions = new ArrayList<>();
            Log.w(TAG, "crew_data.ser did not have the save data", e);

        } finally {
            if (crewList == null) {
                crewList = new ArrayList<>();
                Log.w(TAG, "crewList in file is null");
            }

            if (stations == null) {
                stations = new ArrayList<>();
                Log.w(TAG, "stations in file is null");
            }

            if (rescueMissions == null) {
                rescueMissions = new ArrayList<>();
                Log.w(TAG, "rescueMissions in file is null");
            }

            if (loadedBalance == -1) {
                loadedBalance = 100;
            }

            if (statistics == null){
                Statistics.getInstance();
            }

            Wallet.getInstance().restoreBalance(loadedBalance);
        }

    }


    /**
     * Save crew and Stations to file
     * @param context
     */
    public static void saveTOFile(Context context) {
        try (FileOutputStream outputStream = context.openFileOutput(saveFileName, Context.MODE_PRIVATE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            HashMap<String, Object> data = new HashMap<>();
            data.put("crewList", crewList);
            data.put("stationList", stations);
            data.put("rescueMissionList", rescueMissions);
            data.put("balance", Wallet.getInstance().getBalance());
            data.put("gameStatistics", Statistics.getInstance().getStatistics());
            objectOutputStream.writeObject(data);

            Log.d(TAG, "crew_data.ser saved");

        } catch (FileNotFoundException e) {
            Log.w(TAG, "crew_data.ser not found", e);
        } catch (IOException e) {
            Log.w(TAG, "IO Exception occurred", e);
        }
    }

    public static void deleteSave(Context context) {
        context.deleteFile(saveFileName);
        Statistics.getInstance().resetStatistics();
        Wallet.getInstance().restoreBalance(100);

        if (crewList != null) {
            crewList.clear();
        }
        if (stations != null) {
            stations.clear();
        }
        if (rescueMissions != null) {
            rescueMissions.clear();
        }
    }

    /**
     * Returns a list of stations. If stations list is empty in save file, the 5 stations will be initialized
     * @return List of Stations
     */
    public static List<Station> getStations() {
        if (stations.isEmpty()) {
            stations.add(new CommandCenter());
            stations.add(new TrainingCenter());
            stations.add(new MedBay());
            stations.add(new Turret());
            stations.add(Barracks.getInstance());
        }

        return stations;
    }


    /**
     * Returns List of living crew members
     * @return List of Crew
     */
    public static List<Crew> getCrew(){
        if (crewList.isEmpty()){
            crewList.add(new Gunner("Jew Burner", 100, 100));
            crewList.add(new Medic("Black Plague", 80, 100));
            crewList.add(new Commander("Captain Failure", 120, 120));
            crewList.add(new Technician("I dont have sleep", 100, 100));
            crewList.add(new Navigator("Nigger", 100, 100));
            crewList.add(new Navigator("Best pilot", 10, 222));
            crewList.add(new Gunner("Allahu Akbar", 100, 100));
            crewList.add(new Gunner("Slave", 100, 100));
            crewList.add(new Commander("Corrupted", 200, 500));
            crewList.add(new Technician("Im alone mf", 20, 200));
            crewList.add(new Technician("Engineering Student 1", 20, 200));
            crewList.add(new Technician("Engineering Student 2", 20, 200));
            crewList.add(new Technician("Engineering Student 3", 20, 200));
            crewList.add(new Technician("Engineering Student 4", 20, 200));
            crewList.add(new Technician("Engineering Student 5", 20, 200));
            crewList.add(new Technician("Engineering Student 6", 20, 200));
            crewList.add(new Technician("Engineering Student 7", 20, 200));
            crewList.add(new Technician("Engineering Student 8", 20, 200));
            crewList.add(new Technician("Engineering Student 9", 20, 200));
            crewList.add(new Technician("Engineering Student 10", 20, 200));

            if (!Barracks.getInstance().getCrewMembers().isEmpty()){
                Barracks.getInstance().getCrewMembers().clear();
            }
            for (Crew crew: crewList) {
                Barracks.getInstance().assignCrew(crew);
            }
        }

        return crewList;
    }


    /**
     * Use this when creating the crew in any case
     * @param crew
     */
    public static void addCrew(Crew crew) {
        crewList.add(crew);
        Barracks.getInstance().assignCrew(crew);
    }

    /**
     * Use this when crew dies.
     * @param crew
     */
    public static void removeCrew(Crew crew) {
        crewList.remove(crew);
    }


    /**
     *Get all active Missions
     * @return List of active missions. null if there are no active missions
     */
    public static List<Rescue> getRescueMissions(){
        if (rescueMissions.isEmpty()){
            return null;
        }
        return rescueMissions;
    }


    /**
     * Add a new mission to the list of missions
     * @param mission
     */
    public static void addRescueMission(Rescue mission){
        rescueMissions.add(mission);
    }


    /**
     * Remove a rescue mission from the list when it is completed
     */
    public static void removeRescueMission(Rescue mission){
        rescueMissions.remove(mission);
    }


    private static void setStatistics(HashMap<String, Integer> stats){
        int shipKills = stats.get("shipKills");
        int numDeadCrews = stats.get("numDeadCrews");
        int numLivingCrews = stats.get("numLivingCrews");
        int numTotalCrews = stats.get("numTotalCrews");
        int numSuccessfulMissions = stats.get("numSuccessfulMissions");
        int numFailedMissions = stats.get("numFailedMissions");

        Statistics statsInstance = Statistics.getInstance();

        statsInstance.setShipKills(shipKills);
        statsInstance.setNumDeadCrews(numDeadCrews);
        statsInstance.setNumLivingCrews(numLivingCrews);
        statsInstance.setNumTotalCrews(numTotalCrews);
        statsInstance.setNumSuccessfulMissions(numSuccessfulMissions);
        statsInstance.setNumFailedMissions(numFailedMissions);

    }

}
