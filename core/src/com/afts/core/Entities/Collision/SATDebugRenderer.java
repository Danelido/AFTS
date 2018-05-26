package com.afts.core.Entities.Collision;

import com.afts.core.Entities.Objects.EntityManager;
import com.afts.core.Entities.PlayerPackage.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SATDebugRenderer {

    private ShapeRenderer renderer;
    private Player player;
    private EntityManager entityManager;

    public SATDebugRenderer(Player player, EntityManager entityManager)
    {
        this.player = player;
        this.entityManager = entityManager;

        this.renderer = new ShapeRenderer();
        this.renderer.setColor(Color.YELLOW);
    }

    public void render(OrthographicCamera camera)
    {
        this.renderer.setProjectionMatrix(camera.combined);
        this.renderer.setColor(Color.YELLOW);
        this.renderer.begin(ShapeRenderer.ShapeType.Filled);

        for(int i = 0; i < this.player.getPoints().length; i++)
        {
            this.renderer.rect(this.player.getPoints()[i].x, this.player.getPoints()[i].y, 2.f,2.f);
        }

        for(int k = 0; k < this.entityManager.getEntities().size(); k++)
        {
            for (int i = 0; i < this.entityManager.getEntities().get(k).getPoints().length; i++)
            {
                this.renderer.rect(this.entityManager.getEntities().get(k).getPoints()[i].x, this.entityManager.getEntities().get(k).getPoints()[i].y, 2.f, 2.f);
            }
        }
        this.renderer.end();
    }

    public void beginRenderer(OrthographicCamera camera){
        this.renderer.setProjectionMatrix(camera.combined);
        this.renderer.setColor(Color.GREEN);
        this.renderer.begin(ShapeRenderer.ShapeType.Filled);

    }
    public void addPointsToRender(Vector2[] points, Color color)
    {
        for(int i = 0; i < points.length; i++)
        {
            this.renderer.rect(points[i].x, points[i].y, 2, 2);
        }
    }

    public void addPointsToRender(Vector2 point , Color color)
    {
        this.renderer.rect(point.x, point.y, 2, 2);
    }

    public void endRenderer()
    {
        this.renderer.end();
    }
}
