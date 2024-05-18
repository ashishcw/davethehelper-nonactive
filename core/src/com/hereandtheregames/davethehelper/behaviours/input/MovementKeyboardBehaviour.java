package com.hereandtheregames.davethehelper.behaviours.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.util.Objects;

public class MovementKeyboardBehaviour extends BehaviourAdapter {
    public float ACCELERATION = 50f;
    public float MAX_SPEED = 3f;
    public float ROTATION_ANGLE = 15f;
    public Vector2 velocity = new Vector2();
    public Vector2 movementVector = new Vector2();
    float timeStep;
    private AnimationBehaviour animationBehaviour;
    public MovementKeyboardBehaviour(GameObject gameObject, MainGame context) {
        super(gameObject);

        this.timeStep = context.getUnBox().getOptions().getTimeStep();

        //this.animationBehaviour = new AnimationBehaviour(gameObject, context, AnimationTypes.IDLE_DOWN, CurrentAnimationState.Idle);
        this.animationBehaviour = this.getGameObject().getBehaviour(AnimationBehaviour.class);

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

        Body body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
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
        //this.animationBehaviour.currentAnimation = AnimationTypes.IDLE_DOWN;
        //this.animationBehaviour.currentAnimationState = CurrentAnimationState.Walking;

        movementVector.setLength(MathUtils.clamp(movementVector.len(), 0, 1));

        if(movementVector.isZero()){
            this.animationBehaviour.currentAnimation = AnimationTypes.IDLE_DOWN;
            this.animationBehaviour.currentAnimationState = CurrentAnimationState.Idle;
        }else {
            this.animationBehaviour.currentAnimationState = CurrentAnimationState.Walking;
        }
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        super.onCollisionEnter(other, contact);

        System.out.println("Player is colliding with : " + other.getGameObject().getName() + " Game Object");

    }
}
