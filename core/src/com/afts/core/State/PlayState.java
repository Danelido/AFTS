package com.afts.core.State;

import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class PlayState extends State{

    private OrthographicCamera camera;
    private ResourceHandler resourceHandler = new ResourceHandler();
    private SpriteBatch spriteBatch = new SpriteBatch();

    Texture tempTexture;

    public PlayState(StateManager stateManager)
    {
        super(stateManager);
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);
        this.tempTexture = this.resourceHandler.getTexture("badlogic.jpg");

    }

    @Override
    public void update()
    {
       this.camera.update();
       this.spriteBatch.setProjectionMatrix(this.camera.combined);

    }

    @Override
    public void render()
    {
        this.spriteBatch.begin();
        this.spriteBatch.draw(this.tempTexture, 100.f, 100.f, 100.f, 100.f);
        this.spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        this.spriteBatch.dispose();
        this.resourceHandler.cleanUp();
    }
}
