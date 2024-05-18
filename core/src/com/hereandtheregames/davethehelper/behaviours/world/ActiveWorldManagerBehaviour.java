package com.hereandtheregames.davethehelper.behaviours.world;

import com.badlogic.gdx.utils.TimeUtils;
import com.hereandtheregames.davethehelper.screens.hud.HUD;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActiveWorldManagerBehaviour extends BehaviourAdapter {
    public List<GameObject>interactibleColliders = new ArrayList<>();
    public List<GameObject>nonInteractibleColliders = new ArrayList<>();
    public List<GameObject>allCustomers = new ArrayList<>();
    public int tomatoesCount, potatoesCount, cornCount, waterCount;
    public int tomatoesPlayerHoldingCount, potatoesPlayerHoldingCount, cornPlayerHoldingCount, waterPlayerHoldingCount;
    public int tomatoesReadyToHarvestCount, potatoesReadyToHarvestCount, cornReadyToHarvestCount;

    private long startTime = TimeUtils.millis();
    private long elapsedTime = TimeUtils.timeSinceMillis(startTime);
    private final long cropGrowthWaitTime = 1000;


    private final int
            MAXIMUM_TOMATO_HARVESTABLE_COUNT = 20,
            MAXIMUM_POTATO_HARVESTABLE_COUNT = 10,
            MAXIMUM_CORN_HARVESTABLE_COUNT = 10
            ;

    public ActiveWorldManagerBehaviour(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update(float delta) {

        if(elapsedTime > cropGrowthWaitTime){
            startTime = TimeUtils.timeSinceMillis(elapsedTime)+ cropGrowthWaitTime;

            if(this.tomatoesReadyToHarvestCount < MAXIMUM_TOMATO_HARVESTABLE_COUNT){
                this.tomatoesReadyToHarvestCount++;
            }

            if(this.potatoesReadyToHarvestCount < MAXIMUM_POTATO_HARVESTABLE_COUNT){
                this.potatoesReadyToHarvestCount++;
            }

            if(this.cornReadyToHarvestCount < MAXIMUM_CORN_HARVESTABLE_COUNT){
                this.cornReadyToHarvestCount++;
            }

            HUD.textraTomatoesLable.setText("[BLUE]Tomatoes : [RED]" + this.tomatoesReadyToHarvestCount);
            HUD.textraPotatoesLable.setText("[YELLOW]Potatoes : [RED]" + this.potatoesReadyToHarvestCount);
            HUD.textraCornsLable.setText("[WHITE]Corn : [RED]" + this.cornReadyToHarvestCount);

        }
        elapsedTime = TimeUtils.timeSinceMillis(startTime);


    }

    public void interactibleCollidersLogic(GameObject gameObject){

        if(gameObject != null){
            //resource collection
            //if(Objects.equals(gameObject.getName(), "Tomato-Field-1")){
            if(gameObject.getName().contains("Tomato-Field")){
                if(this.tomatoesReadyToHarvestCount > 0){
                    this.tomatoesPlayerHoldingCount += this.tomatoesReadyToHarvestCount;
                    this.tomatoesReadyToHarvestCount = 0;
                }
            }

            if(gameObject.getName().contains("Potato-Field")){
                if(this.potatoesReadyToHarvestCount > 0){
                    this.potatoesPlayerHoldingCount += this.potatoesReadyToHarvestCount;
                    this.potatoesReadyToHarvestCount = 0;
                }
            }

            if(gameObject.getName().contains("Corn-Field")){
                if(this.cornReadyToHarvestCount > 0){
                    this.cornPlayerHoldingCount += this.cornReadyToHarvestCount;
                    this.cornReadyToHarvestCount = 0;
                }
            }

            //resource dump
            if(Objects.equals(gameObject.getName(), "Player-Resource-Dumper")){
                if(this.tomatoesPlayerHoldingCount > 0){
                    this.tomatoesCount += this.tomatoesPlayerHoldingCount;
                    this.tomatoesPlayerHoldingCount = 0;
                }

                if(this.potatoesPlayerHoldingCount > 0){
                    this.potatoesCount += this.potatoesPlayerHoldingCount;
                    this.potatoesPlayerHoldingCount = 0;
                }

                if(this.cornPlayerHoldingCount > 0){
                    this.cornCount += this.cornPlayerHoldingCount;
                    this.cornPlayerHoldingCount = 0;
                }
            }
        }

    }

    public void handleCustomerQueue(){
        //allCustomers
    }




}
