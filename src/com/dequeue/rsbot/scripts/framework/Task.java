package com.dequeue.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public abstract class Task extends MethodProvider {
    public Task(MethodContext ctx) {
        super(ctx);
    }

    public abstract boolean activate();

    public abstract void execute();
}
