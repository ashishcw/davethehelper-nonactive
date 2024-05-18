package com.hereandtheregames.davethehelper.screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.github.tommyettinger.textra.TypingLabel;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.characters.group.GroupBehaviour;
import com.hereandtheregames.davethehelper.behaviours.characters.npc.NPCSpawningBehaviour;
import com.hereandtheregames.davethehelper.behaviours.characters.player.PlayerCharacterBehaviour;
import com.hereandtheregames.davethehelper.behaviours.test.ActiveCustomerQueueBehaviourTest;
import com.hereandtheregames.davethehelper.behaviours.world.ActiveCustomerQueueBehaviour;
import com.hereandtheregames.davethehelper.managers.GameManager;
import com.hereandtheregames.davethehelper.managers.WorldManager;
import com.hereandtheregames.davethehelper.pathfinding.node.Node;
import com.hereandtheregames.davethehelper.screens.hud.HUD;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.UnBox;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateBoxFixtureBehaviour;

public class PlayScreen implements Screen {

    private final String TAG = PlayScreen.class.getSimpleName();

    //tiled map
    public static TiledMap tiledMap;
    public static World world;
    public static OrthographicCamera gameCamera;

    private final float WIDTH = 19.0f;
    private final float HEIGHT = 23.0f;

    private MainGame context;
    private SpriteBatch spriteBatch;

    //stage
    private Stage stage;

    //viewport
    //private FitViewport viewport;




    private GameManager gameManager;

    private boolean showBox2DDebuggerRenderer = false;
    //world
    //private World world;
    private WorldManager worldManager;

    //box2d
    private Box2DDebugRenderer box2DDebugRenderer;

    //light 2d
    private RayHandler rayHandler;
    private float ambientLight = 0.5f;


    OrthogonalTiledMapRenderer tiledMapRenderer;

    //unbox
    private UnBox unBox;
    private GameObject playerGameObject, cookGO;
    //private GameObject testNPC1;

    private HUD hud;


    public PlayScreen(MainGame mainGame) {
        this.context = mainGame;
        this.spriteBatch = this.context.getBatch();
        Gdx.app.debug(TAG, "Play Screen Loaded");
        this.gameManager = new GameManager(this.context);

    }

    //call this below init method from overriden show method
    private void init(){
        //game camera
        this.gameCamera = new OrthographicCamera();


        //this.stage = new Stage(new FitViewport(GameConfig.WORLD_WIDTH_IN_TILES, GameConfig.WORLD_HEIGHT_IN_TILES, this.gameCamera), this.batch);
        this.stage = new Stage(new StretchViewport(GameConfig.WORLD_WIDTH_IN_TILES, GameConfig.WORLD_HEIGHT_IN_TILES, this.gameCamera), this.spriteBatch);




        this.gameCamera.setToOrtho(
                false,
                (float) this.stage.getViewport().getScreenWidth() / 2,
                (float) this.stage.getViewport().getScreenHeight() / 2
        );
        //this.context.setGameCamera(this.gameCamera);


        //box2d
        this.world = new World(Vector2.Zero, true);
        this.context.setWorld(this.world);
        this.context.setUnBox(new UnBox(this.world));
        this.unBox = this.context.getUnBox();

        //world debugger
        this.box2DDebugRenderer = this.gameManager.getBox2DDebugRenderer();
        this.showBox2DDebuggerRenderer = true; //true;

        //box2d light / rayhandler
        this.rayHandler = new RayHandler(this.world);
        this.rayHandler.setAmbientLight(ambientLight);

        //tiled map
        this.tiledMap = new TmxMapLoader().load("map/zeldamapfinal.tmx");
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap, GameConfig.UNIT_SCALE_PPM, this.spriteBatch);

        this.tiledMapRenderer.setMap(tiledMap);


        //world creator
        this.worldManager = new WorldManager(this.context, this.tiledMap, this.context.getWorld(), this.rayHandler);
        this.worldManager.buildAll();

        //hud
        this.hud = new HUD(
                this.spriteBatch,
                this.context,
                this.stage.getViewport().getWorldWidth() * GameConfig.UNIT_SCALE,
                this.stage.getViewport().getWorldHeight() * GameConfig.UNIT_SCALE
        );

        //unbox
        this.playerGameObject = new GameObject(this.unBox);
        this.playerGameObject.setName("Player");


        BodyDef playerDefGo = new BodyDef();
        playerDefGo.fixedRotation = true;
        playerDefGo.type = BodyDef.BodyType.DynamicBody;
        playerDefGo.position.set(10f,13.5f);

        new Box2dBehaviour(playerDefGo, this.playerGameObject);

        //hitbox
        //fixture
        FixtureDef fixtureDefPlayer = new FixtureDef();
        fixtureDefPlayer.filter.categoryBits = GameConfig.PLAYER_CATEGORY;
        fixtureDefPlayer.filter.maskBits = GameConfig.WALL_CATEGORY + GameConfig.INTERACTIBLE_CATEGORY + GameConfig.NPC_CATEGORY;
        new CreateBoxFixtureBehaviour(
                0.5f,
                0.5f,
                Vector2.Zero,
                fixtureDefPlayer,
                this.playerGameObject);



        new GroupBehaviour(this.playerGameObject, GroupBehaviour.Group.PLAYER);
        new AnimationBehaviour(this.playerGameObject, AnimationTypes.WALK_RIGHT, CurrentAnimationState.Walking, CharacterType.Player);
        new PlayerCharacterBehaviour(this.playerGameObject, this.context);





        //NPC Spawning
        //cook helper
//        GameObject goCook = new GameObject(this.unBox);
//        new NPCSpawningBehaviour(goCook, this.context, CharacterType.NPC_COOK_HELPER, GroupBehaviour.Group.COOK_HELPER);

        //unbox
        cookGO = new GameObject(this.unBox);
        cookGO.setName("Cook-Helper-GO");


        BodyDef cookDefGo = new BodyDef();
        cookDefGo.fixedRotation = true;
        cookDefGo.type = BodyDef.BodyType.DynamicBody;
        //playerDefGo.position.set(this.worldManager.playerStartingLocation.x, this.worldManager.playerStartingLocation.y);
        //playerDefGo.position.set(this.worldManager.playerStartingLocation.x-8f, this.worldManager.playerStartingLocation.y + 1.5f);
        cookDefGo.position.set(2.5f, 15f);

        new Box2dBehaviour(cookDefGo, cookGO);

        //hitbox
        //fixture
        FixtureDef fixtureDefCook = new FixtureDef();
        fixtureDefCook.isSensor = true;
        fixtureDefCook.filter.categoryBits = GameConfig.NPC_CATEGORY;
        fixtureDefCook.filter.maskBits = GameConfig.WALL_CATEGORY + GameConfig.INTERACTIBLE_CATEGORY + GameConfig.PLAYER_CATEGORY;
        new CreateBoxFixtureBehaviour(
                0.5f,
                0.5f,
                Vector2.Zero,
                fixtureDefCook,
                cookGO);




        new GroupBehaviour(cookGO, GroupBehaviour.Group.NPC);
        new AnimationBehaviour(cookGO, AnimationTypes.IDLE_DOWN, CurrentAnimationState.Idle, CharacterType.NPC_Standard);


        //customer
        GameObject go = new GameObject(this.unBox);
        new NPCSpawningBehaviour(go, this.context, CharacterType.NPC_Standard, GroupBehaviour.Group.NPC);



       this.createMapObjectCustomCollisions();




    }

    private void createMapObjectCustomCollisions() {



        //Fountain
        Vector2 position = new Vector2(10.47f,09.5f);
        createCustomBox("Fountain",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                1.5f,
                1.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );

        //Home
        position = new Vector2(3.0f,18f);
        createCustomBox("Home",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                3.5f,
                3f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );

        //Resource-Dumper
        position = new Vector2(7.5f,16.5f);
        createCustomBox("Player-Resource-Dumper",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.5f,
                1.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );


        //shop
        position = new Vector2(16.5f,15.5f);
        createCustomBox("Shop-Table",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                2.5f,
                0.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
                );


        //shop-boxes
        position = new Vector2(18.5f,15.5f);
        createCustomBox("Shop-Boxes",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.5f,
                2.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );



        //bench-1
        position = new Vector2(2.5f,4f);
        createCustomBox("Bench-1",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                1.5f,
                0.8f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );


        //bench-2
        position = new Vector2(16.5f,4f);
        createCustomBox("Bench-2",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                1.5f,
                0.8f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );


        //sea-barrier
        position = new Vector2(10f,2.5f);
        createCustomBox("Sea-Barrier",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                10f,
                0.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );


        //farm
        //tomato fields
        position = new Vector2(1.5f,11.5f);
        createCustomBox("Tomato-Field-1",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        position = new Vector2(2.5f,11.5f);
        createCustomBox("Tomato-Field-2",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        position = new Vector2(3.5f,11.5f);
        createCustomBox("Tomato-Field-3",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        position = new Vector2(1.5f,10.5f);
        createCustomBox("Tomato-Field-4",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        position = new Vector2(2.5f,10.5f);
        createCustomBox("Tomato-Field-5",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        position = new Vector2(3.5f,10.5f);
        createCustomBox("Tomato-Field-6",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        //potatoes fields
        position = new Vector2(1.5f,09.5f);
        createCustomBox("Potato-Field-1",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        position = new Vector2(2.5f,09.5f);
        createCustomBox("Potato-Field-2",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        position = new Vector2(3.5f,09.5f);
        createCustomBox("Potato-Field-3",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        //corn fields
        position = new Vector2(1.5f,08.5f);
        createCustomBox("Corn-Field-1",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        position = new Vector2(2.5f,08.5f);
        createCustomBox("Corn-Field-2",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        position = new Vector2(3.5f,08.5f);
        createCustomBox("Corn-Field-3",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        //farm outerwalls
        //top wall
        position = new Vector2(2.5f,12.5f);
        createCustomBox("Farm-Wall-Top",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                2.2f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );


        //bottom wall
        position = new Vector2(2.5f,7.5f);
        createCustomBox("Farm-Wall-Bottom",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                2.2f,
                0.3f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );

        //left wall
        position = new Vector2(0.5f,10f);
        createCustomBox("Farm-Wall-Left",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.3f,
                2.2f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );




        //npc-spawnpoint1
        position = new Vector2(-10f,5.5f);
        createCustomBox("NPC-SpawnPoint-1",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.5f,
                0.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );
        ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.put(1, position);


        //npc-spawnpoint2
        position = new Vector2(30f,5.5f);
        createCustomBox("NPC-SpawnPoint-2",
                GameConfig.WALL_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.5f,
                0.5f,
                position,
                BodyDef.BodyType.StaticBody,
                false
        );
        ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.put(2, position);

        //NPC to Customer Collider
        position = new Vector2(17.0f,5.5f);
        GameObject npcCustomerConversionColliderGO = createCustomBox("NPC-Customer-Conversion-Collider",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );
        ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.put(3, position);



        //NPC to Customer Collider EXIT
        position = new Vector2(17.0f,6.5f);
        createCustomBox("NPC-Customer-Conversion-Collider-EXIT",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );


        //Customer Queue PlaceHolder #1
        position = new Vector2(16.5f,14.5f);
        GameObject queuePlaceHolder1 = createCustomBox("Queue-PlaceHolder-1",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );

        //ActiveCustomerQueueBehaviour.activeCustomerQueue.put(queuePlaceHolder1, true);
        ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.put(1, position);




        //Customer Queue PlaceHolder #2
        position = new Vector2(16.5f,13.0f);
        GameObject queuePlaceHolder2 = createCustomBox("Queue-PlaceHolder-2",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );
        //ActiveCustomerQueueBehaviour.activeCustomerQueue.put(queuePlaceHolder2, true);
        ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.put(2, position);

        //Customer Queue PlaceHolder #3
        position = new Vector2(16.5f,11.5f);
        GameObject queuePlaceHolder3 = createCustomBox("Queue-PlaceHolder-3",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );
        //ActiveCustomerQueueBehaviour.activeCustomerQueue.put(queuePlaceHolder3, true);
        ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.put(3, position);


        //Customer Queue PlaceHolder #4
        position = new Vector2(16.5f,10.0f);
        GameObject queuePlaceHolder4 = createCustomBox("Queue-PlaceHolder-4",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );
        //ActiveCustomerQueueBehaviour.activeCustomerQueue.put(queuePlaceHolder4, true);
        ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.put(4, position);

        //Customer Queue PlaceHolder #5
        position = new Vector2(16.5f,8.5f);
        GameObject queuePlaceHolder5 = createCustomBox("Queue-PlaceHolder-5",
                GameConfig.INTERACTIBLE_CATEGORY,
                (short) (GameConfig.PLAYER_CATEGORY + GameConfig.NPC_CATEGORY),
                0.01f,
                0.01f,
                position,
                BodyDef.BodyType.StaticBody,
                true
        );
        //ActiveCustomerQueueBehaviour.activeCustomerQueue.put(queuePlaceHolder5, true);
        ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.put(5, position);

        new ActiveCustomerQueueBehaviourTest(queuePlaceHolder5);


    }


    private GameObject createCustomBox(
            String boxName,
            short boxCategory,
            short boxMasks,
            float boxWidth,
            float boxHeight,
            Vector2 boxFixturePosition,
            BodyDef.BodyType bodyType,
            boolean isFixtureSensor

    ){
        //shop rectangle
        GameObject shopGO = new GameObject(this.unBox);
        shopGO.setName(boxName);

        //body definition
        BodyDef bodyDefHome = new BodyDef();
        bodyDefHome.type = bodyType;
        bodyDefHome.position.set(Vector2.Zero);

        //fixture
        FixtureDef fixtureDefHome = new FixtureDef();
        fixtureDefHome.isSensor = isFixtureSensor;
        fixtureDefHome.filter.categoryBits = boxCategory;
        fixtureDefHome.filter.maskBits = (short) (boxMasks);

        new Box2dBehaviour(bodyDefHome, shopGO);
        new CreateBoxFixtureBehaviour(
                boxWidth,
                boxHeight,
                boxFixturePosition,
                fixtureDefHome,
                shopGO);

        return shopGO;
    }

    @Override
    public void show() {
        this.init();

        //pathfinding
        //Node.initializeNodeGraph(); //TODO: Later implement pathfinding

        //ui
        //this.createUI();


    }

    @Override
    public void render(float delta) {
        //add input handler here

        //render logic goes here
        ScreenUtils.clear(0f,0f,0f,1f);

        //unbox
        this.unBox.preRender(delta);

        //map rendering here
        //this.viewport.apply(true);

        this.stage.getViewport().apply(true);
        this.spriteBatch.setProjectionMatrix(this.stage.getViewport().getCamera().combined);
        this.tiledMapRenderer.setView(this.gameCamera);
        this.tiledMapRenderer.render();

        //sprite batch here
        this.spriteBatch.begin();
        this.unBox.render(this.spriteBatch);
        Gdx.graphics.setTitle("FPS : "+Gdx.graphics.getFramesPerSecond()); //fps
        this.spriteBatch.end();




        //box2d
        if(this.showBox2DDebuggerRenderer){
            this.box2DDebugRenderer.render(this.unBox.getWorld(), this.gameCamera.combined);
        }

        //stage
        this.stage.act();
        this.stage.draw();

        //hud
        this.spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined); //set the spriteBatch to draw what our stageViewport sees
        hud.getStage().act(delta); //act the Hud
        hud.getStage().draw(); //draw the Hud

//        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
//            hud.tempCount++;
//            hud.textraLabel.setText("[BLUE]Tomatoes : [RED]" + hud.tempCount);
//
//        }

        //unbox
        this.unBox.postRender();




    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
        //this.gameCamera.zoom = 1.0f;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
        this.box2DDebugRenderer.dispose();
        this.rayHandler.dispose();
    }

    private void createUI() {

        Table root = new Table();
        root.setFillParent(true);


        String text = "[GREEN]Hello,{WAIT} world!"
                + "[ORANGE]{SLOWER} Did you know orange is my favorite color?";

        TypingLabel typingLabel = new TypingLabel(text, this.context.getSkin());

        //root.add(textraLabel);
        typingLabel.setPosition(0.5f * GameConfig.UNIT_SCALE_PPM, 0.5f * GameConfig.UNIT_SCALE_PPM);
        root.add(typingLabel);


        this.stage.addActor(root);
    }
}
