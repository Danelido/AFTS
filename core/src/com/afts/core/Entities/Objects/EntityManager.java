package com.afts.core.Entities.Objects;

import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.PlayerPackage.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entities;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public EntityManager(OrthographicCamera camera)
    {
        this.camera = camera;
        this.entities = new ArrayList<Entity>();
        this.batch = new SpriteBatch();

    }

    public void update(Player player, SATCollision collision)
    {
        for(Entity e : this.entities)
        {
            e.update();
            e.updatePoints();
            if(collision.collision(player, e))
            {
                player.setColor(Color.RED);
            }else
            {
                player.setColor(Color.WHITE);
            }
        }
    }

    public void render()
    {
        this.batch.setProjectionMatrix(this.camera.combined);

        this.batch.begin();

        for(Entity e : this.entities)
        {
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
}
