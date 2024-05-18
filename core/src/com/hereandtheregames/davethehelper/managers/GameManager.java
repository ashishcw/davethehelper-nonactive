package com.hereandtheregames.davethehelper.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hereandtheregames.davethehelper.MainGame;
import dev.lyze.gdxUnBox2d.UnBox;
import jdk.tools.jmod.Main;

import java.awt.*;
import java.util.Optional;

public class GameManager implements Disposable {


    private AssetManager assetManager;
    private MainGame context;
    private Box2DDebugRenderer box2DDebugRenderer;





    public GameManager(MainGame context) {
        this.assetManager = new AssetManager();
        assetManager.load("images/actors.pack", TextureAtlas.class);

        assetManager.finishLoading();

        //main game
        this.context = context;

        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void dispose() {
        this.assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public MainGame getContext() {
        return context;
    }

    public Box2DDebugRenderer getBox2DDebugRenderer() {
        return box2DDebugRenderer;
    }
}
