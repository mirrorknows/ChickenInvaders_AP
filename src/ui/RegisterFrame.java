package ui;

import helpers.ImageLoader;
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
        setSize(800,600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userService = new UserService();

        //load background image
        Image backgroundImage = ImageLoader.loadImage(
                "/images/backgrounds/login_register.png"
        );

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


        //welcome
        welcomeLabel = new JLabel("CREATE ACCOUNT");

        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        welcomeLabel.setForeground(new Color(255,215,0));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(8));

        //info label : enter info
        infoLabel = new JLabel("Please Enter Your Information");

        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 27));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(20));

        //username
        usernameLabel = new JLabel("Username:");

        usernameLabel.setPreferredSize(new Dimension(320, 25));
        usernameLabel.setMaximumSize(new Dimension(320, 25));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5));

        usernameField = new JTextField();

        usernameField.setPreferredSize(new Dimension(320, 40));
        usernameField.setMaximumSize(new Dimension(320, 40));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setFont(new Font("Consolas", Font.PLAIN, 18));
        usernameField.setBackground(new Color(40,40,40));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);

        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(12));

        //password
        passwordLabel = new JLabel("Password:");

        passwordLabel.setPreferredSize(new Dimension(320, 25));
        passwordLabel.setMaximumSize(new Dimension(320, 25));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(5));

        //password field
        passwordField = new JPasswordField();

        passwordField.setPreferredSize(new Dimension(320, 40));
        passwordField.setMaximumSize(new Dimension(320, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setFont(new Font("Consolas", Font.PLAIN, 18));

        passwordField.setBackground(new Color(40,40,40));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);

        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(12));

        // confirm password
        confirmPasswordLabel = new JLabel("Confirm Password:");

        confirmPasswordLabel.setPreferredSize(new Dimension(320, 25));
        confirmPasswordLabel.setMaximumSize(new Dimension(320, 25));
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setFont(new Font("Consolas", Font.PLAIN, 20));

        panel.add(confirmPasswordLabel);
        panel.add(Box.createVerticalStrut(5));

        //confirm password field
        confirmPasswordField = new JPasswordField();

        confirmPasswordField.setPreferredSize(new Dimension(320, 40));
        confirmPasswordField.setMaximumSize(new Dimension(320, 40));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setFont(new Font("Consolas", Font.PLAIN, 18));

        confirmPasswordField.setBackground(new Color(40,40,40));
        confirmPasswordField.setForeground(Color.WHITE);
        confirmPasswordField.setCaretColor(Color.WHITE);

        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(25));

        //register button
        registerButton = new JButton("Register");

        buttonStyle(registerButton);

        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(15));

        registerButton.addActionListener(e ->{

            String username = usernameField.getText().trim();

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

            //create a new user with default game settings
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
        panel.add(Box.createVerticalGlue());

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
