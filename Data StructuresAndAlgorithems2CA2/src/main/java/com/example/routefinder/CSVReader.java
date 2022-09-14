package com.example.routefinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CSVReader {
//    public static void main(String... args) {
//        ArrayList<Node<Room>> rooms = readCSV("src/Gallery.csv");
//
//        for (Node<Room> x : rooms) {
//            System.out.println(x.getData());
//        }
//    }

    public static ArrayList<Node<Room>> readCSV(String csv) {
        ArrayList<Node<Room>> rooms = new ArrayList<>();
        Path path = Paths.get(csv);

        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {

            String token = bufferedReader.readLine();

            while (token != null) {
                String[] values = token.split(",");
                Room room = createRoom(values);
                rooms.add(new Node(room));
                token = bufferedReader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private static Room createRoom(String[] stuff) {
        String name = stuff[0];
        String details = stuff[1];
        int id = Integer.parseInt(stuff[2]);
        int pixelX = Integer.parseInt(stuff[3]);
        int pixelY = Integer.parseInt(stuff[4]);
        ArrayList<String> connectedRooms = new ArrayList<>();
        for (int i = 5; i < stuff.length; i++) {
            connectedRooms.add(stuff[i]);
        }

        return new Room(name,details,id,pixelX,pixelY,connectedRooms);
    }
}
