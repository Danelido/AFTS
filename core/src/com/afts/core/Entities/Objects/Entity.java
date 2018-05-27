package com.afts.core.Entities.Objects;

import com.afts.core.Utility.PointCalculator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    private EntityPointSetting pointSetting;
    private Vector2 position;
    private Vector2 size;
    private Vector2 origin;

    private Vector2[] points;

    private TextureRegion textureRegion;
    private float rotation;

    public Entity(Vector2 position, Vector2 size, Texture texture, EntityPointSetting pointSetting)
    {
        this.position = position;
        this.size = size;
        this.origin = new Vector2(this.size.x/2.f, this.size.y/2.f);
        this.pointSetting = pointSetting;
        this.rotation = 0.f;
        this.textureRegion = new TextureRegion(texture);

        this.setUpPoints();
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    private void setUpPoints()
    {
        if(this.pointSetting == EntityPointSetting.RECTANGLE)
        {
            this.points = new Vector2[4];
            for(int i = 0; i < 4; i++)
            {
                this.points[i] = new Vector2();
            }
        }

        if(this.pointSetting == EntityPointSetting.TRIANGLE)
        {
            this.points = new Vector2[3];
            for(int i = 0; i < 3; i++)
            {
                this.points[i] = new Vector2();
            }
        }
    }

    public void updatePoints()
    {
        PointCalculator.getPoints(this.points, this.position, this.size, this.origin, this.pointSetting, this.rotation);
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public float getRotation()
    {
        return this.rotation;
    }

    public Vector2[] getPoints()
    {
        return this.points;
    }

    public Vector2 getPosition()
    {
        return this.position;
    }

    public Vector2 getSize()
    {
        return this.size;
    }

    public Vector2 getOrigin()
    {
        return this.origin;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public EntityPointSetting getPointSetting() {
        return this.pointSetting;
    }
}
