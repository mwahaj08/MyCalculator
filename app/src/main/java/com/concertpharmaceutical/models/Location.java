package com.concertpharmaceutical.models;

/**
 * Created by Ayesha Ahmad on 3/14/2016.
 */
public class Location {

    private int id;
    private String name;

    public Location(){}
    public Location(int paramID,String paramName){
        paramID = id;
        paramName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
