package com.afts.core.State;

import java.util.Stack;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class StateManager {

    private Stack<State> states;

    public StateManager()
    {
        states = new Stack<State>();
    }

    public void updateCurrentState()
    {
        if(!this.states.empty())
        {
            this.states.peek().update();
        }
    }

    public void renderCurrentState()
    {
        if(!this.states.empty())
        {
            this.states.peek().render();
        }

    }

    public void dispose()
    {
        while(!this.states.empty())
        {
            this.states.peek().dispose();
            this.states.pop();
        }
    }

    public void pushNewState(State newState)
    {
        this.states.push(newState);
    }

    public void popCurrentState()
    {
        if(!this.states.isEmpty())
        {
            this.states.peek().dispose();
        }

        this.states.pop();
    }

}
