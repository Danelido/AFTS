package com.afts.core.Entities.Collision;

import com.afts.core.Entities.Objects.Entity;
import com.afts.core.Entities.PlayerPackage.Player;
import com.badlogic.gdx.math.Vector2;

public class SATCollision {

    private Vector2 minimumPenetrationAxis;
    private float overlap;
    private int numberOfAxes = 0;

    public SATCollision()
    { }

    // This is that we don't need to check the same axis when we doing the calculations
    private boolean contains_axis(Vector2[] axis, Vector2 unit_normal)
    {
        for(int i = 0; i < numberOfAxes; i++)
        {
            if (Math.abs(unit_normal.cpy().dot(axis[i].cpy())) == 1)
                return true;
        }

        return false;
    }

    public boolean collide(Player player, Entity entity)
    {
        Vector2[] axesForPlayer = this.getAxesNormals(player.getPoints());
        this.overlap = 0x0FFFFFFF;

        // If there is NO collision on one single axis then return false because
        // that means there is no collision
        for(int i = 0; i < numberOfAxes; i++)
        {
                Vector2 axis = axesForPlayer[i].cpy();
                Vector2 playerValues = this.projectOntoAxis(player.getPoints(), axis);
                Vector2 entityValues = this.projectOntoAxis(entity.getPoints(), axis);

                // Check for no overlap( if there is none then return false )
                if (playerValues.x > entityValues.y || entityValues.x > playerValues.y) {
                    return false;
                }

                // here is for direction and such
                float overlapAmount = Math.min(playerValues.y, entityValues.y) - Math.max(playerValues.x, entityValues.x);
                if (overlapAmount < this.overlap) {
                    this.overlap = overlapAmount;
                    this.minimumPenetrationAxis = axis.cpy();
                }

        }

        Vector2[] axesForEntity = this.getAxesNormals(entity.getPoints());

        // If there is NO collision on one single axis then return false because
        // that means there is no collision
        for(int i = 0; i < numberOfAxes; i++)
        {
                Vector2 axis = axesForEntity[i].cpy();
                Vector2 playerValues = this.projectOntoAxis(player.getPoints(), axis);
                Vector2 entityValues = this.projectOntoAxis(entity.getPoints(), axis);

                // Check for no overlap( if there is none then return false )
                if (playerValues.x > entityValues.y || entityValues.x > playerValues.y) {
                    return false;
                }

                // here is for direction and such
                float overlapAmount = Math.min(playerValues.y, entityValues.y) - Math.max(playerValues.x, entityValues.x);
                if (overlapAmount < this.overlap) {
                    this.overlap = overlapAmount;
                    this.minimumPenetrationAxis = axis.cpy();
                }
        }

        // If it gets to here then its true obviously
        return true;

    }

    private Vector2 projectOntoAxis(Vector2[] vertices, Vector2 axis)
    {
        float min = axis.cpy().dot(vertices[0]);
        float max = min;

        for(int i = 1; i < vertices.length; i++)
        {
            float projection = axis.cpy().dot(vertices[i]);

            if(projection < min) min = projection;
            else if(projection > max) max = projection;
        }

        return new Vector2(min,max);
    }

    private Vector2[] getAxesNormals(Vector2[] vertices)
    {
        Vector2[] axesNormals = new Vector2[vertices.length];
        this.numberOfAxes = 0;

        for(int i = 0; i < vertices.length; i++)
        {
            Vector2 point_one = vertices[i].cpy();
            Vector2 point_two;

            if((i + 1) == vertices.length)
                point_two = vertices[0].cpy();
            else
                point_two = vertices[i+1].cpy();

            Vector2 axis = new Vector2(point_one.x - point_two.x, point_one.y - point_two.y);

            //Get the normal of the axis
            Vector2 normal = new Vector2(axis.y, -axis.x);
            normal.nor();

            // Get the direction vector of axis
            float mag = (float)Math.sqrt(Math.pow(axis.x, 2) + Math.pow(axis.y,2));

            if(mag > 0.f && !this.contains_axis(axesNormals, normal))
            {
                // add it to list
                axesNormals[this.numberOfAxes++] = normal;
            }

        }

        return axesNormals;
    }

    public Vector2 getMinimumPenetrationAxis()
    {
        return this.minimumPenetrationAxis;
    }

    public float getOverlap()
    {
        return this.overlap;
    }


}
