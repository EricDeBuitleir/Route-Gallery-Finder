package com.example.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private ImageView mapView;
    @FXML
    private TextField startingRoom;
    @FXML
    private TextField destinationRoom;
    @FXML
    private ListView listView;



    ArrayList<Node<Room>> rooms = new ArrayList<>();

    @FXML
    void initialize() throws FileNotFoundException {
        rooms = CSVReader.readCSV("src/Gallery.csv");
        for (Node<Room> room : rooms){
            for (String name: room.getData().getConnectedRooms()){
                for (Node<Room> destRoom : rooms) {
                    if(name.equals(destRoom.getData().getName())) {
                        room.connectNode(destRoom);
                    }
                }
            }
        }

        mapView.setImage(new Image(new FileInputStream("src/main/resources/Museum-Map.png")));
    }

    @FXML
    private void getCoordinates(MouseEvent e) {
        int mouseX = (int) e.getX();
        int mouseY = (int) e.getY();

        System.out.println(mouseX + " " + mouseY);
    }

    @FXML
    private void drawPath() {
        try {
            mapView.setImage(new Image(new FileInputStream("src/main/resources/Museum-Map.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        listView.getItems().clear();

        int roomOne = Integer.parseInt(startingRoom.getText());
        int roomTwo = Integer.parseInt(destinationRoom.getText());

        int oneIndex = 0;
        int twoIndex = 0;

        int oneX = 0;
        int oneY = 0;
        int twoX = 0;
        int twoY = 0;

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomOne) {
                oneX = room.getData().getPixelX();
                oneY = room.getData().getPixelY();
                oneIndex = rooms.indexOf(room);
                System.out.println(oneIndex);
            }
        }

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomTwo) {
                twoX = room.getData().getPixelX();
                twoY = room.getData().getPixelY();
                twoIndex = rooms.indexOf(room);
                System.out.println(twoIndex);
            }
        }

        System.out.println(oneIndex);
        System.out.println(oneX + ", " + oneY);
        System.out.println(twoIndex);
        System.out.println(twoX + ", " + twoY);


        List<Node<?>> path = Node.findPathBreadthFirst(rooms.get(oneIndex),rooms.get(twoIndex).getData());
        for(Node<?> n : path){
            listView.getItems().add(n.getData().toString());
        }


            WritableImage lineImage = new WritableImage(mapView.getImage().getPixelReader(),800,666);

        double lineSlope = (twoY - oneY)/(twoX - oneX);

        System.out.println(lineImage.getWidth() + ", " + lineImage.getHeight());

        if (Math.abs(lineSlope) < 1) {
            for (int x = 0; x < lineImage.getWidth(); ++x) {
                if ((x >= oneX && x <= twoX)) {
                    lineImage.getPixelWriter().setColor(x, oneY+((twoY-oneY)*(x-oneX))/(twoX-oneX), Color.color(0,0,0));
                }
            }
        }
        else {
            for (int y = 0; y < lineImage.getHeight(); ++y) {
                if ((y >= oneY && y <= twoY)) {
                    lineImage.getPixelWriter().setColor(((y-oneY)*(twoX-oneX)/(twoY-oneY)+oneX), y, Color.color(0,0,0));
                }
            }
        }

        lineSlope = (oneY - twoY)/(oneX - twoX);

        System.out.println(lineImage.getWidth() + ", " + lineImage.getHeight());

        if (Math.abs(lineSlope) < 1) {
            for (int x = 0; x < lineImage.getWidth(); ++x) {
                if ((x >= oneX && x <= twoX)) {
                    lineImage.getPixelWriter().setColor(x, oneY+((twoY-oneY)*(x-oneX))/(twoX-oneX), Color.color(0,0,0));
                }
            }
        }
        else {
            for (int y = 0; y < lineImage.getHeight(); ++y) {
                if ((y >= oneY && y <= twoY)) {
                    lineImage.getPixelWriter().setColor(((y-oneY)*(twoX-oneX)/(twoY-oneY)+oneX), y, Color.color(0,0,0));
                }
            }
        }

        mapView.setImage(lineImage);

    }

    @FXML
    private void DepthSearch() {

        listView.getItems().clear();

        int roomOne = Integer.parseInt(startingRoom.getText());
        int roomTwo = Integer.parseInt(destinationRoom.getText());

        int oneIndex = 0;
        int twoIndex = 0;

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomOne) {
                oneIndex = rooms.indexOf(room);
            }
        }

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomTwo) {
                twoIndex = rooms.indexOf(room);
            }
        }

        List<Node<?>> path = Node.findPathDepthFirst(rooms.get(oneIndex),null,rooms.get(twoIndex).getData());

        for(Node<?> n : path){
            listView.getItems().add(n.getData().toString());
        }
    }

    @FXML
    private void DepthSearchAll() {

        int Counter = 0;

        listView.getItems().clear();

        int roomOne = Integer.parseInt(startingRoom.getText());
        int roomTwo = Integer.parseInt(destinationRoom.getText());

        int oneIndex = 0;
        int twoIndex = 0;

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomOne) {
                oneIndex = rooms.indexOf(room);
            }
        }

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomTwo) {
                twoIndex = rooms.indexOf(room);
            }
        }

        List<List<Node<?>>> paths = Node.findAllPathsDepthFirst(rooms.get(oneIndex),null,rooms.get(twoIndex).getData());

        for(List<Node<?>> n : paths){
            Counter += 1;
            listView.getItems().add("Path " + Counter);
            for (Node<?> m : n) {
                listView.getItems().add(m.getData().toString());
            }
        }
    }

    @FXML
    private void ShortestPathDijkstra() {

        int Counter = 0;

        listView.getItems().clear();

        int roomOne = Integer.parseInt(startingRoom.getText());
        int roomTwo = Integer.parseInt(destinationRoom.getText());

        int oneIndex = 0;
        int twoIndex = 0;

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomOne) {
                oneIndex = rooms.indexOf(room);
            }
        }

        for (Node<Room> room : rooms) {
            int checkRoom = Integer.parseInt(room.getData().getName());
            if (checkRoom == roomTwo) {
                twoIndex = rooms.indexOf(room);
            }
        }

        Node.CostPath pathShort = Node.findShortestPathDijkstra(rooms.get(oneIndex),rooms.get(twoIndex).getData());
        listView.getItems().add(pathShort.pathList);
    }

}