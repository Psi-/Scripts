package com.dequeue.rsbot.scripts.framework.graphics;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Mouse {
    MethodContext ctx;

    public Mouse(MethodContext ctx) {
        this.ctx = ctx;
    }

    private static final Color MOUSE_COLOR = new Color(98, 227, 246),
            MOUSE_BORDER_COLOR = new Color(218, 219, 225),
            MOUSE_CENTER_COLOR = new Color(12, 197, 124);
    private static final LinkedList<MousePathPoint> mousePath = new LinkedList<MousePathPoint>();
    private final Object lock = new Object();
    private final ArrayList<Particle> particles = new ArrayList<Particle>();

    public void drawMouse(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = ctx.mouse.getLocation();
        Graphics2D spinG = (Graphics2D) g1.create();
        Graphics2D spinGRev = (Graphics2D) g1.create();
        Graphics2D spinG2 = (Graphics2D) g1.create();
        spinG.setColor(MOUSE_BORDER_COLOR);
        spinGRev.setColor(MOUSE_COLOR);
        spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2 * Math.PI / 180.0, p.x, p.y);
        spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d) * 2 * Math.PI / 180.0, p.x, p.y);
        final int outerSize = 20;
        final int innerSize = 12;
        spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize, outerSize, 100, 75);
        spinG.drawArc(p.x - (outerSize / 2), p.y - (outerSize / 2), outerSize, outerSize, -100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2), innerSize, innerSize, 100, 75);
        spinGRev.drawArc(p.x - (innerSize / 2), p.y - (innerSize / 2), innerSize, innerSize, -100, 75);
        g1.setColor(MOUSE_CENTER_COLOR);
        g1.fillOval(p.x, p.y, 2, 2);
        spinG2.setColor(MOUSE_CENTER_COLOR);
        spinG2.rotate(System.currentTimeMillis() % 2000d / 2000d * 360d * Math.PI / 180.0, p.x, p.y);
        spinG2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        spinG2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
        spinG2.drawLine(p.x, p.y - 5, p.x, p.y + 5);

        while (!mousePath.isEmpty() && mousePath.peek().isUp())
            mousePath.remove();
        MousePathPoint mpp = new MousePathPoint(p.x, p.y);
        if (mousePath.isEmpty() || !mousePath.getLast().equals(mpp))
            mousePath.add(mpp);
        MousePathPoint lastPoint = null;
        for (MousePathPoint a : mousePath) {
            if (lastPoint != null) {
                g1.setColor(a.getColor());
                g1.drawLine(a.x, a.y, lastPoint.x, lastPoint.y);
            }
            lastPoint = a;
        }
        g1.fillRect(p.x - 5, p.y, 12, 2);
        g1.fillRect(p.x, p.y - 5, 2, 12);
        int x = p.x;
        int y = p.y;
        int color = Random.nextInt(0, 3);
        if (ctx.mouse.isPressed()) {
            synchronized (lock) {
                for (int i = 0; i < 50; i++)
                    particles.add(new Particle(x, y, color));
            }
        }
        synchronized (lock) {
            Iterator<Particle> piter = particles.iterator();
            while (piter.hasNext()) {
                Particle part = piter.next();
                if (!part.handle(g1)) {
                    piter.remove();
                }
            }
        }
    }

    private class MousePathPoint extends Point {
        private int toColor(double d) {
            return Math.min(255, Math.max(0, (int) d));
        }

        private final long finishTime;
        private final double lastingTime;

        public MousePathPoint(int x, int y) {
            super(x, y);
            this.lastingTime = 3000;
            finishTime = System.currentTimeMillis() + 3000;
        }

        public boolean isUp() {
            return System.currentTimeMillis() > finishTime;
        }

        public Color getColor() {
            return new Color(76, 197, 182, toColor(256 * ((finishTime - System.currentTimeMillis()) / lastingTime)));
        }
    }

    private static class Particle {

        private double posX;
        private double posY;
        private final double movX;
        private final double movY;
        private int alpha = 255, color = -1;
        final java.util.Random generator = new java.util.Random();

        Particle(int pos_x, int pos_y, int color) {
            posX = (double) pos_x;
            posY = (double) pos_y;
            movX = ((double) generator.nextInt(40) - 20) / 16;
            movY = ((double) generator.nextInt(40) - 20) / 16;
            this.color = color;
        }

        public boolean handle(Graphics page) {
            alpha -= Random.nextInt(1, 7);
            if (alpha <= 0)
                return false;
            switch (color) {
                case 0:
                    page.setColor(new Color(34, 139, 34, alpha));
                    break;
                case 1:
                    page.setColor(new Color(255, 215, 0, alpha));
                    break;
            }
            page.drawLine((int) posX, (int) posY, (int) posX, (int) posY);
            posX += movX;
            posY += movY;
            return true;
        }
    }
}
