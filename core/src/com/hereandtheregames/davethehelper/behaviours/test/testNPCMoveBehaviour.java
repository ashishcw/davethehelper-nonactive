package com.hereandtheregames.davethehelper.behaviours.test;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class testNPCMoveBehaviour extends BehaviourAdapter {
    private final TextureAtlas textureAtlasAllInOne;

    public testNPCMoveBehaviour(GameObject gameObject, TextureAtlas textureAtlasAllInOne) {
        super(gameObject);
        this.textureAtlasAllInOne = textureAtlasAllInOne;
    }

    //    public testNPCMoveBehaviour(GameObject gameObject) {
//        super(gameObject);
//
//        this.textureAtlasAllInOne = new TextureAtlas(Gdx.files.internal("images/characters/temp/ANIMATIONS/npc.atlas"));
//
//        Array<TextureAtlas.AtlasRegion> allRegions = this.textureAtlasAllInOne.getRegions();
//
//        Array<Object> allRegions1 = new Array<>();
//        allRegions2 = new Array<>();
//        int counter = 0;
//        for(int i = 0; i < allRegions.size; i++){
//            if(
//                    (allRegions.get(i).index > 27 && allRegions.get(i).index <= 33)
//                            &&
//                            (Objects.equals(allRegions.get(i).name, "Davis_Schneider"))
//            ){
//                allRegions1.add(allRegions.get(i));
//                allAnimationsDictionary.put(allRegions.get(i).name + "_Walk_RIGHT_" + counter, allRegions.get(i));
//                counter++;
//            }
//            else if(
//                    (allRegions.get(i).index > 33 && allRegions.get(i).index <= 39)
//                            &&
//                            (Objects.equals(allRegions.get(i).name, "Davis_Schneider"))
//            ){
//                allRegions1.add(allRegions.get(i));
//                allAnimationsDictionary.put(allRegions.get(i).name + "_Walk_UP_" + counter, allRegions.get(i));
//                counter++;
//            }
//            else if(
//                    (allRegions.get(i).index > 39 && allRegions.get(i).index <= 45)
//                            &&
//                            (Objects.equals(allRegions.get(i).name, "Davis_Schneider"))
//            ){
//                allRegions1.add(allRegions.get(i));
//                allAnimationsDictionary.put(allRegions.get(i).name + "_Walk_LEFT_" + counter, allRegions.get(i));
//                counter++;
//            }
//            else if(
//                    (allRegions.get(i).index > 45 && allRegions.get(i).index <= 51)
//                            &&
//                            (Objects.equals(allRegions.get(i).name, "Davis_Schneider"))
//            ){
//                allRegions1.add(allRegions.get(i));
//                allAnimationsDictionary.put(allRegions.get(i).name + "_Walk_DOWN_" + counter, allRegions.get(i));
//                counter++;
//            }
//
//        }
//
//        //this.allInOneAnimation = new Animation<>(this.frameRate, allRegions1);
//
//
//        // Displaying the Enumeration
//        for(int i = 0; i < this.allAnimationsDictionary.size(); i++){
//            if(this.allAnimationsDictionary.keys().nextElement().contains("Davis_Schneider_Walk_Right")){
//                this.allInOneAnimation = new Animation<>(this.frameRate, this.allAnimationsDictionary.get("Davis_Schneider_Walk_Right"));
//            }
//        }
//
//        allRegions2.clear();
//        indexToStart = 0; //right = 0, up = 5, left = 12, down = 18
//        for(int i = indexToStart; i < indexToStart+6; i++){
//            allRegions2.add(allRegions1.get(i));
//        }
//
//    }
}
