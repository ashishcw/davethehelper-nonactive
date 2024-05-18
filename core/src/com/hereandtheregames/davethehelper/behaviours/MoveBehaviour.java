package com.hereandtheregames.davethehelper.behaviours;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import dev.lyze.gdxUnBox2d.Behaviour;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateCircleFixtureBehaviour;

import java.util.Objects;

public class MoveBehaviour extends BehaviourAdapter {
    private final boolean moveToRight;

    public MoveBehaviour(boolean moveToTheRight, GameObject gameObject) {
        super(gameObject);
        this.moveToRight = moveToTheRight;
    }

    @Override
    public void fixedUpdate() {
        //super.fixedUpdate();
        Vector2 position = getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getPosition();
        //if(position.x <= 01 && position.y <= 10)
        {
            getGameObject().getBehaviour(Box2dBehaviour.class).getBody().applyLinearImpulse(0.01f * (moveToRight ? 1 : -1), 0, position.x, position.y, true);
            //System.out.println("Right Game Object Position : " + position);
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        Vector2 position = this.getGameObject().getBehaviour(Box2dBehaviour.class).getBody().getPosition();

        //System.out.println("Position : " + position);
    }

    @Override
    public void onCollisionEnter(Behaviour other, Contact contact) {
        super.onCollisionEnter(other, contact);
        System.out.println("Fixture A : " + contact.getFixtureA().getUserData());
        System.out.println("Fixture B : " + contact.getFixtureB().getUserData());
        System.out.println(other.getGameObject().getName());
    }
}
