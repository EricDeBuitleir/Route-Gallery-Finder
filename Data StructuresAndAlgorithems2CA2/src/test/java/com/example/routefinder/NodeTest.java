package com.example.routefinder;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class NodeTest {

    ArrayList<Node<Room>> rooms;
    ArrayList<List<List<Node<?>>>> nodes;

    @BeforeEach
    void setUp() {
        rooms = CSVReader.readCSV("src/Gallery.csv");
        for (Node<Room> room : rooms) {
            for (String name : room.getData().getConnectedRooms()) {
                for (Node<Room> destRoom : rooms) {
                    if (name.equals(destRoom.getData().getName())) {
                        room.connectNode(destRoom);
                    }
                }
            }
        }


    }

    @Test
    void findPathDepthFirst() {
        assertTrue(Node.findPathDepthFirst(rooms.get(0), null, rooms.get(55).getData()).contains(rooms.get(1)));

    }

    @Test
    void findPathBreadthFirst() {
        assertTrue(Node.findPathBreadthFirst(rooms.get(0), rooms.get(55).getData()).contains(rooms.get(1)));
    }

    @Test
    void findAllPathsDepthFirst() {
        assertFalse(Node.findAllPathsDepthFirst(rooms.get(0), null, rooms.get(55).getData()).contains(rooms.get(1)));
    }




}