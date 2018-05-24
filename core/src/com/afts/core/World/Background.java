package com.afts.core.World;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

// This class is just to make something happen to the screen
public class Background {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ParticleGenerator particleGenerator;
    private float outerXBounds, outerYBounds;

    private float particleFrequency = 60.f;
    private float minParticleSize = 2.f;
    private float maxParticleSize = 10.f;
    private float ticker = 0.f;

    public Background(OrthographicCamera camera, ResourceHandler resources)
    {
        this.camera = camera;
        // This batch might be useful later if we want some other stuff in the background.
        this.batch = new SpriteBatch();

        this.particleGenerator = new ParticleGenerator(500, resources.getTexture("basicParticle"), camera);
        this.particleGenerator.setParticleColor(1.f,1.f,1.f,0.25f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.fade_in_fade_out);
        this.particleGenerator.setMaxAlphaForParticles(0.4f);
        this.particleGenerator.setParticleCounterCameraFriction(0.95f, 1.f);

        // Basically spawn particles a little bit outside the screen
        this.outerXBounds = 100.f;
        this.outerYBounds = 100.f;

        this.onStartUp();
    }

    private void onStartUp()
    {
        for(int i = 0; i < this.particleFrequency; i++) {
            this.particleGenerator.generateParticle(
                    MathUtils.random(((-StaticSettings.GAME_WIDTH / 2.f) * this.camera.zoom + this.camera.position.x - this.outerXBounds),
                            ((StaticSettings.GAME_WIDTH / 2.f) * this.camera.zoom + this.camera.position.x + this.outerXBounds)),
                    MathUtils.random(((-StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y - this.outerYBounds),
                            ((StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y + this.outerYBounds)),
                    MathUtils.random(this.minParticleSize, this.maxParticleSize),
                    MathUtils.random(this.minParticleSize, this.maxParticleSize),
                    4.f,
                    0.f,
                    MathUtils.random(-30.f, -10.f));
        }
        this.particleGenerator.update();
    }

    public void update()
    {
        this.ticker += Gdx.graphics.getDeltaTime();
        if(this.ticker >= 1.f / this.particleFrequency)
        {
            this.particleGenerator.generateParticle(
                    MathUtils.random(((-StaticSettings.GAME_WIDTH / 2.f) * this.camera.zoom + this.camera.position.x - this.outerXBounds),
                            ((StaticSettings.GAME_WIDTH / 2.f) * this.camera.zoom + this.camera.position.x + this.outerXBounds) ),
                    MathUtils.random(((-StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y - this.outerYBounds),
                            ((StaticSettings.GAME_HEIGHT / 2.f) * this.camera.zoom + this.camera.position.y + this.outerYBounds)),
                    MathUtils.random(this.minParticleSize, this.maxParticleSize),
                    MathUtils.random(this.minParticleSize, this.maxParticleSize),
                    4.f,
                    0.f,
                    MathUtils.random(-30.f, -10.f));
            this.ticker = 0.f;
        }

        this.particleGenerator.update();
    }

    public void render()
    {
        this.particleGenerator.render();
    }

    public void dispose()
    {
        this.batch.dispose();
        this.particleGenerator.dispose();
    }
}
