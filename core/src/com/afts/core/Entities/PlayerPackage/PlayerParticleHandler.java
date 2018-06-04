package com.afts.core.Entities.PlayerPackage;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PlayerParticleHandler {

    private ParticleGenerator particleGenerator;
    private Player player;
    private float size, frequency, lifetime;
    private float ticker;
    private Color color;

    public PlayerParticleHandler(Player player, OrthographicCamera camera, Texture texture)
    {
        this.player = player;
        this.particleGenerator = new ParticleGenerator(200, texture, camera);
        this.size = player.getSize().x;
        this.frequency = 60.f;
        this.lifetime = .5f;
        this.ticker = 0.f;
        this.color = new Color(0.f, 1.f, 1.f, 1.f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.shrink_fade_out);
        this.particleGenerator.setParticleCounterCameraFriction(.75f, .75f);

    }

    public void update()
    {
        this.ticker += Gdx.graphics.getDeltaTime();

        if(this.ticker >= 1.f/this.frequency)
        {
            this.spawnParticle();
            this.ticker -= (1.f/this.frequency);
        }
        this.particleGenerator.update();

    }

    public void render()
    {
        this.particleGenerator.render();
    }

    private void spawnParticle()
    {
        this.particleGenerator.generateParticle(
                (this.player.getPosition().x + this.player.getOrigin().x - this.size / 2.f),
                (this.player.getPosition().y + this.player.getOrigin().y - this.size / 2.f),
                this.size,
                this.size,
                this.lifetime,
                0,
                0,
                0.f,
                this.color);
    }

    public void dispose()
    {
        this.particleGenerator.dispose();
    }

    public void setSize(float size)
    {
        this.size = size;
    }

    public void setLifeTime(float lifetime)
    {
        this.lifetime = lifetime;
    }

    public void setColorRGB(float r, float g, float b)
    {
        this.color.set(r,g,b,this.color.a);
    }

    public ParticleGenerator getParticleGenerator()
    {
        return this.particleGenerator;
    }

}
