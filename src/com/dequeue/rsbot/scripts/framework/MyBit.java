package com.dequeue.rsbot.scripts.framework;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class MyBit extends MyMethodProvider {
    public MyBit(MyMethodContext c) {
        super(c);
    }

    public boolean doStuff() {
        return ctx.npcs.select().isEmpty();
    }
}
