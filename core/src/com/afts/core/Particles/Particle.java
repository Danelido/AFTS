package com.afts.core.Particles;

import com.badlogic.gdx.graphics.Color;

public class Particle {

    private float x,y,width,height;
    private float rotation;
    private float lifetime;
    private float velX, velY;
    private float initialLifeTime;
    private Color color;

    public Particle(float x, float y, float width, float height,float lifetime)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lifetime = lifetime;
        this.initialLifeTime = lifetime;
        this.rotation = 0.f;
        this.velX = 0;
        this.velY = 0;
        this.color = new Color(1.f,1.f,1.f,0.f);

    }

    public void update(float delta)
    {
        this.x += this.velX * delta;
        this.y += this.velY * delta;
        this.lifetime -= delta;


        if(this.lifetime < 0.f)
        {
           this.lifetime = 0.f;
           this.color.a = 0.f;
        }
    }

    public boolean isDead()
    {
        return lifetime <= 0.f;
    }

    public void setLifeTime(float lifetime)
    {
        this.lifetime = lifetime;
        this.initialLifeTime = lifetime;
    }

    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(float velX, float velY)
    {
        this.velX = velX;
        this.velY = velY;
    }

    public void setColor(float r, float g, float b, float a)
    {
        this.color.set(r,g,b,a);
    }

    public void setColor(float r, float g, float b)
    {
        this.color.set(r,g,b,this.color.a);
    }

    public void setColorRGB(Color newColor){this.color.set(newColor.r, newColor.g, newColor.b, this.color.a);}

    public void setAlpha(float a)
    {
        if(a < 0.f) a = 0.f;
        if(a > 1.f) a = 1.f;

        this.color.a = a;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLifetime() {
        return lifetime;
    }

    public Color getColor()
    {
        return this.color;
    }

    public float getInitialLifeTime()
    {
        return this.initialLifeTime;
    }

    public float getRotation()
    {
        return this.rotation;
    }

}
