package com.hereandtheregames.davethehelper.behaviours.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AnimationBehaviour2 extends BehaviourAdapter {
    private AnimationTypes animationTypes;
    private GameObject gameObject;


    private float frameRate = 1f/10f;
    private String current;
    private float timer = 0f;
    private boolean looping = true;
    private final HashMap<String, Animation<TextureRegion>> animations = new HashMap<>();

    //constructor
    //1. take idle animation state as input
    //2. take game object/entity which needs to be performed animation on
    //3. assign the index value based on the animation type selection
    public AnimationBehaviour2(GameObject gameObject, AnimationTypes animationType) {
        super(gameObject);
        this.setAnimationTypes(animationType);
        this.gameObject = gameObject;

        TextureAtlas textureAtlasAllInOne = new TextureAtlas(Gdx.files.internal("images/atlas/allinoneatlas/DaveAnimations.atlas"));

        Array<TextureAtlas.AtlasRegion> allRegions = textureAtlasAllInOne.getRegions();

        this.add(AnimationTypes.IDLE_RIGHT.toString(), new Animation<>(frameRate, textureAtlasAllInOne.findRegion("Idle")));


        System.out.println("Animations Loaded");
    }

    public AnimationTypes getAnimationTypes() {
        return animationTypes;
    }

    public void setAnimationTypes(AnimationTypes animationTypes) {
        this.animationTypes = animationTypes;
    }

    public void add(String name, Animation<TextureRegion> animationToAdd){
        this.animations.put(name, animationToAdd);
    }


    public void setCurrent(String name){
        if(Objects.equals(this.current, name)){
            return;
        }
        this.current = name;
        this.timer = 0f;
        this.looping = true;
    }

    public void setCurrent(String name, boolean loopingInput){
        setCurrent(name);
        this.looping = loopingInput;
    }

    public void setAnimationDuration(long duration){
        this.animations.get(current).setFrameDuration(duration/((float)this.animations.get(current).getKeyFrames().length * 1000));
    }

    public boolean isCurrent(String name){
        return current.equals(name);
    }
    public boolean isFinished(){
        return animations.get(current).isAnimationFinished(timer);
    }
    public int frameIndex(){
        return animations.get(current).getKeyFrameIndex(timer);
    }

    public TextureRegion getFrame(){
        timer += Gdx.graphics.getDeltaTime();
        return animations.get(current).getKeyFrame(timer, looping);
    }
}
