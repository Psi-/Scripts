package com.dequeue.rsbot.scripts.framework.graphics;

import javax.swing.*;
import java.awt.*;

public abstract class ProgressBar {

    private boolean fill;
    private int percentage;
    private int level;
    private long ttl;
    private long secondsTilLevel, minutesTilLevel, hoursTilLevel;
    private int experience;
    private int expPerHour;
    private int expGained;
    private long timeRun;
    private long secondsRun, minutesRun, hoursRun;
    private String status = "Starting up...";
    private Rectangle bounds;
    private Color colorText;
    private Color colorBorder = Color.BLACK;
    private LinearGradientPaint paint;
    private LinearGradientPaint shader;
    private final Color BLACK_125 = new Color(0, 0, 0, 125);
    private final Color WHITE_125 = new Color(255, 255, 255, 125);
    public final Font DEFAULT_FONT = new JLabel().getFont();
    public final Font DEFAULT_FONT_BOLD = new Font(DEFAULT_FONT.getName(), Font.BOLD, DEFAULT_FONT.getSize());

    public ProgressBar(final int x, final int y, final int width, final int height) {
        setBounds(new Rectangle(x, y, width, height));
        setBorderColor(Color.BLACK);
        setTextColor(Color.WHITE);
        setColors(true, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK);
        setShader(true, BLACK_125, WHITE_125, BLACK_125);
        fillShader(true);
    }

    public abstract String getText();

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    public String getTimeRun() {
        secondsRun = (timeRun / 1000) % 60;
        minutesRun = (timeRun / (1000 * 60)) % 60;
        hoursRun = (timeRun / (1000 * 60 * 60)) % 24;
        return String.format("Time Run: %02d:%02d:%02d", hoursRun, minutesRun, secondsRun);
    }

    public void setTimeRun(long timeRun) {
        this.timeRun = timeRun;
    }

    public void setBounds(final Rectangle bounds) {
        this.bounds = bounds;
    }

    public String getTtl() {
        if (ttl > 0) {
            secondsTilLevel = (ttl / 1000) % 60;
            minutesTilLevel = (ttl / (1000 * 60)) % 60;
            hoursTilLevel = (ttl / (1000 * 60 * 60)) % 24;
            return String.format("TTL: %02d:%02d:%02d at " + getExpPerHour() + "xp/hour", hoursTilLevel, minutesTilLevel, secondsTilLevel);
        }
        return "TTL: Calculating...";
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getExpGained() {
        return expGained + "xp gained";
    }

    public void setExpGained(int expGained) {
        this.expGained = expGained;
    }

    public int getExpPerHour() {
        return expPerHour;
    }

    public void setExpPerHour(int expPerHour) {
        this.expPerHour = expPerHour;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.min(99, level);
    }

    public String getStatus() {
        return "Status: " + status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(final int percentage) {
        this.percentage = Math.max(0, Math.min(100, percentage));
    }

    public void setTextColor(final Color color) {
        colorText = color;
    }

    public void setBorderColor(final Color color) {
        colorBorder = color;
    }

    public void setColors(final boolean horizontal, final Color... colors) {
        final float[] fractals = new float[colors.length];
        final double interval = 1.0D / (colors.length - 1);
        fractals[colors.length - 1] = 1;
        for (int i = 1; i < fractals.length - 1; i++)
            fractals[i] = (float) (i * interval);
        if (horizontal) {
            paint = new LinearGradientPaint(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height, fractals, colors);
        } else {
            paint = new LinearGradientPaint(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, fractals, colors);
        }
    }

    public void setShader(final boolean horizontal, final Color... colors) {
        final float[] fractals = new float[colors.length];
        final double interval = 1.0D / (colors.length - 1);
        fractals[colors.length - 1] = 1;
        for (int i = 1; i < fractals.length - 1; i++)
            fractals[i] = (float) (i * interval);
        if (horizontal) {
            shader = new LinearGradientPaint(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height, fractals, colors);
        } else {
            shader = new LinearGradientPaint(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, fractals, colors);
        }
    }

    public void fillShader(final boolean fill) {
        this.fill = fill;
    }

    public void drawStringCenter(final Graphics2D g, final String text, final Color color, int x, int y, final int width, final int height, final boolean border) {
        final int string_width = g.getFontMetrics().stringWidth(text);
        final int string_height = g.getFontMetrics().getHeight();
        x = x + width / 2 - string_width / 2;
        y = y + height / 2 + string_height / 2;
        if (!border)
            g.drawString(text, x, y);
        else {
            g.drawString(text, x - 1, y - 1);
            g.drawString(text, x - 1, y + 1);
            g.drawString(text, x + 1, y - 1);
            g.drawString(text, x + 1, y + 1);
        }
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public void onRepaint(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.setFont(DEFAULT_FONT_BOLD);
        g2.setColor(Color.BLACK);
        onRepaint(g2);
    }

    public void onRepaint(final Graphics2D g) {
        g.setColor(colorBorder);
        g.fill(bounds);

        if (paint != null) {
            g.setPaint(paint);
            g.fillRect(bounds.x + 2, bounds.y + 2, percentage * (bounds.width - 4) / 100, bounds.height - 4);
        }

        if (shader != null) {
            g.setPaint(shader);
            if (fill) {
                g.fillRect(bounds.x + 2, bounds.y + 2, bounds.width - 4, bounds.height - 4);
            } else {
                g.fillRect(bounds.x + 2, bounds.y + 2, percentage * (bounds.width - 4) / 100, bounds.height - 4);
            }
        }

        g.setColor(colorBorder);
        drawStringCenter(g, getText(), colorText, bounds.x + 2, bounds.y + 1, bounds.width - 8, bounds.height - 8, true);
        drawStringCenter(g, getTtl(), colorText, bounds.x + 2, bounds.y - 20, bounds.width - 8, bounds.height - 8, false);
        drawStringCenter(g, getTimeRun(), colorText, bounds.x + 2, bounds.y + 22, bounds.width - 8, bounds.height - 8, false);
        drawStringCenter(g, getExpGained(), colorText, bounds.x + 2, bounds.y + 37, bounds.width - 8, bounds.height - 8, false);
        drawStringCenter(g, getStatus(), colorText, bounds.x + 2, bounds.y + 52, bounds.width - 8, bounds.height - 8, false);
    }
}