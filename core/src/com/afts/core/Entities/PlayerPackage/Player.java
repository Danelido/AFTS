package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Entities.Collision.AABBRectangle;
import com.afts.core.Entities.Objects.CollisionPointSetup;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.PointCalculator;
import com.afts.core.Utility.ResourceHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private OrthographicCamera camera;

    // Particles
    private PlayerParticleHandler particleHandler;

    // Collision
    private AABBRectangle aabbRectangle;
    private Vector2[] points;

    // Controller for player (The joystick and throttle)
    private PlayerController controller;

    public Player(ResourceHandler resources, OrthographicCamera camera, Vector2 position, Vector2 size)
    {
        this.batch = new SpriteBatch();
        this.playerColor = new Color(Color.WHITE);
        this.textureRegion = new TextureRegion(resources.getTexture("tile"));

        this.camera = camera;
        this.size = size;
        this.position = new Vector3(position.cpy(), 0.f);

        // Center the player on the coordinates that was sent in
        this.position.x -= (this.size.x / 2.f);
        this.position.y -= (this.size.y / 2.f);

        this.origin = new Vector2(this.size.cpy().scl(0.5f));

        //Scale (Might be useful later if we want some cool effects on the player?)
        this.scale = 1.f;

        this.rotation = 0.f;

        this.particleHandler = new PlayerParticleHandler(this,PlayerParticleSetting.SPREAD, this.camera, 500,resources.getTexture("trailParticle"));
        this.particleHandler.setSize(12.f);
        this.particleHandler.setLifeTime(.4f);
        this.particleHandler.getParticleGenerator().setParticleColor(1.f, .5f, 0.5f, 1.f);
        this.particleHandler.getParticleGenerator().setSpawnSetting(SpawnSetting.fade_out);

        this.aabbRectangle = new AABBRectangle();
        this.setUpPoints();

        this.controller = new PlayerController(this, resources);
    }

    public void update()
    {
        this.controller.update();
        this.particleHandler.update();
        PointCalculator.getPoints(this.points, new Vector2(this.position.x, this.position.y), this.size, this.origin , CollisionPointSetup.RECTANGLE, this.rotation- 90.f );
        this.aabbRectangle.update(new Vector2(this.position.x, this.position.y), new Vector2(150.f,150.f), this.origin);
    }

    public void render()
    {

        this.particleHandler.render();

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        this.batch.setColor(this.playerColor);

        this.batch.draw(this.textureRegion,
                this.position.x, this.position.y,
                this.origin.x, this.origin.y,
                this.size.x, this.size.y,
                this.scale, this.scale,
                this.rotation);

        this.batch.setColor(Color.WHITE);

        this.controller.render(this.batch);

        this.batch.end();
    }

    public void dispose()
    {
        this.batch.dispose();
        this.particleHandler.dispose();
    }

    private void setUpPoints()
    {
        this.points = new Vector2[4];
        for(int i = 0; i < this.points.length; i++)
        {
            this.points[i] = new Vector2();
        }
        PointCalculator.getPoints(this.points, new Vector2(this.position.x, this.position.y), this.size, this.origin , CollisionPointSetup.RECTANGLE, 0.f);
    }

    public void translatePosition(Vector2 velocity)
    {
        this.position.add(velocity.x * Gdx.graphics.getDeltaTime(), velocity.y * Gdx.graphics.getDeltaTime(), 0.f);
    }

    public void registerTouchDown(float x, float y, int pointer)
    {
        this.controller.registerTouchDown(new Vector3(x,y,pointer));
    }

    public void registerTouchMoved(float x, float y, int pointer)
    {
        this.controller.registerTouchMoved(new Vector3(x,y,pointer));
    }

    public void registerTouchUp(float x, float y, int pointer)
    {
        this.controller.registerTouchUp(new Vector3(x,y,pointer));
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

    public Vector2 getSize()
    {
        return this.size;
    }

    public Vector2[] getPoints()
    {
        return this.points;
    }

    public void setColor(Color color)
    {
        this.playerColor = color;
    }

    public void setPosition(Vector3 position)
    {
        this.position = position;
    }

    public void setPosition(Vector2 position)
    {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    public void addToPosition(Vector2 amount)
    {
        this.position.x += amount.x;
        this.position.y += amount.y;
    }

    public void addToRotation(float amount)
    {
        this.rotation += amount;
    }
    public PlayerController getController() {
        return this.controller;
    }

    public AABBRectangle getAabbRectangle() {
        return this.aabbRectangle;
    }
}
