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

        panel = new JPanel();
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
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 55));
        panel.add(welcomeLabel);

        //info label : login
        infoLabel = new JLabel("Please login or register");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(infoLabel);

        // username
        usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        // password
        passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        userService = new UserService();

        // buttons
        loginButton = new JButton("Login");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(80, 143, 152));

        panel.add(loginButton);

        loginButton.addActionListener(e ->{
            String username = usernameField.getText();

            String password = new String(passwordField.getPassword());

            User user = userService.loginUser(username, password);

            if(user != null){

                LoggedUser.setUser(user);

                JOptionPane.showMessageDialog(this,
                        "Login Successfully");

            }else{

                JOptionPane.showMessageDialog(this,
                        "your login info is incorrect"
                );
            }
        });

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(255,255,255));

        panel.add(registerButton);

        registerButton.addActionListener(e ->{
            new RegisterFrame();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
