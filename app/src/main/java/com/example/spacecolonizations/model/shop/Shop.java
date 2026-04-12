package com.example.spacecolonizations.model.shop;

import static com.example.spacecolonizations.NameGen.generateName;

import com.example.spacecolonizations.NameGen;
import com.example.spacecolonizations.model.Statistics;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.CrewManager;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Shop {
    private LinkedHashMap<Crew,Integer> shopableCrew;
    private ArrayList<Crew> generatableCrew;
    public Shop(){
        shopableCrew = new LinkedHashMap<Crew,Integer>();
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

    public void generateShopableCrew(){
        shopableCrew.clear();

        while (shopableCrew.size()<3){
            Crew selected = generatableCrew.get((int)(Math.random()*generatableCrew.size()));
            int maxHealth =(int) ((Math.random()*40)+80);
            int currentHealth = (int) (maxHealth - (maxHealth*(Math.random()*2/10)));
            int price = (int) (Math.random()*50)+100;
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

    }

    public boolean buyCrew(int numberInShop){
        List<Integer> prices = new ArrayList<>(shopableCrew.values());
        int price = prices.get(numberInShop);

        if (Wallet.getInstance().getBalance()>price){

            Wallet.getInstance().reduceBalance(price);

            List<Crew> crew = new ArrayList<>(shopableCrew.keySet());
            Crew c = crew.get(numberInShop);

            CrewManager.addCrew(c);
            Statistics.getInstance().setNumLivingCrews(Statistics.getInstance().getNumLivingCrews()+1);

//            shopableCrew.remove(c);

            return true;
        }


        return false;
    }

    public HashMap<Crew,Integer> getShopableCrew(){
        return shopableCrew;
    }

}
