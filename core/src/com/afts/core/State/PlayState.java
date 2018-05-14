package com.afts.core.State;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class PlayState extends State{


    public PlayState(StateManager stateManager)
    {
        super(stateManager);
        System.out.println("PlayState created!");
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
        System.out.println("PlayState disposed (Destroyed)");
    }
}
