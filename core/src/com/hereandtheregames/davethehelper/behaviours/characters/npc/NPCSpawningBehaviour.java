package com.hereandtheregames.davethehelper.behaviours.characters.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationBehaviour;
import com.hereandtheregames.davethehelper.behaviours.animations.AnimationTypes;
import com.hereandtheregames.davethehelper.behaviours.animations.CurrentAnimationState;
import com.hereandtheregames.davethehelper.behaviours.characters.CharacterType;
import com.hereandtheregames.davethehelper.behaviours.characters.group.GroupBehaviour;
import com.hereandtheregames.davethehelper.behaviours.test.NPCDefaultBehaviourTest;
import com.hereandtheregames.davethehelper.utils.GameConfig;
import dev.lyze.gdxUnBox2d.Box2dBehaviour;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;
import dev.lyze.gdxUnBox2d.behaviours.fixtures.CreateBoxFixtureBehaviour;

import java.util.ArrayList;
import java.util.List;

public class NPCSpawningBehaviour extends BehaviourAdapter {

    private int upperCapLimit = 7;
    private int currentSpawnedIndex = 0;
    private List<GameObject>allNPCs = new ArrayList<>();
    private MainGame mainGame;

    private CharacterType characterType;
    private GroupBehaviour.Group group;

    //TODO: DELETE BELOW VARIABLES, ONLY FOR TESTING PURPOSE
    private boolean keyPrssed = false;
    public NPCSpawningBehaviour(
            GameObject gameObject,
            MainGame mainGame,
            CharacterType characterType,
            GroupBehaviour.Group group
    ) {
        super(gameObject);
        this.mainGame = mainGame;
        this.characterType = characterType;
        this.group = group;

        //TODO: Uncomment this later
//        for(int i = 0; i < this.upperCapLimit; i++){
//            createNPC();
//            currentSpawnedIndex = i;
//        }
    }

    private void createNPC(){
        GameObject go = new GameObject(this.mainGame.getUnBox());



        BodyDef npcDefGo = new BodyDef();
        npcDefGo.fixedRotation = true;
        npcDefGo.type = BodyDef.BodyType.DynamicBody;


        if(this.characterType == CharacterType.NPC_Standard){
            npcDefGo.position.set(-5f, 5.5f);
            go.setName("Test-NPC-" + currentSpawnedIndex);
        }else if(this.characterType == CharacterType.NPC_COOK_HELPER){
            npcDefGo.position.set(0f, 10f);
            go.setName("Cook-Helper-GO");
        }



        new Box2dBehaviour(npcDefGo, go);

        //hitbox
        //fixture
        FixtureDef fixtureDefNPC = new FixtureDef();
        fixtureDefNPC.isSensor = true;
        fixtureDefNPC.filter.categoryBits = GameConfig.NPC_CATEGORY;
        fixtureDefNPC.filter.maskBits = GameConfig.WALL_CATEGORY + GameConfig.INTERACTIBLE_CATEGORY + GameConfig.PLAYER_CATEGORY;
        new CreateBoxFixtureBehaviour(
                0.5f,
                0.5f,
                Vector2.Zero,
                fixtureDefNPC,
                go);


        //new NPCDefaultBehaviour(go, this.characterType);
        //new NPCDefaultBehaviourTest(go, this.characterType, MathUtils.random(1.5f, 4f));
        new NPCDefaultBehaviourTest(go, this.characterType);
        new GroupBehaviour(go, this.group);


        if(this.characterType == CharacterType.NPC_Standard){
            new AnimationBehaviour(go, AnimationTypes.WALK_RIGHT, CurrentAnimationState.Walking, CharacterType.NPC_Standard);
        }else if(this.characterType == CharacterType.NPC_COOK_HELPER){
            //new AnimationBehaviour(go, AnimationTypes.IDLE_DOWN, CurrentAnimationState.Idle, CharacterType.NPC_COOK_HELPER);
        }

        allNPCs.add(go);

    }


    //TODO: DELETE BELOW METHOD LATER AS ITS ONLY FOR TESTING PURPOSE
    @Override
    public void update(float delta) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.B) && !this.keyPrssed) {
//            createNPC();
//            currentSpawnedIndex++;
//            this.keyPrssed = true;
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            createNPC();
            currentSpawnedIndex++;
            this.keyPrssed = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && this.keyPrssed) {
            this.keyPrssed = false;
        }
    }
}
