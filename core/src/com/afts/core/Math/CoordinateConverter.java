package com.afts.core.Math;

/**
 * Created by Steven on 14/05/2018.
 * A simple Y axis converter. It takes takes a desired Y-Coordinate and converts it to a Y-Down system, instead of the built in Y-Up.
 */

public class CoordinateConverter {

    int sizeOfScreenY;

    public CoordinateConverter(int sizeOfScreenY) {
        this.sizeOfScreenY = sizeOfScreenY;
    }

    //Takes a coordinate and returns the Y-Down equivalent
    public int calcYCoord(int coordinate) {

        //If the coordinate is within the screenbounds
        if(coordinate < sizeOfScreenY && coordinate >= 0) {
            coordinate = this.sizeOfScreenY - coordinate;
        }
        //Return a unusable coordinate
        else {
            coordinate = -1;
        }

        return coordinate;
    }
}
