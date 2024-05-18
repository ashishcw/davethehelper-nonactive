package com.hereandtheregames.davethehelper.behaviours.characters.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.TimeUtils;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.animations.FacingDirection;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterCore;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.world.ActiveCustomerQueueBehaviour;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NPCDefaultBehaviour extends CharacterCore {
    //private float movementSpeed = 3f;
    public Body body;

    private boolean customerConverted = false;

    public NPCDefaultBehaviour(GameObject gameObject, CharacterType characterTypeParam) {
        super(gameObject, characterTypeParam);
        this.characterType = characterTypeParam;
    }

    @Override
    public void start() {
        body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        body.setTransform(
                body.getTransform().getPosition().x,
                body.getTransform().getPosition().y,
                0);
        body.setLinearVelocity(this.movementSpeed, 0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

//        if (this.body.getTransform().getPosition().x > GameConfig.WORLD_WIDTH_IN_TILES){
//            //getGameObject().destroy();
//        }

        if(this.body != null){
            if(this.body.getLinearVelocity().isZero()){
                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimationState = CurrentAnimationState.Idle;
            }else {
                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimationState = CurrentAnimationState.Walking;
            }
        }

        switch (this.currentState){
            case Wandering:
                break;

            case Waiting_in_Queue:
                break;

            case Waiting_for_an_order_sitting:
                break;

            case Waiting_for_an_order_standing:
                this.elapsedTime = TimeUtils.timeSinceMillis(this.startTime);
                if(this.elapsedTime > this.waitTime){
                    this.currentState = CurrentState.Exiting_Queue;
                }
                break;

            case Order_Complete:
                break;

            case Exiting_Queue:
                this.exiting_Queue();
                break;
        }



    }

    private void npcWander(){
        if(body != null){
            if(this.movementSpeed > 0){
                this.movementSpeed = -this.movementSpeed;
                //movementSpeed = -MathUtils.random(1f, 4f);
                if (this.getGameObject().getBehaviour(AnimationBehaviour.class) != null) {
                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_LEFT;
                }
            }else {
                //movementSpeed = MathUtils.random(1f, 4f);
                this.movementSpeed = 2.5f;
                if (this.getGameObject().getBehaviour(AnimationBehaviour.class) != null) {
                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_RIGHT;
                }
            }
            body.setLinearVelocity(this.movementSpeed, 0f);
            this.currentState = CurrentState.Wandering;
            this.characterType = CharacterType.NPC_Standard;
        }
    }

    //customer
    private void waiting_for_an_Order_Standing(){
        if(body != null){
            this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.IDLE_UP;
            this.facingDirection = FacingDirection.North;
            this.movementSpeed = 0;
            body.setLinearVelocity(0f, this.movementSpeed);
        }
        this.startTime = TimeUtils.millis();
        this.elapsedTime = TimeUtils.timeSinceMillis(startTime);
        this.currentState = CurrentState.Waiting_for_an_order_standing;
        this.customerConverted = true;
    }

    private void waiting_in_queue(){
        if(body != null){
            this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_UP;
            this.facingDirection = FacingDirection.North;
            body.setLinearVelocity(0f, movementSpeed);
            this.characterType = CharacterType.NPC_Queue_Customer;
            this.currentState = CurrentState.Waiting_in_Queue;
        }
    }

    private void exiting_Queue(){
        if(body != null){
            this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_DOWN;
            this.facingDirection = FacingDirection.South;
            this.movementSpeed = 3;
            body.setLinearVelocity(0f, -movementSpeed);
            this.currentState = CurrentState.Exiting_Queue;
        }
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        super.onCollisionEnter(other, contact);

        if(
                (Objects.equals(other.getGameObject().getName(), "NPC-SpawnPoint-1")
                ||
                Objects.equals(other.getGameObject().getName(), "NPC-SpawnPoint-2"))
        ){
            this.npcWander();
//            this.movementSpeed = 2.5f;
//            if (this.getGameObject().getBehaviour(AnimationBehaviour.class) != null) {
//                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_RIGHT;
//            }


            if(Objects.equals(other.getGameObject().getName(), "NPC-SpawnPoint-1")){
                this.customerConverted = false;
            }

        }

        System.out.println(this.getGameObject().getName()+" is colliding with : " + other.getGameObject().getName());









    }

    @Override
    public void onCollisionExit(Behaviour other, Contact contact) {
        //super.onCollisionExit(other, contact);

//        if
//        (
//                Objects.equals(other.getGameObject().getName(), "NPC-Customer-Conversion-Collider")
//                &&
//                this.characterType == CharacterType.NPC_Queue_Customer
//                &&
//                this.currentState == CurrentState.Exiting_Queue
//        ){
//            this.characterType = CharacterType.NPC_Standard;
//            this.npcWander();
//        }


        if
        (
                Objects.equals(other.getGameObject().getName(), "NPC-Customer-Conversion-Collider-EXIT")
                        &&
                        this.characterType == CharacterType.NPC_Queue_Customer
                        &&
                        this.currentState == CurrentState.Exiting_Queue
                        &&
                        this.customerConverted
        ){
            //this.characterType = CharacterType.NPC_Standard;
            this.npcWander();
            //ActiveCustomerQueueBehaviour.totalQueueCustomers--;
        }

        if
        (
                Objects.equals(other.getGameObject().getName(), "Queue-PlaceHolder-1")
                        &&
                        this.characterType == CharacterType.NPC_Queue_Customer
                        &&
                        this.currentState == CurrentState.Exiting_Queue
                        &&
                        this.customerConverted
        ){

            this.npcWander();
            //ActiveCustomerQueueBehaviour.REPOSITIONING_QUEUE();
        }
    }

    @Override
    public void onCollisionStay(Behaviour other) {
        //super.onCollisionStay(other);
        //convert npc to customer on collision
        if(
                Objects.equals(other.getGameObject().getName(), "NPC-Customer-Conversion-Collider")
                        &&
                        this.characterType == CharacterType.NPC_Standard
                        &&
                        !this.customerConverted
        )
        {
            if(MathUtils.random(1, 5) > 3){
                if(ActiveCustomerQueueBehaviour.totalQueueCustomers < ActiveCustomerQueueBehaviour.totalCustomersQueueTarget){
                    this.waiting_in_queue();
                }
            }



        }
        //System.out.println(this.getGameObject().getName()+" is IN COLLISION with : " + other.getGameObject().getName());


        if(
                Objects.equals(other.getGameObject().getName(), "Queue-PlaceHolder-"+ActiveCustomerQueueBehaviour.totalQueueCustomers)
                &&
                this.characterType == CharacterType.NPC_Queue_Customer
                &&
                this.currentState == CurrentState.Waiting_in_Queue
        ){


            //this.waitTime = this.waitTime * ActiveCustomerQueueBehaviour.totalQueueCustomers;
            waiting_for_an_Order_Standing();
        }
    }
}
