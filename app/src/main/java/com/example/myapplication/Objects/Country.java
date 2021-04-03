package com.example.myapplication.Objects;

import java.util.ArrayList;

public class Country {
    private String name;
    private String shortName;
    private String nativeName;
    private ArrayList<String> borders;

    public Country(String name, String shortName, String nativeName, ArrayList<String> borders) {
        this.name = name;
        this.shortName = shortName;
        this.borders = borders;
        this.nativeName = nativeName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

}
