package com.hereandtheregames.davethehelper;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hereandtheregames.davethehelper.managers.GameManager;
import com.hereandtheregames.davethehelper.screens.AnimationTest;
import com.hereandtheregames.davethehelper.screens.PlayScreen;
import com.hereandtheregames.davethehelper.screens.TestSplashScreen;
import com.hereandtheregames.davethehelper.screens.Unbox2DTest;
import dev.lyze.gdxUnBox2d.UnBox;

public class MainGame extends Game {
	private World world;
	private SpriteBatch batch;
	private UnBox unBox;

	private GameManager gameManager;


	private Skin skin;
	private BitmapFont font;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		gameManager = new GameManager(this);


//		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("splashscreen/atlas/finalatlas/Dave-the-helper.atlas"));
//		this.skin = new Skin(Gdx.files.internal("splashscreen/atlas/finalatlas/skin.json"), textureAtlas);

		TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("images/atlas/allinoneatlas/skin/skin.atlas"));
		this.skin = new Skin(Gdx.files.internal("images/atlas/allinoneatlas/skin/skin.json"), textureAtlas);

		this.font = new BitmapFont();


		//this.setScreen(new Unbox2DTest(this));
		//this.setScreen(new AnimationTest(this));
		this.setScreen(new PlayScreen(this));
		//this.setScreen(new TestSplashScreen(this));


	}

	@Override
	public void dispose () {
		this.batch.dispose();
		//GameManager.instance.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}



	public UnBox getUnBox() {
		return unBox;
	}

	public void setUnBox(UnBox unBox) {
		this.unBox = unBox;
	}

	public Skin getSkin() {
		return skin;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public BitmapFont getFont() {
		return font;
	}
}
