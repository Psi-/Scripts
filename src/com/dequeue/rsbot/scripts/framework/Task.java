package com.dequeue.rsbot.scripts.framework;

import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public abstract class Task<T> extends MethodProvider {
    public T script;
    public Task(T script, MethodContext ctx) {
        super(ctx);
        this.script = script;
    }

    public abstract boolean activate();

    public abstract void execute();
}
