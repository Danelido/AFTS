package com.afts.core.Utility;

public class Utils {

    public static boolean hasSameSign(float a, float b)
    {
        if(a <= 0 && b <= 0) return true;
        else if(a >= 0 && b >= 0) return true;
        else if(a == b) return true;

        return false;
    }

}
