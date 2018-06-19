package com.afts.core.Utility;

import com.badlogic.gdx.Gdx;

public class StaticSettings {

    public static final String GAME_TITLE = "Space Drifter";
    public static final int GAME_WIDTH = Gdx.graphics.getWidth();
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight();
    public static final double GAME_RATIO = GAME_WIDTH / GAME_HEIGHT; // if ever needed, maybe when working with UI
    public static boolean muted = false;

}
