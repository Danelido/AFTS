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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Steven on 15/05/2018
 */

public class Player {

    private Vector3 position; // Vector 3 to make camera lerp easier, just ignore z
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
    private float particleSpeed = 2.5f;
    private float particleWidth = 10.f;
    private float particleHeight = 10.f;
    private float particleSpreadMultiplier = 100.f;
    private float spawnFrequency = 60.f;
    private float ticker = 0.f;

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

        // Set the origin of the player
        this.origin = new Vector2(this.size.x / 2.f, this.size.y / 2.f);

        //Scale (Might be useful later if we want some cool effects on the player?)
        this.scale = 1.f;

        // Rotation
        this.rotation = 0.f;

        // Movement /w handler
        this.userPressed = new Vector3(0.f, 0.f, 0.f);
        this.isUserPressing = false;
        this.movementHandler = new PlayerMovementHandler(this);

        // Particle initialization
        this.particleGenerator = new ParticleGenerator(500, resources.getTexture("trailParticle"), this.camera);
        this.particleGenerator.setParticleColor(1.f, 1.f, 1.f, 0.5f);
        this.particleGenerator.setMaxAlphaForParticles(0.6f);
        this.particleGenerator.setCoreParticleColorRGB(1.f,1.f,1.f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.fade_out);

        // Temp
        this.tempTex = resources.getTexture("playerSprite");

    }

    public void update()
    {

        this.movementHandler.update(this.userPressed, this.isUserPressing);

        this.ticker += Gdx.graphics.getDeltaTime();

        if(this.ticker >= 1.f / this.spawnFrequency)
        {
            this.particleGenerator.generateParticle(
                    this.getParticleSpawnX(),
                    this.getParticleSpawnY(),
                    this.particleWidth,
                    this.particleHeight,
                    1.5f ,
                    (float)this.getParticleVelocityX(),
                    (float)this.getParticleVelocityY());
            this.ticker = 0.f;

        }

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
    private void drawTempTexture()
    {
        this.batch.draw(this.tempTex, tempX, tempY, 64.f, 64.f);

        if((tempY - this.camera.position.y) + 100.f  < (- StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom)
            tempY = (StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y + 100.f;

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

        float rangeX = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) * this.particleSpreadMultiplier;

        float spreadX = MathUtils.random(-rangeX, rangeX);

        spreadX /= 100.f;

        velDir += spreadX;

        return particleSpeed * velDir ;
    }

    private double getParticleVelocityY()
    {
        float velDir = MathUtils.sinDeg(this.rotation -90.f);

        float rangeY = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) * this.particleSpreadMultiplier;

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
}
