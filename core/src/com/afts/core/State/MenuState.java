package com.afts.core.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class MenuState extends State {

    private InputProcessor inputProcessor;

    public MenuState(StateManager stateManager) { super(stateManager); }

    @Override
    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        // Creates and initializes inputProcessor
        this.inputHandler();

        Gdx.input.setInputProcessor(this.inputProcessor);

        System.out.println("Menustate created");
    }

    @Override
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

    }

    @Override
    public void render()
    {

    }

    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {

                if(Input.Keys.E == keycode)
                {
                    System.out.println("Off to playstate booois");
                    MenuState.this.stateManager.pushNewState(new PlayState(MenuState.this.stateManager));
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
        System.out.println("Menustate disposed");
    }
}
