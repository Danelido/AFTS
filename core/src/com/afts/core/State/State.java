package com.afts.core.State;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public abstract class State {

    protected StateManager stateManager;

    protected State(StateManager stateManager)
    {
        this.stateManager = stateManager;
        this.initialize();
    }

    protected abstract void initialize();
    public abstract void reInitializeAfterStateChange();
    public abstract void update();
    public abstract void render();
    public abstract void dispose();

}
