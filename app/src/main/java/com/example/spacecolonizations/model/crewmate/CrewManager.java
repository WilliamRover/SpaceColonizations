package com.example.spacecolonizations.model.crewmate;

import android.content.Context;
import android.util.Log;

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
import java.util.List;

public class CrewManager {
    private static final String TAG = "CrewManager";
    private static List<Crew> crewList;
    private static final String saveFileName = "crew_data.ser";

    //TODO find a way to add existing crew to the list
    //TODO find a way to remove dead crew

    public void loadFromFile(Context context) {
        File file = new File(context.getFilesDir(), saveFileName);

        if (!file.exists()) {
            crewList = new ArrayList<>();
            return;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            crewList = (List<Crew>) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            crewList = new ArrayList<>();
            Log.w(TAG, "crew_data.ser not found", e);
        } catch (IOException e) {
            Log.w(TAG, "IO Exception occurred", e);
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "crew_data.ser did not have the crewList", e);
        } finally {
            if (crewList == null) {
                crewList = new ArrayList<>();
                Log.w(TAG, "crewList in file is null");
            }
        }

    }

    public static void saveCrewTOFile(Context context) {
        try (FileOutputStream outputStream = context.openFileOutput(saveFileName, context.MODE_PRIVATE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            objectOutputStream.writeObject(crewList);

        } catch (FileNotFoundException e) {
            Log.w(TAG, "crew_data.ser not found", e);
        } catch (IOException e) {
            Log.w(TAG, "IO Exception occurred", e);
        }
    }

    public static List<Station> getStations() {
        List<Station> stations = new ArrayList<>();

        for (Crew crew : crewList) {
            Station station = crew.getCurrentStation();
            if (station == null) {
                continue;
            }
            if (stations.contains(station)) {
                continue;
            }
            stations.add(station);

            // only 5 stations are there in total
            if (stations.size() == 5) {
                break;
            }

        }

        if (stations.size() < 5) {
            boolean hasMedBay = false;
            boolean hasTurret = false;
            boolean hasCommand = false;
            boolean hasTraining = false;
            boolean hasBarracks = false;

            for (Station s : stations) {
                if (s instanceof MedBay) hasMedBay = true;
                else if (s instanceof Turret) hasTurret = true;
                else if (s instanceof CommandCenter) hasCommand = true;
                else if (s instanceof TrainingCenter) hasTraining = true;
                else if (s instanceof Barracks) hasBarracks = true;
            }

            // Create and add missing ones
            if (!hasMedBay) stations.add(new MedBay());
            if (!hasTurret) stations.add(new Turret());
            if (!hasCommand) stations.add(new CommandCenter());
            if (!hasTraining) stations.add(new TrainingCenter());
            if (!hasBarracks) stations.add(Barracks.getInstance());
        }

        return stations;
    }

}
