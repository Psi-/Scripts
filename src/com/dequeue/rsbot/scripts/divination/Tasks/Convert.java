package com.dequeue.rsbot.scripts.divination.tasks;

import com.dequeue.rsbot.scripts.divination.DQDivination;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */
public class Convert extends Task {
    private DQDivination script;

    private final int RIFT_ID = 87306;
    private final int CONVERT_WIDGET_ID = 131;
    private final int MEMORY_TO_ENERGY_COMPONENT_ID = 36;
    private final int MEMORY_TO_EXP_COMPONENT_ID = 26;
    private final int BOTH_TO_EXP_COMPONENT_ID = 46;
    private final int[] CONVERSION_CHOICES = {
            MEMORY_TO_ENERGY_COMPONENT_ID,
            MEMORY_TO_EXP_COMPONENT_ID,
            BOTH_TO_EXP_COMPONENT_ID
    };

    public Convert(AbstractScript script) {
        super(script);
        this.script = (DQDivination) script;
    }

    @Override
    public boolean activate() {
        return ctx.widgets.get(CONVERT_WIDGET_ID, MEMORY_TO_EXP_COMPONENT_ID).isOnScreen()
                || (ctx.backpack.select().count() == 28 && !ctx.players.local().isInMotion());
    }

    @Override
    public void execute() {
        script.painter.setStatus("Converting memories");
        final int componentId = CONVERSION_CHOICES[script.conversionChoice];
        if (ctx.widgets.get(CONVERT_WIDGET_ID, componentId).isOnScreen()) {
            ctx.widgets.get(CONVERT_WIDGET_ID, componentId).click();
            return;
        }
        if (!ctx.objects.select().id(RIFT_ID).first().isEmpty()) {
            GameObject rift = ctx.objects.poll();
            script.currentFocus = rift;
            if (rift.isOnScreen() && rift.interact("Convert")) {
                if (!Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.widgets.get(CONVERT_WIDGET_ID, componentId).isOnScreen();
                    }
                }, 500, 8)) {
                    return;
                }
                ctx.widgets.get(CONVERT_WIDGET_ID, componentId).click();
            } else {
                ctx.camera.turnTo(rift);
                if (!rift.isOnScreen()) {
                    ctx.movement.stepTowards(rift);
                }
            }
        }
    }
}
