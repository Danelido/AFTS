package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Need to be tested with android phone!
// Right now it uses the mouse ( Mostly for debug and tweeking :P )
public class PlayerMovementHandler {

    private Player player;
    private OrthographicCamera camera;

    // Movement stuff
    private float acceleration;
    private float deAcceleration;
    private Vector2 maxVelocity;
    private Vector2 currentVelocity;

    // Rotation stuff
    private float maxRotation;
    private float currentRotation;
    private float rotationAcceleration;
    private float rotationDeAcceleration;

    public PlayerMovementHandler(Player player, OrthographicCamera camera)
    {
        this.player = player;
        this.camera = camera;

        // Initialize movement and rotation variables
        this.acceleration = 20.f;
        this.deAcceleration = 20.f;
        this.maxVelocity = new Vector2(8.f, 20.f);
        this.currentVelocity = new Vector2(0.f, 0.f);
        this.maxRotation = 60.f;
        this.currentRotation = 0.f;
        this.rotationAcceleration = 300.f;
        this.rotationDeAcceleration = 300.f;

    }

    public void update(Vector3 userPressed, boolean isBeingPressed)
    {
        float delta = Gdx.graphics.getDeltaTime();

        if(isBeingPressed)
        {
           float multiplier =  this.getVelocityMultiplier(userPressed);
           this.moving(multiplier, delta);
        }
        else
        {
            this.stoping(delta);
        }

        this.player.translatePosition(this.currentVelocity);
        this.player.setRotation(this.currentRotation);
    }


    private void moving(float multiplier, float delta)
    {
        if(multiplier > 0.f)
        {
            // Movement
            if(this.currentVelocity.x < (this.maxVelocity.x * multiplier))
            {
                this.currentVelocity.x += (this.acceleration * multiplier) * delta;
            }
            else if(this.currentVelocity.x > (this.maxVelocity.x * multiplier))
            {
                this.currentVelocity.x -= (this.acceleration * multiplier) * delta;
            }
            else
            {
                this.currentVelocity.x = this.maxVelocity.x * multiplier;
            }


            // Rotation
            if(this.currentRotation > -(this.maxRotation * multiplier))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToDecreaseWith = (this.rotationAcceleration * multiplier * delta);
                float endResult = this.currentRotation - amountToDecreaseWith;

                if(endResult < -(this.maxRotation*multiplier))
                {
                    float newDecreaseValue =  -(this.maxRotation*multiplier) - this.currentRotation;
                    this.currentRotation += newDecreaseValue;
                }
                else
                {
                    this.currentRotation -= this.rotationAcceleration * multiplier * delta;
                }
            }
            else
            {
                this.currentRotation = -this.maxRotation * multiplier;
            }

        }
        else if(multiplier < 0.f)
        {
            // Movement
            if(this.currentVelocity.x > (this.maxVelocity.x * multiplier))
            {
                this.currentVelocity.x += (this.acceleration * multiplier) * delta;
            }
            else if(this.currentVelocity.x < (this.maxVelocity.x * multiplier))
            {
                this.currentVelocity.x -= (this.acceleration * multiplier) * delta;
            }
            else
            {
                this.currentVelocity.x = this.maxVelocity.x * multiplier;
            }


            // Rotation
            if(this.currentRotation < -(this.maxRotation * multiplier))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToIncreaseWith = -(this.rotationAcceleration * multiplier * delta);
                float endResult = this.currentRotation + amountToIncreaseWith;

                if(endResult > -(this.maxRotation*multiplier))
                {
                    float newIncreaseValue =  -(this.maxRotation*multiplier) - this.currentRotation;
                    this.currentRotation += newIncreaseValue;
                }
                else
                {
                    this.currentRotation -= this.rotationAcceleration * multiplier * delta;
                }
            }
            else
            {
                this.currentRotation = -this.maxRotation * multiplier;
            }

        }

    }

    private void stoping(float delta)
    {
        // Movement
        if(this.currentVelocity.x > 0.f)
        {
            this.currentVelocity.x -= this.deAcceleration * delta;

            if(this.currentVelocity.x <= 0.f)
            {
                this.currentVelocity.x = 0.f;
            }

        }
        else if(this.currentVelocity.x < 0.f)
        {
            this.currentVelocity.x += (this.deAcceleration) * delta;

            if(this.currentVelocity.x >= 0.f)
            {
                this.currentVelocity.x = 0.f;
            }
        }


        // Rotation
        if(this.currentRotation > 0.f)
        {
            this.currentRotation -= this.rotationDeAcceleration * delta;

            if(this.currentRotation <= 0.f)
            {
                this.currentRotation = 0.f;
            }
        }
        else if(this.currentRotation < 0.f)
        {
            this.currentRotation += this.rotationDeAcceleration * delta;

            if(this.currentRotation >= 0.f)
            {
                this.currentRotation = 0.f;
            }
        }

    }

    private float getVelocityMultiplier(Vector3 userPressed)
    {
        // Convert the screen position to game position
        float xPress = (userPressed.x / Gdx.graphics.getWidth()) * StaticSettings.GAME_WIDTH;
        // Calculate an multiplier
        float  multiplier = (xPress / ( StaticSettings.GAME_WIDTH / 2)) - 1.f;

        return multiplier;
    }
}
