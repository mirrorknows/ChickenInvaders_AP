package ui;

import models.User;
import services.LoggedUser;
import services.UserService;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//displays planes and allows user to select one
public class StoreFrame extends JFrame {

    private JLabel selectedPlaneLabel;

    private JButton defaultButton;
    private JButton fastButton;
    private JButton heavyButton;
    private JButton sniperButton;
    private JButton backButton;

    private UserService userService;
    private User user;

    public StoreFrame() {

        userService = new UserService();
        user = LoggedUser.getUser();

        setTitle("Store");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(
                new Color(20, 20, 20)
        );

        add(mainPanel);

        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(
                new BoxLayout(headerPanel, BoxLayout.Y_AXIS)
        );

        headerPanel.setBackground(
                new Color(20, 20, 20)
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        7,
                        20
                )
        );

        JLabel titleLabel = new JLabel("STORE");

        titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        titleLabel.setForeground(new Color(255, 215, 0));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));

        selectedPlaneLabel = new JLabel(
                "High Score: " +
                        user.getHighScore() +
                        "     |     Selected: " +
                        user.getSelectedPlane()
        );

        selectedPlaneLabel.setFont(new Font("Consolas", Font.BOLD, 14));

        selectedPlaneLabel.setForeground(Color.WHITE);

        selectedPlaneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(selectedPlaneLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);


        //planes
        JPanel planesPanel = new JPanel(new GridLayout(4, 1, 0, 8));

        planesPanel.setBackground(
                new Color(20, 20, 20)
        );

        planesPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        18,
                        5,
                        18
                )
        );

        defaultButton = createSelectButton();

        JPanel defaultPanel = createPlanePanel(
                "Default",
                "FREE",
                "Speed: 5   Fire Rate: 300 ms   Lives: 3",
                "Ability: -",
                "/images/planes/5.png",
                defaultButton
        );

        fastButton = createSelectButton();

        JPanel fastPanel = createPlanePanel(
                "Fast",
                "Required Score: 5000",
                "Speed: 7   Fire Rate: 250 ms   Lives: 3",
                "Ability: Higher movement speed",
                "/images/planes/4.png",
                fastButton
        );

        heavyButton = createSelectButton();

        JPanel heavyPanel = createPlanePanel(
                "Heavy",
                "Required Score: 8000",
                "Speed: 4   Fire Rate: 200 ms   Lives: 5",
                "Ability: Starts with 5 lives",
                "/images/planes/7.png",
                heavyButton
        );

        sniperButton = createSelectButton();

        JPanel sniperPanel = createPlanePanel(
                "Sniper",
                "Required Score: 10000",
                "Speed: 5   Fire Rate: 150 ms   Lives: 3",
                "Ability: Double boss damage",
                "/images/planes/6.png",
                sniperButton
        );

        planesPanel.add(defaultPanel);
        planesPanel.add(fastPanel);
        planesPanel.add(heavyPanel);
        planesPanel.add(sniperPanel);

        mainPanel.add(planesPanel, BorderLayout.CENTER);


        //back button
        JPanel bottomPanel = new JPanel();

        bottomPanel.setBackground(new Color(20, 20, 20));

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        7,
                        0,
                        10,
                        0
                )
        );

        backButton = new JButton("Back");

        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(40, 40, 40));

        backButton.setPreferredSize(new Dimension(170, 38));

        backButton.setFocusPainted(false);

        backButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 0, 0),
                        2
                )
        );

        bottomPanel.add(backButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //actions
        defaultButton.addActionListener(e ->
                selectPlane("Default", 0)
        );

        fastButton.addActionListener(e ->
                selectPlane("Fast", 5000)
        );

        heavyButton.addActionListener(e ->
                selectPlane("Heavy", 8000)
        );

        sniperButton.addActionListener(e ->
                selectPlane("Sniper", 10000)
        );

        backButton.addActionListener(e -> {

            new MainMenu();
            dispose();
        });

        updateButtons();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //create plane row
    private JPanel createPlanePanel(
            String planeName,
            String price,
            String properties,
            String ability,
            String imagePath,
            JButton selectButton
    ) {

        JPanel planePanel = new JPanel(new BorderLayout(12, 0));

        planePanel.setBackground(new Color(32, 32, 32));

        planePanel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(255, 215, 0),
                                1
                        ),

                        BorderFactory.createEmptyBorder(
                                5, 10,
                                5, 10
                        )
                )
        );

       //plane images
        JLabel imageLabel =
                createPlaneImage(imagePath);

        imageLabel.setPreferredSize(new Dimension(80, 75));

        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        planePanel.add(imageLabel, BorderLayout.WEST);

        //plane info
        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        infoPanel.setBackground(new Color(32, 32, 32));

        JLabel nameLabel = new JLabel(planeName);

        nameLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        nameLabel.setForeground(Color.WHITE);
        infoPanel.add(nameLabel);

        JLabel priceLabel = new JLabel(price);

        priceLabel.setFont(new Font("Consolas", Font.BOLD, 12));

        priceLabel.setForeground(new Color(255, 215, 0));

        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalStrut(3));

        JLabel propertiesLabel = new JLabel(properties);

        informationStyle(propertiesLabel);
        infoPanel.add(propertiesLabel);

        JLabel abilityLabel = new JLabel(ability);

        informationStyle(abilityLabel);
        infoPanel.add(abilityLabel);

        planePanel.add(infoPanel, BorderLayout.CENTER);

        //select buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        buttonPanel.setBackground(new Color(32, 32, 32));

        buttonPanel.setPreferredSize(new Dimension(115, 75));

        buttonPanel.add(selectButton);

        planePanel.add(buttonPanel, BorderLayout.EAST);

        return planePanel;
    }

    //creates plane image
    private JLabel createPlaneImage(String imagePath) {

        JLabel imageLabel = new JLabel();

        URL imageUrl = getClass().getResource(imagePath);

        if(imageUrl != null) {

            ImageIcon icon = new ImageIcon(imageUrl);

            Image scaledImage =
                    icon.getImage().getScaledInstance(
                            60, 60,
                            Image.SCALE_SMOOTH
                    );

            imageLabel.setIcon(new ImageIcon(scaledImage));

        } else {

            imageLabel.setText("NO IMAGE");
            imageLabel.setFont(new Font("Consolas", Font.PLAIN, 10));
            imageLabel.setForeground(Color.LIGHT_GRAY);
        }

        return imageLabel;
    }

    //creates select button
    private JButton createSelectButton() {

        JButton button = new JButton("SELECT");

        button.setFont(new Font("Trebuchet MS", Font.BOLD, 11));

        button.setForeground(Color.WHITE);
        button.setBackground(new Color(40, 40, 40));

        button.setPreferredSize(new Dimension(90, 32));

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 215, 0),
                        1
                )
        );

        return button;
    }

    //styles plane information
    private void informationStyle(
            JLabel label
    ) {

        label.setFont(new Font("Consolas", Font.PLAIN, 11));
        label.setForeground(new Color(220, 220, 220));
    }

    //selects plane if user has enough score
    private void selectPlane(String planeName, int neededScore) {

        if(planeName.equals(user.getSelectedPlane())) {
            return;
        }

        if(user.getHighScore() < neededScore) {

            JOptionPane.showMessageDialog(
                    this,
                    "You need a high score of " +
                            neededScore +
                            " to unlock this plane."
            );
            return;
        }

        boolean saved =
                userService.updateSelectedPlane(
                        user.getUsername(),
                        planeName
                );

        if(saved) {

            user.setSelectedPlane(planeName);

            selectedPlaneLabel.setText(
                    "High Score: " +
                            user.getHighScore() +
                            "     |     Selected: " +
                            planeName
            );

            updateButtons();

            JOptionPane.showMessageDialog(
                    this,
                    planeName +
                            " plane selected."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Plane could not be selected."
            );
        }
    }

    //updates plane buttons
    private void updateButtons() {

        updateButton(defaultButton, "Default", 0);
        updateButton(fastButton, "Fast", 5000);
        updateButton(heavyButton, "Heavy", 8000);
        updateButton(sniperButton, "Sniper", 10000);
    }

    //changes button based on plane state
    private void updateButton(JButton button, String planeName, int requiredScore) {

        boolean selected =
                planeName.equals(
                        user.getSelectedPlane()
                );

        boolean unlocked = user.getHighScore() >= requiredScore;

        if(selected) {

            button.setText("SELECTED");
            button.setBackground(new Color(45, 145, 70));

            button.setEnabled(true);

        } else if(unlocked) {

            button.setText("SELECT");
            button.setBackground(new Color(40, 40, 40));
            button.setEnabled(true);

        } else {

            button.setText("LOCKED");
            button.setEnabled(false);
        }
    }
}