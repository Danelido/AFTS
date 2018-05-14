package com.afts.core.State;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class MenuState extends State {

    public MenuState(StateManager stateManager)
    {
        super(stateManager);
        System.out.println("MenuState created!");
        // This is how it works when changing from one state to another
        System.out.println("Switching from menustate to playstate!");
        this.stateManager.pushNewState(new PlayState(this.stateManager));
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render()
    {

    }

    @Override
    public void dispose()
    {
        System.out.println("MenuState disposed (Destroyed)");
    }
}
