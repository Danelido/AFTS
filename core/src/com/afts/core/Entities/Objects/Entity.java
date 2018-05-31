package com.afts.core.Entities.Objects;

import com.afts.core.Utility.PointCalculator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    private EntityPointSetting pointSetting;
    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 origin;
    protected Vector2 velocity;
    protected Vector2[] points;
    protected Color color;

    protected TextureRegion textureRegion;
    protected float rotation;
    protected float deAccelerationRate;
    protected float collideForce;

    public Entity(Vector2 position, Vector2 size, Texture texture, EntityPointSetting pointSetting)
    {
        this.position = position;
        this.size = size;
        this.origin = new Vector2(this.size.x/2.f, this.size.y/2.f);
        this.pointSetting = pointSetting;
        this.rotation = 0.f;
        this.textureRegion = new TextureRegion(texture);
        this.velocity = new Vector2(0.f, 0.f);
        this.deAccelerationRate = 0.1f;
        this.collideForce = 100.f;
        this.color = new Color(Color.WHITE);
        this.setUpPoints();
    }

    public abstract void update(OrthographicCamera camera);
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

    public void translatePosition(Vector2 velocity)
    {
        this.position.add(velocity);
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public void setDeAccelerationRate(float rate)
    {
        this.deAccelerationRate = rate;
    }

    public float getCollideForce()
    {
        return this.collideForce;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return this.color;
    }
}
