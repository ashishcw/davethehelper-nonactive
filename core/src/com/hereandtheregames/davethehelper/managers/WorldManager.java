package com.hereandtheregames.davethehelper.managers;

import box2dLight.RayHandler;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import com.hereandtheregames.davethehelper.utils.ShapeFactory;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateBoxFixtureBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;
import dev.lyze.projectTrianglePlatforming.TiledTileCollisionToBox2d;
import dev.lyze.projectTrianglePlatforming.TiledTileCollisionToBox2dOptions;
import dev.lyze.projectTrianglePlatforming.TiledTileLayerToBox2d;
import dev.lyze.projectTrianglePlatforming.TiledTileLayerToBox2dOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class WorldManager {
    private MainGame context;
    private GameManager gameManager;
    private final TiledMap tiledMap;
    private final World world;
    private final RayHandler rayHandler;
    private AssetManager assetManager;
    private TextureAtlas actorAtlas;

    public Vector2 playerStartingLocation = new Vector2();

    public java.util.List<GameObject>allMapGameObjects;

    public WorldManager(MainGame mainGame, TiledMap tiledMap, World world, RayHandler rayHandler) {
        this.context = mainGame;
        this.tiledMap = tiledMap;
        this.world = world;
        this.rayHandler = rayHandler;

        this.gameManager = new GameManager(this.context);

        //this.assetManager = GameManager.instance.assetManager;
        this.assetManager = this.gameManager.getAssetManager();

        this.actorAtlas = assetManager.get("images/actors.pack", TextureAtlas.class);

        this.allMapGameObjects = new ArrayList<>();

    }

    public void buildAll() {

        //this.playerStartingLocation = new Vector2(19.77f,13.75f);
        //this.playerStartingLocation = new Vector2(19.77f,13.75f);
//
//
////        TiledTileLayerToBox2d builder = new TiledTileLayerToBox2d(TiledTileLayerToBox2dOptions.builder()
////                .scale(GameConfig.UNIT_SCALE_PPM)
////                .build());
////
////        builder.parseAllLayers(tiledMap, world);
//
//        TiledTileLayerToBox2d builder = new TiledTileLayerToBox2d(TiledTileLayerToBox2dOptions.builder()
//                .scale(GameConfig.UNIT_SCALE_PPM)
//                .build()
//        );
//
//
//        Iterator<MapLayer> it = this.tiledMap.getLayers().iterator();
//        while (it.hasNext()) {
//            MapLayer layer = it.next();
//            // some layers can be plain MapLayer instances (ie. object groups), just ignore them
//            if (layer instanceof TiledMapTileLayer) {
//                if(!Objects.equals(layer.getName(), "collisionTier1")){
//                    continue;
//                }
//                System.out.println(layer.getName());
//                TiledMapTileLayer tlayer = (TiledMapTileLayer)layer;
//                //builder.parseLayer(tlayer, this.world);
//            }
//        }




        buildMap();
    }

    private void buildMap() {

        MapLayers mapLayers = this.tiledMap.getLayers();

        for (MapLayer mapLayer:mapLayers) {
            for (MapObject mapObject : mapLayer.getObjects()) {

                BodyDef bodyDef = new BodyDef();
                FixtureDef fixtureDef = new FixtureDef();
                GameObject gameObject = new GameObject(this.context.getUnBox());

                bodyDef.fixedRotation = true;
                bodyDef.type = BodyDef.BodyType.StaticBody;



                if(mapObject instanceof TextureMapObject){
                    continue;
                }

                //Shape shape;

                if (mapObject instanceof RectangleMapObject) {
                    bodyDef.position.set(((RectangleMapObject) mapObject).getRectangle().getX(), ((RectangleMapObject) mapObject).getRectangle().getY());
                    new Box2dBehaviour(bodyDef, gameObject);
                    new CreateBoxFixtureBehaviour(0.5f, 0.5f, gameObject);
                    this.allMapGameObjects.add(gameObject);
                }
                else if (mapObject instanceof PolygonMapObject) {
                    bodyDef.position.set(((PolygonMapObject) mapObject).getPolygon().getX(), ((PolygonMapObject) mapObject).getPolygon().getY());
                    new Box2dBehaviour(bodyDef, gameObject);
                    new CreateBoxFixtureBehaviour(0.5f, 0.5f, gameObject);
                    this.allMapGameObjects.add(gameObject);
                }
                else if (mapObject instanceof PolylineMapObject) {
                    bodyDef.position.set(((PolylineMapObject) mapObject).getPolyline().getX(), ((PolylineMapObject) mapObject).getPolyline().getY());
                    new Box2dBehaviour(bodyDef, gameObject);
                    new CreateBoxFixtureBehaviour(0.5f, 0.5f, gameObject);
                    this.allMapGameObjects.add(gameObject);
                }
                else if (mapObject instanceof CircleMapObject) {
                    //shape = ShapeFactory.getCircle((CircleMapObject) mapObject);
                    bodyDef.position.set(((CircleMapObject) mapObject).getCircle().x, ((CircleMapObject) mapObject).getCircle().y);
                    new Box2dBehaviour(bodyDef, gameObject);
                    new CreateBoxFixtureBehaviour(0.5f, 0.5f, gameObject);
                    this.allMapGameObjects.add(gameObject);
                }
                else {
                    continue;
                }








//                bodyDef.type = BodyDef.BodyType.StaticBody;
//                Body body = context.getWorld().createBody(bodyDef);
//
//                body.setUserData(mapObject.getName());
//                gameObject.setName(mapObject.getName());
//
//                fixtureDef.filter.categoryBits = GameConfig.BIT_GROUND;
//                fixtureDef.filter.maskBits = -1;
//                body.createFixture(fixtureDef);
//
//                shape.dispose();
            }
        }

        for (MapObject object : this.tiledMap.getLayers().get("playerspawnpoint").getObjects()) {
            if(object.getName().equals("playerspawnpoint")){
                RectangleMapObject spawnPoint = (RectangleMapObject) object;
                float x = spawnPoint.getRectangle().getX() / GameConfig.UNIT_SCALE;
                float y = spawnPoint.getRectangle().getY() / GameConfig.UNIT_SCALE;

//                GameObject gameObject = new GameObject(this.context.getUnBox());
//                System.out.println(x + " / " + y);
//
//                BodyDef bodyDef = new BodyDef();
//                bodyDef.position.set(x, y);
//                gameObject.setName(object.getName());
//                new Box2dBehaviour(bodyDef, gameObject);
//                new CreateBoxFixtureBehaviour(0.5f, 0.5f, gameObject);
//                //this.allMapGameObjects.add(gameObject);

                this.playerStartingLocation.set(x, y);
            }
        }

    }
}
