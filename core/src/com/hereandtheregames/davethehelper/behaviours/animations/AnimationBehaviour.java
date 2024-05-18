package com.hereandtheregames.davethehelper.behaviours.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.tommyettinger.textra.utils.StringUtils;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.characters.group.GroupBehaviour;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.util.HashMap;
import java.util.Map;

public class AnimationBehaviour extends BehaviourAdapter {
    private  TextureAtlas
            textureAtlas, textureAtlas2, textureAtlas3, textureAtlas4,//walking
            textureAtlas5, textureAtlas6, textureAtlas7, textureAtlas8, allInOneTextureAtlas;//idle

    public AnimationTypes currentAnimation;

    private Animation<TextureRegion> animation;

    private float elapsedTime = 0f;
    private float frameRate = 1f/10f;
    public TextureRegion currentFrame;
    public CurrentAnimationState currentAnimationState;
    private Map<AnimationTypes, TextureAtlas>textureToAnimationTypeMap = new HashMap<>();

    private CharacterType characterType;

    public AnimationBehaviour(
            GameObject gameObject,
            AnimationTypes currentAnimationType,
            CurrentAnimationState currentAnimationStateParam,
            CharacterType characterTypeParam
            ) {
        super(gameObject);

        this.characterType = characterTypeParam;
        if(this.characterType == CharacterType.Player){
            //walking
            this.textureAtlas = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-walk-up.atlas"));
            this.textureAtlas2 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-walk-right.atlas"));
            this.textureAtlas3 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-walk-down.atlas"));
            this.textureAtlas4 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-walk-left.atlas"));


            //idle
            this.textureAtlas5 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-idle-up.atlas"));
            this.textureAtlas6 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-idle-right.atlas"));
            this.textureAtlas7 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-idle-down.atlas"));
            this.textureAtlas8 = new TextureAtlas(Gdx.files.internal("images/characters/temp/dave/dave-idle-left.atlas"));
        }
        else if(this.characterType == CharacterType.NPC_Standard){
            //walking
            this.textureAtlas = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/walk-up.atlas"));
            this.textureAtlas2 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/walk-right.atlas"));
            this.textureAtlas3 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/walk-down.atlas"));
            this.textureAtlas4 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/walk-left.atlas"));


            //idle
            this.textureAtlas5 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/idle-up.atlas"));
            this.textureAtlas6 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/idle-right.atlas"));
            this.textureAtlas7 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/idle-down.atlas"));
            this.textureAtlas8 = new TextureAtlas(Gdx.files.internal("images/characters/temp/Elvin_Hammond/Animations/idle-left.atlas"));


        }


        this.allInOneTextureAtlas = new TextureAtlas(Gdx.files.internal("images/atlas/allinoneatlas/DaveAnimations.atlas"));



        for(int i = 0; i < this.allInOneTextureAtlas.getRegions().size; i++){
            //idle right

            //idle up

            //idle left

            //idle down

            //walk right

            //walk up

            //walk left

            //walk down
        }
        textureToAnimationTypeMap.put(AnimationTypes.WALK_UP, textureAtlas);
        textureToAnimationTypeMap.put(AnimationTypes.WALK_RIGHT, textureAtlas2);
        textureToAnimationTypeMap.put(AnimationTypes.WALK_DOWN, textureAtlas3);
        textureToAnimationTypeMap.put(AnimationTypes.WALK_LEFT, textureAtlas4);
        textureToAnimationTypeMap.put(AnimationTypes.IDLE_DOWN, textureAtlas5);





        this.animation = new Animation<>(frameRate, this.textureAtlas.getRegions());

        this.currentAnimation = currentAnimationType;
        this.currentAnimationState = currentAnimationStateParam;

    }

    @Override
    public void render(Batch batch) {

        if(this.characterType == CharacterType.Player){
            batch.draw(
                    currentFrame,
                    getGameObject().getBox2dBehaviour().getBody().getPosition().x-0.5f,
                    getGameObject().getBox2dBehaviour().getBody().getPosition().y-0.5f,
                    GameConfig.UNIT_SPRITE_WIDTH,
                    GameConfig.UNIT_SPRITE_HEIGHT);
        }else if(this.characterType == CharacterType.NPC_Standard){
            batch.draw(
                    currentFrame,
                    getGameObject().getBox2dBehaviour().getBody().getPosition().x-0.5f,
                    getGameObject().getBox2dBehaviour().getBody().getPosition().y-0.5f,
                    GameConfig.NPC_SPRITE_WIDTH,
                    GameConfig.NPC_SPRITE_HEIGHT);
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        this.elapsedTime += delta;

        this.currentFrame = this.animation.getKeyFrame(this.elapsedTime, true);

        if(this.currentAnimationState == CurrentAnimationState.Idle){
            switch (currentAnimation){
                case IDLE_UP:
                    this.animation = new Animation<>(frameRate, this.textureAtlas5.getRegions());
                    break;
                case IDLE_DOWN:
                    this.animation = new Animation<>(frameRate, this.textureAtlas7.getRegions());
                    break;
                case IDLE_LEFT:
                    this.animation = new Animation<>(frameRate, this.textureAtlas8.getRegions());
                    break;
                case IDLE_RIGHT:
                    this.animation = new Animation<>(frameRate, this.textureAtlas6.getRegions());
                    break;
            }
        }else if(currentAnimationState == CurrentAnimationState.Walking){
            switch (currentAnimation){
                case WALK_UP:
                    this.animation = new Animation<>(frameRate, this.textureAtlas.getRegions());
                    //this.animation  = new Animation<>(frameRate, textureToAnimationTypeMap.get(AnimationTypes.WALK_UP).getRegions());
                    break;
                case WALK_DOWN:
                    this.animation = new Animation<>(frameRate, this.textureAtlas3.getRegions());
                    break;
                case WALK_LEFT:
                    this.animation = new Animation<>(frameRate, this.textureAtlas4.getRegions());
                    break;
                case WALK_RIGHT:
                    this.animation = new Animation<>(frameRate, this.textureAtlas2.getRegions());
                    break;
            }
        }
    }



}
