package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class Travel extends Task {
    private DQDivination script;

    public Travel(AbstractScript script) {
        super(script);
        this.script = (DQDivination) script;
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
