package com.afts.core.Entities.Collision;

import com.badlogic.gdx.math.Vector2;

public class AABBRectangle {

    private float beginX, beginY, endX, endY;

    public AABBRectangle()
    {
       this.beginX = 0.f;
       this.beginY = 0.f;
       this.endX = 0.f;
       this.endY = 0.f;
    }

    public void update(Vector2 position, Vector2 size, Vector2 origin)
    {
        this.beginX = position.x - size.x + origin.x;
        this.beginY = position.y - size.y + origin.y;

        this.endX = position.x + size.x + origin.x;
        this.endY = position.y + size.y + origin.y;
    }

    public float getBeginX()
    { return this.beginX; }

    public float getEndX()
    { return this.endX; }

    public float getBeginY()
    { return this.beginY; }

    public float getEndY()
    { return this.endY; }

}
