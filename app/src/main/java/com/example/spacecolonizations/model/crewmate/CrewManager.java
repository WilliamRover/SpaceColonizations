package com.example.spacecolonizations.model.crewmate;

import android.content.Context;
import android.util.Log;

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
            objectOutputStream.writeObject(data);

        } catch (FileNotFoundException e) {
            Log.w(TAG, "crew_data.ser not found", e);
        } catch (IOException e) {
            Log.w(TAG, "IO Exception occurred", e);
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
            // TODO ask java to create crew with the name gen thingy majigy
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

}
