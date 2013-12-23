package com.dequeue.rsbot.scripts.woodcutting;

import com.dequeue.rsbot.scripts.framework.Task;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

@Manifest(name = "DQCut", authors = {"Dequeue"}, hidden = false, description = "Cuts somethin", topic = 0, version = 0.1)
public class DQCut extends PollingScript implements MessageListener, PaintListener {
    private static List<Task> taskList = new ArrayList<Task>();
    private static int logsCut = 0;

    @Override
    public void start() {
        taskList.add(new Chop(ctx));
        taskList.add(new Drop(ctx));
    }

    @Override
    public int poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                return 500;
            }
        }
        return 300;
    }

    @Override
    public void messaged(MessageEvent e) {
        if (e.getMessage().contains("you get some logs")) {
            logsCut++;
        }
    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString("Logs Cut: " + logsCut, 50, 50);
    }
}
