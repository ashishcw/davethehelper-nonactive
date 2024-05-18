package com.hereandtheregames.davethehelper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hereandtheregames.davethehelper.MainGame;

public class AnimationTest extends ScreenAdapter {
    MainGame context;
    SpriteBatch batch;
    TextureAtlas textureAtlas, textureAtlas2, textureAtlas3, textureAtlas4,
            textureAtlas5, textureAtlas6, textureAtlas7, textureAtlas8;
    Animation<TextureRegion> animation, animation2, animation3, animation4,
            animation5, animation6, animation7, animation8;
    float elapsedTime = 0f;
    float frameRate = 1f/08f;

    OrthographicCamera camera;


    public AnimationTest(MainGame context) {

        this.context = context;
        this.batch = this.context.getBatch();

        //idle animation
        this.textureAtlas = new TextureAtlas(Gdx.files.internal("images/atlas/dave-idle-up.atlas"));
        this.textureAtlas2 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-idle-right.atlas"));
        this.textureAtlas3 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-idle-down.atlas"));
        this.textureAtlas4 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-idle-left.atlas"));

        this.animation = new Animation<>(frameRate, this.textureAtlas.getRegions());
        this.animation2 = new Animation<>(frameRate, this.textureAtlas2.getRegions());
        this.animation3 = new Animation<>(frameRate, this.textureAtlas3.getRegions());
        this.animation4 = new Animation<>(frameRate, this.textureAtlas4.getRegions());


        //Walk animation
        this.textureAtlas5 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-walk-up.atlas"));
        this.textureAtlas6 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-walk-right.atlas"));
        this.textureAtlas7 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-walk-down.atlas"));
        this.textureAtlas8 = new TextureAtlas(Gdx.files.internal("images/atlas/dave-walk-left.atlas"));

        this.animation5 = new Animation<>(frameRate, this.textureAtlas5.getRegions());
        this.animation6 = new Animation<>(frameRate, this.textureAtlas6.getRegions());
        this.animation7 = new Animation<>(frameRate, this.textureAtlas7.getRegions());
        this.animation8 = new Animation<>(frameRate, this.textureAtlas8.getRegions());



        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 0.5f, 0.5f);


    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            this.animation = new Animation<>(frameRate, this.textureAtlas2.getRegions());
        }else if(Gdx.input.isKeyPressed(Input.Keys.B)){
            this.animation = new Animation<>(frameRate, this.textureAtlas.getRegions());
        }
        this.elapsedTime += delta;
        //idle animation
        TextureRegion currentFrame = this.animation.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame2 = this.animation2.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame3 = this.animation3.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame4 = this.animation4.getKeyFrame(this.elapsedTime, true);

        //walk animation
        TextureRegion currentFrame5 = this.animation5.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame6 = this.animation6.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame7 = this.animation7.getKeyFrame(this.elapsedTime, true);
//        TextureRegion currentFrame8 = this.animation8.getKeyFrame(this.elapsedTime, true);

        ScreenUtils.clear(Color.GRAY);
        this.batch.begin();

        //idle animation
//        this.batch.draw(currentFrame,120f,100f);
//        this.batch.draw(currentFrame2,150f,100f);
//        this.batch.draw(currentFrame3,180f,100f);
//        this.batch.draw(currentFrame4,210f,100f);

        //walk animation
//        this.batch.draw(currentFrame5,120f,50f);
//        this.batch.draw(currentFrame6,150f,50f);
//        this.batch.draw(currentFrame7,180f,50f);
//        this.batch.draw(currentFrame8,210f,50f);

        this.batch.draw(currentFrame,50f,100f);
        this.batch.draw(currentFrame5,70f,100f);
//        this.batch.draw(currentFrame2,100f,100f);
//        this.batch.draw(currentFrame6,120f,100f);
//        this.batch.draw(currentFrame3,150f,100f);
//        this.batch.draw(currentFrame7,170f,100f);
//        this.batch.draw(currentFrame4,200f,100f);
//        this.batch.draw(currentFrame8,220f,100f);

        this.batch.setTransformMatrix(camera.combined);
        this.batch.end();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width*2, height*2);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.batch.dispose();
        this.textureAtlas.dispose();
        this.context.dispose();
    }
}
