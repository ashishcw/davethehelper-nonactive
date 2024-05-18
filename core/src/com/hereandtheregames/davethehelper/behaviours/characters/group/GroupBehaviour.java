package com.hereandtheregames.davethehelper.behaviours.characters.group;

import dev.lyze.gdxUnBox2d.GameObject;
import dev.lyze.gdxUnBox2d.behaviours.BehaviourAdapter;

public class GroupBehaviour extends BehaviourAdapter {
    public enum Group {
        PLAYER, NPC;
    }
    public Group group;
    public GroupBehaviour(GameObject gameObject, Group groupParam) {
        super(gameObject);
        this.group = groupParam;
    }
}
