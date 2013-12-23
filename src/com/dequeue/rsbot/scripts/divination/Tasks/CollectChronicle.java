package com.dequeue.rsbot.scripts.divination.Tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Npc;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class CollectChronicle extends Task {
    private int[] chronicleIds = {18204};
    public static int CHRONICLE_ID = 29293;

    public CollectChronicle(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !DQDivination.haveMaxChronicles && !ctx.npcs.select().id(chronicleIds).isEmpty();
    }

    @Override
    public void execute() {
        ctx.properties.setProperty("chronicle", "false");
        for (Npc npc : ctx.npcs.nearest().first()) {
            npc.interact("Capture");
        }
    }
}
