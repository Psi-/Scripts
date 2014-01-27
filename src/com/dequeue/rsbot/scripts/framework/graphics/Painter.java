package com.dequeue.rsbot.scripts.framework.graphics;

import org.powerbot.script.AbstractScript;
import org.powerbot.script.methods.MethodContext;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class Painter {
    public final ProgressBar bar = new ProgressBar(210, 30, 300, 25) {
        @Override
        public String getText() {
            return getPercentage() + "% to level " + getLevel() + " (" + getExperience() + "xp)";
        }
    };
    MethodContext ctx;
    AbstractScript script;
    int skillIndex;
    private int startExp, currentExp, expTilLevel, expGained, expPerHour, currentLevel;
    private Mouse mouse;


    public Painter(AbstractScript script, MethodContext ctx, int skillIndex) {
        this.ctx = ctx;
        this.script = script;
        this.skillIndex = skillIndex;
        mouse = new Mouse(ctx);
    }

    public void repaint(Graphics g) {
        mouse.drawMouse(g);
        currentLevel = ctx.skills.getLevel(skillIndex);
        if (currentLevel < 1) return;
        currentExp = ctx.skills.getExperience(skillIndex);
        expTilLevel = ctx.skills.getExperienceAt(ctx.skills.getLevel(skillIndex) + 1) - currentExp;
        expGained = currentExp - startExp;
        expPerHour = (int) (3600000L * expGained / script.getRuntime());
        if (expGained > 0)
            bar.setTtl((int) ((long) expTilLevel * script.getRuntime() / (long) expGained));
        else
            bar.setTtl(0);
        bar.setTimeRun(script.getRuntime());
        bar.setExpGained(expGained);
        bar.setExpPerHour(expPerHour);
        bar.setPercentage(100 - (100 * expTilLevel / (ctx.skills.getExperienceAt(currentLevel + 1) - ctx.skills.getExperienceAt(currentLevel))));
        bar.setLevel(currentLevel + 1);
        bar.setExperience(expTilLevel);
        bar.onRepaint(g);
    }

    public void setStartExp(int startExp) {
        this.startExp = startExp;
    }

    public void setStatus(String status) {
        bar.setStatus(status);
    }
}
