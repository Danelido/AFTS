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
        if(!this.states.isEmpty() && this.states.get(this.states.size() - 1) != null)
        {
           this.states.get(this.states.size() - 1).update();
        }
    }

    public void renderCurrentState()
    {
        if(!this.states.isEmpty() && this.states.get(this.states.size() - 1) != null)
        {
            this.states.get(this.states.size() - 1).render();
        }

    }

    public void dispose()
    {
        while(!this.states.isEmpty() && this.states.get(this.states.size() - 1) != null)
        {
            this.states.get(this.states.size() - 1).dispose();
            this.states.remove(this.states.size() - 1);
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
        if(!this.states.isEmpty() && this.states.get(this.states.size() - 1) != null)
        {
            this.states.get(this.states.size() - 1).dispose();
            this.states.remove(this.states.size() - 1);
            this.states.get(this.states.size() - 1).reInitializeAfterStateChange();
        }


    }

}
