package com.dequeue.rsbot.scripts.woodcutting;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Drop extends Task<DQCut> {
    private static int logId = 1519;

    public Drop(DQCut script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    @Override
    public void execute() {
        for (Item i : ctx.backpack.id(logId)) {
            i.interact("Drop");
            sleep(350, 500);
        }
    }
}
