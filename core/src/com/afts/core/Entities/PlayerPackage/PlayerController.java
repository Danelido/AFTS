package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerController {

    private Player player;
    private OrthographicCamera camera;
    //old
    private TextureRegion joyStickTexture, throttleTexture;
    //New
    private TextureRegion joystickBaseTexture, joystickTopTexture;

    private Vector2 throttleSize;
    //Old
    private float joystickRadius, joystickRotation;
    private Vector2 joyStickPosition, throttlePosition;
    //New
    private Vector2 joystickBasePosition, joystickTopPosition;
    private float joystickBaseDiameter, joystickTopRadius;


    private int throttlePointer, joystickPointer;
    private boolean joyStickActive, throttleActive;

    private PlayerMovementHandler playerMovementHandler;

    public PlayerController(Player player, ResourceHandler resourceHandler)
    {
        this.player = player;
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);

        // So that (0,0) is at the left corner
        this.camera.position.x = StaticSettings.GAME_WIDTH / 2.f;
        this.camera.position.y = StaticSettings.GAME_HEIGHT / 2.f;



        // Joystick textures
        this.joystickBaseTexture = new TextureRegion(resourceHandler.getTexture("joystickBase"));
        this.joystickTopTexture = new TextureRegion(resourceHandler.getTexture("joystickTop"));
        //
        // Joystick positions
        this.joystickBasePosition = new Vector2(100, 100);
        this.joystickTopPosition = new Vector2(125, 125);
        //
        //Joystick Radii & positions
        this.joystickBaseDiameter = 150.0f;
        this.joystickTopRadius = 100.0f;


        this.joyStickTexture = new TextureRegion(resourceHandler.getTexture("joystick"));
        this.throttleTexture = new TextureRegion(resourceHandler.getTexture("throttle"));

        this.joystickRadius = 100.f;
        this.joystickRotation = 0.f;
        this.throttleSize = new Vector2(128.f, 170.f);

        this.joyStickPosition = new Vector2(100, 100);
        this.throttlePosition = new Vector2(StaticSettings.GAME_WIDTH - 100.f - this.throttleSize.x,100.f );

        this.joystickPointer = -1;
        this.throttlePointer = -1;

        this.playerMovementHandler = new PlayerMovementHandler(this.player);
    }

    // The z value is the pointer
    public void registerTouchDown(Vector3 touch)
    {
        if(!this.joyStickActive)
        {
            this.joyStickActive = this.checkJoystick(touch);

            // to actually be able to move the player on a computer
            //Should not be here when running on android
            //this.throttleActive = this.joyStickActive;
            //______________________________________________
        }

        if(!this.throttleActive)
        {
            this.throttleActive = this.checkThrottle(touch);
        }
    }

    // The z value is the pointer
    public void registerTouchMoved(Vector3 touch)
    {
        if(touch.z == this.joystickPointer)
        {
            this.updateRotationOfJoystick(touch);
        }
    }

    // The z value is the pointer
    public void registerTouchUp(Vector3 touch)
    {
        if(this.joyStickActive)
        {
            if(touch.z == this.joystickPointer) {
                this.joyStickActive = false;
                this.joystickPointer = -1;

                // to actually be able to move the player on a computer
                //Should not be here when running on android
               // this.throttleActive = this.joyStickActive;
                //______________________________________________
            }
        }

        if(this.throttleActive)
        {
            if(touch.z == this.throttlePointer)
            {
                this.throttleActive = false;
                this.throttlePointer = -1;
            }
        }
    }

    public void update()
    {
        this.camera.update();
       // this.player.setRotation(this.joystickRotation);
        this.playerMovementHandler.update();

        if(this.throttleActive )
        {
           this.playerMovementHandler.setMove(true);
        }else
        {
            this.playerMovementHandler.setMove(false);
        }
    }

    public void render(SpriteBatch batch)
    {
        batch.setProjectionMatrix(this.camera.combined);
/*
        batch.draw(this.joyStickTexture,
                this.joyStickPosition.x, this.joyStickPosition.y,
                this.joystickRadius, this.joystickRadius,
                this.joystickRadius * 2.f,  this.joystickRadius * 2.f,
                1.f, 1.f,
                this.joystickRotation);*/

        batch.draw(this.throttleTexture,
                this.throttlePosition.x, this.throttlePosition.y,
                this.throttleSize.x, this.throttleSize.y,
                this.throttleSize.x,   this.throttleSize.y,
                1.f, 1.f,
                0.f);

        batch.draw(this.joystickBaseTexture,
                this.joystickBasePosition.x, this.joystickBasePosition.y,
               100 * 1.5f,100 * 1.5f);

        batch.draw(this.joystickTopTexture,
                this.joystickTopPosition.x, this.joystickTopPosition.y,
                100, 100);
    }

    public void dispose()
    {

    }

    //Check to see if the touch position is within the boundaries of the joystick
    private boolean checkJoystick(Vector3 touch)
    {
        Vector3 fingerPos = touch.cpy();
        this.camera.unproject(fingerPos);

        if(fingerPos.x >= this.joystickBasePosition.x && fingerPos.x <= this.joystickBasePosition.x + this.joystickBaseDiameter) {
            if(fingerPos.y >= this.joystickBasePosition.y && fingerPos.y <= this.joystickBasePosition.y + this.joystickBaseDiameter) {

                /*Calculate the specific position for the top joystick*/
                //Old ---- We can polish this
                this.joystickPointer = (int)touch.z;
                //
                this.joystickTopPosition.x = fingerPos.x - (100 / 2);
                this.joystickTopPosition.y = fingerPos.y - (100 / 2);
                return true;
            }

        }

        return false;
    }

    private boolean checkThrottle(Vector3 touch)
    {
        Vector3 fingerPos = touch.cpy();
        this.camera.unproject(fingerPos);

        if(fingerPos.x >= this.throttlePosition.x && fingerPos.x <= this.throttlePosition.x + this.throttleSize.x)
        {
            if(fingerPos.y >= this.throttlePosition.y && fingerPos.y <= this.throttlePosition.y + this.throttleSize.y)
            {
                this.throttlePointer = (int)touch.z;
               return true;
            }
        }

        return false;
    }

    private void updateRotationOfJoystick(Vector3 touch)
    {
        Vector3 fingerPos = touch.cpy();
        this.camera.unproject(fingerPos);

        //This is very sloppy and need to be polished
        if(fingerPos.x >= this.joystickBasePosition.x && fingerPos.x <= this.joystickBasePosition.x + this.joystickBaseDiameter) {
            if (fingerPos.y >= this.joystickBasePosition.y && fingerPos.y <= this.joystickBasePosition.y + this.joystickBaseDiameter) {


                this.joystickTopPosition.x = fingerPos.x - (100 / 2);
                this.joystickTopPosition.y = fingerPos.y - (100 / 2);

            }
        }

        this.joystickRotation = (float)Math.atan2(
                this.joyStickPosition.y + this.joystickRadius - fingerPos.y,
                this.joyStickPosition.x + this.joystickRadius - fingerPos.x

        ) * 180.f / (float)Math.PI;

        this.joystickRotation += 90.f;
    }

    public PlayerMovementHandler getMovementHandler()
    {
        return this.playerMovementHandler;
    }

    public float getJoystickRotation()
    {
        return this.joystickRotation;
    }
}
