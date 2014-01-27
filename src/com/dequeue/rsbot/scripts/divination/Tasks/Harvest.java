package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;

import java.util.concurrent.Callable;

import static com.dequeue.rsbot.util.Methods.nextNpc;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Harvest extends Task {
    private DQDivination script;

    public Harvest(AbstractScript script) {
        super(script);
        this.script = (DQDivination) script;
    }

    @Override
    public boolean activate() {
        if (ctx.backpack.select().count() < 28
                && !ctx.players.local().isInMotion()) {
            return !Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().getAnimation() != -1;
                }
            }, 100, Random.nextInt(10, 20));
        }
        return false;
    }

    @Override
    public void execute() {
        script.painter.setStatus("Harvesting " + script.wisp + " wisps");
        Npc npc;
        try {
            npc = (Npc) script.currentFocus;
        } catch (ClassCastException e) {
            npc = nextNpc(1, ctx, script.wisp.getWispId(), script.wisp.getSpringId());
            script.currentFocus = npc;
        }
        if (npc == null || !npc.isValid()) {
            npc = nextNpc(1, ctx, script.wisp.getWispId(), script.wisp.getSpringId());
            script.currentFocus = npc;
        }

        if (!npc.isOnScreen())
            ctx.camera.turnTo(npc);
        if (!npc.isOnScreen())
            ctx.movement.stepTowards(npc);
        else npc.interact("Harvest");
    }
}
