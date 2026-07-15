package ui;

import models.HighScore;
import services.GameHistoryService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//displays high scores
public class HighScoreFrame extends JFrame {

    private JLabel titleLabel;
    private JPanel scoresPanel;
    private JButton backButton;
    private JButton refreshButton;

    private GameHistoryService gameHistoryService;

    public HighScoreFrame(){

        gameHistoryService = new GameHistoryService();

        setTitle("High Scores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15,15,15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30,60,30,60));
        mainPanel.setLayout(new BorderLayout(0,25));

        add(mainPanel);

        //title
        titleLabel = new JLabel("HIGH SCORES");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 42));
        titleLabel.setForeground(new Color(255,215,0));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(25,25,25));
        centerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190,0,255), 3),
                        BorderFactory.createEmptyBorder(20,25,20,25)
                )
        );
        centerPanel.setLayout(new BorderLayout(0,15));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        //header row
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1,3));
        headerPanel.setBackground(new Color(35,35,35));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel rankHeader = createHeaderLabel("Rank");
        JLabel usernameHeader = createHeaderLabel("Username");
        JLabel scoreHeader = createHeaderLabel("Score");

        headerPanel.add(rankHeader);
        headerPanel.add(usernameHeader);
        headerPanel.add(scoreHeader);

        centerPanel.add(headerPanel, BorderLayout.NORTH);

        //scores list panel
        scoresPanel = new JPanel();
        scoresPanel.setBackground(new Color(25,25,25));
        scoresPanel.setLayout(new GridLayout(0,1,0,10));

        JScrollPane scrollPane = new JScrollPane(scoresPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(25,25,25));
        scrollPane.setBackground(new Color(25,25,25));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        //buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(15,15,15));
        buttonsPanel.setLayout(new GridLayout(1,2,25,0));

        refreshButton = new JButton("Refresh");
        buttonStyle(refreshButton, new Color(190,0,255));

        backButton = new JButton("Back");
        buttonStyle(backButton, new Color(255,0,0));

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(backButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        loadHighScores();

        refreshButton.addActionListener(e -> {

            loadHighScores();

        });

        backButton.addActionListener(e -> {

            new MainMenu();
            dispose();

        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //create header label
    private JLabel createHeaderLabel(String text){

        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 20));
        label.setForeground(new Color(255,215,0));

        return label;
    }

    //create score row
    private JPanel createScoreRow(int rank, String username, int score){

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new GridLayout(1,3));
        rowPanel.setBackground(new Color(40,40,40));
        rowPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(80,80,80), 1),
                        BorderFactory.createEmptyBorder(12,10,12,10)
                )
        );

        JLabel rankLabel = createRowLabel(String.valueOf(rank));
        JLabel usernameLabel = createRowLabel(username);
        JLabel scoreLabel = createRowLabel(String.valueOf(score));

        rowPanel.add(rankLabel);
        rowPanel.add(usernameLabel);
        rowPanel.add(scoreLabel);

        return rowPanel;
    }

    //create row label
    private JLabel createRowLabel(String text){

        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 19));
        label.setForeground(Color.WHITE);

        return label;
    }

    //button style
    private void buttonStyle(JButton button, Color borderColor){

        button.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(35,35,35));
        button.setBorder(
                BorderFactory.createLineBorder(
                        borderColor,
                        2
                )
        );
        button.setFocusPainted(false);
    }

    //load high scores from database
    private void loadHighScores(){

        scoresPanel.removeAll();

        ArrayList<HighScore> highScores = gameHistoryService.getHighScores();

        if(highScores.isEmpty()){

            JLabel emptyLabel = new JLabel("No scores saved yet.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Consolas", Font.BOLD, 22));
            emptyLabel.setForeground(Color.WHITE);

            scoresPanel.add(emptyLabel);

        } else {

            for(int i = 0; i < highScores.size(); i++){

                HighScore highScore = highScores.get(i);

                JPanel row = createScoreRow(
                        i + 1,
                        highScore.getUsername(),
                        highScore.getScore()
                );

                scoresPanel.add(row);
            }
        }

        scoresPanel.revalidate();
        scoresPanel.repaint();
    }
}