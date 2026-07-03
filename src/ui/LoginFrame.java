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
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel
        panel = new JPanel();
        panel.setBackground(new Color(20,20,20));

        add(panel);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        GridLayout layout = new GridLayout();
        layout.setRows(8);
        layout.setColumns(1);
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);

        // welcome
        welcomeLabel = new JLabel("WELCOME!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 50));
        welcomeLabel.setForeground(new Color(255,215,0));
        panel.add(welcomeLabel);

        //info label : login
        infoLabel = new JLabel("Please login or register");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 27));

        panel.add(infoLabel);

        // username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(usernameLabel);


        //username field
        usernameField = new JTextField();

        usernameField.setBackground(new Color(40,40,40));
        usernameField.setForeground(Color.WHITE);

        panel.add(usernameField);

        // password
        passwordLabel = new JLabel("Password:");

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(passwordLabel);

        //password field
        passwordField = new JPasswordField();

        passwordField.setBackground(new Color(40,40,40));
        passwordField.setForeground(Color.WHITE);

        panel.add(passwordField);

        userService = new UserService();

        // buttons
        //login button
        loginButton = new JButton("Login");
        buttonStyle(loginButton);

        panel.add(loginButton);

        loginButton.addActionListener(e ->{
            String username = usernameField.getText();

            String password = new String(passwordField.getPassword());

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

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(207,255,4),
                        2
                )
        );
    }
}
