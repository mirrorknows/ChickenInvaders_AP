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
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20,20,20));
        add(panel);

        panel.setBorder(
                BorderFactory.createEmptyBorder(30,70,30,70)
        );

        GridLayout layout = new GridLayout();
        layout.setRows(11);
        layout.setColumns(1);
        layout.setHgap(10);
        layout.setVgap(15);

        panel.setLayout(layout);

        titleLabel = new JLabel("HOW TO PLAY");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255,215,0));

        panel.add(titleLabel);

        controlsTitleLabel = new JLabel("CONTROLS");
        controlsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        controlsTitleLabel.setFont(new Font("lucida console", Font.BOLD, 22));
        controlsTitleLabel.setForeground(new Color(190, 0, 255));

        panel.add(controlsTitleLabel);

        moveRightLabel = new JLabel("D      Move Right");
        labelStyle(moveRightLabel);
        panel.add(moveRightLabel);

        moveLeftLabel = new JLabel("A      Move Left");
        labelStyle(moveLeftLabel);
        panel.add(moveLeftLabel);

        moveUpLabel = new JLabel("W      Move Up");
        labelStyle(moveUpLabel);
        panel.add(moveUpLabel);

        moveDownLabel = new JLabel("S      Move Down");
        labelStyle(moveDownLabel);
        panel.add(moveDownLabel);

        shootLabel = new JLabel("SPACE      Shoot");
        labelStyle(shootLabel);
        panel.add(shootLabel);

        pauseLabel = new JLabel("P          Pause / Resume");
        labelStyle(pauseLabel);
        panel.add(pauseLabel);

        exitLabel = new JLabel("ESC        Return to Main Menu");
        labelStyle(exitLabel);
        panel.add(exitLabel);

        //button
        backButton = new JButton("Back");

        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40,40,40));
        backButton.setFocusPainted(false);

        panel.add(backButton);

        backButton.addActionListener(e -> {

            new MainMenu();

            dispose();

        });
        setLocationRelativeTo(null);
        setVisible(true);

    }

    //makes a certain style for every label
    private void labelStyle(JLabel label){

        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);

    }
}
