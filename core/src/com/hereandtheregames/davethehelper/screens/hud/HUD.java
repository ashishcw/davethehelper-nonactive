package com.hereandtheregames.davethehelper.screens.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TextraLabel;
import com.github.tommyettinger.textra.TypingLabel;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.utils.GameConfig;

public class HUD {
    private Stage stage;
    //private FitViewport stageViewport;
    private ScreenViewport stageViewport;

    private MainGame context;

    public static TextraLabel textraTomatoesLable,
            textraPotatoesLable,
            textraCornsLable,
            textraWaterLable;

    public HUD(SpriteBatch spriteBatch, MainGame mainGame, float worldWidth, float worldHeight) {
        //this.stageViewport = new FitViewport(1000,1000);
        //this.stageViewport = new FitViewport(worldWidth,worldHeight);
        //this.stageViewport = new FitViewport(1920,1080);
        this.stageViewport = new ScreenViewport();
        this.stage = new Stage(this.stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor
        this.context = mainGame;


        this.createUI();
    }

    private void createUI(){
        Table root = new Table();
        root.setFillParent(true);

//        String text = "[GREEN]Hello,{WAIT} world!\n"
//                + "[ORANGE]{SLOWER} Did you know \norange is my \nfavorite color?";



        String playerHoldingCount = "[BLUE]Tomatoes : [RED]0";
        textraTomatoesLable = new TextraLabel(playerHoldingCount, this.context.getSkin());
        root.add(textraTomatoesLable);
//        root.padTop(-800f); //orignal
//        root.padLeft(100f); //orignal

        //root.padTop(-200f);
        root.padLeft(-350f);

        root.row();

        playerHoldingCount = "[YELLOW]Potatoes : [RED]0";
        textraPotatoesLable = new TextraLabel(playerHoldingCount, this.context.getSkin());
        root.add(textraPotatoesLable);

        root.row();

        playerHoldingCount = "[WHITE]Corn : [RED]0";
        textraCornsLable = new TextraLabel(playerHoldingCount, this.context.getSkin());
        root.add(textraCornsLable);

        this.stage.addActor(root);

    }



    public Stage getStage() { return stage; }

    public void dispose(){
        stage.dispose();
    }
}
