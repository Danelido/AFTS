package com.afts.core.Utility;

import java.util.Random;

public class Randomizer {

    private static Random rand = new Random();

    public static float getRandomFloat(float min, float max)
    {
        return (min + rand.nextFloat() * (max - min));

    }

}
