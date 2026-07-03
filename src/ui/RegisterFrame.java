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
        panel.setBackground(new Color(20,20,20));

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

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        welcomeLabel.setForeground(new Color(255,215,0));

        panel.add(welcomeLabel);

        //info label : enter info
        infoLabel = new JLabel("Please Enter Your Information");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 27));

        panel.add(infoLabel);

        //username
        usernameLabel = new JLabel("Username:");

        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(usernameLabel);

        usernameField = new JTextField();

        usernameField.setBackground(new Color(40,40,40));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);

        panel.add(usernameField);

        //password
        passwordLabel = new JLabel("Password:");

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(passwordLabel);

        //password field
        passwordField = new JPasswordField();

        passwordField.setBackground(new Color(40,40,40));
        passwordField.setForeground(Color.WHITE);

        panel.add(passwordField);

        //confirm password
        confirmPasswordLabel = new JLabel("Confirm Password:");

        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(confirmPasswordLabel);

        //confirm password field
        confirmPasswordField = new JPasswordField();

        confirmPasswordField.setBackground(new Color(40,40,40));
        confirmPasswordField.setForeground(Color.WHITE);

        panel.add(confirmPasswordField);

        //register button
        registerButton = new JButton("Register");

        buttonStyle(registerButton);

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

        buttonStyle(backButton);

        panel.add(backButton);

        backButton.addActionListener(e ->{

            new LoginFrame();
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
