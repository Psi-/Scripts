package com.dequeue.rsbot.scripts.framework;

import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public abstract class Task extends MethodProvider {
    public Task(AbstractScript script) {
        super(script.getContext());
    }

    public abstract boolean activate();

    public abstract void execute();
}
