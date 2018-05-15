package com.afts.core.State;

import com.afts.core.Entities.PlayerPackage.Player;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class PlayState extends State{

    private OrthographicCamera camera;
    private ResourceHandler resourceHandler;
    private InputProcessor inputProcessor;
    private Player player;


    public PlayState(StateManager stateManager) { super(stateManager); }

    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        // Creates and initializes inputProcessor
        this.inputHandler();

        // Initialize classes
        this.resourceHandler = new ResourceHandler();

        // Initialize camera
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);

        // Tells GDX that it's now time to listen to this class inputHandler
        Gdx.input.setInputProcessor(this.inputProcessor);

        // Initialize player class
        this.player = new Player(this.resourceHandler.getTexture("Textures/Player/TemporaryPlayerTexture.png"), this.camera, new Vector2(0.f,0.f), new Vector2(64.f, 64.f));

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
       this.player.update();
    }

    @Override
    public void render()
    {
        this.player.render();
    }


    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(Input.Keys.W == keycode)
                {

                }

                if(Input.Keys.A == keycode)
                {
                    PlayState.this.player.translatePosition(new Vector2(-20.f, 0.f));
                }

                if(Input.Keys.S == keycode)
                {

                }

                if(Input.Keys.D == keycode)
                {
                    PlayState.this.player.translatePosition(new Vector2(20.f, 0.f));
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
        this.player.dispose();
        this.resourceHandler.cleanUp();
        System.out.println("Playstate disposed");
    }
}
