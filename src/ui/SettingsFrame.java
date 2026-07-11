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
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        panel.setBackground(new Color(20,20,20));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30,70,30,70)
        );

        add(panel);

        GridLayout layout = new GridLayout();

        layout.setRows(8);
        layout.setColumns(1);
        layout.setHgap(10);
        layout.setVgap(15);

        panel.setLayout(layout);

        //settings label
        titleLabel = new JLabel("Settings");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setFont(new Font("Verdana", Font.BOLD,33));
        titleLabel.setForeground(new Color(255,215,0));

        panel.add(titleLabel);

        //check boxes
        //back ground music
        backgroundMusicCheckBox = new JCheckBox("Background Music");
        checkboxesStyle(backgroundMusicCheckBox);

        panel.add(backgroundMusicCheckBox);

        //gun shot sound
        shotSoundCheckBox = new JCheckBox("Gun Shot Sound");
        checkboxesStyle(shotSoundCheckBox);

        panel.add(shotSoundCheckBox);

        //game over sound
        gameOverSoundCheckBox = new JCheckBox("Game Over Sound");
        checkboxesStyle(gameOverSoundCheckBox);

        panel.add(gameOverSoundCheckBox);

        //explosion sound
        crashSoundCheckBox = new JCheckBox("Crash / Explosion Sound");
        checkboxesStyle(crashSoundCheckBox);

        panel.add(crashSoundCheckBox);

        //save button
        saveButton = new JButton("Save Settings");

        saveButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        saveButton.setForeground(Color.WHITE);

        saveButton.setBackground(new Color(40,40,40));
        saveButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(190,0,255),
                        2
                )
        );

        saveButton.setFocusPainted(false);

        panel.add(saveButton);

        saveButton.addActionListener(e ->{
            saveSettings();
        });

        //back button

        backButton = new JButton("Back");

        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        backButton.setForeground(Color.WHITE);

        backButton.setBackground(new Color(40,40,40));
        backButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,0,0),
                        2
                )
        );

        backButton.setFocusPainted(false);

        panel.add(backButton);

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
        checkBox.setFocusPainted(false);
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
