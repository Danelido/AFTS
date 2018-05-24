package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// Need to be tested with android phone!
// Right now it uses the mouse ( Mostly for debug and tweeking :P )
public class PlayerMovementHandler {

    private Player player;

    // Movement stuff
    private float accelerationX;
    private float accelerationY;
    private float deAccelerationX;
    private float deAccelerationY;
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
        this.accelerationX = 2000.f;
        this.accelerationY = 600.f;
        this.deAccelerationX = 900.f;
        this.deAccelerationY = 600.f;
        this.maxVelocity = new Vector2(1400.f, 1200.f);
        this.currentVelocity = new Vector2(0.f, 0.f);
        this.maxRotation = 90.f;
        this.currentRotation = 0.f;
        this.rotationAcceleration = 550.f;
        this.rotationDeAcceleration = 550.f;

    }

    public void update(Vector3 userPressed, boolean isBeingPressed)
    {
        float delta = Gdx.graphics.getDeltaTime();
        float multiplierY = this.getVelocityMultiplierY();
        float multiplierX =  this.getVelocityMultiplierX(userPressed);
        if(isBeingPressed)
        {
           this.movingX(multiplierX, delta);
        }
        else
        {
            this.stoping(delta);
        }
        this.movingY(multiplierY, delta);

        this.player.translatePosition(this.currentVelocity);
        this.player.setRotation(this.currentRotation);
    }

    private void movingY(float multiplierY, float delta)
    {
        // Movement for Y
        if(this.currentVelocity.y < (this.maxVelocity.y * multiplierY))
        {
            this.currentVelocity.y += (this.accelerationY) * delta;
        }
        else if(this.currentVelocity.y > (this.maxVelocity.y * multiplierY))
        {
            this.currentVelocity.y -= ( this.deAccelerationY) * delta;
        }
        else
        {
            this.currentVelocity.y = this.maxVelocity.y * multiplierY;
        }


    }

    private void movingX(float multiplierX, float delta)
    {
        if(multiplierX > 0.f)
        {
            // Movement
            if(this.currentVelocity.x < (this.maxVelocity.x))
            {
                this.currentVelocity.x += (this.accelerationX) * delta;
            }
            else
            {
                this.currentVelocity.x = this.maxVelocity.x;
            }


            // Rotation
            if(this.currentRotation > -(this.maxRotation))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToDecreaseWith = (this.rotationAcceleration * delta);
                float endResult = this.currentRotation - amountToDecreaseWith;

                if(endResult > (this.maxRotation ))
                {
                    float newDecreaseValue =  -(this.maxRotation ) - this.currentRotation;
                    this.currentRotation += newDecreaseValue;
                }
                else
                {
                    this.currentRotation -= this.rotationAcceleration * delta;
                }
            }
            else
            {
                this.currentRotation = -this.maxRotation ;
            }

        }
        else if(multiplierX < 0.f)
        {
            // Movement
            if(this.currentVelocity.x > -(this.maxVelocity.x ))
            {
                this.currentVelocity.x -= (this.accelerationX) * delta;
            }
            else
            {
                this.currentVelocity.x = -this.maxVelocity.x;
            }


            // Rotation
            if(this.currentRotation < (this.maxRotation))
            {
                // Quick fix to avoid "looking jagged" when rotating towards the end
                float amountToIncreaseWith = (this.rotationAcceleration * delta);
                float endResult = this.currentRotation + amountToIncreaseWith;

                if(endResult > (this.maxRotation))
                {
                    float newIncreaseValue =  (this.maxRotation) - this.currentRotation;
                    this.currentRotation += newIncreaseValue;
                }
                else
                {
                    this.currentRotation += this.rotationAcceleration * delta;
                }
            }
            else
            {
                this.currentRotation = this.maxRotation;
            }

        }

    }

    private void rotationOfPlayer()
    {
       /*float xDec = this.currentVelocity.x / this.maxVelocity.x;
       float yDec = this.currentVelocity.y / this.maxVelocity.y;
       float c = (float)Math.sqrt(Math.pow(xDec,2) + Math.pow(yDec,2));

        float rotation = (float)(Math.acos((xDec/c)) * 180.f / (float)Math.PI) - 90.f;
        //System.out.println("Rotation: " + rotation+ "\nCurrentRotation: " + this.currentRotation + "\n");
        this.currentRotation = rotation;*/
    }

    private void stoping(float delta)
    {
        // Movement
        if(this.currentVelocity.x > 0.f)
        {
            this.currentVelocity.x -= this.deAccelerationX * delta;

            if(this.currentVelocity.x <= 0.f)
            {
                this.currentVelocity.x = 0.f;
            }

        }
        else if(this.currentVelocity.x < 0.f)
        {
            this.currentVelocity.x += (this.deAccelerationX) * delta;

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
        float xPress = (userPressed.x / Gdx.graphics.getWidth()) * StaticSettings.GAME_WIDTH;

        if(xPress < StaticSettings.GAME_WIDTH/2.f)
            return -1f;

        return 1f;



        /*
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
        */
    }

    private float getVelocityMultiplierY()
    {
        return (Math.min(1.f, Math.abs(1.f - (Math.abs(this.currentVelocity.x) / this.maxVelocity.x))));
    }

    public Vector2 getMaximumVelocity()
    {
        return this.maxVelocity;
    }

    public float getTotalSpeed()
    {
        return (float)Math.sqrt(Math.pow((double)this.currentVelocity.x, 2) + Math.pow((double)this.currentVelocity.y, 2));
    }

    public int getVerticalSpeed()
    {
        return (int)Math.abs(this.currentVelocity.y);
    }

    public int getHorizontalSpeed()
    {
        return (int)Math.abs(this.currentVelocity.x);
    }

}
