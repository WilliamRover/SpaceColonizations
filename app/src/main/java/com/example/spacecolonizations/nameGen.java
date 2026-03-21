package com.example.spacecolonizations;

public class nameGen {


    static String nGen(int n){
        String AlphaString = "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0;i<=n;i++){
            int locate = (int)( Math.random() * AlphaString.length());
            sb.append(AlphaString.charAt(locate));
        }

        return sb.toString();
    }

    static String generateName (){
        String fName = nGen((int) (Math.random()*5) + 3);
        String lName = nGen((int) (Math.random()*5) + 3);
        return fName + " " + lName;
    }
}
