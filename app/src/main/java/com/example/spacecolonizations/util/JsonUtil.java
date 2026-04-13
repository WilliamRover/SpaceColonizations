package com.example.spacecolonizations.util;

import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.mission.Rescue;
import com.example.spacecolonizations.model.station.Barracks;
import com.example.spacecolonizations.model.station.CommandCenter;
import com.example.spacecolonizations.model.station.MedBay;
import com.example.spacecolonizations.model.station.Station;
import com.example.spacecolonizations.model.station.TrainingCenter;
import com.example.spacecolonizations.model.station.Turret;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static String toJsonResponse(List<Crew> crewList, List<Station> stations, List<Rescue> rescueMissions, int balance, Map<String, Integer> statistics) throws JSONException {
        JSONObject root = new JSONObject();
        
        // Crew
        JSONArray crewArray = new JSONArray();
        for (Crew crew : crewList) {
            JSONObject c = new JSONObject();
            c.put("type", crew.getClass().getSimpleName());
            c.put("name", crew.getName());
            c.put("hp", crew.getHealthPoints());
            c.put("maxHp", crew.getMaxHealthPoints());
            c.put("level", crew.getLevel());
            c.put("exp", crew.getExp());
            c.put("canWork", crew.getCanWork());
            c.put("isPatient", crew.isPatient());
            
            if (crew.getCurrentStation() != null) {
                c.put("station", crew.getCurrentStation().getClass().getSimpleName());
            }
            crewArray.put(c);
        }
        root.put("crewList", crewArray);

        // Stations
        JSONArray stationArray = new JSONArray();
        for (Station station : stations) {
            JSONObject s = new JSONObject();
            s.put("type", station.getClass().getSimpleName());
            s.put("isUsable", station.getisUsable());
            s.put("repairTimeRemaining", station.getRepairTimeRemaining());
            s.put("breakTimeRemaining", station.getBreakTimeRemaining());
            
            // Repair Men names
            JSONArray repairMenArray = new JSONArray();
            if (station.getRepairMan() != null) {
                for (Crew rm : station.getRepairMan()) {
                    repairMenArray.put(rm.getName());
                }
            }
            s.put("repairMen", repairMenArray);
            
            if (station instanceof MedBay) {
                MedBay medBay = (MedBay) station;
                JSONArray patientsArray = new JSONArray();
                for (Crew p : medBay.getPatients()) {
                    patientsArray.put(p.getName());
                }
                s.put("patients", patientsArray);
            }
            stationArray.put(s);
        }
        root.put("stationList", stationArray);

        // Rescue Missions
        JSONArray rescueArray = new JSONArray();
        if (rescueMissions != null) {
            for (Rescue rescue : rescueMissions) {
                JSONObject r = new JSONObject();
                r.put("missionName", rescue.getMissionName());
                r.put("startTime", rescue.getStartTime());
                r.put("numCrew", rescue.getNumCrew());
                
                JSONArray missionCrew = new JSONArray();
                for (Crew c : rescue.getCrewMembers()) {
                    missionCrew.put(c.getName());
                }
                r.put("crewNames", missionCrew);
                rescueArray.put(r);
            }
        }
        root.put("rescueMissionList", rescueArray);

        root.put("balance", balance);

        // Statistics
        JSONObject statsObj = new JSONObject();
        if (statistics != null) {
            for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
                statsObj.put(entry.getKey(), entry.getValue());
            }
        }
        root.put("gameStatistics", statsObj);

        return root.toString(4);
    }

    public static Map<String, Object> fromJsonResponse(String json) throws JSONException {
        Map<String, Object> data = new HashMap<>();
        JSONObject root = new JSONObject(json);

        // Balance
        data.put("balance", root.optInt("balance", 100));

        // Statistics
        Map<String, Integer> statisticsMap = new HashMap<>();
        JSONObject statsObj = root.optJSONObject("gameStatistics");
        if (statsObj != null) {
            Iterator<String> keys = statsObj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                statisticsMap.put(key, statsObj.getInt(key));
            }
        }
        data.put("gameStatistics", statisticsMap);

        // Stations
        List<Station> stations = new ArrayList<>();
        Map<String, Station> stationMap = new HashMap<>();
        Map<String, JSONArray> patientDataMap = new HashMap<>();
        Map<String, JSONArray> repairMenDataMap = new HashMap<>();
        Map<String, JSONObject> stationAttrMap = new HashMap<>();

        JSONArray stationArray = root.optJSONArray("stationList");
        if (stationArray != null) {
            for (int i = 0; i < stationArray.length(); i++) {
                JSONObject sObj = stationArray.getJSONObject(i);
                String type = sObj.getString("type");
                Station station = createStation(type);
                if (station != null) {
                    stations.add(station);
                    stationMap.put(type, station);
                    stationAttrMap.put(type, sObj);
                    
                    if (sObj.has("patients")) {
                        patientDataMap.put(type, sObj.getJSONArray("patients"));
                    }
                    if (sObj.has("repairMen")) {
                        repairMenDataMap.put(type, sObj.getJSONArray("repairMen"));
                    }
                }
            }
        }
        data.put("stationList", stations);

        // Crew
        List<Crew> crewList = new ArrayList<>();
        Map<String, Crew> crewNameMap = new HashMap<>();
        JSONArray crewArray = root.optJSONArray("crewList");
        if (crewArray != null) {
            for (int i = 0; i < crewArray.length(); i++) {
                JSONObject cObj = crewArray.getJSONObject(i);
                String type = cObj.getString("type");
                String name = cObj.getString("name");
                int hp = cObj.getInt("hp");
                int maxHp = cObj.getInt("maxHp");
                
                Crew crew = createCrew(type, name, hp, maxHp);
                if (crew != null) {
                    crew.setLevel(cObj.optInt("level", 1));
                    crew.setExp((float) cObj.optDouble("exp", 0.0));
                    crew.setCanWork(cObj.optBoolean("canWork", true));
                    crew.setPatient(cObj.optBoolean("isPatient", false));
                    
                    if (cObj.has("station")) {
                        String stationType = cObj.getString("station");
                        Station station = stationMap.get(stationType);
                        if (station != null) {
                            station.assignCrew(crew);
                        }
                    }
                    crewList.add(crew);
                    crewNameMap.put(name, crew);
                }
            }
        }
        data.put("crewList", crewList);

        // Restore Station Attributes and Repair Men
        for (Station station : stations) {
            String type = station.getClass().getSimpleName();
            JSONObject sObj = stationAttrMap.get(type);
            if (sObj != null) {
                station.setisUsable(sObj.optBoolean("isUsable", true));
                station.setRepairTimeRemaining(sObj.optDouble("repairTimeRemaining", 0.0));
                station.setBreakTimeRemaining(sObj.optInt("breakTimeRemaining", 0));
                
                JSONArray repairMenNames = repairMenDataMap.get(type);
                if (repairMenNames != null) {
                    for (int j = 0; j < repairMenNames.length(); j++) {
                        String name = repairMenNames.getString(j);
                        Crew rm = crewNameMap.get(name);
                        if (rm != null) {
                            // addRepairMan handles setting currentStation and canWork
                            // It only works if isUsable is false, which we just restored above
                            rm.setCanWork(true); // Temporarily allow to bypass canWork check in addRepairMan
                            station.addRepairMan(rm);
                        }
                    }
                }
                
                // Restart handlers if necessary
                station.resumeHandlers();
            }
        }

        // Restore MedBay Patients
        for (Map.Entry<String, JSONArray> entry : patientDataMap.entrySet()) {
            Station station = stationMap.get(entry.getKey());
            if (station instanceof MedBay) {
                MedBay medBay = (MedBay) station;
                JSONArray patientNames = entry.getValue();
                for (int j = 0; j < patientNames.length(); j++) {
                    Crew p = crewNameMap.get(patientNames.getString(j));
                    if (p != null) {
                        medBay.addPatient(p);
                    }
                }
            }
        }

        // Rescue Missions
        List<Rescue> rescueMissions = new ArrayList<>();
        JSONArray rescueArray = root.optJSONArray("rescueMissionList");
        if (rescueArray != null) {
            for (int i = 0; i < rescueArray.length(); i++) {
                JSONObject rObj = rescueArray.getJSONObject(i);
                Rescue rescue = new Rescue(rObj.getString("missionName"));
                rescue.setStartTime(rObj.optLong("startTime", 0));
                
                // Set numCrew from save if available
                if (rObj.has("numCrew")) {
                    try {
                        java.lang.reflect.Field field = rescue.getClass().getSuperclass().getDeclaredField("numCrew");
                        field.setAccessible(true);
                        field.set(rescue, rObj.getInt("numCrew"));
                    } catch (Exception e) {
                        // fallback or ignore
                    }
                }
                
                JSONArray crewNames = rObj.optJSONArray("crewNames");
                if (crewNames != null) {
                    for (int j = 0; j < crewNames.length(); j++) {
                        String name = crewNames.getString(j);
                        Crew c = crewNameMap.get(name);
                        if (c != null) {
                            c.setCanWork(true); // Temporarily allow adding to mission during load
                            rescue.addCrew(c);
                        }
                    }
                }
                rescueMissions.add(rescue);
                rescue.start();
            }
        }
        data.put("rescueMissionList", rescueMissions);

        return data;
    }

    private static Station createStation(String type) {
        switch (type) {
            case "CommandCenter": return new CommandCenter();
            case "TrainingCenter": return new TrainingCenter();
            case "MedBay": return new MedBay();
            case "Turret": return new Turret();
            case "Barracks": return Barracks.getInstance();
            default: return null;
        }
    }

    private static Crew createCrew(String type, String name, int hp, int maxHp) {
        switch (type) {
            case "Gunner": return new Gunner(name, hp, maxHp);
            case "Medic": return new Medic(name, hp, maxHp);
            case "Commander": return new Commander(name, hp, maxHp);
            case "Technician": return new Technician(name, hp, maxHp);
            case "Navigator": return new Navigator(name, hp, maxHp);
            default: return null;
        }
    }
}
