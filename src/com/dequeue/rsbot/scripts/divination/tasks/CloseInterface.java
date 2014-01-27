package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class CloseInterface extends Task<DQDivination> {
    final int INTERFACE_WIDGET_ID = 1477;
    final int CLOSE_COMPONENT_ID_1 = 74;
    final int CLOSE_COMPONENT_ID_2 = 1;
    Component closeButton;

    public CloseInterface(DQDivination script, MethodContext ctx) {
        super(script, ctx);
    }

    @Override
    public boolean activate() {
        closeButton = ctx.widgets.get(INTERFACE_WIDGET_ID, CLOSE_COMPONENT_ID_1).getChild(CLOSE_COMPONENT_ID_2);
        return closeButton.isValid() && closeButton.isVisible();
    }

    @Override
    public void execute() {
        script.currentFocus = closeButton;
        script.painter.setStatus("Closing interface");
        closeButton.interact("Close");
    }
}
