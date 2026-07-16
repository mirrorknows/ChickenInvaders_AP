package ui;

import models.User;
import services.LoggedUser;
import services.UserService;

import javax.swing.*;
import java.awt.*;

//displays setting menu (for sounds)
public class SettingsFrame extends JFrame {

    //check boxes
    private JCheckBox backgroundMusicCheckBox;
    private JCheckBox shotSoundCheckBox;
    private JCheckBox crashSoundCheckBox;
    private JCheckBox gameOverSoundCheckBox;

    private JButton saveButton;
    private JButton backButton;

    private JLabel titleLabel;

    private UserService userService;

    public SettingsFrame(){

        userService = new UserService();

        setTitle("Settings");
        setSize(800, 600);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        panel.setBackground(new Color(20,20,20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        add(panel);
        panel.add(Box.createVerticalStrut(50));

        //settings label
        titleLabel = new JLabel("Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Verdana", Font.BOLD,33));
        titleLabel.setForeground(new Color(255,215,0));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(35));

        //check boxes
        //background music
        backgroundMusicCheckBox = new JCheckBox("Background Music");
        checkboxesStyle(backgroundMusicCheckBox);

        panel.add(backgroundMusicCheckBox);
        panel.add(Box.createVerticalStrut(12));

        //gun shot sound
        shotSoundCheckBox = new JCheckBox("Gun Shot Sound");
        checkboxesStyle(shotSoundCheckBox);

        panel.add(shotSoundCheckBox);
        panel.add(Box.createVerticalStrut(12));

        //game over sound
        gameOverSoundCheckBox = new JCheckBox("Game Over Sound");
        checkboxesStyle(gameOverSoundCheckBox);

        panel.add(gameOverSoundCheckBox);
        panel.add(Box.createVerticalStrut(12));

        //explosion sound
        crashSoundCheckBox = new JCheckBox("Crash / Explosion Sound");
        checkboxesStyle(crashSoundCheckBox);

        panel.add(crashSoundCheckBox);
        panel.add(Box.createVerticalStrut(30));

        //save button
        saveButton = new JButton("Save Settings");

        buttonStyle(saveButton);

        saveButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(190,0,255),
                        2
                )
        );

        panel.add(saveButton);
        panel.add(Box.createVerticalStrut(12));


        saveButton.addActionListener(e ->{
            saveSettings();
        });

        //back button

        backButton = new JButton("Back");

        buttonStyle(backButton);

        backButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,0,0),
                        2
                )
        );


        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        backButton.addActionListener(e-> {

            new MainMenu();
            dispose();

        });

        loadUserSettings();

        setLocationRelativeTo(null);

        setVisible(true);

    }

    private void checkboxesStyle(JCheckBox checkBox){

        checkBox.setFont(new Font("Consolas", Font.PLAIN, 18));
        checkBox.setForeground(Color.WHITE);

        checkBox.setBackground(new Color(20,20,20));

        checkBox.setPreferredSize(new Dimension(340, 35));
        checkBox.setMaximumSize(new Dimension(340, 35));
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setHorizontalAlignment(SwingConstants.LEFT);

        checkBox.setFocusPainted(false);
    }

    private void buttonStyle(JButton button) {

        button.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 40));

        button.setPreferredSize(new Dimension(240, 45));
        button.setMaximumSize(new Dimension(240, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFocusPainted(false);

    }

    //load current user settings
    private void loadUserSettings(){

        User user = LoggedUser.getUser();

        if(user == null){
            return;
        }

        backgroundMusicCheckBox.setSelected(user.isMusicOn());
        shotSoundCheckBox.setSelected(user.isShotSoundOn());
        crashSoundCheckBox.setSelected(user.isCrashSoundOn());
        gameOverSoundCheckBox.setSelected(user.isGameOverSoundOn());

    }

    //save settings in database
    private void saveSettings(){

        User user = LoggedUser.getUser();

        if(user == null){
            JOptionPane.showMessageDialog(this, "No user is logged in");
            return;
        }


        boolean musicOn = backgroundMusicCheckBox.isSelected();
        boolean shotSoundOn = shotSoundCheckBox.isSelected();
        boolean crashSoundOn = crashSoundCheckBox.isSelected();
        boolean gameOverSoundOn = gameOverSoundCheckBox.isSelected();

        userService.updateSoundSettings(
                user.getUsername(),
                musicOn,
                shotSoundOn,
                crashSoundOn,
                gameOverSoundOn
        );
        user.setMusicOn(musicOn);
        user.setShotSoundOn(shotSoundOn);
        user.setCrashSoundOn(crashSoundOn);
        user.setGameOverSoundOn(gameOverSoundOn);

        JOptionPane.showMessageDialog(this, "Settings Saved");
    }
}
