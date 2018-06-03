package com.afts.core.Utility;

import com.afts.core.Entities.Objects.CollisionPointSetup;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PointCalculator {

    // This assume that the poiints has already been initialized (the're not null)
    public static Vector2[] getPoints(Vector2[] points, Vector2 position, Vector2 size, Vector2 origin, CollisionPointSetup setting, float degree)
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

        return vec;
    }

    public static Vector2 getPointSeparationDirectionVector(Vector2[] object1, Vector2[] object2)
    {

      float object_one_lowestX = object1[0].x;
      float object_one_lowestY = object1[0].y;
      float object_two_greatestX = object2[0].x;
      float object_two_greatestY = object2[0].y;

      //Find the lowest x and y for object one
      for(int i = 1; i < object1.length; i++)
      {
          if(object_one_lowestX > object1[i].x)
          {
              object_one_lowestX = object1[i].x;
          }

          if(object_one_lowestY > object1[i].y)
          {
              object_one_lowestY = object1[i].y;
          }
      }


        //Find the greatest x and y for object two
        for(int i = 1; i < object2.length; i++)
        {
            if(object_two_greatestX < object2[i].x)
            {
                object_two_greatestX = object2[i].x;
            }

            if(object_two_greatestY < object2[i].y)
            {
                object_two_greatestY = object2[i].y;
            }
        }

        Vector2 separationDirection = new Vector2(
                object_one_lowestX - object_two_greatestX,
                object_two_greatestY - object_one_lowestY
        );

        return separationDirection;
    }


}
