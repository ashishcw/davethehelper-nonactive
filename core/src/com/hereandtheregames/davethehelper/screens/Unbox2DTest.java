package com.hereandtheregames.davethehelper.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.MoveBehaviour;
import dev.lyze.gdxUnBox2d.BodyDefType;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.UnBox;
import dev.lyze.gdxUnBox2d.behaviours.SoutBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateBoxFixtureBehaviour;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

public class Unbox2DTest implements Screen {

    MainGame context;
    private Viewport viewport;
    private SpriteBatch batch;

    private UnBox unBox;
    private Box2DDebugRenderer box2DDebugRenderer;

    public Unbox2DTest(MainGame mainGame) {
        this.context = mainGame;
        this.viewport = new FitViewport(30,10);
        this.viewport.getCamera().translate(-5, 0, 0);

        this.batch = new SpriteBatch();
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.unBox = new UnBox(new World(Vector2.Zero, true));

        GameObject rightGo = new GameObject(unBox);
        GameObject leftGo = new GameObject(unBox);

        rightGo.setName("Right Game Object");
        leftGo.setName("Left Game Object");

        BodyDef bodyDefRightGo = new BodyDef();
        bodyDefRightGo.fixedRotation = true;
        bodyDefRightGo.type = BodyDef.BodyType.DynamicBody;
        bodyDefRightGo.position.set(-5.0f, 0);
        new Box2dBehaviour(bodyDefRightGo, rightGo);

        BodyDef bodyDefLeftGo = new BodyDef();
        bodyDefLeftGo.fixedRotation = true;
        bodyDefLeftGo.type = BodyDef.BodyType.DynamicBody;
        bodyDefLeftGo.position.set(5.0f, 0);
        new Box2dBehaviour(bodyDefLeftGo, leftGo);

        //UI
        new CreateCircleFixtureBehaviour(0.5f, rightGo);
        new CreateCircleFixtureBehaviour(0.5f, leftGo);




        new SoutBehaviour("Right GO", false, rightGo);
        new SoutBehaviour("Left GO", false, leftGo);

        new MoveBehaviour(true, rightGo);
        new MoveBehaviour(false, leftGo);



        System.out.println("Unbox2DTest screen loaded");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f,0f,0f,1f);

        this.unBox.preRender(delta);
        this.viewport.apply();
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);

        this.box2DDebugRenderer.render(this.unBox.getWorld(), this.viewport.getCamera().combined);

        this.batch.begin();
        this.unBox.render(this.batch);
        this.batch.end();

        this.unBox.postRender();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
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

    }
}
