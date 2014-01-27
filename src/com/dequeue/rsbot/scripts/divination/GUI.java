package com.dequeue.rsbot.scripts.divination;

import com.dequeue.rsbot.scripts.divination.data.Wisp;
import org.powerbot.script.methods.Skills;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class GUI extends JFrame {
    private final Properties prop = new Properties();
    JRadioButton memoryToEnergyRadioButton;
    JRadioButton memoryToExperienceRadioButton;
    JRadioButton bothToExperienceRadioButton;
    private File PATH_FILE;
    private String FILE_NAME = "SETTINGS.ini";
    private JList<String> wispList;
    private ButtonGroup buttonGroup;

    public GUI(DQDivination script) {
        init(script);
        load();
    }

    public Wisp getWisp() {
        if (wispList.getSelectedValue() == null)
            return Wisp.NONE;
        return Wisp.valueOf(wispList.getSelectedValue().toUpperCase());
    }

    public int getConversionChoice() {
        return buttonGroup.getSelection().getMnemonic();
    }

    public void init(final DQDivination script) {
        File f = script.getStorageDirectory();
        if (!f.exists()) {
            f.mkdirs();
        }
        PATH_FILE = new File(f, FILE_NAME);
        setTitle(f.getAbsolutePath() + FILE_NAME);
//        setTitle("Dequeue Divination");
        setSize(360, 274);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                script.getController().stop();
                dispose();
            }
        });

        JButton startButton = new JButton("Commence");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                script.init();
                save();
                dispose();
            }
        });

        JLabel wispLabel = new JLabel("Wisp:");

        wispList = new JList<String>(new String[]
                {"Pale", "Flickering", "Bright", "Glowing",
                        "Sparkling", "Gleaming", "Vibrant",
                        "Lustrous", "Brilliant", "Radiant",
                        "Luminous", "Incandescent"
                });
        int divLevel = script.getContext().skills.getLevel(Skills.DIVINATION) / 5;
        if (divLevel == 19)
            wispList.setSelectedIndex(11);
        else
            wispList.setSelectedIndex(divLevel / 2);
        wispList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(wispList);

        JLabel conversionLabel = new JLabel("This can be changed in Script -> Options");
        buttonGroup = new ButtonGroup();
        memoryToEnergyRadioButton = new JRadioButton("Convert memory to energy");
        memoryToExperienceRadioButton = new JRadioButton("Convert memory to experience");
        bothToExperienceRadioButton = new JRadioButton("Convert both to experience", true);
        memoryToEnergyRadioButton.setMnemonic(0);
        memoryToExperienceRadioButton.setMnemonic(1);
        bothToExperienceRadioButton.setMnemonic(2);
        buttonGroup.add(memoryToEnergyRadioButton);
        buttonGroup.add(memoryToExperienceRadioButton);
        buttonGroup.add(bothToExperienceRadioButton);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));

        JPanel wispPanel = new JPanel();
        wispPanel.setLayout(new BoxLayout(wispPanel, BoxLayout.Y_AXIS));
        wispPanel.add(wispLabel);
        wispPanel.add(scrollPane);

        JPanel radioPanel = new JPanel();
        radioPanel.add(conversionLabel);
        radioPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(memoryToEnergyRadioButton);
        radioPanel.add(memoryToExperienceRadioButton);
        radioPanel.add(bothToExperienceRadioButton);

        main.add(Box.createRigidArea(new Dimension(15, 0)));
        main.add(wispPanel);
        main.add(Box.createRigidArea(new Dimension(10, 0)));
        main.add(radioPanel);
        main.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottom.setAlignmentX(1f);
        bottom.add(Box.createRigidArea(new Dimension(15, 0)));
        bottom.add(quitButton);
        bottom.add(Box.createRigidArea(new Dimension(5, 0)));
        bottom.add(startButton);
        bottom.add(Box.createRigidArea(new Dimension(15, 0)));

        panel.add(Box.createVerticalGlue());
        panel.add(main);
        panel.add(Box.createVerticalGlue());
        panel.add(bottom);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public synchronized void save() {
        try {
            if (!PATH_FILE.exists() && !PATH_FILE.createNewFile()) {
                return;
            }
            if (!PATH_FILE.canWrite()) {
                PATH_FILE.setWritable(true);
            }
            prop.clear();

            prop.put("Convert", String.valueOf(buttonGroup.getSelection().getMnemonic()));

            prop.store(new FileOutputStream(PATH_FILE), "GUI Settings");
            PATH_FILE.setReadOnly();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public synchronized void load() {
        try {
            if (PATH_FILE.exists()) {
                prop.load(new FileInputStream(PATH_FILE));
                switch (Integer.valueOf(prop.getProperty("Convert"))) {
                    case 0:
                        memoryToEnergyRadioButton.setSelected(true);
                        break;
                    case 1:
                        memoryToExperienceRadioButton.setSelected(true);
                        break;
                    case 2:
                        bothToExperienceRadioButton.setSelected(true);
                        break;
                    default:
                        break;
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
