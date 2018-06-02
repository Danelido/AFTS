package com.afts.core.Math;

import com.afts.core.Utility.StaticSettings;

/**
 * Created by Steven on 14/05/2018.
 * A simple Y axis converter. It takes takes a desired Y-Coordinate and converts it to a Y-up system, instead of the built in Y-down.
 * We can now look at the system as a Y-up system
 */

public class CoordinateConverter {

    //Takes a coordinate and returns the Y-Down equivalent
    public static int calcYCoord(int coordinate)
    {
        int sizeOfScreenY = StaticSettings.GAME_HEIGHT;

        //System.out.println(sizeOfScreenY);

        //If the coordinate is within the screenBounds
        if(coordinate <= sizeOfScreenY && coordinate >= 0) {
            coordinate = sizeOfScreenY - coordinate;
        }
        //Return a unusable coordinate
        else {
            coordinate = -1;
        }

        return coordinate;
    }

}
