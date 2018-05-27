package com.afts.core.Entities.Objects;

import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.PlayerPackage.Player;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entities;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Vector2 separationDirection;

    public EntityManager(OrthographicCamera camera)
    {
        this.camera = camera;
        this.entities = new ArrayList<Entity>();
        this.batch = new SpriteBatch();
        this.separationDirection = new Vector2();

    }

    public void updateAndRender(Player player, SATCollision collision)
    {
        this.batch.setProjectionMatrix(this.camera.combined);

        this.batch.begin();

        for(Entity e : this.entities)
        {
            e.update();

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

                    player.addToPosition(collision.getMinimumPenetrationAxis().scl(collision.getOverlap()));

                }


            }

            e.render(this.batch);
        }

        this.batch.end();
    }


    public void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }



    public void dispose()
    {
        this.batch.dispose();
    }

    public ArrayList<Entity> getEntities()
    {
        return this.entities;
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
