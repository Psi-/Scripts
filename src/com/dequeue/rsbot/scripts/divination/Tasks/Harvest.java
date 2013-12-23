package com.dequeue.rsbot.scripts.divination.Tasks;

import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Npc;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Harvest extends Task {
    private int[] springIds = {18174};
    private int[] wispIds = {18151};

    public Harvest(MethodContext ctx) {
        super(ctx);
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
            }, 200, 5);
        }
        return false;
    }

    @Override
    public void execute() {
        if (!ctx.npcs.select().id(springIds, wispIds).nearest().first().isEmpty()) {
            for (Npc npc : ctx.npcs) {
                if (!(npc.isOnScreen() && npc.interact("Harvest"))) {
                    ctx.camera.turnTo(npc);
                    if (!npc.isOnScreen()) {
                        ctx.movement.stepTowards(npc);
                    }
                }
            }
        }
    }
}
