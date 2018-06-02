package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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

    // Particles
    private ParticleGenerator particleGenerator;
    private float particleSpeed = 1.5f;
    private float particleWidth = 6.f;
    private float particleHeight = 6.f;
    private float particleSpreadMultiplier;

    //Temp
    private Texture tempTex;

    public Player(ResourceHandler resources, OrthographicCamera camera, Vector2 position, Vector2 size)
    {
        this.batch = new SpriteBatch();
        this.playerColor = new Color(Color.WHITE);
        this.textureRegion = new TextureRegion(resources.getTexture("playerSprite"));

        this.camera = camera;
        this.size = size;
        this.position = position;

        // Center the player on the coordinates that was sent in
        this.position = position;
        this.position.x -= (this.size.x / 2.f);
        this.position.y -= (this.size.y / 2.f);

        // Set the origin of the player
        this.origin = new Vector2(this.size.x / 2.f, this.size.y / 2.f);

        //Scale (Might be useful later if we want some cool effects on the player?) -true
        this.scale = 1.f;

        // Rotation
        this.rotation = 0.f;

        this.userPressed = new Vector3(0.f, 0.f, 0.f);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
            this.isUserPressing = true;
        else
            this.isUserPressing = false;

        this.movementHandler = new PlayerMovementHandler(this);

        this.particleGenerator = new ParticleGenerator(500, resources.getTexture("basicParticle"), this.camera);
        this.particleGenerator.setParticleColor(1.f, 1.f, 1.f, 0.5f);
        this.particleGenerator.setMaxAlphaForParticles(0.6f);
        this.particleGenerator.setCoreParticleColorRGB(1.f,1.f,1.f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.fade_out);
        this.particleSpreadMultiplier = this.movementHandler.getMaximumVelocity().y * 50.f;

        this.tempTex = resources.getTexture("playerSprite"); // Temp
    }

    public void update()
    {
        // THIS is the debug line for the movement, right now it uses the mouse
        // To test on android, remove this here and in playstate call
        // player.setTouch in the touchDown function AND remember to set
        // userPressing to true. Remember to set it to false in the touchUp function!
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
        this.userPressed.set((float)Gdx.input.getX(), (float)Gdx.input.getY(), 0.f);

        this.movementHandler.update(this.userPressed, this.isUserPressing);

        this.particleGenerator.generateParticle(
                this.getParticleSpawnX(),
                this.getParticleSpawnY(),
                this.particleWidth,
                this.particleHeight,
                1.5f ,
                (float)this.getParticleVelocityX(),
                (float)this.getParticleVelocityY());

        this.particleGenerator.update();

    }

    public void render()
    {
        this.particleGenerator.render();
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
    private float tempX = 0.f;
    private float tempY = 200.f;
    private void drawTempTexture()
    {
        this.batch.draw(this.tempTex, tempX, tempY, 64.f, 64.f);

        if((tempY - this.camera.position.y) + 100.f  < - StaticSettings.GAME_HEIGHT / 2.f)
            tempY = (StaticSettings.GAME_HEIGHT / 2.f) + this.camera.position.y + 100.f;

    }
    // --------------------------------------------------------------------------------

    private float getParticleSpawnX()
    {
        float centerSpot = (this.origin.x - (this.particleWidth / 2.f));
        float spotChangeDueToRotation =  this.position.x + (centerSpot) * MathUtils.cosDeg(this.rotation - 90.f);

        return (centerSpot + spotChangeDueToRotation);
    }

    private float getParticleSpawnY()
    {
        float centerSpot = (this.origin.y - (this.particleHeight/ 2.f));
        float spotChangeDueToRotation = this.position.y + (centerSpot) * MathUtils.sinDeg(this.rotation - 90.f);

        return (spotChangeDueToRotation +  centerSpot);
    }

    private double getParticleVelocityX()
    {
        float velDir = MathUtils.cosDeg(this.rotation -90.f);
        float rangeX = (((1.f - Math.abs(velDir)) * 100.f) / 4.f) * this.particleSpreadMultiplier;
        float spreadX = MathUtils.random(-rangeX, rangeX );
        spreadX /= 100.f;
        velDir += spreadX;

        return particleSpeed * velDir;
    }

    private double getParticleVelocityY()
    {
        float velDir = MathUtils.sinDeg(this.rotation -90.f);

        float rangeY = (((1.f - Math.abs(velDir)) * 100.f) / 4.f) * this.particleSpreadMultiplier;

        float spreadY = MathUtils.random(-rangeY, rangeY);

        spreadY /= 100.f;

        velDir += spreadY;

        return particleSpeed * velDir;
    }

    public void dispose()
    {
        this.batch.dispose();
        this.particleGenerator.dispose();
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


    public void setIsBeingPressed(boolean condition)
    {
        this.isUserPressing = condition;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

}
