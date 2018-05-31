package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class PlayerParticleHandler {

    private ParticleGenerator particleGenerator;
    private PlayerParticleSetting particleSetting;
    private Player player;
    private float size, speed, spread, frequency, lifetime, maxSpeed, minSpeed;
    private float ticker;
    private Color color;

    public PlayerParticleHandler(Player player, PlayerParticleSetting particleSetting, OrthographicCamera camera, int maxParticles, Texture texture)
    {
        this.player = player;
        this.particleGenerator = new ParticleGenerator(maxParticles, texture, camera);
        this.particleSetting = particleSetting;
        this.size = 5.f;
        this.maxSpeed = 25.f;
        this.minSpeed = 12.f;
        this.speed = 0.f;
        this.spread = 0.6f;
        this.frequency = 60.f;
        this.lifetime = 3.f;
        this.ticker = 0.f;
        this.color = new Color(1.f, .5f, 0.5f, 1.f);

        this.particleGenerator.setParticleCounterCameraFriction(0.6f,0.6f);

    }

    public void update()
    {
        this.ticker += Gdx.graphics.getDeltaTime();
        if(this.ticker >= 1.f/this.frequency)
        {
            if(this.particleSetting == PlayerParticleSetting.SPREAD)
            {
                this.update_spread();
            }

            else if(this.particleSetting == PlayerParticleSetting.TRAIL)
            {
                this.update_trail();
            }

            this.ticker = 0.f;

        }
        this.particleGenerator.update();

    }

    public void render()
    {
        this.particleGenerator.render();
    }

    private void update_spread()
    {
            this.particleGenerator.generateParticle(
                    this.getParticleSpawnX(),
                    this.getParticleSpawnY(),
                    this.size,
                    this.size,
                    this.lifetime,
                    (float)this.getParticleVelocityX() * this.speed,
                    (float)this.getParticleVelocityY() * this.speed,
                    0.f,
                    this.color);
    }

    private void update_trail()
    {
        if(this.player.getController().getMovementHandler().isMoving()) {
            this.particleGenerator.generateParticle(
                    this.getParticleSpawnX(),
                    this.getParticleSpawnY(),
                    this.size,
                    this.size,
                    this.lifetime,
                    0.f,
                    0.f,
                    this.player.getRotation(),
                    this.color);
        }
    }

    private float getParticleSpawnX()
    {

        float centerSpot = (this.player.getOrigin().x - (this.size / 2.f));
        float spotChangeDueToRotation =  this.player.getPosition().x + (centerSpot) * MathUtils.cosDeg(this.player.getRotation() - 90.f);

        return (centerSpot + spotChangeDueToRotation);
    }

    private float getParticleSpawnY()
    {
        float centerSpot = (this.player.getOrigin().y - (this.size / 2.f));
        float spotChangeDueToRotation = this.player.getPosition().y + (centerSpot) * MathUtils.sinDeg(this.player.getRotation() - 90.f);

        return (spotChangeDueToRotation + centerSpot);
    }



    private double getParticleVelocityX()
    {
//        float velDir = MathUtils.cosDeg(this.player.getRotation() - 90.f);
//        System.out.println("VelDir: " + velDir);
//        float maxSpread = Math.abs(this.player.getController().getMovementHandler().getCurrentVelocity().y / this.player.getController().getMovementHandler().getMaxSpeed()) * this.spreadMultiplier;
//        System.out.println("maxSpread: " + maxSpread);
//        float rangeX = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) * maxSpread;
//        System.out.println("RangeX: " + rangeX);
//        float spreadX = MathUtils.random(-rangeX, rangeX);
//        System.out.println("SpreadX: " + spreadX);
//        spreadX /= 100.f;
//        System.out.println("SpreadX normalized: " + spreadX + "\n");
//        velDir += spreadX;
//
//        return speed * velDir ;

        float direction = MathUtils.cosDeg(this.player.getRotation() - 90.f);
        direction += MathUtils.random(-this.spread, this.spread);

        this.speed =
                Math.max(this.minSpeed,(Math.abs(this.player.getController().getMovementHandler().getCurrentVelocity().x) / this.player.getController().getMovementHandler().getMaxSpeed()) * this.maxSpeed);
        return direction * speed;
    }

    private double getParticleVelocityY()
    {
//        float velDir = MathUtils.sinDeg(this.player.getRotation() - 90.f);
//
//        float maxSpread = Math.abs(this.player.getController().getMovementHandler().getCurrentVelocity().x / this.player.getController().getMovementHandler().getMaxSpeed()) * this.spreadMultiplier;
//
//        float rangeY = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) *maxSpread;
//
//        float spreadY = MathUtils.random(-rangeY, rangeY);
//
//        spreadY /= 100.f;
//
//        velDir += spreadY;
//
//
//        return speed * velDir;

        float direction = MathUtils.sinDeg(this.player.getRotation() - 90.f);
        direction += MathUtils.random(-this.spread, this.spread);
        this.speed =
                Math.max(this.minSpeed,(Math.abs(this.player.getController().getMovementHandler().getCurrentVelocity().y) / this.player.getController().getMovementHandler().getMaxSpeed()) * this.maxSpeed);
        return direction * speed;
    }

    public void dispose()
    {
        this.particleGenerator.dispose();
    }

    public void setParticleSetting(PlayerParticleSetting particleSetting)
    {
        this.particleSetting = particleSetting;
    }

    /* 100 as default */
    public void setSpawnFrequency(float freq)
    {
        this.frequency = freq;
    }

    public void setSize(float size)
    {
        this.size = size;
    }

    public void setLifeTime(float lifetime)
    {
        this.lifetime = lifetime;
    }

    public ParticleGenerator getParticleGenerator()
    {
        return this.particleGenerator;
    }

    public PlayerParticleSetting getParticleSetting() {
        return this.particleSetting;
    }
}
