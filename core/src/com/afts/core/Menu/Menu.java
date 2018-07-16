package com.afts.core.Menu;

import com.afts.core.Math.CoordinateConverter;
import com.afts.core.Ui.UIButton;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steven on 26/06/2018
 */

public class Menu {

    private MenuParticles menuParticles;

    private SpriteBatch batch;

    private UIButton[] uiButtons;
    private int nrOfButtons;

    private ResourceHandler resourceHandler;

    public Menu(OrthographicCamera camera, ResourceHandler resourceHandler)
    {
        this.batch = new SpriteBatch();
        this.resourceHandler = resourceHandler;
        this.setupUIButtons();
        this.menuParticles = new MenuParticles(resourceHandler, camera);
    }

    private void setupUIButtons()
    {
        this.nrOfButtons = 4;

        Vector2 buttonPosition;
        Vector2 buttonSize = new Vector2(calculateButtonSize(20, StaticSettings.GAME_WIDTH), calculateButtonSize(12, StaticSettings.GAME_WIDTH));


        this.uiButtons = new UIButton[nrOfButtons];
        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 4);
        this.uiButtons[Buttons.Start.ordinal()] = new UIButton(this.resourceHandler.getTexture("Start"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 2);
        this.uiButtons[Buttons.Quit.ordinal()] = new UIButton(this.resourceHandler.getTexture("Quit"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 2), (StaticSettings.GAME_HEIGHT / 5) * 3);
        this.uiButtons[Buttons.Score.ordinal()] = new UIButton(this.resourceHandler.getTexture("Score"), buttonPosition, buttonSize, Buttons.Start.ordinal());

        buttonPosition = new Vector2((StaticSettings.GAME_WIDTH / 5) * 1, (StaticSettings.GAME_HEIGHT / 10) * 2);

        buttonSize = new Vector2(buttonSize.x / 2, buttonSize.y / 2);
        this.uiButtons[Buttons.Mute.ordinal()] = new UIButton(this.resourceHandler.getTexture("Sound"), buttonPosition, buttonSize, Buttons.Start.ordinal());

    }

    //Scale the buttons, so that they occupy a certain % of the screen
    private int calculateButtonSize(int percent, int screenSize)
    {
        float onePercent = screenSize / 100;
        return Math.round(onePercent * percent);
    }

    public void update()
    {
        this.menuParticles.update();
    }


    //Returns the button that was selected
    public int mouseDown(int screenX, int screenY)
    {
        int buttonPressed = -1;
        this.menuParticles.generateParticles(screenX, screenY);

        if(this.uiButtons[Buttons.Start.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
        {
            this.uiButtons[Buttons.Start.ordinal()].setTexture(this.resourceHandler.getTexture("StartDark"));
            buttonPressed = Buttons.Start.ordinal();

        }

        if(this.uiButtons[Buttons.Mute.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
        {
            buttonPressed = Buttons.Mute.ordinal();

            if(StaticSettings.muted == true)
            {
                StaticSettings.muted = false;
                this.uiButtons[Buttons.Mute.ordinal()].setTexture(this.resourceHandler.getTexture("Sound"));
            }
            else
            {
                StaticSettings.muted = true;
                this.uiButtons[Buttons.Mute.ordinal()].setTexture(this.resourceHandler.getTexture("Mute"));
            }
        }

        if(this.uiButtons[Buttons.Quit.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
        {
            this.uiButtons[Buttons.Quit.ordinal()].setTexture(this.resourceHandler.getTexture("QuitDark"));
            buttonPressed = Buttons.Quit.ordinal();
        }


        return buttonPressed;
    }

    public int mouseUp(int screenX, int screenY)
    {
        int buttonPressed = -1;

        //If the button that was pressed is the start button, and the mouse cursor is STILL within the bounds
        if(uiButtons[Buttons.Start.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
        {
            buttonPressed = Buttons.Start.ordinal();
        }
        else
        {
            uiButtons[Buttons.Start.ordinal()].setTexture(resourceHandler.getTexture("Start"));
        }
        if(uiButtons[Buttons.Quit.ordinal()].clicked(new Vector2(screenX, CoordinateConverter.calcYCoord(screenY))))
        {
            buttonPressed = Buttons.Quit.ordinal();
        }
        else
        {
            uiButtons[Buttons.Quit.ordinal()].setTexture(resourceHandler.getTexture("Quit"));
        }

        return buttonPressed;
    }

    public void render()
    {
        this.menuParticles.renderBackground();
        this.batch.begin();
        for(int i = 0; i < nrOfButtons; i++)
        {
            this.uiButtons[i].render(this.batch);
        }
        this.batch.end();
        this.menuParticles.renderForeground();

    }

    public void dispose()
    {
        for(int i = 0; i < nrOfButtons; i++)
        {
            this.uiButtons[i].dispose();
        }
        this.menuParticles.dispose();
        this.batch.dispose();
        resourceHandler.cleanUp();
    }
}

