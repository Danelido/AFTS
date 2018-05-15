package com.afts.core.Math;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steven on 14/05/2018.
 * A simple Y axis converter. It takes takes a desired Y-Coordinate and converts it to a Y-Down system, instead of the built in Y-Up.
 * We can now look at the system as a Y-down system
 */

public class CoordinateConverter {

    int sizeOfScreenY;

    public CoordinateConverter()
    {
        //Change this to the
        this.sizeOfScreenY = StaticSettings.GAME_HEIGHT;
    }

    //Takes a coordinate and returns the Y-Down equivalent
    public int calcYCoord(int coordinate)
    {

        //If the coordinate is within the screenbounds
        if(coordinate <= sizeOfScreenY && coordinate >= 0) {
            coordinate = this.sizeOfScreenY - coordinate;
        }
        //Return a unusable coordinate
        else {
            coordinate = -1;
        }

        return coordinate;
    }

}
