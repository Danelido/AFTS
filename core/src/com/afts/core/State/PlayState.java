package com.afts.core.State;

import com.afts.core.Entities.Collision.PointDebugRenderer;
import com.afts.core.Entities.Collision.SATCollision;
import com.afts.core.Entities.Objects.EntityManager;
import com.afts.core.Entities.Objects.OnCollisionSetting;
import com.afts.core.Entities.Objects.Rock;
import com.afts.core.Entities.PlayerPackage.Player;
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

        this.player = new Player(this.resourceHandler, this.camera, new Vector2(0.f,-50.f), new Vector2(20.f, 20.f));
        this.entityManager = new EntityManager(this.camera, this.player, this.resourceHandler);

        // 10k destroyable entities, sick
        for(int i = 0; i < 100; i++) {
           for(int j = 0; j < 100; j++)
           {
               this.entityManager.addEntity(
                       new Rock(
                               new Vector2(j* 11.f, i * 11.f),
                               new Vector2(10, 10.f),
                               this.resourceHandler.getTexture("tile"),
                               OnCollisionSetting.DESTROY));
           }
        }

        for(int i = 0; i < 10; i++)
        {
            this.entityManager.addEntity(
                    new Rock(
                            new Vector2(MathUtils.random(-2000, -500), MathUtils.random(0, 1500)),
                            new Vector2(50, 50.f),
                            this.resourceHandler.getTexture("tile"),
                            OnCollisionSetting.MOVABLE));
        }

        for(int i = 0; i < 10; i++)
        {
            this.entityManager.addEntity(
                    new Rock(
                            new Vector2(MathUtils.random(1600, 3100), MathUtils.random(0, 1500)),
                            new Vector2(50, 50.f),
                            this.resourceHandler.getTexture("tile"),
                            OnCollisionSetting.NON_MOVABLE));
        }

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
        this.resourceHandler.addTexture("playerSprite", "Textures/Player/TemporaryPlayerTexture.png");
        this.resourceHandler.addTexture("joystick", "Textures/Player/joystick.png");
        this.resourceHandler.addTexture("throttle", "Textures/Player/throttle.png");
        this.resourceHandler.addTexture("basicParticle", "Textures/Particles/particle_1.png");
        this.resourceHandler.addTexture("backgroundParticle", "Textures/Particles/particle_2.png");
        this.resourceHandler.addTexture("trailParticle", "Textures/Particles/particle_trail.png");
        this.resourceHandler.addTexture("tile", "Textures/Random/whiteTile.png");
        this.resourceHandler.addTexture("particle_03", "Textures/Particles/particle_03.png");
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
