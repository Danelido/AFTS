package com.afts.core.State;

import com.afts.core.Menu.Buttons;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.afts.core.World.Background;
import com.afts.core.Menu.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;


/**
 * Created by Alexander Danliden on 2018-05-14.
 *
 */

public class MenuState extends State {


    private OrthographicCamera camera;

    private InputProcessor inputProcessor;

    public MenuState(StateManager stateManager) { super(stateManager); }

    private Menu menu;

    private ResourceHandler resourceHandler;


    @Override
    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);

        this.resourceHandler = new ResourceHandler();
        //Initialize textures
        this.initializeResources();

        this.menu = new Menu(this.camera, this.resourceHandler);

        // Creates and initializes inputProcessor
        this.inputHandler();

        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    @Override
    // This function is ONLY called after a state change where a state higher up in the "stack"
    // is being poped. Some GDX things needs to be re-initialized when that happens.
    // Inputprocessor is one of them atm.
    public void reInitializeAfterStateChange()
    {
        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    private void initializeResources()
    {
        this.resourceHandler.addTexture("Start", "Textures/UI/Start_Button_Temp.png");
        this.resourceHandler.addTexture("StartDark", "Textures/UI/Start_Button_Temp_Dark.png");
        this.resourceHandler.addTexture("Score", "Textures/UI/Score_Button_Temp.png");
        this.resourceHandler.addTexture("Quit", "Textures/UI/Quit_Button_Temp.png");
        this.resourceHandler.addTexture("QuitDark", "Textures/UI/Quit_Button_Temp_Dark.png");
        this.resourceHandler.addTexture("Mute", "Textures/UI/Mute.png");
        this.resourceHandler.addTexture("Sound", "Textures/UI/Sound.png");

        this.resourceHandler.addTexture("fadedRoundParticle", "Textures/Particles/fadedRoundParticle.png");
    }

    @Override
    public void update()
    {
        menu.update();
    }

    @Override
    public void render()
    {
        menu.render();

    }

    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
            int buttonPressed = -1;

            @Override
            public boolean keyDown(int keycode) {

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

            //Mousebutton Down
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                buttonPressed = menu.mouseDown(screenX, screenY);
                return false;
            }

            @Override //MouseButton Up
            public boolean touchUp(int screenX, int screenY, int pointer, int button)
            {
                if(buttonPressed == menu.mouseUp(screenX, screenY))
                {
                    System.out.println(buttonPressed);
                    if(buttonPressed == Buttons.Start.ordinal())
                    {
                        MenuState.this.stateManager.pushNewState(new PlayState(MenuState.this.stateManager));
                    }
                    else if(buttonPressed == Buttons.Quit.ordinal())
                    {
                        Gdx.app.exit();
                    }
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY)
            {

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
        menu.dispose();
        this.resourceHandler.cleanUp();
    }

}
