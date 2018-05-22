package com.afts.core.World;

import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

// This class is just to make something happen to the screen
public class Background {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ParticleGenerator particleGenerator;
    private float outerXBounds, outerYBounds;

    public Background(OrthographicCamera camera, ResourceHandler resources)
    {
        this.camera = camera;
        // This batch might be useful later if we want some other stuff in the background.
        this.batch = new SpriteBatch();

        this.particleGenerator = new ParticleGenerator(500, resources.getTexture("basicParticle"), camera);
        this.particleGenerator.setParticleColor(1.f,1.f,1.f,0.25f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.fade_in_fade_out);
        this.particleGenerator.setMaxAlphaForParticles(0.4f);
        this.particleGenerator.setParticleCounterCameraFriction(0.90f, 1.f);

        // Basically spawn particles a little bit outside the screen
        this.outerXBounds = 100.f;
        this.outerYBounds = 100.f;
    }

    public void update()
    {
        this.particleGenerator.generateParticle(
                MathUtils.random((-StaticSettings.GAME_WIDTH / 2.f) + this.camera.position.x - this.outerXBounds, (StaticSettings.GAME_WIDTH / 2.f) + this.camera.position.x + this.outerXBounds),
                MathUtils.random((-StaticSettings.GAME_HEIGHT / 2.f) + this.camera.position.y - this.outerYBounds, (StaticSettings.GAME_HEIGHT / 2.f) + this.camera.position.y + this.outerYBounds),
                MathUtils.random(2.f, 6.f),
                MathUtils.random(2.f, 6.f),
                4.f,
                0.f,
                MathUtils.random(-30.f, -10.f));

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
