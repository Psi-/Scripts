package com.dequeue.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodProvider;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class MyMethodProvider extends MethodProvider {
    public MyMethodContext ctx;
    public Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public MyMethodProvider(MyMethodContext c) {
        super(c);
        this.ctx = c;
    }
}
