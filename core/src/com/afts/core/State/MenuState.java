package com.afts.core.State;

import com.afts.core.Math.CoordinateConverter;
import com.afts.core.Ui.UIButton;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.afts.core.World.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.Button;

/**
 * Created by Alexander Danliden on 2018-05-14.
 *
 */

public class MenuState extends State {

    public enum Buttons {
        Start,
        Score,
        Mute,
        Quit //Only used for PC
    }

    private OrthographicCamera camera;

    private InputProcessor inputProcessor;

    public MenuState(StateManager stateManager) { super(stateManager); }


    private int nrOfButtons;
    private UIButton[] uiButtons;

    private ResourceHandler resourceHandler;
    private SpriteBatch batch;

    private Background background;

    @Override
    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        this.batch = new SpriteBatch();
        resourceHandler = new ResourceHandler();

        //Add all of the textures etc.
        this.initializeResources();

        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);

        this.initializeButtons();

        this.background = new Background(this.camera, this.resourceHandler);
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
    //Scale the buttons, so that they occupy a certain % of the screen
    public int calculateButtonSize(int percent, int screenSize)
    {
        float onePercent = screenSize / 100;
        return Math.round(onePercent * percent);
    }
    private void initializeButtons()
    {
        this.nrOfButtons = 4;

        Vector2 buttonPosition;
        Vector2 buttonSize = new Vector2(calculateButtonSize(20, StaticSettings.GAME_WIDTH), calculateButtonSize(12, StaticSettings.GAME_WIDTH));


        this.uiButtons = new UIButton[nrOfButtons];
        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 4);
        this.uiButtons[Buttons.Start.ordinal()] = new UIButton(resourceHandler.getTexture("Start"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 2);
        this.uiButtons[Buttons.Quit.ordinal()] = new UIButton(resourceHandler.getTexture("Quit"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 3);
        this.uiButtons[Buttons.Score.ordinal()] = new UIButton(resourceHandler.getTexture("Score"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 5) * 1, (StaticSettings.GAME_HEIGHT / 10) * 2);

        buttonSize = new Vector2(buttonSize.x / 2, buttonSize.y / 2);
        this.uiButtons[Buttons.Mute.ordinal()] = new UIButton(resourceHandler.getTexture("Sound"), buttonPosition, buttonSize, Buttons.Start.ordinal());
    }
    private void initializeResources()
    {
        this.resourceHandler.addTexture("Start", "Textures/UI/UTButtonTemp.png");
        this.resourceHandler.addTexture("Score", "Textures/UI/scoreButtonTemp.png");
        this.resourceHandler.addTexture("Quit", "Textures/UI/quitButtonTemp.png");
        this.resourceHandler.addTexture("Mute", "Textures/UI/Mute.png");
        this.resourceHandler.addTexture("Sound", "Textures/UI/Sound.png");
        this.resourceHandler.addTexture("fadedRoundParticle", "Textures/Particles/fadedRoundParticle.png");
    }

    @Override
    public void update()
    {
        this.background.update();
    }

    @Override
    public void render()
    {
        this.batch.begin();
        for(int i = 0; i < nrOfButtons; i++)
        {
            this.uiButtons[i].render(this.batch);
        }
        this.batch.end();

        this.background.render();
    }

    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
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

                if(button == 0)
                {
                    System.out.println(new Vector2(screenX, screenY));
                    if(uiButtons[Buttons.Start.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
                    {
                        System.out.println("Clicked Button");
                        MenuState.this.stateManager.pushNewState(new PlayState(MenuState.this.stateManager));
                    }

                    if(uiButtons[Buttons.Mute.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
                    {
                        if(StaticSettings.muted == true)
                        {
                            StaticSettings.muted = false;
                            uiButtons[Buttons.Mute.ordinal()].setTexture(resourceHandler.getTexture("Sound"));
                        }
                        else
                        {
                            StaticSettings.muted = true;
                            uiButtons[Buttons.Mute.ordinal()].setTexture(resourceHandler.getTexture("Mute"));
                        }
                    }
                    if(uiButtons[Buttons.Quit.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
                    {
                        Gdx.app.exit();
                    }

                }
                return false;
            }

            @Override //MouseButton Up
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
        for(int i = 0; i < 1; i++)
        {
            this.uiButtons[i].dispose();
        }
        this.resourceHandler.cleanUp();
    }

}
