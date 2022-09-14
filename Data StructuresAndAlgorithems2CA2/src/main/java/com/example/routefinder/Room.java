package com.example.routefinder;

import java.util.ArrayList;

public class Room {
    private String name;
    private String details;
    private int id;
    private int pixelX;
    private int pixelY;
    private ArrayList<String> connectedRooms;


    public Room(String name, String details, int id, int pixelX, int pixelY, ArrayList<String> connectedRooms) {
        this.name = name;
        this.details = details;
        this.id = id;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
        this.connectedRooms = connectedRooms;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public ArrayList<String> getConnectedRooms() {
        return connectedRooms;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", id=" + id +
                ", pixelX=" + pixelX +
                ", pixelY=" + pixelY +
                ", connectedRooms=" + connectedRooms +
                '}';
    }
}
