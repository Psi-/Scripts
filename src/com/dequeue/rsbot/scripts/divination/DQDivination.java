package com.dequeue.rsbot.scripts.divination;

import com.dequeue.rsbot.scripts.divination.Tasks.CollectChronicle;
import com.dequeue.rsbot.scripts.divination.Tasks.Convert;
import com.dequeue.rsbot.scripts.divination.Tasks.Harvest;
import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.event.BotMenuListener;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

@Manifest(name = "DQDivination", hidden = false, description = "Trains Divination", topic = 361)
public class DQDivination extends PollingScript implements MessageListener, PaintListener, BotMenuListener {
    private static List<Task> taskList = new ArrayList<Task>();
    private static int startExp, expGained;
    private static long startTime, timeRun;
    public static int conversionChoice = 2;
    public static boolean haveMaxChronicles;

    @Override
    public void start() {
        taskList.add(new Harvest(ctx));
        taskList.add(new CollectChronicle(ctx));
        taskList.add(new Convert(ctx));
//        taskList.add(new Travel(ctx));
        startExp = ctx.skills.getExperience(Skills.DIVINATION);
        startTime = System.currentTimeMillis();
        if (ctx.backpack.select().id(CollectChronicle.CHRONICLE_ID).count(true) == 10) {
            haveMaxChronicles = true;
        }
    }

    @Override
    public int poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                log.info((new Date()).toString().split(" ")[3] + " " + task.getClass().getSimpleName());
                return 1000;
            }
        }
        return 500;
    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        expGained = ctx.skills.getExperience(Skills.DIVINATION) - startExp;
        timeRun = System.currentTimeMillis() - startTime;
        g.drawString("Time Running: " + timeRun / 1000 + "s", 50, 150);
        g.drawString("Exp. Gained: " + expGained, 50, 170);
        g.drawString("Exp/hr: " + (int) ((double) expGained / (double) timeRun * 3600000), 50, 190);
    }

    @Override
    public void menuSelected(MenuEvent e) {
        final JMenu parent = (JMenu) e.getSource();
        final JMenuItem option1 = new JMenuItem("Convert memory to energy");
        final JMenuItem option2 = new JMenuItem("Convert memory to experience");
        final JMenuItem option3 = new JMenuItem("Convert both to experience");
        option2.setSelected(true);
        parent.add(option1);
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 0;
                option1.setSelected(true);
                option2.setSelected(false);
                option3.setSelected(false);
            }
        });
        parent.add(option2);
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 1;
                option1.setSelected(false);
                option2.setSelected(true);
                option3.setSelected(false);
            }
        });
        parent.add(option3);
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 2;
                option1.setSelected(false);
                option2.setSelected(false);
                option3.setSelected(true);
            }
        });
    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        if (messageEvent.getMessage().contains("have already collected ten chronicle")) {
            haveMaxChronicles = true;
        }
    }
}
