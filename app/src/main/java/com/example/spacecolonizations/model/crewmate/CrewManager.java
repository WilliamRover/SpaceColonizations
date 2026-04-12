package com.example.spacecolonizations.model.crewmate;

import static com.example.spacecolonizations.NameGen.generateName;

import android.content.Context;
import android.util.Log;

import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.mission.Rescue;
import com.example.spacecolonizations.model.ship.FriendlyShip;
import com.example.spacecolonizations.model.shop.Wallet;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;
import com.example.spacecolonizations.util.JsonUtil;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CrewManager {
    private static final String TAG = "CrewManager";
    private static final List<Crew> crewList = new ArrayList<>();
    private static final String saveFileName = "crew_data.json";
    private static final List<Station> stations = new ArrayList<>();
    private static final List<Rescue> rescueMissions = new ArrayList<>();


    /**
     * To be called when the app starts
     * @param context
     */
    public static void loadFromFile(Context context) {
        File file = new File(context.getFilesDir(), saveFileName);
        Log.d(TAG, "Attempting to load from: " + file.getAbsolutePath());
        int loadedBalance = -1;
        Map<String, Integer> loadedStats = null;

        if (!file.exists()) {
            crewList.clear();
            stations.clear();
            rescueMissions.clear();
            loadedBalance = 100;
            Statistics.getInstance();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(saveFileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Map<String, Object> data = JsonUtil.fromJsonResponse(stringBuilder.toString());

            List<Crew> loadedCrews = (List<Crew>) data.get("crewList");
            crewList.clear();
            if (loadedCrews != null) crewList.addAll(loadedCrews);

            List<Station> loadedStations = (List<Station>) data.get("stationList");
            stations.clear();
            if (loadedStations != null) stations.addAll(loadedStations);

            List<Rescue> loadedRescues = (List<Rescue>) data.get("rescueMissionList");
            rescueMissions.clear();
            if (loadedRescues != null) rescueMissions.addAll(loadedRescues);

            loadedBalance = (int) data.get("balance");
            loadedStats = (Map<String, Integer>) data.get("gameStatistics");

            if (loadedStats != null) {
                setStatistics(loadedStats);
            }

            Log.d(TAG, "crew_data.json loaded successfully");

        } catch (FileNotFoundException e) {
            crewList.clear();
            stations.clear();
            rescueMissions.clear();
            Log.w(TAG, "crew_data.json not found", e);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error loading crew_data.json", e);
        } finally {
            if (loadedBalance == -1) {
                loadedBalance = 100;
            }
            if (loadedStats == null) {
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
        File file = new File(context.getFilesDir(), saveFileName);
        Log.d(TAG, "Attempting to save to: " + file.getAbsolutePath());
        try (FileOutputStream fos = context.openFileOutput(saveFileName, Context.MODE_PRIVATE);
             PrintWriter writer = new PrintWriter(fos)) {

            String json = JsonUtil.toJsonResponse(
                    crewList,
                    getStations(),
                    rescueMissions,
                    Wallet.getInstance().getBalance(),
                    Statistics.getInstance().getStatistics()
            );
            writer.print(json);
            Log.d(TAG, "crew_data.json saved successfully");

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error saving crew_data.json", e);
        }
    }

    public static void deleteSave(Context context) {
        context.deleteFile(saveFileName);
        Statistics.getInstance().resetStatistics();
        Wallet.getInstance().restoreBalance(100);

        FriendlyShip.getShip().resetShip();

        crewList.clear();
        stations.clear();
        rescueMissions.clear();
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
            crewList.add(new Gunner(generateName(), 100, 100));
            crewList.add(new Medic(generateName(), 80, 100));
            crewList.add(new Commander(generateName(), 120, 120));
            crewList.add(new Technician(generateName(), 100, 100));
            crewList.add(new Navigator(generateName(), 100, 100));


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


    private static void setStatistics(Map<String, Integer> stats){
        int shipKills = getStatOrDefault(stats, "shipKills", 0);
        int numDeadCrews = getStatOrDefault(stats, "numDeadCrews", 0);
        int numLivingCrews = getStatOrDefault(stats, "numLivingCrews", 0);
        int numTotalCrews = getStatOrDefault(stats, "numTotalCrews", 0);
        int numSuccessfulMissions = getStatOrDefault(stats, "numSuccessfulMissions", 0);
        int numFailedMissions = getStatOrDefault(stats, "numFailedMissions", 0);

        Statistics statsInstance = Statistics.getInstance();

        statsInstance.setShipKills(shipKills);
        statsInstance.setNumDeadCrews(numDeadCrews);
        statsInstance.setNumLivingCrews(numLivingCrews);
        statsInstance.setNumTotalCrews(numTotalCrews);
        statsInstance.setNumSuccessfulMissions(numSuccessfulMissions);
        statsInstance.setNumFailedMissions(numFailedMissions);

    }

    private static int getStatOrDefault(Map<String, Integer> map, String key, int defaultValue) {
        Integer value = map.get(key);
        return value != null ? value : defaultValue;
    }

}
