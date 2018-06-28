package com.afts.core.Ui;

import com.afts.core.Utility.ResourceHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steve on 23/05/2018.
 *
 */

public class UIButton {

    private SpriteBatch batch;


    private Texture buttonTexture;

    //Transformations
    private Vector2 buttonPosition;
    private Vector2 buttonSize;
    private Vector2 buttonOrigin;


    private int buttonID;

    public UIButton(Texture buttonTexture, Vector2 buttonPosition, Vector2 buttonSize, int buttonID) {
        this.batch = new SpriteBatch();


        this.buttonOrigin = buttonPosition;

        this.buttonSize = buttonSize;

        this.buttonPosition = this.buttonOrigin;
        this.buttonPosition.x = this.buttonPosition.x - (this.buttonSize.x / 2);
        this.buttonPosition.y = this.buttonPosition.y - (this.buttonSize.y / 2);

        this.buttonOrigin = buttonPosition;

        this.buttonTexture = buttonTexture;

        this.buttonID = buttonID;
    }


    public void render(SpriteBatch batch)
    {
        batch.draw(this.buttonTexture, this.buttonOrigin.x, this.buttonOrigin.y, this.buttonSize.x, this.buttonSize.y);
    }

    //Checks if the mousePosition is within the bounds of the box
    public boolean clicked(Vector2 mousePosition)
    {
        boolean inBounds = false;
        float maxX = buttonOrigin.x + buttonSize.x;

        if(mousePosition.x > buttonOrigin.x && mousePosition.x < maxX)
        {
            float maxY = buttonOrigin.y + buttonSize.y;
            if(mousePosition.y > buttonOrigin.y && mousePosition.y < maxY)
            {
                inBounds = true;
            }
        }
        return inBounds;
    }

    public Vector2 getPositions()
    {
        return this.buttonOrigin;
    }
    public int getButtonID()
    {
        return this.buttonID;
    }
    private void drawTexture()
    {
        this.batch.draw(this.buttonTexture, this.buttonOrigin.x, this.buttonOrigin.y, this.buttonSize.x, this.buttonSize.y);
    }
    public void setTexture(Texture buttonTexture)
    {
        this.buttonTexture = buttonTexture;
    }


    public void dispose()
    {
        this.batch.dispose();
    }

}
