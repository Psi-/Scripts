package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Npc;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class CollectChronicle extends Task<DQDivination> {
    private int chronicleId = 18204;
    public static int CHRONICLE_ID = 29293;

    public CollectChronicle(DQDivination script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        return !script.haveMaxChronicles && !ctx.npcs.select().id(chronicleId).isEmpty();
    }

    @Override
    public void execute() {
        script.painter.setStatus("Collecting Chronicle");
        Npc npc = ctx.npcs.poll();
        npc.interact("Capture");
    }
}
