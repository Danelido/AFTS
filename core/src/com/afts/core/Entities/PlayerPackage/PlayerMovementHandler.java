package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Need to be tested with android phone!
// Right now it uses the mouse ( Mostly for debug and tweeking :P )
public class PlayerMovementHandler {

    private Player player;

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

    // How far from the borders would you like the "max turning" would be in percentage
    private float controllerMaxWidthPercentageOfScreenSize = 0.5f;

    public PlayerMovementHandler(Player player)
    {
        this.player = player;

        // Initialize movement and rotation variables
        this.acceleration = 20.f;
        this.deAcceleration = 20.f;
        this.maxVelocity = new Vector2(8.f, 8.f);
        this.currentVelocity = new Vector2(0.f, 0.f);
        this.maxRotation = 90.f;
        this.currentRotation = 0.f;
        this.rotationAcceleration = 300.f;
        this.rotationDeAcceleration = 300.f;

    }

    public void update(Vector3 userPressed, boolean isBeingPressed)
    {
        float delta = Gdx.graphics.getDeltaTime();
        float multiplierY = this.getVelocityMultiplierY();

        if(isBeingPressed)
        {
           float multiplierX =  this.getVelocityMultiplierX(userPressed);
           this.moving(multiplierX, multiplierY, delta);
        }
        else
        {
            this.stoping(delta);
        }

        this.player.translatePosition(this.currentVelocity);
        this.player.setRotation(this.currentRotation);
    }


    private void moving(float multiplierX, float multiplierY, float delta)
    {
        if(multiplierX > 0.f)
        {
            // Movement
            if(this.currentVelocity.x < (this.maxVelocity.x * multiplierX))
            {
                this.currentVelocity.x += (this.acceleration * multiplierX) * delta;
            }
            else if(this.currentVelocity.x > (this.maxVelocity.x * multiplierX))
            {
                this.currentVelocity.x -= (this.acceleration * multiplierX) * delta;
            }
            else
            {
                this.currentVelocity.x = this.maxVelocity.x * multiplierX;
            }


            // Rotation
            if(this.currentRotation > -(this.maxRotation * multiplierX))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToDecreaseWith = (this.rotationAcceleration * multiplierX * delta);
                float endResult = this.currentRotation - amountToDecreaseWith;

                if(endResult < -(this.maxRotation*multiplierX))
                {
                    float newDecreaseValue =  -(this.maxRotation*multiplierX) - this.currentRotation;
                    this.currentRotation += newDecreaseValue;
                }
                else
                {
                    this.currentRotation -= this.rotationAcceleration * delta;
                }
            }
            else
            {
                this.currentRotation = -this.maxRotation * multiplierX;
            }

        }
        else if(multiplierX < 0.f)
        {
            // Movement
            if(this.currentVelocity.x > (this.maxVelocity.x * multiplierX))
            {
                this.currentVelocity.x += (this.acceleration * multiplierX) * delta;
            }
            else if(this.currentVelocity.x < (this.maxVelocity.x * multiplierX))
            {
                this.currentVelocity.x -= (this.acceleration * multiplierX) * delta;
            }
            else
            {
                this.currentVelocity.x = this.maxVelocity.x * multiplierX;
            }


            // Rotation
            if(this.currentRotation < -(this.maxRotation * multiplierX))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToIncreaseWith = -(this.rotationAcceleration * multiplierX * delta);
                float endResult = this.currentRotation + amountToIncreaseWith;

                if(endResult > -(this.maxRotation*multiplierX))
                {
                    float newIncreaseValue =  -(this.maxRotation*multiplierX) - this.currentRotation;
                    this.currentRotation += newIncreaseValue;
                }
                else
                {
                    this.currentRotation += this.rotationAcceleration * delta;
                }
            }
            else
            {
                this.currentRotation = -this.maxRotation * multiplierX;
            }

        }

        //System.out.println("Yvel: " +  Math.abs(1.f - (Math.abs(this.currentVelocity.x) / this.maxVelocity.x)));
        // Movement for Y
        if(this.currentVelocity.y < (this.maxVelocity.y * multiplierY))
        {
            this.currentVelocity.y += (this.acceleration *  0.1f) * delta;
        }
        else if(this.currentVelocity.y > (this.maxVelocity.y * multiplierY))
        {
            this.currentVelocity.y -= (this.acceleration * 0.1f) * delta;
        }
        else
        {
            this.currentVelocity.y = this.maxVelocity.y * multiplierY;
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

    private float getVelocityMultiplierX(Vector3 userPressed)
    {
        // Convert the screen position to game position
        float xPress = (userPressed.x / Gdx.graphics.getWidth()) *StaticSettings.GAME_WIDTH;
        // Calculate an multiplier
        float multiplier = (((xPress)  / ( StaticSettings.GAME_WIDTH / 2)) - 1.f);

        // Basically where is the max, when multiplier is either 1 or -1
        if(multiplier < -0.0f)
        {
            multiplier += (this.controllerMaxWidthPercentageOfScreenSize * multiplier);
            if(multiplier < -1.f)
                multiplier = -1.f;

        }else if(multiplier > 0.0f)
        {
            multiplier += (this.controllerMaxWidthPercentageOfScreenSize * multiplier);
            if(multiplier > 1.f)
                multiplier = 1.f;

        }

        return multiplier;

    }

    private float getVelocityMultiplierY()
    {
        return (Math.min(1.f, Math.abs(1.f - (Math.abs(this.currentVelocity.x) / this.maxVelocity.x))));
    }

    public Vector2 getMaximumVelocity()
    {
        return this.maxVelocity;
    }
}
