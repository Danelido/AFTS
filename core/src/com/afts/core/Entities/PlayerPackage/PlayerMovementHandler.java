package com.afts.core.Entities.PlayerPackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

// Need to be tested with android phone!
// Right now it uses the mouse ( Mostly for debug and tweeking :P )
public class PlayerMovementHandler {

    private Player player;

    // Movement stuff
    private float acceleration;
    private float deAcceleration;
    private float maxSpeed;
    private float minAccMultiplier;

    private Vector2 currentVelocity;

    private boolean move = false;

    public PlayerMovementHandler(Player player)
    {
        this.player = player;

        // Initialize movement and rotation variables
        this.acceleration = 1000.f;
        this.deAcceleration = 600.f;
        this.maxSpeed = 1000;
        this.minAccMultiplier = 0.095f;
        this.currentVelocity = new Vector2(0.f, 0.f);

    }

    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();
        float multiplierX = MathUtils.cosDeg(this.player.getRotation() + 90.f);
        float multiplierY = MathUtils.sinDeg(this.player.getRotation() + 90.f);

        if(this.move) {
            this.movingX(multiplierX, delta);
            this.movingY(multiplierY,delta);
        }else
        {
            this.stopping(delta);
        }

        this.player.translatePosition(this.currentVelocity);

    }

    private void movingY(float multiplierY, float delta)
    {
        if(multiplierY > 0.f)
        {
            // Movement
            if(this.currentVelocity.y < this.maxSpeed * multiplierY)
            {
                this.currentVelocity.y += this.acceleration * Math.max(multiplierY, this.minAccMultiplier) * delta;
            }

            if(this.currentVelocity.y > this.maxSpeed*multiplierY)
            {
                this.currentVelocity.y -= this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed)* delta;

                if(this.currentVelocity.y <= this.maxSpeed*multiplierY)
                {
                    this.currentVelocity.y = this.maxSpeed*multiplierY;
                }
            }

        }
        else if(multiplierY < 0.f)
        {
            // Movement
            if(this.currentVelocity.y > this.maxSpeed * multiplierY)
            {
                this.currentVelocity.y += this.acceleration * Math.min(multiplierY, -this.minAccMultiplier) * delta;
            }

            if(this.currentVelocity.y < this.maxSpeed*multiplierY)
            {
                this.currentVelocity.y += this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed)* delta;

                if(this.currentVelocity.y >= this.maxSpeed*multiplierY)
                {
                    this.currentVelocity.y = this.maxSpeed*multiplierY;
                }
            }

        }else{

            // Y stopping
            if(this.currentVelocity.y > 0.f)
            {
                this.currentVelocity.y -= this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed)* delta;

                if(this.currentVelocity.y <= 0.f)
                {
                    this.currentVelocity.y = 0.f;
                }

            }
            else if(this.currentVelocity.y < 0.f)
            {
                this.currentVelocity.y += this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed) * delta;

                if(this.currentVelocity.y >= 0.f)
                {
                    this.currentVelocity.y = 0.f;
                }
            }


        }

    }

    private void movingX(float multiplierX, float delta)
    {
        if(multiplierX > 0.f)
        {
            // Movement
            if(this.currentVelocity.x < this.maxSpeed * multiplierX)
            {
                this.currentVelocity.x += this.acceleration * Math.max(multiplierX, this.minAccMultiplier) * delta;
            }

            if(this.currentVelocity.x > this.maxSpeed*multiplierX)
            {
                this.currentVelocity.x -= this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed)* delta;

                if(this.currentVelocity.x <= this.maxSpeed*multiplierX)
                {
                    this.currentVelocity.x = this.maxSpeed*multiplierX;
                }
            }


        }
        else if(multiplierX < 0.f)
        {
            // Movement
            if(this.currentVelocity.x > this.maxSpeed * multiplierX)
            {
                this.currentVelocity.x += this.acceleration * Math.min(multiplierX, -this.minAccMultiplier) * delta;
            }

            if(this.currentVelocity.x < this.maxSpeed*multiplierX)
            {
                this.currentVelocity.x += this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed)* delta;

                if(this.currentVelocity.x >= this.maxSpeed*multiplierX)
                {
                    this.currentVelocity.x = this.maxSpeed*multiplierX;
                }
            }

        }else
        {
            // X stopping
            if(this.currentVelocity.x > 0.f)
            {
                this.currentVelocity.x -= this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed) * delta;

                if(this.currentVelocity.x <= 0.f)
                {
                    this.currentVelocity.x = 0.f;
                }

            }
            else if(this.currentVelocity.x < 0.f)
            {
                this.currentVelocity.x += this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed) * delta;

                if(this.currentVelocity.x >= 0.f)
                {
                    this.currentVelocity.x = 0.f;
                }
            }
        }

    }

    private void stopping(float delta)
    {
        // X
        if(this.currentVelocity.x > 0.f)
        {
            this.currentVelocity.x -= this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed) * delta;

            if(this.currentVelocity.x <= 0.f)
            {
                this.currentVelocity.x = 0.f;
            }

        }
        else if(this.currentVelocity.x < 0.f)
        {
            this.currentVelocity.x += this.deAcceleration * Math.abs(this.currentVelocity.x /  this.maxSpeed) * delta;

            if(this.currentVelocity.x >= 0.f)
            {
                this.currentVelocity.x = 0.f;
            }
        }


        // Y
        if(this.currentVelocity.y > 0.f)
        {
            this.currentVelocity.y -= this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed)* delta;

            if(this.currentVelocity.y <= 0.f)
            {
                this.currentVelocity.y = 0.f;
            }

        }
        else if(this.currentVelocity.y < 0.f)
        {
            this.currentVelocity.y += this.deAcceleration * Math.abs(this.currentVelocity.y /  this.maxSpeed) * delta;

            if(this.currentVelocity.y >= 0.f)
            {
                this.currentVelocity.y = 0.f;
            }
        }

    }

    public void setMove(boolean move)
    {
        this.move = move;
    }

    public float getRotationBasedOnCurrentVelocity()
    {
        float xDec = this.currentVelocity.x / this.maxSpeed;
        float yDec = this.currentVelocity.y / this.maxSpeed;
        float c = (float)Math.sqrt(Math.pow(xDec,2) + Math.pow(yDec,2));
        float rotation = (float)(Math.acos((xDec/c)) * 180.f / (float)Math.PI) - 90.f;

        return rotation;
    }

    public Vector2 getCurrentVelocity()
    {
        return this.currentVelocity;
    }

    public float getMaxSpeed()
    {
        return this.maxSpeed;
    }

    public boolean isMoving()
    {
        return this.move;
    }
}
