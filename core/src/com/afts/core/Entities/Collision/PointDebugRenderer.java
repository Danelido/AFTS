package com.afts.core.Entities.Collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

// Inefficient but its for debugging
public class PointDebugRenderer {

    private static OrthographicCamera Camera;
    private static ShapeRenderer Renderer;

    public PointDebugRenderer(OrthographicCamera camera)
    {
        Camera = camera;
        Renderer = new ShapeRenderer();
    }

    public static void addPointsToBeRendered(Vector2[] points)
    {
        Renderer.setProjectionMatrix(Camera.combined);

        Renderer.begin(ShapeRenderer.ShapeType.Line);
        Renderer.setColor(Color.MAGENTA);
        for(int i = 0; i < points.length; i++)
        {
            for(int j = 0; j < points.length; j++)
            {
                if(j+1 != points.length)
                    Renderer.line(points[i], points[j]);
                else
                    Renderer.line(points[i], points[0]);
            }

        }

        Renderer.end();

    }

    public static void addPointsToBeRendered(Vector2 point, Vector2 dest)
    {
        Renderer.setProjectionMatrix(Camera.combined);

        Renderer.begin(ShapeRenderer.ShapeType.Line);
        Renderer.setColor(Color.MAGENTA);
        Renderer.line(point, dest);


        Renderer.end();

    }

    public static void addPointsToBeRendered(Vector2 points)
    {
        Renderer.setProjectionMatrix(Camera.combined);

        Renderer.begin(ShapeRenderer.ShapeType.Point);
        Renderer.setColor(Color.MAGENTA);
        Renderer.point(points.x, points.y, 0.f);
        Renderer.end();

    }
}
