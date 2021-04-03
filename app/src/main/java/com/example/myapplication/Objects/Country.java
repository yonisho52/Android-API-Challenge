package com.example.myapplication.Objects;

import java.util.ArrayList;

public class Country {
    private String name;
    private String shortName;
    private ArrayList<String> borders;

    public Country(String name, String shortName, ArrayList<String> borders) {
        this.name = name;
        this.shortName = shortName;
        this.borders = borders;
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
