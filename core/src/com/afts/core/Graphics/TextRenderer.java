package com.afts.core.Graphics;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TextRenderer {

    private class TextContainer
    {
        String text;
        Vector2 position;
        Color color;
        float scale;

        public TextContainer(String text, Vector2 position, Color color, float scale)
        {
            this.text = text;
            this.position = position;
            this.color = color;
            this.scale = scale;
        }
    }

    private OrthographicCamera camera;
    private ArrayList<TextContainer> texts;
    private BitmapFont bitmapFont;

    public TextRenderer()
    {
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);
        this.camera.position.add(StaticSettings.GAME_WIDTH/2.f, StaticSettings.GAME_HEIGHT/2.f, 0f);
        this.texts = new ArrayList<TextContainer>(1000);
        this.bitmapFont = new BitmapFont();
    }


    public void addTextToRenderQue(String text, Vector2 position, Color color, float scale)
    {
        this.texts.add(new TextContainer(text,position,color,scale));
    }

    public void render(SpriteBatch batch, OrthographicCamera oldCamera)
    {
        this.camera.update();
        batch.setProjectionMatrix(this.camera.combined);

        for(int i = 0; i < this.texts.size(); i++)
        {
            TextContainer tc = this.texts.get(i);
            this.bitmapFont.getData().setScale(tc.scale);
            this.bitmapFont.setColor(tc.color);
            this.bitmapFont.draw(batch, tc.text, tc.position.x, tc.position.y);
        }

        batch.setProjectionMatrix(oldCamera.combined);

        this.texts.clear();
    }

}
