package com.afts.core.Entities.Objects;

import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.PlayerPackage.Player;
import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entities;
    private SpriteBatch batch;
    private ParticleGenerator particleGenerator;
    private OrthographicCamera camera;

    private Vector2 separationDirection;

    public EntityManager(OrthographicCamera camera, ResourceHandler resourceHandler)
    {
        this.camera = camera;
        this.entities = new ArrayList<Entity>();
        this.batch = new SpriteBatch();
        this.separationDirection = new Vector2();
        this.particleGenerator = new ParticleGenerator(500, resourceHandler.getTexture("tile"), this.camera);
        this.particleGenerator.setMaxAlphaForParticles(0.8f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.fade_out);
    }

    public void updateAndRender(Player player, SATCollision collision)
    {
        this.batch.setProjectionMatrix(this.camera.combined);

        this.batch.begin();

        for(int i = 0; i < this.entities.size(); i++)
        {
            Entity e = this.entities.get(i);
            e.update(this.camera);

            if(this.checkIfEntityIsInsideScreenBounds(e))
            {
                e.updatePoints();

                if (collision.collide(player, e)) {

                    // This might be done somewhere else in the future (re-positioning the player that is)
                    this.separationDirection.set(player.getPosition().x - e.getPosition().x, player.getPosition().y - e.getPosition().y);

                    if (!this.hasSameSign(this.separationDirection.x, collision.getMinimumPenetrationAxis().x))
                    {
                        collision.getMinimumPenetrationAxis().x *= -1.f;
                    }

                    if (!this.hasSameSign(this.separationDirection.y, collision.getMinimumPenetrationAxis().y))
                    {
                        collision.getMinimumPenetrationAxis().y *= -1.f;
                    }


                    this.destroyEntity(i);
                    //player.addToPosition(collision.getMinimumPenetrationAxis().scl(collision.getOverlap()));
                    //e.translatePosition(new Vector2(collision.getMinimumPenetrationAxis()).scl(-1.f*collision.getOverlap()));
                    //player.getMovementHandler().handleCollision(0.5f);

                    //e.setVelocity(collision.getMinimumPenetrationAxis().scl(-1f * e.getCollideForce()));


                }


            }

            e.render(this.batch);
        }

        this.batch.end();

        this.particleGenerator.update();
        this.particleGenerator.render();
    }


    public void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }

    public void dispose()
    {
        this.batch.dispose();
        this.particleGenerator.dispose();
    }

    public ArrayList<Entity> getEntities()
    {
        return this.entities;
    }

    private void destroyEntity(int index)
    {
        Entity e = this.entities.get(index);
        for(int i = 0; i < 50; i++)
        {
            this.particleGenerator.generateParticle(
                    e.position.x + e.origin.x,
                    e.position.y + e.origin.y,
                    e.size.x / 4.f,
                    e.size.y / 4.f,
                    MathUtils.random(0.5f,2.f),
                    MathUtils.random(-300.f,300.f),
                    MathUtils.random(-300.f,300.f),
                    MathUtils.random(-90.f,90.f),
                    e.getColor()
            );
        }
        this.entities.remove(index);

    }

    private boolean checkIfEntityIsInsideScreenBounds(Entity e)
    {
        if(e.getPosition().x - this.camera.position.x > -(StaticSettings.GAME_WIDTH / 2.f) - 100.f && e.getPosition().x - this.camera.position.x < (StaticSettings.GAME_WIDTH /2.f) + 100.f &&
           e.getPosition().y - this.camera.position.y > -(StaticSettings.GAME_HEIGHT / 2.f) - 100.f && e.getPosition().y - this.camera.position.y < (StaticSettings.GAME_HEIGHT / 2.f) + 100.f )
        {
           return true;
        }
            return false;
    }

    private boolean hasSameSign(float a, float b)
    {
        if(a < 0 && b < 0) return true;
        if(a > 0 && b > 0) return true;
        if(a == b) return true;

        return false;
    }
}
