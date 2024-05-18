package com.hereandtheregames.davethehelper.behaviours.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.animations.FacingDirection;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterCore;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;

import java.util.Objects;

public class NPCDefaultBehaviourTest extends CharacterCore {
    //private float movementSpeed = 3f;
    public boolean isCustomerServed = false;
    public Body body;

    private boolean customerConverted = false;

    private Vector2 targetDiff = Vector2.Zero;
    private float distanceThreashold = 0.5f;
    private float targetRadius = 0.2f;
    private float distance = targetDiff.len();

    public NPCDefaultBehaviourTest(GameObject gameObject, CharacterType characterTypeParam) {
        super(gameObject, characterTypeParam);
        this.characterType = characterTypeParam;
        this.facingDirection = FacingDirection.East;
    }

    public NPCDefaultBehaviourTest(GameObject gameObject, CharacterType characterTypeParam, float movementSpeedParam) {
        super(gameObject, characterTypeParam);
        this.characterType = characterTypeParam;
        this.facingDirection = FacingDirection.East;
        this.movementSpeed = movementSpeedParam;
    }

    @Override
    public void start() {
        //this.movementSpeed = MathUtils.random(1f, 5f);
        this.movementSpeed = 3.5f;
        body = getGameObject().getBehaviour(Box2dBehaviour.class).getBody();
        body.setTransform(
                body.getTransform().getPosition().x,
                body.getTransform().getPosition().y,
                0);
        body.setLinearVelocity(this.movementSpeed, 0);

        this.targetReached = false;

        //this.targetPosition = new Vector2(16.5f,14.5f);
        this.targetPosition = new Vector2(30f,0f);

        //Vector2 targetDiff = this.body.getPosition().sub(this.targetPosition);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(this.body != null){
            if(this.body.getLinearVelocity().isZero()){
                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimationState = CurrentAnimationState.Idle;
            }else {
                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimationState = CurrentAnimationState.Walking;
            }
        }

        //TODO: Delete below method, for testing purpose only
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
            GameObject firstNPC = ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.first();

            firstNPC.getBehaviour(NPCDefaultBehaviourTest.class).exiting_Queue(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(3));
            //this.exiting_Queue(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(3));
        }

        if(!this.targetReached){
            if(!this.targetPosition.isZero()){
                if(this.body != null){
                    targetDiff = this.body.getPosition().sub(this.targetPosition);
                    distanceThreashold = 0.5f;
                    targetRadius = 0.2f;
                    distance = targetDiff.len();

                    //System.out.println(targetDiff);

                    if (distance < distanceThreashold) {
                        //don't do anything target reached
                        this.targetReached = true;
                        this.targetPosition = Vector2.Zero;
                        this.body.setLinearVelocity(0f, 0f);

                        switch (this.facingDirection){
                            case North:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.IDLE_UP;
                                }
                                break;
                            case South:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.IDLE_DOWN;
                                }
                                break;
                            case East:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.IDLE_RIGHT;
                                }
                                break;
                            case West:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.IDLE_LEFT;
                                }
                                break;
                        }
                    }
                    else {

//                        if(targetDiff.x < -0.2f){
//                            moveRight(targetDiff.x, -0.2f);
//                        }else if(targetDiff.x > 0.2f){
//                            moveLeft(targetDiff.x, 0.2f);
//                        }else if(targetDiff.y < 0.2f){
//                            moveUp(targetDiff.y, -0.2f);
//                        }else if(targetDiff.y > 0){
//                            moveDown(targetDiff.y, 0.2f);
//                        }


                        if(targetDiff.x < -targetRadius){
                            moveRight(targetDiff.x, -targetRadius);
                        }else if(targetDiff.x > targetRadius){
                            moveLeft(targetDiff.x, targetRadius);
                        }else if(targetDiff.y < targetRadius){
                            moveUp(targetDiff.y, -targetRadius);
                        }else if(targetDiff.y > 0){
                            moveDown(targetDiff.y, targetRadius);
                        }


                        switch (this.facingDirection){
                            case North:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_UP;
                                }
                                break;
                            case South:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_DOWN;
                                }
                                break;
                            case East:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_RIGHT;
                                }
                                break;
                            case West:
                                if(body != null){
                                    this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_LEFT;
                                }
                                break;
                        }


                    }
                }

            }
        }

        switch (this.currentState){
            case Wandering:
                break;

            case Waiting_in_Queue:
                //timer logic goes here
                //this.waiting_In_Queue_Timer();
                break;

            case Waiting_for_an_order_sitting:
                break;

            case Waiting_for_an_order_standing:
//                this.elapsedTime = TimeUtils.timeSinceMillis(this.startTime);
//                if(this.elapsedTime > this.waitTime){
//                    this.currentState = CurrentState.Exiting_Queue;
//                }
                break;

            case Order_Complete:
                break;

            case Exiting_Queue:
                //this.exiting_Queue();
                if(!this.isCustomerServed){
                    //this.exiting_Queue(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(3));
                }
                //TODO: Write else block as well
                break;
        }



    }

    private void moveRight(float xAxis, float diff){
        if(xAxis < diff){
            //this.movementSpeed = 3;
            this.body.setLinearVelocity(this.movementSpeed, 0f);
        }else {
            this.body.setLinearVelocity(0f, 0f);
        }
    }

    private void moveLeft(float xAxis, float diff){
        if(xAxis > diff){
            //this.movementSpeed = -3;
            this.body.setLinearVelocity(-this.movementSpeed, 0f);
        }else {
            this.body.setLinearVelocity(0f, 0f);
        }
    }

    private void moveUp(float yAxis, float diff){
        if(yAxis < diff){
            //this.movementSpeed = 3;
            this.body.setLinearVelocity(0f, this.movementSpeed);
        }else {
            this.body.setLinearVelocity(0f, 0f);
        }
    }

    private void moveDown(float yAxis, float diff){
        if(yAxis > diff){
            //this.movementSpeed = -3;
            this.body.setLinearVelocity(0f, -this.movementSpeed);
        }else {
            this.body.setLinearVelocity(0f, 0f);
        }
    }

    private void npcWander(Vector2 targetPositionQueueSpot){
        this.targetPosition = targetPositionQueueSpot;
        this.targetReached = false;
        //this.movementSpeed = MathUtils.random(1.5f, 3f);
        this.movementSpeed = 3f;
        this.characterType = CharacterType.NPC_Standard;
    }

    //customer
    private void waiting_for_an_Order_Standing(){

        this.startTime = TimeUtils.millis();
        this.elapsedTime = TimeUtils.timeSinceMillis(startTime);
        this.currentState = CurrentState.Waiting_for_an_order_standing;
        this.customerConverted = true;
    }

    private void entering_in_waiting_queue(Vector2 targetPositionQueueSpot){
        //this.targetPosition = new Vector2(this.body.getPosition().x,14.5f);
        if(!targetPositionQueueSpot.isZero()){
            this.targetPosition = targetPositionQueueSpot;
            if(body != null){
                //this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_UP;
                this.facingDirection = FacingDirection.North;
                body.setLinearVelocity(0f, this.movementSpeed);
                this.characterType = CharacterType.NPC_Queue_Customer;
                this.currentState = CurrentState.Waiting_in_Queue;
            }
        }else {
            System.out.println("Target is zero");
        }

    }
    private void waiting_In_Queue_Timer(){
        this.elapsedTime = TimeUtils.timeSinceMillis(this.startTime);
        if(this.elapsedTime > this.waitTime){
            this.currentState = CurrentState.Exiting_Queue;
            this.customerConverted = true;
        }

    }

    private void exiting_Queue(Vector2 targetPositionQueueSpot){
//        if(body != null){
//            this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_DOWN;
//            this.facingDirection = FacingDirection.South;
//            this.movementSpeed = 3;
//            body.setLinearVelocity(0f, -movementSpeed);
//            this.currentState = CurrentState.Exiting_Queue;
//        }

        if(!targetPositionQueueSpot.isZero()){
            this.targetPosition = targetPositionQueueSpot;
            if(body != null){
                this.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_DOWN;
                this.facingDirection = FacingDirection.South;
                body.setLinearVelocity(0f, -3f);
                this.characterType = CharacterType.NPC_Queue_Customer;
                this.currentState = CurrentState.Wandering;
                this.customerConverted = true;
            }
        }else {
            System.out.println("Target is zero");
        }

    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        super.onCollisionEnter(other, contact);

        if(
                Objects.equals(other.getGameObject().getName(), "NPC-SpawnPoint-1")
        ){
            this.npcWander(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(2));
            this.facingDirection = FacingDirection.East;
            this.customerConverted = false;
        }


        if(Objects.equals(other.getGameObject().getName(), "NPC-SpawnPoint-2")){
            this.npcWander(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(1));
            this.facingDirection = FacingDirection.West;
        }

        //System.out.println(this.getGameObject().getName()+" is colliding with : " + other.getGameObject().getName());


    }

    @Override
    public void onCollisionExit(Behaviour other, Contact contact) {
        if
        (
                Objects.equals(other.getGameObject().getName(), "NPC-Customer-Conversion-Collider-EXIT")
                        &&
                        this.characterType == CharacterType.NPC_Queue_Customer
                        &&
                        this.customerConverted
        ){

            //ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.removeValue(this.getGameObject(), false);
            //ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.removeFirst();
            ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.removeLast();
            //ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.removeIndex(0);



            this.npcWander(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(1));
            this.facingDirection = FacingDirection.West;
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
            Vector2 target = Vector2.Zero;
            if(ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.size >= ActiveCustomerQueueBehaviourTest.totalCustomersQueueTargetThreashold){
                return;
            }
            if(ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.size <= 0){

                ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.addLast(this.getGameObject());
                target = ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.get(1);
                //this.entering_in_waiting_queue(target);
            }else {
                int counter = ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.size+1;
                ActiveCustomerQueueBehaviourTest.activeOrderWaitingQueueQUEUE.addLast(this.getGameObject());
                target = ActiveCustomerQueueBehaviourTest.allQueuePlaceHoldersFINALHOLDING.get(counter);
            }
            this.entering_in_waiting_queue(target);
            this.startTime = TimeUtils.millis(); //TODO: To remove the logic for start time from here, and add it when customer reaches the location
        }


        if(
                Objects.equals(other.getGameObject().getName(), "NPC-Customer-Conversion-Collider")
                &&
                this.characterType == CharacterType.NPC_Queue_Customer
                &&
                this.customerConverted
        )
        {
            Vector2 target = Vector2.Zero;
            this.npcWander(ActiveCustomerQueueBehaviourTest.allQueueNPCSPAWNINGLOCATIONS.get(1));
            this.facingDirection = FacingDirection.West;
        }
    }
}
