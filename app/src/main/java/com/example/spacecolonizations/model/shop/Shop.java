package com.example.spacecolonizations.model.shop;

import static com.example.spacecolonizations.NameGen.generateName;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private HashMap<Crew,Integer> shopableCrew;
    private ArrayList<Crew> generatableCrew;
    public Shop(){
        shopableCrew = new HashMap<Crew,Integer>();
        generatableCrew = new ArrayList<>();
        Commander cm = new Commander("cm",1,1);
        Navigator nv = new Navigator("nv",1,1);
        Technician tc = new Technician("tc",1,1);
        Gunner gn = new Gunner("gn",1,1);
        Medic md = new Medic("md",1,1);
        generatableCrew.add(cm);
        generatableCrew.add(nv);
        generatableCrew.add(gn);
        generatableCrew.add(tc);
        generatableCrew.add(md);
    }

    public HashMap<Crew,Integer> generateShopableCrew(){
        HashMap<Crew,Integer> shopableCrew = new HashMap<Crew,Integer>();


        while (shopableCrew.size()>3){
            Crew selected = generatableCrew.get((int)(Math.random()*generatableCrew.size()));
            int maxHealth =(int) ((Math.random()*40)+80);
            int currentHealth = (int) (maxHealth - (maxHealth*(Math.random()*2/10)));
            int price = (int) (Math.random()*30)+20;
            if (selected instanceof Commander){
                Commander cm = new Commander(generateName(),currentHealth,maxHealth);
                shopableCrew.put(cm,price);
            } else if (selected instanceof Gunner) {
                Gunner gn = new Gunner(generateName(),currentHealth,maxHealth);
                shopableCrew.put(gn,price);
            } else if (selected instanceof Medic) {
                Medic md = new Medic(generateName(),currentHealth,maxHealth);
                shopableCrew.put(md,price);
            } else if (selected instanceof Navigator) {
                Navigator nv = new Navigator(generateName(),currentHealth,maxHealth);
                shopableCrew.put(nv,price);
            } else if (selected instanceof Technician) {
                Technician tc = new Technician(generateName(),currentHealth,maxHealth);
                shopableCrew.put(tc,price);
            }
        }

        return shopableCrew;
    }

    public void setUp(){
        shopableCrew = generateShopableCrew();
    }

    public HashMap<Crew,Integer> getShopableCrew(){
        return shopableCrew;
    }

}
