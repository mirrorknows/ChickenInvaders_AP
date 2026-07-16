package ui;

import models.HighScore;
import services.GameHistoryService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//displays high scores
public class HighScoreFrame extends JFrame {

    private JLabel titleLabel;
    private JLabel infoLabel;

    private JPanel scoresPanel;

    private JButton backButton;

    private GameHistoryService gameHistoryService;

    public HighScoreFrame(){

        gameHistoryService = new GameHistoryService();

        setTitle("High Scores");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //main panel
        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(new Color(15, 15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        mainPanel.setLayout(new BorderLayout(0, 20));

        add(mainPanel);

        //top panel
        JPanel topPanel = new JPanel();

        topPanel.setBackground(new Color(15, 15, 15));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        mainPanel.add(topPanel, BorderLayout.NORTH);

        //title
        titleLabel = new JLabel("HIGH SCORES");

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 42));
        titleLabel.setForeground(new Color(255, 215, 0));

        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(8));

        //information
        infoLabel = new JLabel("Best score of each player");

        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        infoLabel.setForeground(Color.WHITE);

        topPanel.add(infoLabel);

        //center panel
        JPanel centerPanel = new JPanel();

        centerPanel.setBackground(new Color(25, 25, 25));

        centerPanel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(new Color(61, 61, 80), 3),
                        BorderFactory.createEmptyBorder(10, 8, 10, 8)
                )
        );

        centerPanel.setLayout(new BorderLayout(0, 10));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        //header row
        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(new GridLayout(1, 5));
        headerPanel.setBackground(new Color(35, 35, 35));
        headerPanel.setPreferredSize(new Dimension(0, 42));

        headerPanel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                )
        );

        headerPanel.add(createHeaderLabel("Rank"));
        headerPanel.add(createHeaderLabel("Username"));
        headerPanel.add(createHeaderLabel("Score"));
        headerPanel.add(createHeaderLabel("Level"));
        headerPanel.add(createHeaderLabel("Date"));

        centerPanel.add(headerPanel, BorderLayout.NORTH);

        //scores list
        scoresPanel = new JPanel();

        scoresPanel.setBackground(new Color(25, 25, 25));
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(scoresPanel);

        //vertical scroll
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        //scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        //smaller scroll bar
        scrollPane.getVerticalScrollBar().setPreferredSize(
                new Dimension(10, 0));

        scrollPane.setBorder(null);

        scrollPane.setBackground(new Color(25, 25, 25));
        scrollPane.getViewport().setBackground(new Color(25, 25, 25));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        //bottom button panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonsPanel.setBackground(new Color(15, 15, 15));
        backButton = new JButton("Back");

        backButton.setFont(new Font("Trebuchet MS", Font.BOLD, 18));

        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(35, 35, 35));

        backButton.setPreferredSize(new Dimension(220, 45));

        backButton.setFocusPainted(false);

        backButton.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2));

        buttonsPanel.add(backButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        //back action
        backButton.addActionListener(e -> {

            new MainMenu();
            dispose();

        });

        loadHighScores();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //create header label
    private JLabel createHeaderLabel(String text){

        JLabel label = new JLabel(text);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 16));
        label.setForeground(new Color(255, 215, 0));

        return label;
    }

    //create score row
    private JPanel createScoreRow(
            int rank,
            String username,
            int score,
            int level,
            String playedTime
    ){

        JPanel rowPanel = new JPanel();

        rowPanel.setLayout(new GridLayout(1, 5));

        rowPanel.setBackground(new Color(40, 40, 40));

        //fixed row height
        rowPanel.setPreferredSize(new Dimension(650, 42));
        rowPanel.setMinimumSize(new Dimension(0, 42));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rowPanel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                        BorderFactory.createEmptyBorder(5, 3, 5, 3)
                )
        );

        rowPanel.add(createRowLabel(String.valueOf(rank)));

        rowPanel.add(createRowLabel(username));

        rowPanel.add(createRowLabel(String.valueOf(score)));
        rowPanel.add(createRowLabel(String.valueOf(level)));

        String dateText;

        if(playedTime == null || playedTime.isEmpty()){
            dateText = "-";
        } else {
            dateText = playedTime;
        }

        JLabel dateLabel = createRowLabel(dateText);

        dateLabel.setFont(new Font("Consolas", Font.BOLD, 12));

        rowPanel.add(dateLabel);

        return rowPanel;
    }

    //create row label
    private JLabel createRowLabel(String text){

        JLabel label = new JLabel(text);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 14));
        label.setForeground(Color.WHITE);

        return label;
    }

    //load high scores from database
    private void loadHighScores(){

        ArrayList<HighScore> highScores =
                gameHistoryService.getHighScores();

        if(highScores.isEmpty()){

            JLabel emptyLabel = new JLabel("No scores saved yet.");

            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);

            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            emptyLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            emptyLabel.setFont(new Font("Consolas", Font.BOLD, 22));

            emptyLabel.setForeground(Color.WHITE);

            scoresPanel.add(emptyLabel);

        } else {

            for(int i = 0; i < highScores.size(); i++){

                HighScore highScore = highScores.get(i);

                JPanel row = createScoreRow(
                        i + 1,
                        highScore.getUsername(),
                        highScore.getScore(),
                        highScore.getLevel(),
                        highScore.getPlayedTime()
                );

                scoresPanel.add(row);

                //space between rows
                if(i < highScores.size() - 1){

                    scoresPanel.add(Box.createVerticalStrut(7));

                }
            }
        }
    }
}
