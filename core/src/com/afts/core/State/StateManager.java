package com.afts.core.State;

import java.util.Stack;
import java.util.Vector;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class StateManager {

    private Vector<State> states;

    public StateManager()
    {
        states = new Vector<State>();
    }

    public void updateCurrentState()
    {
        if(!this.states.isEmpty())
        {
           this.states.get(0).update();
        }
    }

    public void renderCurrentState()
    {
        if(!this.states.isEmpty())
        {
            this.states.get(0).render();
        }

    }

    public void dispose()
    {
        while(!this.states.isEmpty())
        {
            this.states.get(0).dispose();
            this.states.remove(0);
        }
    }

    public void pushNewState(State newState)
    {
       if(newState == null)
       {
           System.out.println("The \"new state\" is null..");
           return;
       }

        this.states.add(newState);
    }

    public void popCurrentState()
    {
        if(!this.states.isEmpty())
        {
            this.states.get(0).dispose();
        }

        this.states.remove(0);
    }

}
