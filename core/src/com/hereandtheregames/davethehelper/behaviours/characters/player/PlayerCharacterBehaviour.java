package com.hereandtheregames.davethehelper.behaviours.characters.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterCore;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.input.MovementKeyboardBehaviour;
import com.hereandtheregames.davethehelper.behaviours.world.ActiveWorldManagerBehaviour;
import com.hereandtheregames.davethehelper.pathfinding.node.Node;
import com.hereandtheregames.davethehelper.screens.PlayScreen;
import com.hereandtheregames.davethehelper.screens.hud.HUD;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.awt.*;

public class PlayerCharacterBehaviour extends BehaviourAdapter {

    MainGame context;

    public float ACCELERATION = 50f;
    public float MAX_SPEED = 3f;
    public float ROTATION_ANGLE = 15f;
    public Vector2 velocity = new Vector2();
    public Vector2 movementVector = new Vector2();
    float timeStep;
    private AnimationBehaviour animationBehaviour;

    private ActiveWorldManagerBehaviour activeWorldManagerBehaviour;
    private Body body;

    public PlayerCharacterBehaviour(GameObject gameObject, MainGame contextParam) {
        super(gameObject);

        this.context = contextParam;

        this.timeStep = context.getUnBox().getOptions().getTimeStep();

        //this.animationBehaviour = new AnimationBehaviour(gameObject, context, AnimationTypes.IDLE_DOWN, CurrentAnimationState.Idle);
        this.animationBehaviour = this.getGameObject().getBehaviour(AnimationBehaviour.class);

        this.activeWorldManagerBehaviour = new ActiveWorldManagerBehaviour(this.getGameObject());

    }

    @Override
    public void fixedUpdate() {
        super.fixedUpdate();
        if (!movementVector.isZero()) {
            velocity.add(movementVector.x * ACCELERATION * timeStep, movementVector.y * ACCELERATION * timeStep);
            velocity.setLength(MathUtils.clamp(velocity.len(), 0, MAX_SPEED));
        } else {
            velocity.setLength(MathUtils.clamp(velocity.len() - ACCELERATION * timeStep, 0, MAX_SPEED));
        }

        if(this.body == null){
            body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        }
        body.setLinearVelocity(velocity);



    }

    @Override
    public void update(float delta) {
        super.update(delta);
        movementVector.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            movementVector.y += 1;
            this.animationBehaviour.currentAnimation = AnimationTypes.WALK_UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            movementVector.y -= 1;
            this.animationBehaviour.currentAnimation = AnimationTypes.WALK_DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            movementVector.x += 1;
            this.animationBehaviour.currentAnimation = AnimationTypes.WALK_RIGHT;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            movementVector.x -= 1;
            this.animationBehaviour.currentAnimation = AnimationTypes.WALK_LEFT;
        }

        movementVector.setLength(MathUtils.clamp(movementVector.len(), 0, 1));

        if(movementVector.isZero()){
            this.animationBehaviour.currentAnimation = AnimationTypes.IDLE_DOWN;
            this.animationBehaviour.currentAnimationState = CurrentAnimationState.Idle;
        }else {
            this.animationBehaviour.currentAnimationState = CurrentAnimationState.Walking;
        }

//        boolean mouseLeftDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
//        int mouseX = Gdx.input.getX();
//        int mouseY = Gdx.input.getY();


//        boolean mouseLeftDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
//
//        if(mouseLeftDown){
//            int mouseX = (int) (Gdx.input.getX()/ GameConfig.UNIT_SCALE);
//            int mouseY = (int) (Gdx.input.getY()/GameConfig.UNIT_SCALE);
//            System.out.println("Mouse X : " + mouseX);
//            System.out.println("Mouse Y : " + (mouseY));
//            //16.7052,5.806945
//            //this.body.setTransform(new Vector2(16.7f, 5.8f), this.body.getAngle());
//            this.body.setTransform(new Vector2(mouseX, mouseY), this.body.getAngle());
//            System.out.println("Player position : " + this.getGameObject().getBox2dBehaviour().getBody().getPosition());
//
//
//        }

    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        super.onCollisionEnter(other, contact);

        System.out.println("Player is colliding with : " + other.getGameObject().getName());

    }

    @Override
    public void onCollisionStay(Behaviour other) {
        super.onCollisionStay(other);



        if (Gdx.input.isKeyPressed(Input.Keys.E)){

            //farm fields interaction
            if(this.activeWorldManagerBehaviour != null){
                if(other.getGameObject().getName().contains("Field") || other.getGameObject().getName().contains("Dump")){
                    this.activeWorldManagerBehaviour.interactibleCollidersLogic(other.getGameObject());
                    HUD.textraTomatoesLable.setText("[BLUE]Tomatoes : [RED]" + this.activeWorldManagerBehaviour.tomatoesCount);
                    HUD.textraPotatoesLable.setText("[YELLOW]Potatoes : [RED]" + this.activeWorldManagerBehaviour.potatoesCount);
                    HUD.textraCornsLable.setText("[WHITE]Corn : [RED]" + this.activeWorldManagerBehaviour.cornCount);
                }

            }
        }
    }
}
