package com.dequeue.rsbot.scripts.woodcutting;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.GameObject;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Chop extends Task<DQCut> {
    private static int[] treeIds = {38616, 38627, 58006};

    public Chop(DQCut script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() < 28
                && !ctx.objects.select().id(treeIds).isEmpty()
                && ctx.players.local().getAnimation() == -1;
    }

    @Override
    public void execute() {
        GameObject tree = ctx.objects.nearest().poll();
        if (tree.isOnScreen()) {
            tree.interact("Chop");
        } else {
            ctx.movement.stepTowards(tree);
            ctx.camera.turnTo(tree);
        }
    }
}
