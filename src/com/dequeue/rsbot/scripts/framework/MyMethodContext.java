package com.dequeue.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodContext;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class MyMethodContext extends MethodContext {
    public MyBit bits;

    public MyMethodContext(MethodContext orig) {
        super(orig.getBot());
    }

    @Override
    public void init(MethodContext ctx) {
        super.init(ctx);
        this.bits = new MyBit(this);
    }
}
