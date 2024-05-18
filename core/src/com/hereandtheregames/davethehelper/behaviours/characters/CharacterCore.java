package com.hereandtheregames.davethehelper.behaviours.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.animations.FacingDirection;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class CharacterCore extends BehaviourAdapter {
    public float movementSpeed = 4f;
    public long startTime = TimeUtils.millis();
    public long elapsedTime = TimeUtils.timeSinceMillis(startTime);
    public long waitTime = 5000;
    public float spendingCapacity = 5f;
    public CharacterType characterType;

    public FacingDirection facingDirection;
    public Vector2 targetPosition = Vector2.Zero;
    public boolean targetReached = false;

    public enum CurrentState{
        Wandering,
        Waiting_in_Queue,
        Exiting_Queue,
        Waiting_for_an_order_standing,
        Waiting_for_an_order_sitting,
        Order_Complete,
    }
    public CurrentState currentState = CurrentState.Wandering;
    public CharacterCore(GameObject gameObject, CharacterType characterTypeParam) {
        super(gameObject);
        this.characterType = characterTypeParam;
    }


}
