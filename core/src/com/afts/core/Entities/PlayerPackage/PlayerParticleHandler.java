package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class PlayerParticleHandler {

    private ParticleGenerator particleGenerator;
    private PlayerParticleSetting particleSetting;
    private Player player;
    private float size, speed, spreadMultiplier, frequency, lifetime;
    private float ticker;

    public PlayerParticleHandler(Player player, PlayerParticleSetting particleSetting, OrthographicCamera camera, int maxParticles, Texture texture)
    {
        this.player = player;
        this.particleGenerator = new ParticleGenerator(maxParticles, texture, camera);
        this.particleSetting = particleSetting;
        this.size = 5.f;
        this.speed = 2.5f;
        this.spreadMultiplier = 100.f;
        this.frequency = 60.f;
        this.lifetime = 1.5f;
        this.ticker = 0.f;
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
                (float)this.getParticleVelocityX(),
                (float)this.getParticleVelocityY(),
                0.f);
    }

    private void update_trail()
    {
        this.particleGenerator.generateParticle(
                this.getParticleSpawnX(),
                this.getParticleSpawnY(),
                this.size,
                this.size,
                this.lifetime,
                0.f,
                0.f,
                this.player.getMovementHandler().getRotationBasedOnCurrentVelocity());

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

        return (spotChangeDueToRotation +  centerSpot);
    }



    private double getParticleVelocityX()
    {
        float velDir = MathUtils.cosDeg(this.player.getRotation() -90.f);

        float rangeX = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) * this.spreadMultiplier;

        float spreadX = MathUtils.random(-rangeX, rangeX);

        spreadX /= 100.f;

        velDir += spreadX;

        return speed * velDir ;
    }

    private double getParticleVelocityY()
    {
        float velDir = MathUtils.sinDeg(this.player.getRotation() -90.f);

        float rangeY = (((1.f - Math.abs(velDir)) * 100.f) / 2.f) * this.spreadMultiplier;

        float spreadY = MathUtils.random(-rangeY, rangeY);

        spreadY /= 100.f;

        velDir += spreadY;


        return speed * velDir;
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

    //Only works if setting is set to SPREAD
    public void setSpreadMultiplier(float spreadMulti)
    {
        this.spreadMultiplier = spreadMulti;
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
