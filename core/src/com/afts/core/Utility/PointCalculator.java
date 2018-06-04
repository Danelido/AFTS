package com.afts.core.Utility;

import com.afts.core.Entities.Objects.CollisionPointSetup;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PointCalculator {

    public static int getNumberOfPointsForSpecifiedSetting(CollisionPointSetup setting)
    {
        if(setting == CollisionPointSetup.RECTANGLE) return 4;
        if(setting == CollisionPointSetup.TRIANGLE) return 3;

        return -1;
    }

    // This assume that the points has already been initialized (the're not null)
    public static void getPoints(Vector2[] points, Vector2 position, Vector2 size, Vector2 origin, CollisionPointSetup setting, float degree)
    {
        Vector2[] vec = points.clone();
        float posX;
        float posY;
        float rotatePointX;
        float rotatePointY;

        if(setting == CollisionPointSetup.RECTANGLE)
        {
            // (bottom left)
            posX = position.x;
            posY = position.y;
            rotatePointX =  posX + origin.x;
            rotatePointY =  posY + origin.y;

            vec[0].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree - 90.f) + rotatePointX;
            vec[0].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree - 90.f) + rotatePointY;

            // (bottom right)
            posX = position.x + size.x;
            posY = position.y;
            rotatePointX = posX - origin.x;
            rotatePointY = posY + origin.y;

            vec[1].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[1].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;

            // (top right)
            posX = position.x + size.x;
            posY = position.y + size.y;
            rotatePointX = posX - origin.x;
            rotatePointY = posY - origin.y;

            vec[2].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[2].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;

            // (top left)
            posX = position.x;
            posY = position.y + size.y;
            rotatePointX = posX + origin.x;
            rotatePointY = posY - origin.y;

            vec[3].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[3].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;
        }

        // bottom-left, top, bottom-right
        else if(setting == CollisionPointSetup.TRIANGLE)
        {
            // (bottom left)
            posX = position.x;
            posY = position.y + size.y;
            rotatePointX = posX + origin.x;
            rotatePointY = posY - origin.y;

            vec[0].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[0].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;

            // (top)
            posX = position.x + (size.x / 2.f);
            posY = position.y;
            rotatePointX = posX;
            rotatePointY = posY + origin.y;

            vec[1].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[1].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;

            // (bottom right)
            posX = position.x + size.x;
            posY = position.y + size.y;
            rotatePointX = posX - origin.x;
            rotatePointY = posY - origin.y;

            vec[2].x = (posX - rotatePointX) * MathUtils.cosDeg(degree - 90.f) - (posY - rotatePointY) * MathUtils.sinDeg(degree- 90.f) + rotatePointX;
            vec[2].y = (posX - rotatePointX) * MathUtils.sinDeg(degree - 90.f) + (posY - rotatePointY) * MathUtils.cosDeg(degree- 90.f) + rotatePointY;
        }
    }


}
