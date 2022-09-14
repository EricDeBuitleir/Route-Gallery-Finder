package com.example.routefinder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
    int startX;
    int startY;

    int endX;
    int endY;
    @BeforeEach
    void setUp(){
        startX = 300;
        startY = 200;

        endX = 500;
        endY = 300;
    }

    @Test
    void pixelDist() {
        assertEquals(223,Utilities.pixelDist(startX,startY,endX,endY));
    }
}