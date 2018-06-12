package com.afts.core.State;

import com.afts.core.Entities.Collision.PointDebugRenderer;
import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.Objects.EntityManager;
import com.afts.core.Entities.Objects.OnCollisionSetting;
import com.afts.core.Entities.Objects.Rock;
import com.afts.core.Entities.PlayerPackage.Player;
import com.afts.core.Utility.Parsers.LoadLevelFromXML;
import com.afts.core.Utility.ResourceHandler;
import com.afts.core.Utility.StaticSettings;
import com.afts.core.World.Background;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander Danliden on 2018-05-14.
 */

public class PlayState extends State{

    private OrthographicCamera camera;
    private ResourceHandler resourceHandler;
    private InputProcessor inputProcessor;
    private Player player;
    private Background background;
    private EntityManager entityManager;
    private SATCollision collision;
    private LoadLevelFromXML levelLoader;

    //Debug
    private boolean up,down,left,right;
    private float speed = 100.f;

    public PlayState(StateManager stateManager) { super(stateManager); }

    // this is automatically called when a state is initialized!
    protected void initialize()
    {
        // Creates and initializes inputProcessor
        this.inputHandler();
        // Tells GDX that it's now time to listen to this class inputHandler
        Gdx.input.setInputProcessor(this.inputProcessor);

        this.resourceHandler = new ResourceHandler();
        this.initializeResources();

        this.camera = new OrthographicCamera(StaticSettings.GAME_WIDTH, StaticSettings.GAME_HEIGHT);
        this.camera.zoom = 1.f;

        this.player = new Player(this.resourceHandler, this.camera, new Vector2(20.f, 20.f));
        this.entityManager = new EntityManager(this.camera, this.player, this.resourceHandler);

        // The playstate should also take in a string "filename" of the level.
        // The string is based on what the user selects in the menu state
        this.levelLoader = new LoadLevelFromXML("Sandbox_2", this.entityManager, player);

        // Might make this class static in the future
        this.collision = new SATCollision();

        // Initialize the background "More of a visual effect than anything else"
        this.background = new Background(this.camera, this.resourceHandler);

        new PointDebugRenderer(this.camera);
    }

    // This function is ONLY called after a state change where a state higher up in the "stack"
    // is being popped. Some GDX things needs to be re-initialized when that happens.
    // InputProcessor is one of them atm.
    public void reInitializeAfterStateChange()
    {
        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    private void initializeResources()
    {
        // No need to load in map textures because those will be set in the level loader
        // Only load GUI,HUD and particles and stuff like that
        this.resourceHandler.addTexture("playerSprite", "Textures/Player/TemporaryPlayerTexture.png");
        this.resourceHandler.addTexture("joystick", "Textures/Player/joystick.png");
        this.resourceHandler.addTexture("throttle", "Textures/Player/throttle.png");
        this.resourceHandler.addTexture("fadedRoundParticle", "Textures/Particles/fadedRoundParticle.png");
        this.resourceHandler.addTexture("blurredCircleParticle", "Textures/Particles/blurredCircleParticle.png");
        this.resourceHandler.addTexture("squareParticle", "Textures/Particles/squareParticle.png");
    }

    @Override
    public void update()
    {
       this.background.update();
       this.player.update();
       this.camera.position.lerp(this.player.getPosition().cpy().add(this.player.getOrigin().x,this.player.getOrigin().y, 0.f), Gdx.graphics.getDeltaTime() * 15.f);
       this.camera.update();

       //Debug code
        if(this.up) this.player.getController().getMovementHandler().addToVelocity(new Vector2(0.f, speed * Gdx.graphics.getDeltaTime()));
        if(this.down) this.player.getController().getMovementHandler().addToVelocity(new Vector2(0.f, -speed * Gdx.graphics.getDeltaTime()));
        if(this.left) this.player.getController().getMovementHandler().addToVelocity(new Vector2(-speed * Gdx.graphics.getDeltaTime(), 0.f ));
        if(this.right) this.player.getController().getMovementHandler().addToVelocity(new Vector2(speed * Gdx.graphics.getDeltaTime(), 0.f));

    }
    @Override
    public void render()
    {
        this.background.render();
        this.entityManager.updateAndRender(this.collision);
        this.player.render();
    }

    private void inputHandler()
    {
        this.inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {

                if(Input.Keys.W == keycode)
                {
                 PlayState.this.up = true;
                }

                if(Input.Keys.A == keycode)
                {
                    PlayState.this.left = true;
                }

                if(Input.Keys.S == keycode)
                {
                    PlayState.this.down = true;
                }

                if(Input.Keys.D == keycode)
                {
                    PlayState.this.right = true;
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if(Input.Keys.W == keycode)
                {
                    PlayState.this.up = false;
                }

                if(Input.Keys.A == keycode)
                {
                    PlayState.this.left = false;
                }

                if(Input.Keys.S == keycode)
                {
                    PlayState.this.down = false;
                }

                if(Input.Keys.D == keycode)
                {
                    PlayState.this.right = false;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                PlayState.this.player.registerTouchDown(screenX, screenY, pointer);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                PlayState.this.player.registerTouchUp(0.f,0.f, pointer);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                PlayState.this.player.registerTouchMoved(screenX, screenY, pointer);
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
        this.player.dispose();
        this.resourceHandler.cleanUp();
        this.background.dispose();
        this.entityManager.dispose();
    }
}
