package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class Weave extends Task<DQDivination> {

    private final static int[] ENERGY_IDS = {29314};
    private final static int
            CREATE_WIDGET_ID = 1370,
            CREATE_COMPONENT_ID = 20,
            PRODUCT_WIDGET_ID = 1371,
            PRODUCT_COMPONENT_ID_1 = 44,
            PRODUCT_COMPONENT_ID_2 = 28;

    public Weave(DQDivination script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {
        script.painter.setStatus("Weaving boon");
    }
}
