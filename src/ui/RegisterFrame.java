package ui;

import models.User;
import services.UserService;

import javax.swing.*;
import java.awt.*;

//this class creates new user account

public class RegisterFrame extends JFrame {

    //fields
    private UserService userService;

    //panel
    private JPanel panel;

    //labels
    private JLabel welcomeLabel;
    private JLabel infoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;

    //text fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    //buttons
    private JButton registerButton;
    private JButton backButton;

    public RegisterFrame(){
        setTitle("Register");
        setSize(550,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userService = new UserService();

        panel = new JPanel();
        panel.setBackground(new Color(111, 144, 175));
        add(panel);

        panel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));


        GridLayout layout = new GridLayout();
        layout.setRows(10);
        layout.setColumns(1);
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);

        //welcome
        welcomeLabel = new JLabel("CREATE ACCOUNT");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        panel.add(welcomeLabel);

        //info label : enter info
        infoLabel = new JLabel("Please Enter Your Information");
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(infoLabel);

        //username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        //password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        //confirm password
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        //register button
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(255, 255, 255));
        panel.add(registerButton);

        registerButton.addActionListener(e ->{

            String username = usernameField.getText();

            String password = new String(passwordField.getPassword());

            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() ||
            confirmPassword.isEmpty()){

                JOptionPane.showMessageDialog(this,
                        "Please fill all the fields.");
                return;
            }

            if(!password.equals(confirmPassword)){

                JOptionPane.showMessageDialog(this,
                        "Passwords don't match.");
                return;
            }

            User user = new User(username, password,
                    0,0,1,
                    true,true,
                    true,true);

            //register new user
            boolean success = userService.registerUser(user);
            if(success){
                JOptionPane.showMessageDialog(this,
                        "Registered Successfully");

                new LoginFrame();
                dispose();
            }else{
                JOptionPane.showMessageDialog(this,
                        "Username already exists! Try Again.");
            }
        });

        //back button
        backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(4, 17, 49));
        panel.add(backButton);

        backButton.addActionListener(e ->{

            new LoginFrame();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
