package com.jakespringer.engine.collisions;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Vec2;
import java.util.HashSet;

public class CollisionComponent extends AbstractComponent {

    public AbstractEntity ae;
    public PositionComponent pc;
    public double height;
    public double width;
    public HashSet<CollisionComponent> collisions;
    public int xHit, yHit, zHit;

    public CollisionComponent(AbstractEntity ae, PositionComponent pc, double height, double width) {
        this.ae = ae;
        this.pc = pc;
        this.height = height;
        this.width = width;
        collisions = new HashSet();
    }

    public boolean open(Vec2 pos) {
//        LevelComponent lc = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class);
//        for (int i = (int) Math.floor(pos.x - width); i < pos.x + width; i++) {
//            for (int j = (int) Math.floor(pos.y - width); j < pos.y + width; j++) {
//                if (lc.heightAt(i, j) > pos.z) {
//                    return false;
//                }
//            }
//        }
        return true;
    }
}