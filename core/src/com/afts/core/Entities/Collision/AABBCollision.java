package com.afts.core.Entities.Collision;

public class AABBCollision {

    public static boolean isColliding(AABBRectangle rect1, AABBRectangle rect2)
    {
        if(rect1.getBeginX() >= rect2.getBeginX() && rect1.getEndX() <= rect2.getEndX())
        {
            if(rect1.getBeginY() >= rect2.getBeginY() && rect1.getEndY() <= rect2.getEndY())
            {
                return true;
            }
        }
        return false;
    }
}
