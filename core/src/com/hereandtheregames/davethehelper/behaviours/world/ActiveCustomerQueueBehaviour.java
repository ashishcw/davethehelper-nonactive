package com.hereandtheregames.davethehelper.behaviours.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.FacingDirection;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterCore;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.characters.npc.NPCDefaultBehaviour;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.util.HashMap;
import java.util.Map;

public class ActiveCustomerQueueBehaviour extends BehaviourAdapter {
    //public static Map<GameObject, Boolean> activeCustomersInQueue = new HashMap<>();
    public static Map<GameObject, Integer> activeCustomersInQueue = new HashMap<>();
    public static GameObject queuePlaceHolder;

    public static int totalQueueCustomers = 0, totalCustomersQueueTarget = 5;

    public ActiveCustomerQueueBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        //super.onCollisionEnter(other, contact);



    }

    @Override
    public void onCollisionExit(Behaviour other, Contact contact) {
        //super.onCollisionExit(other, contact);
//        if(other.getGameObject().getBehaviour(NPCDefaultBehaviour.class).characterType == CharacterType.NPC_Queue_Customer){
//            System.out.println("ACQ : " + other.getGameObject().getName());
//            activeCustomersInQueue.put(other.getGameObject(), totalQueueCustomers);
//            totalQueueCustomers++;
//        }
    }

    public static void REPOSITIONING_QUEUE(){
        activeCustomersInQueue.values().remove(1);
        for (GameObject gameObj : activeCustomersInQueue.keySet()) {
            int updatedPosition = activeCustomersInQueue.get(gameObj);
            updatedPosition--;
            activeCustomersInQueue.put(gameObj, updatedPosition);
            NPCDefaultBehaviour npcDefaultBehaviour = gameObj.getBehaviour(NPCDefaultBehaviour.class);

            if(npcDefaultBehaviour.body != null){
                npcDefaultBehaviour.getGameObject().getBehaviour(AnimationBehaviour.class).currentAnimation = AnimationTypes.WALK_UP;
                npcDefaultBehaviour.facingDirection = FacingDirection.North;
                npcDefaultBehaviour.movementSpeed = 3f;
                npcDefaultBehaviour.body.setLinearVelocity(0f, npcDefaultBehaviour.movementSpeed);
                npcDefaultBehaviour.characterType = CharacterType.NPC_Queue_Customer;
                npcDefaultBehaviour.currentState = CharacterCore.CurrentState.Waiting_in_Queue;
            }
        }
    }
}
