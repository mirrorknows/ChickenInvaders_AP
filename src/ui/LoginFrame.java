package ui;

import models.User;
import services.LoggedUser;
import services.UserService;

import javax.swing.*;
import java.awt.*;

//this class shows login window

public class LoginFrame extends JFrame {

    //fields
    private UserService userService;

    //panel
    private JPanel panel;

    //labels
    private JLabel welcomeLabel;
    private JLabel infoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    //text fields
    private JTextField usernameField;
    private JPasswordField passwordField;

    //buttons
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {

        setTitle("Login Page");
        setSize(800, 600);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel

        // Load background image from resources
        ImageIcon backgroundIcon =
                new ImageIcon(getClass().getResource("/images/backgrounds/login_background2.png"));

        Image backgroundImage = backgroundIcon.getImage();

        panel = new JPanel() {

            //draw the image as the panel background
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            }
        };

        add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        // welcome
        welcomeLabel = new JLabel("WELCOME!");

        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 50));
        welcomeLabel.setForeground(new Color(207,255,4));

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(10));

        //info label : login
        infoLabel = new JLabel("Please login or register");

        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));

        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(25));

        // username
        usernameLabel = new JLabel("Username:");

        usernameLabel.setPreferredSize(new Dimension(320, 25));
        usernameLabel.setMaximumSize(new Dimension(320, 25));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5));

        //username field
        usernameField = new JTextField();

        usernameField.setMaximumSize(new Dimension(320, 40));
        usernameField.setPreferredSize(new Dimension(320, 40));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setFont(new Font("Consolas", Font.PLAIN, 18));
        usernameField.setBackground(new Color(40,40,40));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);

        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15));

        // password
        passwordLabel = new JLabel("Password:");

        passwordLabel.setPreferredSize(new Dimension(320, 25));
        passwordLabel.setMaximumSize(new Dimension(320, 25));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(5));

        //password field
        passwordField = new JPasswordField();

        passwordField.setMaximumSize(new Dimension(320, 40));
        passwordField.setPreferredSize(new Dimension(320, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField.setFont(new Font("Consolas", Font.PLAIN, 18));

        passwordField.setBackground(new Color(40,40,40));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);

        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(25));

        userService = new UserService();

        // buttons
        //login button
        loginButton = new JButton("Login");
        buttonStyle(loginButton);

        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(20));

        loginButton.addActionListener(e ->{
            String username = usernameField.getText().trim();

            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your username and password."
                );
                return;
            }

            User user = userService.loginUser(username, password);

            if(user != null){

                LoggedUser.setUser(user);

                JOptionPane.showMessageDialog(this,
                        "Login Successfully");

                new MainMenu();
                dispose();

            }else{

                JOptionPane.showMessageDialog(this,
                        "your login info is incorrect"
                );
            }
        });

        //register button
        registerButton = new JButton("Register");
        buttonStyle(registerButton);

        panel.add(registerButton);

        panel.add(Box.createVerticalGlue());

        //open register window
        registerButton.addActionListener(e ->{
            new RegisterFrame();
            dispose();
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }


    //the style of buttons: including background color, font,...

    private void buttonStyle(JButton button){

        button.setFont(new Font("Trebuchet MS", Font.BOLD,18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40,40,40));

        button.setPreferredSize(new Dimension(220, 45));
        button.setMaximumSize(new Dimension(220, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);


        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255,215,0),
                        2
                )
        );
    }
}
