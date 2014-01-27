package com.dequeue.rsbot.util;

import org.powerbot.script.lang.BasicNamedQuery;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Npcs;
import org.powerbot.script.wrappers.Interactive;
import org.powerbot.script.wrappers.Npc;

import java.awt.*;
import java.util.Comparator;

public class Methods {
    public static Npc nextNpc(int limit, MethodContext ctx, int... ids) {
        final Point p = ctx.mouse.getLocation();
        return ctx.npcs.select(//Interactive.areOnScreen()
        ).id(ids).sort(new Comparator<Npc>() {
            @Override
            public int compare(final Npc o1, final Npc o2) {
                if (o1.isOnScreen() && o2.isOnScreen())
                    return (int) (p.distance(o1.getNextPoint()) - p.distance(o2.getNextPoint()));
                else if (o1.isOnScreen())
                    return -1;
                else return 1;
            }
        }).limit(limit).shuffle().poll();
    }
}
