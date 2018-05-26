package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Steven on 15/05/2018
 */

public class Player {

    private Vector3 position; // Vector 3 to make camera lerp easier, just ignore z
    private Vector3 position_lastframe;
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

    // Particles
    private PlayerParticleHandler particleHandler;

    //Temp
    private Texture tempTex;
    private float tempX = 0.f;
    private float tempY = 200.f;

    public Player(ResourceHandler resources, OrthographicCamera camera, Vector2 position, Vector2 size)
    {
        this.batch = new SpriteBatch();
        this.playerColor = new Color(Color.WHITE);
        this.textureRegion = new TextureRegion(resources.getTexture("playerSprite"));
        
        this.camera = camera;
        this.size = size;
        this.position = new Vector3(position.cpy(), 0.f);

        // Center the player on the coordinates that was sent in
        this.position.x -= (this.size.x / 2.f);
        this.position.y -= (this.size.y / 2.f);

        this.origin = new Vector2(this.size.x / 2.f, this.size.y / 2.f);

        //Scale (Might be useful later if we want some cool effects on the player?)
        this.scale = 1.f;

        this.rotation = 0.f;

        this.userPressed = new Vector3(0.f, 0.f, 0.f);
        this.isUserPressing = false;
        this.movementHandler = new PlayerMovementHandler(this);

        this.particleHandler = new PlayerParticleHandler(this,PlayerParticleSetting.SPREAD, this.camera, 500,resources.getTexture("trailParticle"));
        this.particleHandler.setSize(12.f);
        this.particleHandler.setLifeTime(.4f);
        this.particleHandler.getParticleGenerator().setParticleColor(1.f, .5f, 0.5f, 1.f);
        this.particleHandler.getParticleGenerator().setSpawnSetting(SpawnSetting.fade_in_fade_out);

        // Temp
        this.tempTex = resources.getTexture("playerSprite");

    }

    public void update()
    {
        this.position_lastframe = this.position.cpy();
        this.movementHandler.update(this.userPressed, this.isUserPressing);
        this.particleHandler.update();
    }

    public void render()
    {

        this.particleHandler.render();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        this.drawTempTexture();

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

    // Temporary, for debug and for measurement when it comes to movement tweeking
    //--------------------------------------------------------------------------------
    private void drawTempTexture()
    {
        this.batch.draw(this.tempTex, tempX, tempY, 64.f, 64.f);

        if((tempY - this.camera.position.y) + 100.f  < (- StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom)
            tempY = (StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y + 100.f;

    }
    // --------------------------------------------------------------------------------

    public void dispose()
    {
        this.batch.dispose();
        this.particleHandler.dispose();
    }

    public void translatePosition(Vector2 velocity)
    {
        this.position.add(velocity.x * Gdx.graphics.getDeltaTime(), velocity.y * Gdx.graphics.getDeltaTime(), 0.f);
    }

    public void setTouch(float x, float y)
    {
        this.userPressed.set(x,y, 0.f);
    }

    public void setIsBeingPressed(boolean condition)
    {
        this.isUserPressing = condition;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Vector3 getPosition()
    {
        return this.position;
    }

    public Vector2 getOrigin()
    {
        return this.origin;
    }

    public float getRotation()
    {
        return this.rotation;
    }

    public Vector3 getPositonLastFrame()
    {
        return this.position_lastframe;
    }

    public PlayerMovementHandler getMovementHandler() {
        return this.movementHandler;
    }

    public Vector2 getSize()
    {
        return this.size;
    }
}
