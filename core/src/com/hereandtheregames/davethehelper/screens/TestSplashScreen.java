package com.hereandtheregames.davethehelper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import dev.lyze.gdxUnBox2d.UnBox;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class TestSplashScreen extends ScreenAdapter {
    private MainGame mainGame;


    //viewport
    private ExtendViewport mainMenuViewport;

    //spritebatch
    private SpriteBatch spriteBatch;

    //stage
    private Stage stage;

    //unbox
    private UnBox unBox;

    private long startTime;
    private long waitTargetTime = 10000;

    public TestSplashScreen(MainGame mainGameContext) {
        this.mainGame = mainGameContext;
        this.init();
    }

    private void init(){
        //spritebatch
        if(this.spriteBatch == null){
            this.spriteBatch = new SpriteBatch();
        }

        //viewport
        if(this.mainMenuViewport == null){
            this.mainMenuViewport = new ExtendViewport(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
        }

        //stage
        if(this.stage == null){
            this.stage = new Stage(this.mainMenuViewport, this.spriteBatch);
        }


        //unbox
        if(this.unBox == null){
            this.unBox = new UnBox();
        }
    }

    @Override
    public void show() {
        super.show();

        this.createUI();
        System.out.println("Splash Screen Loaded");

        startTime = TimeUtils.millis();

    }

    private void createUI() {
        Table root = new Table();
        root.setFillParent(true);
        this.stage.addActor(root);

        //title
        root.pad(10f);
        Image image = new Image(this.mainGame.getSkin(), "gametitle");
        image.setScaling(Scaling.fill);
        root.add(image);
        image.addAction(sequence(alpha(0),delay(2), fadeIn(1)));


        //press any key subtitle
        //root.row();
        image = new Image(this.mainGame.getSkin(), "poweredbytitle");
        image.setScaling(Scaling.fit);
        root.add(image);
        image.addAction(sequence(alpha(0), delay(5), fadeIn(1)));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        ScreenUtils.clear(Color.BLACK);

        this.mainMenuViewport.apply();

        //unbox
        this.unBox.preRender(delta);

        this.spriteBatch.begin();
        this.spriteBatch.setProjectionMatrix(this.mainMenuViewport.getCamera().combined);
        this.unBox.render(this.stage.getBatch());
        this.spriteBatch.end();

        this.unBox.postRender();

        //stage
        this.stage.act(delta);
        this.stage.draw();

//        if(TimeUtils.timeSinceMillis(startTime) > 10000){
//            this.mainGame.setScreen(new PlayScreen(this.mainGame));
//        }
        if(this.mainGame.getGameManager().getAssetManager().isFinished() && TimeUtils.timeSinceMillis(startTime) > waitTargetTime){
            System.out.println("Loading Game Screen");
            this.mainGame.setScreen(new PlayScreen(this.mainGame));
        }


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.mainMenuViewport.update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
