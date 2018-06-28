package com.afts.core.Menu;

import com.afts.core.Math.CoordinateConverter;
import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.World.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Steven on 26/06/2018
 */

public class MenuParticles {

    private Background background;
    //Particle Generation
    private ParticleGenerator particleGenerator;
    private float particleFrequency = 60.0f;
    private float particleSize = 50.0f;
    private float deltaTimer = 0.0f;
    private float mouseX= 0.0f;
    private float mouseY= 0.0f;
    private int maxParticles = 10;
    private float lifetime = 1.0f;
    private boolean generateParticles = false;

    public MenuParticles(ResourceHandler resourceHandler, OrthographicCamera camera)
    {
        this.background = new Background(camera, resourceHandler);
        //A particle effect for when the screen is pressed
        //Used to give the user a confident response from the system.
        this.particleGenerator = new ParticleGenerator(this.maxParticles, resourceHandler.getTexture("fadedRoundParticle"), camera);
        this.particleGenerator.setSpawnSetting(SpawnSetting.expand_and_shrink_fade_in_fade_out);
        this.particleGenerator.setMaxAlphaForParticles(.25f);
    }

    public void update()
    {
        if(this.generateParticles) {
            while(this.particleGenerator.getNrOfAliveParticles() < maxParticles) {

                this.deltaTimer += Gdx.graphics.getDeltaTime();

                if(this.deltaTimer >= 1.0f / this.particleFrequency) //if the deltaTime is larger than or equal to 1 / the frequency of the particle spawnage
                {

                    this.particleGenerator.generateParticle(this.mouseX,
                            CoordinateConverter.calcYCoord(this.mouseY),
                            this.particleSize,
                            this.particleSize,
                            this.lifetime, 0, 0, 0.f,  new Color(1.f,255.f/255.f,255.f/255.f,1.f));
                    this.deltaTimer = 0;

                }
                if(this.particleGenerator.getNrOfAliveParticles() == this.maxParticles)
                {
                    this.generateParticles = false;
                }
            }
        }

        this.background.update();
        this.particleGenerator.update();

    }

    public void generateParticles(int screenX, int screenY)
    {
        float posX = screenX - (this.particleSize / 2);
        float posY = screenY + (this.particleSize / 2);


        this.mouseX = posX;
        this.mouseY = posY;

        this.generateParticles = true;
    }
    public void renderBackground()
    {
        this.background.render();
    }
    public void renderForeground()
    {
        this.particleGenerator.renderMenuParticles();
    }

    public void dispose()
    {
        this.background.dispose();
        this.particleGenerator.dispose();
    }
}
