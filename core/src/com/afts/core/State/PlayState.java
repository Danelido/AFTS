package com.afts.core.State;

import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class PlayState extends State{

    private OrthographicCamera camera;
    private ResourceHandler resourceHandler;
    private SpriteBatch spriteBatch;
    private InputProcessor inputProcessor;

    // Testing
    Texture tempTexture;

    public PlayState(StateManager stateManager) { super(stateManager); }

    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        // Creates and initializes inputProcessor
        this.inputHandler();

        // Initialize classes
        this.resourceHandler = new ResourceHandler();
        this.spriteBatch  = new SpriteBatch();

        // Initialize camera
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);

        // Initializing temp texture (for testing purposes)
        this.tempTexture = this.resourceHandler.getTexture("badlogic.jpg");

        // Tells GDX that it's now time to listen to this class inputHandler
        Gdx.input.setInputProcessor(this.inputProcessor);

        // Debug stuff
        System.out.println("Playstate created");
    }

    // This function is ONLY called after a state change where a state higher up in the "stack"
    // is being poped. Some GDX things needs to be re-initialized when that happens.
    // Inputprocessor is one of them atm.
    public void reInitializeAfterStateChange()
    {
        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    @Override
    public void update()
    {
       this.camera.update();
       this.spriteBatch.setProjectionMatrix(this.camera.combined);

       // Yet another debug
       //System.out.println("Mouse [" + Gdx.input.getX() + ", " + Gdx.input.getY() + "]");

    }

    @Override
    public void render()
    {
        this.spriteBatch.begin();
        this.spriteBatch.draw(this.tempTexture, 0.f, 0.f, 100.f, 100.f);
        this.spriteBatch.end();
    }


    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(Input.Keys.W == keycode)
                {
                    System.out.println("W");
                }

                if(Input.Keys.A == keycode)
                {
                    System.out.println("A");
                }

                if(Input.Keys.S == keycode)
                {
                    System.out.println("S");
                }

                if(Input.Keys.D == keycode)
                {
                    System.out.println("D");
                }

                if(Input.Keys.E == keycode)
                {
                    System.out.println("Back to menu boois");
                    PlayState.this.stateManager.popCurrentState();
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
    }

    @Override
    public void dispose()
    {
        this.spriteBatch.dispose();
        this.resourceHandler.cleanUp();
        System.out.println("Playstate disposed");
    }
}
