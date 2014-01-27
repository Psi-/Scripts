package com.dequeue.rsbot.scripts.divination;

import com.dequeue.rsbot.scripts.divination.data.Wisp;
import com.dequeue.rsbot.scripts.divination.tasks.CloseInterface;
import com.dequeue.rsbot.scripts.divination.tasks.CollectChronicle;
import com.dequeue.rsbot.scripts.divination.tasks.Convert;
import com.dequeue.rsbot.scripts.divination.tasks.Harvest;
import com.dequeue.rsbot.scripts.framework.Task;
import com.dequeue.rsbot.scripts.framework.graphics.*;
import com.dequeue.rsbot.scripts.framework.graphics.Painter;
import org.powerbot.event.BotMenuListener;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.wrappers.Drawable;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

@Manifest(name = "DQDivination", hidden = false, description = "Trains Divination", topic = 361)
public class DQDivination extends PollingScript implements MessageListener, PaintListener, BotMenuListener {

    public int conversionChoice = 2;
    public boolean guiFinished, initialized, haveMaxChronicles;
    private List<Task> taskList = new ArrayList<Task>();
    public Wisp wisp = Wisp.NONE;
    private GUI gui;
    public Drawable currentFocus;
    public Painter painter = new Painter(this, ctx, Skills.DIVINATION);

    @Override
    public void start() {
        final DQDivination script = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new GUI(script);
                gui.setVisible(true);
            }
        });
    }

    public void init() {
        guiFinished = true;
        wisp = gui.getWisp();
        conversionChoice = gui.getConversionChoice();
        if (ctx.skills.getLevel(Skills.DIVINATION) < 1) return;
        taskList.add(new Harvest(this));
//        taskList.add(new CollectChronicle(this));
        taskList.add(new Convert(this));
        taskList.add(new CloseInterface(this));
//        taskList.add(new Travel(this));
        painter.setStartExp(ctx.skills.getExperience(Skills.DIVINATION));
        if (ctx.backpack.select().id(CollectChronicle.CHRONICLE_ID).count(true) == 10) {
            haveMaxChronicles = true;
        }
        initialized = true;
    }

    @Override
    public int poll() {
        if (!guiFinished) return 1000;
        if (!initialized) {
            init();
        }
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                log.info((new Date()).toString().split(" ")[3] + " " + task.getClass().getSimpleName());
            }
        }
        return 600;
    }

    @Override
    public void repaint(Graphics g) {
        if (!initialized || !ctx.players.local().isValid()) return;
        if (currentFocus != null) {
            currentFocus.draw(g);
            g.drawString(currentFocus.toString(), 400, 400);
        } else g.drawString("null", 400, 400);
        painter.repaint(g);
    }

    @Override
    public void menuSelected(MenuEvent e) {
        final JMenu parent = (JMenu) e.getSource();
        final JMenuItem option1 = new JMenuItem("Convert memory to energy");
        final JMenuItem option2 = new JMenuItem("Convert memory to experience");
        final JMenuItem option3 = new JMenuItem("Convert both to experience");
        parent.add(option1);
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 0;
            }
        });
        parent.add(option2);
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 1;
            }
        });
        parent.add(option3);
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conversionChoice = 2;
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

    @Override
    public void stop() {
        log.info("Stopping script");
        super.stop();
    }
}
