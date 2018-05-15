package com.afts.core;

import com.afts.core.Entities.Player;
import com.afts.core.Math.CoordinateConverter;
import com.afts.core.State.MenuState;
import com.afts.core.State.PlayState;
import com.afts.core.State.StateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class Core extends ApplicationAdapter {

	private StateManager stateManager;

	@Override
	public void create()
	{
		this.stateManager = new StateManager();
		this.stateManager.pushNewState(new MenuState(this.stateManager));
<<<<<<< HEAD

=======
>>>>>>> d019b5354cc86ad7b74472eb5df08122f0b36318
	}

	@Override
	public void render ()
	{
		// Update current state
		this.stateManager.updateCurrentState();

		// Clear screen and buffers etc
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render current state
		this.stateManager.renderCurrentState();

	}
	
	@Override
	public void dispose ()
	{
		// Dispose current state
		this.stateManager.dispose();
	}
}
