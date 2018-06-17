package com.afts.core.Entities.PlayerPackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PlayerMovementHandler {

    private Player player;

    // Movement stuff

    private float deAcceleration;
    private float maxSpeed;
    private float minAccMultiplier;
    private float miniVel = 2.f;
    private Vector2 currentVelocity;
    private Vector2 mulitpliers;
    private boolean move = false;

    public PlayerMovementHandler(Player player)
    {
        this.player = player;

        // Initialize movement and rotation variables
        this.deAcceleration = 2000.f;
        this.maxSpeed = 600;
        this.minAccMultiplier = 0.2f;
        this.currentVelocity = new Vector2(0.f, 0.f);
        this.mulitpliers = new Vector2(0,0);
}

    public void update()
    {
        float delta = Gdx.graphics.getDeltaTime();
        float multiplierX = MathUtils.cosDeg(this.player.getController().getJoystickRotation() + 90.f);
        float multiplierY = MathUtils.sinDeg(this.player.getController().getJoystickRotation()+ 90.f);
        this.mulitpliers.set(multiplierX,multiplierY);

        if(this.move)
        {
            this.moving(delta,multiplierX,multiplierY);
        }
        else
        {
            this.stopping(delta,multiplierX,multiplierY);
        }

        this.player.translatePosition(this.currentVelocity);

    }

    private void moving(float delta, float mX, float mY)
    {
        this.currentVelocity.x = this.maxSpeed * delta * mX * 35.f;
        this.currentVelocity.y = this.maxSpeed * delta * mY * 35.f;
    }

    private void stopping(float delta, float mX, float mY)
    {
    // X
      if(mX > 0)
      {
          if(this.currentVelocity.x > miniVel)
          {
              this.currentVelocity.x -= this.deAcceleration * Math.max(mX, this.minAccMultiplier) * delta;
          }
          else if(this.currentVelocity.x < -miniVel)
          {
              this.currentVelocity.x += this.deAcceleration * Math.max(mX, this.minAccMultiplier) * delta;
          }
          else
          {
                this.currentVelocity.x = 0.f;
          }

      }
      else if(mX < 0)
      {
          if(this.currentVelocity.x > miniVel)
          {
              this.currentVelocity.x += this.deAcceleration * Math.min(mX, -this.minAccMultiplier) * delta;
          }
          else if(this.currentVelocity.x < -miniVel)
          {
              this.currentVelocity.x -= this.deAcceleration * Math.min(mX, -this.minAccMultiplier) * delta;
          }
          else
          {
              this.currentVelocity.x = 0.f;
          }
      }


      // Y
        if(mY > 0)
        {
            if(this.currentVelocity.y > miniVel)
            {
                this.currentVelocity.y -= this.deAcceleration * Math.max(mY, this.minAccMultiplier) * delta;
            }
            else if(this.currentVelocity.y < -miniVel)
            {
                this.currentVelocity.y += this.deAcceleration * Math.max(mY, this.minAccMultiplier) * delta;
            }
            else
            {
                this.currentVelocity.y = 0.f;
            }

        }
        else if(mY < 0)
        {
            if(this.currentVelocity.y > miniVel)
            {
                this.currentVelocity.y += this.deAcceleration * Math.min(mY, -this.minAccMultiplier) * delta;
            }
            else if(this.currentVelocity.y < -miniVel)
            {
                this.currentVelocity.y -= this.deAcceleration * Math.min(mY, -this.minAccMultiplier) * delta;
            }
            else
            {
                this.currentVelocity.y = 0.f;
            }
        }


    }

    public void bounce(Vector2 axis)
    {
        axis.nor();
        float scalar = 0.25f;
        float minForce = 50.f;
        float force = Math.max(minForce, this.currentVelocity.len());

        this.currentVelocity.x = axis.x * scalar * force;
        this.currentVelocity.y = axis.y * scalar * force;

    }

    public void setMove(boolean move)
    {
        this.move = move;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.currentVelocity = velocity;
    }

    public float getRotationBasedOnCurrentVelocity()
    {
        float xDec = this.currentVelocity.x / this.maxSpeed;
        float yDec = this.currentVelocity.y / this.maxSpeed;
        float c = (float)Math.sqrt(Math.pow(xDec,2) + Math.pow(yDec,2));
        float rotation = (float)(Math.acos((xDec/c)) * 180.f / (float)Math.PI) - 90.f;

        return rotation;
    }

    public void addToVelocity(Vector2 vel)
    {
        this.currentVelocity.add(vel);
    }

    public Vector2 getCurrentVelocity()
    {
        return this.currentVelocity;
    }

    public void zeroVelocity()
    {
        this.currentVelocity.set(0.f,0.f);
    }

    public float getMaxSpeed()
    {
        return this.maxSpeed;
    }

    public boolean isMoving()
    {
        return this.move;
    }

    public Vector2 getMultipliers()
    {
        return this.mulitpliers;
    }

    public Vector2 getVelocityFractionABS()
    {
        return new Vector2(Math.abs(this.currentVelocity.x) / this.maxSpeed, Math.abs(this.currentVelocity.y) / this.maxSpeed);
    }
}
