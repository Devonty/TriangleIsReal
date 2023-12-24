package com.cgvsu.rasterization;

import javafx.scene.paint.Color;

import java.util.Random;

public class MyColorUtils {
    public static final Random rd = new Random();

    public static Color getRandColor(){
        return Color.color(rd.nextFloat(), rd.nextFloat(), rd.nextFloat());
    }
}
