package com.afts.core.Entities.Objects;

import com.afts.core.Entities.Collision.AABBRectangle;
import com.afts.core.Entities.Collision.PointDebugRenderer;
import com.afts.core.Utility.PointCalculator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    private CollisionPointSetup collisionPointSetup;
    private OnCollisionSetting onCollisionSetting;
    protected Vector2 position;
    protected Vector2 size;
    protected Vector2 origin;
    protected Vector2 velocity;
    private Vector2[] points;
    protected Color color;

    protected TextureRegion textureRegion;
    protected float rotation;

    protected float deAccelerationAmount;
    private Vector2 initialPushVelocity;

    private AABBRectangle aabbRectangle;

    public Entity(Vector2 position, Vector2 size, float rotation, Texture texture, CollisionPointSetup collisionPointSetup, OnCollisionSetting onCollisionSetting)
    {
        this.position = position;
        this.size = size;
        this.origin = new Vector2(this.size.x/2.f, this.size.y/2.f);
        this.collisionPointSetup = collisionPointSetup;
        this.onCollisionSetting = onCollisionSetting;
        this.rotation = rotation;
        this.textureRegion = new TextureRegion(texture);
        this.velocity = new Vector2(0.f, 0.f);
        this.color = new Color(Color.WHITE);
        this.deAccelerationAmount = 1200.f;

        // When player is colliding it sets a vector of an velocity in which the object will be pushed towards, this saves the initial value
        // for a smoother de-acceleration
        this.initialPushVelocity = new Vector2();
        this.aabbRectangle = new AABBRectangle();
        this.setUpPoints();
    }

    public abstract void update(OrthographicCamera camera);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    private void setUpPoints()
    {

        this.points = new Vector2[PointCalculator.getNumberOfPointsForSpecifiedSetting(this.collisionPointSetup)];
        for(int i = 0; i < PointCalculator.getNumberOfPointsForSpecifiedSetting(this.collisionPointSetup); i++)
        {
            this.points[i] = new Vector2();
        }

    }

    public void updatePoints()
    {
        PointCalculator.getPoints(this.points, this.position, this.size, this.origin, this.collisionPointSetup, this.rotation + 90.f);
    }

    public void internal_update(){
       this.position.add(this.velocity.cpy().scl(Gdx.graphics.getDeltaTime()));

       if(this.velocity.x > 0.f)
       {
           this.velocity.x -= this.deAccelerationAmount * Math.abs(this.velocity.x /  this.initialPushVelocity.x) * Gdx.graphics.getDeltaTime();
           if(this.velocity.x < 0.f) this.velocity.x = 0.f;
       }else if(this.velocity.x < 0.f)
       {
           this.velocity.x += this.deAccelerationAmount * Math.abs(this.velocity.x /  this.initialPushVelocity.x) * Gdx.graphics.getDeltaTime();
           if(this.velocity.x > 0.f) this.velocity.x = 0.f;
       }

        if(this.velocity.y > 0.f)
        {
            this.velocity.y -= this.deAccelerationAmount * Math.abs(this.velocity.y /  this.initialPushVelocity.y) * Gdx.graphics.getDeltaTime();
            if(this.velocity.y < 0.f) this.velocity.y = 0.f;
        }else if(this.velocity.y < 0.f)
        {
            this.velocity.y += this.deAccelerationAmount * Math.abs(this.velocity.y /  this.initialPushVelocity.y) * Gdx.graphics.getDeltaTime();
            if(this.velocity.y > 0.f) this.velocity.y = 0.f;
        }

        this.aabbRectangle.update(this.position, this.size, this.origin);


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

    public CollisionPointSetup getCollisionPointSetup() {
        return this.collisionPointSetup;
    }

    public OnCollisionSetting getOnCollisionSetting() {
        return this.onCollisionSetting;
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

    public void bounce(Vector2 axis, Vector2 playerVelocity)
    {
        axis.nor();
        axis.scl(-1.f);
        float force = playerVelocity.len();
        float scalar = 0.75f;

        this.velocity.x = axis.x * scalar * force;
        this.velocity.y = axis.y * scalar * force;

        this.initialPushVelocity = this.velocity.cpy();

    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return this.color;
    }

    public AABBRectangle getAabbRectangle() {
        return this.aabbRectangle;
    }
}

