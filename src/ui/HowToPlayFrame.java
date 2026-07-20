package ui;

import javax.swing.*;
import java.awt.*;

//displays guide for players

public class HowToPlayFrame extends JFrame {

    private JLabel controlsTitleLabel;

    private JLabel titleLabel;

    private JLabel moveRightLabel;
    private JLabel moveLeftLabel;
    private JLabel moveUpLabel;
    private JLabel moveDownLabel;

    private JLabel shootLabel;
    private JLabel pauseLabel;
    private JLabel exitLabel;

    private JButton backButton;

    public HowToPlayFrame(){

        setTitle("Guide");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20,20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        titleLabel = new JLabel("HOW TO PLAY");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255,215,0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        controlsTitleLabel = new JLabel("CONTROLS");
        controlsTitleLabel.setFont(new Font("lucida console", Font.BOLD, 22));
        controlsTitleLabel.setForeground(new Color(190, 0, 255));
        controlsTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(controlsTitleLabel);
        panel.add(Box.createVerticalStrut(20));

        moveRightLabel = new JLabel("→ / D     Move Right");
        addControlLabel(panel, moveRightLabel);

        moveLeftLabel  = new JLabel("← / A     Move Left");
        addControlLabel(panel, moveLeftLabel);

        moveUpLabel    = new JLabel("↑ / W     Move Up");
        addControlLabel(panel, moveUpLabel);

        moveDownLabel  = new JLabel("↓ / S     Move Down");
        addControlLabel(panel, moveDownLabel);

        shootLabel = new JLabel("SPACE     Shoot");
        addControlLabel(panel, shootLabel);

        pauseLabel = new JLabel("P         Pause / Resume");
        addControlLabel(panel, pauseLabel);

        exitLabel  = new JLabel("ESC       Return to Main Menu");
        labelStyle(exitLabel);
        panel.add(exitLabel);
        panel.add(Box.createVerticalStrut(25));

        //button
        backButton = new JButton("Back");

        backButton.setPreferredSize(new Dimension(220, 45));
        backButton.setMaximumSize(new Dimension(220, 45));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40,40,40));
        backButton.setFocusPainted(false);

        backButton.setBorder(
                BorderFactory.createLineBorder(new Color(255, 0, 0), 2)
        );

        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        backButton.addActionListener(e -> {

            new MainMenu();

            dispose();

        });
        setLocationRelativeTo(null);
        setVisible(true);

    }

    //apply the same style to control labels
    private void labelStyle(JLabel label){

        label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);

        label.setPreferredSize(new Dimension(390, 26));
        label.setMaximumSize(new Dimension(390, 26));

        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setHorizontalAlignment(SwingConstants.LEFT);
    }

    //add control label
    private void addControlLabel(JPanel panel, JLabel label){

        labelStyle(label);
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));

    }
}
