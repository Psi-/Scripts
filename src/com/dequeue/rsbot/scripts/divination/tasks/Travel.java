package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class Travel extends Task<DQDivination> {

    public Travel(DQDivination script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {
        script.painter.setStatus("Traveling to wisps");
    }
}
