package com.example.routefinder;

public class Utilities {
    public static float pixelDist(int startX, int startY, int endX, int endY){
        return (int) Math.sqrt(Math.pow(endX-startX,2)+Math.pow(endY - startY,2));
    }
}
