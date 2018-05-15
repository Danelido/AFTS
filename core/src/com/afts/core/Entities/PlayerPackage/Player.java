package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Math.CoordinateConverter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Steven on 15/05/2018
 */

public class Player {

    private Vector2 position;
    private Vector2 size;
    private Vector2 origin;

    private float rotation, scale;

    private TextureRegion textureRegion;
    private SpriteBatch batch;
    private Color playerColor;

    // Where the user is pressing
    private Vector3 userPressed;
    private boolean isUserPressing;

    private OrthographicCamera camera;
    private PlayerMovementHandler movementHandler;

    //Temp
    private Texture tempTex;

    public Player(Texture texture, OrthographicCamera camera, Vector2 position,Vector2 size)
    {
        this.batch = new SpriteBatch();
        this.playerColor = new Color(Color.WHITE);
        this.textureRegion = new TextureRegion(texture);

        this.camera = camera;
        this.size = size;
        this.position = position;

        // Center the player on the coordinates that was sent in
        this.position = position;
        this.position.x -= (this.size.x / 2.f);
        this.position.y -= (this.size.y / 2.f);

        // Set the origin of the player
        this.origin = new Vector2(this.size.x / 2.f, this.size.y / 2.f);

        //Scale (Might be useful later if we want some cool effects on the player?)
        this.scale = 1.f;

        // Rotation
        this.rotation = 0.f;

        this.userPressed = new Vector3(0.f, 0.f, 0.f);
        this.isUserPressing = true;

        this.movementHandler = new PlayerMovementHandler(this, this.camera);

        this.tempTex = texture;
    }

    public void update()
    {
        // THIS is the debug line for the movement, right now it uses the mouse
        // To test on android, remove this here and in playstate call
        // player.setTouch in the touchDown function AND remember to set
        // userPressing to true. Remember to set it to false in the touchUp function!
        this.userPressed.set((float)Gdx.input.getX(), (float)Gdx.input.getY(), 0.f);

        this.movementHandler.update(this.userPressed, this.isUserPressing);
    }

    public void render()
    {
        this.batch.setProjectionMatrix(this.camera.combined);

        // Might be using this batch for the particles later on
        this.batch.begin();

        // Just temporary, used it to tweek the movement
        this.batch.draw(this.tempTex, 0.f, 200.f, 64.f, 64.f);

        this.batch.setColor(this.playerColor);
        this.batch.draw(this.textureRegion,
                this.position.x, this.position.y,
                this.origin.x, this.origin.y,
                this.size.x, this.size.y,
                this.scale, this.scale,
                this.rotation);
        this.batch.setColor(Color.WHITE);

        this.batch.end();
    }

    public void dispose()
    {
        this.batch.dispose();
    }


    public void translatePosition(Vector2 velocity)
    {
        this.camera.translate(velocity);
        this.position.add(velocity);
    }

    public void setTouch(float x, float y)
    {
        this.userPressed.set(x,y, 0.f);
    }

    public void setShapeColour(float r, float g, float b, float a)
    {
       this.playerColor.set(r,g,b,a);
    }

    public void setShapeColour(Color color)
    {
        this.playerColor.set(color);
    }

    public void setIsBeingPressed(boolean condition)
    {
        this.isUserPressing = condition;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public void translateRotation(float rotationVelocity)
    {
        this.rotation += rotationVelocity;
    }

    public float getRotation()
    {
        return this.rotation;
    }

    public Vector2 getPosition()
    {
        return this.position;
    }

    public Vector2 getSize()
    {
        return this.position;
    }

    public Vector2 getOrigin()
    {
        return this.position;
    }


}
