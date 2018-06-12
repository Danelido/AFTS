package com.afts.core.Entities.Objects;

import com.afts.core.Entities.Collision.AABBCollision;
import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.PlayerPackage.Player;
import com.afts.core.Graphics.TextRenderer;
import com.afts.core.Particles.Generator.ParticleGenerator;
import com.afts.core.Particles.Generator.SpawnSetting;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.afts.core.Utility.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    private Player player;
    private Vector2 separationDirection;
    private TextRenderer textRenderer;

    public EntityManager(OrthographicCamera camera, Player player, ResourceHandler resourceHandler)
    {
        this.camera = camera;
        this.player = player;
        this.entities = new ArrayList<Entity>();
        this.batch = new SpriteBatch();
        this.separationDirection = new Vector2();
        this.particleGenerator = new ParticleGenerator(5000, resourceHandler.getTexture("squareParticle"), this.camera);
        this.particleGenerator.setMaxAlphaForParticles(0.8f);
        this.particleGenerator.setSpawnSetting(SpawnSetting.shrink_fade_out);

        this.textRenderer = new TextRenderer();
    }

    public void updateAndRender(SATCollision satCollision)
    {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        int entitiesGettingAABB = 0;
        int entitiesGettingSAT = 0;

        for(int i = 0; i < this.entities.size(); i++)
        {
            Entity e = this.entities.get(i);
            e.internal_update();
            e.update(this.camera);

            if(this.checkIfEntityIsInsideScreenBounds(e))
            {
                entitiesGettingAABB++;
                if(AABBCollision.isColliding(e.getAabbRectangle(), this.player.getAabbRectangle()))
                {

                    entitiesGettingSAT++;
                    this.checkCollisionWithSAT(i, satCollision);
                }

                e.render(this.batch);
            }




        }
        this.textRenderer.addTextToRenderQue(
                "DEBUG INFO (inside EntityManager class)\n"
                        + "\nNumber of entities: " + this.entities.size()
                        + "\nNumber of entity particles: " + this.particleGenerator.getNrOfAliveParticles()
                        + "\nProcessing AABB-collision on " + entitiesGettingAABB + " entities"
                        + "\nProcessing SAT-collision on " + entitiesGettingSAT + " entities"
                        + "\nPlayer Velocity: " + this.player.getController().getMovementHandler().getCurrentVelocity().toString()
                        + "\nNumber of player particles: " + this.player.getNrOfParticles()
                        + "\n\nFPS: " + Gdx.graphics.getFramesPerSecond()
                        + "\nDelta: " + Gdx.graphics.getDeltaTime(),
                new Vector2(10.f,StaticSettings.GAME_HEIGHT - 10.f),
                Color.ORANGE,
                1.f
        );

        this.textRenderer.render(this.batch,this.camera);

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
        for(int i = 0; i < 10; i++)
        {
            this.particleGenerator.generateParticle(
                    e.position.x + e.origin.x,
                    e.position.y + e.origin.y,
                    e.size.x,
                    e.size.y,
                    MathUtils.random(0.5f,2.f),
                    MathUtils.random(-300.f,300.f),
                    MathUtils.random(-300.f,300.f),
                    MathUtils.random(-90.f,90.f),
                    e.getColor()
            );
        }
        this.entities.remove(index);

    }

    private void checkCollisionWithSAT(int index, SATCollision satCollision)
    {
        Entity e = this.entities.get(index);
        e.updatePoints();

        if (satCollision.collide(player, e)) {

            // This might be done somewhere else in the future (re-positioning the player that is)
           this.separationDirection.set((player.getPosition().x + player.getOrigin().x) - (e.getPosition().x + e.getOrigin().x),
                   (player.getPosition().y + player.getOrigin().y) - (e.getPosition().y + e.getOrigin().y));


            if(!Utils.hasSameSign(separationDirection.x, satCollision.getMinimumPenetrationAxis().x))
            {

                satCollision.getMinimumPenetrationAxis().x *= -1.f;
            }

            if(!Utils.hasSameSign(separationDirection.y, satCollision.getMinimumPenetrationAxis().y))
            {

                satCollision.getMinimumPenetrationAxis().y *= -1.f;
            }

            this.player.setColor(e.getColor());

            if(e.getOnCollisionSetting() == OnCollisionSetting.DESTROY)
            {
                this.destroyEntity(index);

            }else if(e.getOnCollisionSetting() == OnCollisionSetting.MOVABLE)
            {
                e.bounce(this.separationDirection.cpy(), this.player.getController().getMovementHandler().getCurrentVelocity().cpy());
                this.player.addToPosition(satCollision.getMinimumPenetrationAxis().cpy().scl(satCollision.getOverlap()));
                this.player.getController().getMovementHandler().bounce(this.separationDirection.cpy());

            }else if(e.getOnCollisionSetting() == OnCollisionSetting.NON_MOVABLE)
            {
                this.player.addToPosition(satCollision.getMinimumPenetrationAxis().cpy().scl(satCollision.getOverlap()));
                this.player.getController().getMovementHandler().bounce(this.separationDirection.cpy());

            }else if(e.getOnCollisionSetting() == OnCollisionSetting.HURT_PLAYER)
            {
                this.player.getController().getMovementHandler().zeroVelocity();
                this.player.moveToStartPosition();
                Gdx.app.log("PLAYER-EVENT", "God damn it hurts!");
            }

        }
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


}
