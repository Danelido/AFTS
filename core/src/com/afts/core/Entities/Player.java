package com.afts.core.Entities;

import com.afts.core.Math.CoordinateConverter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Steven on 15/05/2018
 */

public class Player {

    private Color shapeColour;
    private ShapeRenderer shapeRenderer;
    private CoordinateConverter testConverter;
    private float width;
    private float height;
    private int x;
    private int y;

    //Not used atm
    //private Texture playerTexture;

    public Player(int x, int y, int width, int height)
    {
        this.testConverter = new CoordinateConverter();
        this.shapeColour = new Color();
        this.shapeRenderer = new ShapeRenderer();
        this.width = width;
        this.height = -height;
        this.x = x;
        this.y = testConverter.calcYCoord(y);
    }

    public void draw()
    {
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.setShapeColour(shapeColour);
        this.shapeRenderer.rect(x, y, width, height);
        this.shapeRenderer.end();
    }

    public void setShapeColour(int r, int g, int b, int a)
    {
        this.shapeColour = new Color(r, g, b, a);
    }

    public void setShapeColour(Color color)
    {
        this.shapeColour = color;
    }
}
