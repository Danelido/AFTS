package com.afts.core.Particles.Generator;

import com.afts.core.Particles.Particle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ParticleGenerator {

    private int maxParticles;

    private TextureRegion particleTexture;
    private Color particleBatchColor;

    private List<Particle> particleList;
    private int nrOfAliveParticles;
    private SpriteBatch particleBatch;
    private OrthographicCamera camera;

    private SpawnSetting setting;
    private float maxAlphaAllowed;

    // this is to make the particles (if wanted) to move slower/faster than the camera.
    // Mainly used to simulate if a particle is far away it should move slower than the camera right?
    // NOTE that setting them to 1.f will not make them move at all ;)
    private float counterCameraFrictionX, counterCameraFrictionY;

    public ParticleGenerator(int maxParticles, Texture particleTexture, OrthographicCamera camera)
    {
        this.maxParticles = maxParticles;
        this.particleTexture = new TextureRegion(particleTexture);
        this.camera = camera;
        this.particleList = new ArrayList<Particle>(this.maxParticles);
        this.particleBatch = new SpriteBatch();
        this.nrOfAliveParticles = 0;
        this.particleBatchColor = new Color();
        this.particleBatchColor.set(1.f, 1.f, 1.f, 1.f);
        this.setting = SpawnSetting.normal;
        this.maxAlphaAllowed = 1.f;

        this.counterCameraFrictionX = 0.f;
        this.counterCameraFrictionY = 0.f;

        // Initialize them all
        for(int i = 0; i < this.maxParticles; i++)
        {
            this.particleList.add(new Particle(0.f,0.f, 0.f, 0.f, 0.f));
        }

    }

    public void update()
    {
        for(int i = 0; i < this.nrOfAliveParticles; i++) {

            Particle particle = this.particleList.get(i);

            // Update position and lifetime ( Particle does that internally)
            particle.update(Gdx.graphics.getDeltaTime());

            //--- Update particles based on setting here ---
            if(this.setting == SpawnSetting.fade_in_fade_out)
                this.fade_in_fade_out(particle);

            else if(this.setting == SpawnSetting.fade_out)
                this.fade_out(particle);
            //---                                        ---

            if(particle.isDead())
            {
                this.swapParticleIfNeeded(i);
                this.nrOfAliveParticles--;
            }

        }

        this.particleBatch.setProjectionMatrix(this.camera.combined);
    }

    public void render()
    {
        if(this.nrOfAliveParticles > 0)
        {
            this.particleBatch.enableBlending();
            this.particleBatch.begin();

            for(int i = 0; i < this.nrOfAliveParticles; i++)
            {
                Particle particle = this.particleList.get(i);
                this.particleBatch.setColor(particle.getColor());

                this.particleBatch.draw(
                        this.particleTexture,
                        particle.getX() + (this.camera.position.x * this.counterCameraFrictionX),
                        particle.getY() + (this.camera.position.y * this.counterCameraFrictionY),
                        particle.getWidth() / 2.f,
                        particle.getHeight() / 2.f,
                        particle.getWidth(),
                        particle.getHeight(),
                        1.f,
                        1.f,
                        particle.getRotation());

                this.particleBatch.setColor(Color.WHITE);
            }

            this.particleBatch.end();

        }
    }

    private void swapParticleIfNeeded(int index)
    {
        if(index != (this.nrOfAliveParticles - 1))
        Collections.swap(this.particleList, index, this.nrOfAliveParticles-1);
    }

    public void generateParticle(float x, float y, float width, float height, float lifetime, float velX, float velY, float rotation)
    {
        if(this.nrOfAliveParticles < this.maxParticles)
        {
            Particle particle = this.particleList.get(this.nrOfAliveParticles++);
            particle.setPosition(
                    x - (this.camera.position.x * this.counterCameraFrictionX),
                    y - (this.camera.position.y * this.counterCameraFrictionY));

            particle.setSize(width,height);
            particle.setLifeTime(lifetime);
            particle.setVelocity(velX, velY);
            particle.setRotation(rotation);
        }
    }

    public void generateParticle(float x, float y, float width, float height, float lifetime, float velX, float velY, float rotation, Color color)
    {
        if(this.nrOfAliveParticles < this.maxParticles)
        {
            Particle particle = this.particleList.get(this.nrOfAliveParticles++);
            particle.setPosition(
                    x - (this.camera.position.x * this.counterCameraFrictionX),
                    y - (this.camera.position.y * this.counterCameraFrictionY));

            particle.setSize(width,height);
            particle.setLifeTime(lifetime);
            particle.setVelocity(velX, velY);
            particle.setRotation(rotation);
            particle.setColorRGB(color);
        }
    }

    private void fade_in_fade_out(Particle particle)
    {
        float timeLived = particle.getInitialLifeTime() - particle.getLifetime();
        float halfTotalLifetime = particle.getInitialLifeTime() / 2.f;
        float alpha = -Math.abs( (timeLived - halfTotalLifetime) * (this.maxAlphaAllowed / halfTotalLifetime)) + this.maxAlphaAllowed;
        particle.setAlpha(alpha);
    }

    private void fade_out(Particle particle)
    {
        float timeLived = particle.getInitialLifeTime() - particle.getLifetime();
        float alpha = ((-this.maxAlphaAllowed / particle.getInitialLifeTime()) * timeLived) + this.maxAlphaAllowed;
        particle.setAlpha(alpha);
    }

    public void dispose()
    {
        this.particleBatch.dispose();
    }

    public float getNrOfAliveParticles()
    {
        return this.nrOfAliveParticles;
    }

    public void setParticleColor(float r, float g, float b, float a)
    {
        this.particleBatchColor.set(r,g,b,a);
    }

    public void setSpawnSetting(SpawnSetting setting)
    {
        this.setting = setting;
    }

    public void setMaxAlphaForParticles(float alpha)
    {
        this.maxAlphaAllowed = alpha;
    }

    public void setParticleCounterCameraFriction(float counterX, float counterY)
    {
        this.counterCameraFrictionX = counterX;
        this.counterCameraFrictionY = counterY;
    }

}
