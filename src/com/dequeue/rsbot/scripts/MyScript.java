package com.dequeue.rsbot.scripts;

import com.dequeue.rsbot.scripts.framework.MyMethodContext;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.MethodContext;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class MyScript extends PollingScript {
    public MyMethodContext ctx;

    public MyScript() {
        this.ctx = new MyMethodContext(super.ctx);
    }

    @Override
    public void setContext(MethodContext mc) {
        this.ctx.init(mc);
    }

    @Override
    public int poll() {
        return 0;
    }
}
