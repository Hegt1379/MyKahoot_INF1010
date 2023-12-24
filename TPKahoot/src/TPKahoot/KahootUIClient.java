package TPKahoot;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.NonWritableChannelException;

public class KahootUIClient extends JFrame {
    // UI Components
    private Client client;
    private Thread clientThread;
    private JLabel questionLabel;
    private JButton optionAButton;
    private JButton optionBButton;
    private JButton optionCButton;
    private JButton optionDButton;
    private JLabel timerLabel;
    private JPanel gamePanel;
    private JLabel stateLabel;
    private int scoreGood = 0;
    private int scoreBad = 0;
    public KahootUIClient() {

        String ip = JOptionPane.showInputDialog(null, "Entrez l'adresse ip du destinataire", "127.0.0.1");
    	
        client = new Client(ip, 5555, this);
        clientThread = new Thread(client);
        clientThread.start();


    	JPanel statePanel = new JPanel(new BorderLayout());
    	stateLabel = new JLabel("Score: en attente d'une première question");
    	
    	statePanel.setPreferredSize(new Dimension(this.getWidth(),200));
    	
    	statePanel.add(stateLabel,BorderLayout.CENTER);
        // Initialize the components
    	Font f = new Font("Arial", Font.BOLD, 20);
        questionLabel = new JLabel("Question");
        
        optionAButton = new JButton("OptionA");
        optionAButton.setBackground(Color.RED);

        var obj = this;

        optionAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData("RP:" + optionAButton.getText());
                obj.setSize(800,200);
                gamePanel.setVisible(false);
            }
        });
        
        optionBButton = new JButton("OptionB");
        optionBButton.setBackground(Color.GREEN);

        optionBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData("RP:" + optionBButton.getText());
                obj.setSize(800,200);
                gamePanel.setVisible(false);
            }
        });
        
        optionCButton = new JButton("OptionC");
        optionCButton.setBackground(Color.BLUE);

        optionCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData("RP:" + optionCButton.getText());
                obj.setSize(800,200);
                gamePanel.setVisible(false);
            }
        });
        
        optionDButton = new JButton("OptionD");
        optionDButton.setBackground(Color.YELLOW);

        optionDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData("RP:" + optionDButton.getText());
                obj.setSize(800,200);
                gamePanel.setVisible(false);
            }
        });
        
        timerLabel = new JLabel("Time: 0");
        
        gamePanel = new JPanel(new BorderLayout());

        // Layout setup
        
        JPanel questionPanel = new JPanel(new GridLayout(1, 1));
        questionPanel.add(questionLabel);
        gamePanel.add(questionPanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        optionsPanel.add(optionAButton);
        optionsPanel.add(optionBButton);
        optionsPanel.add(optionCButton);
        optionsPanel.add(optionDButton);
        gamePanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.add(timerLabel);
        gamePanel.add(timerPanel, BorderLayout.SOUTH);
        
        this.setLayout(new BorderLayout());
        this.add(statePanel,BorderLayout.NORTH);
        this.add(gamePanel,BorderLayout.CENTER);
        gamePanel.setVisible(false);
      

        // Setting up the frame
        this.setTitle("Client UI");
        this.setSize(800, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // stateLabel.setVisible(false);
        this.setVisible(true);
    }

    public void incommingQuestion(String question, String[] choix){
        var obj = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                optionAButton.setText(choix[0]);
                optionBButton.setText(choix[1]);
                optionCButton.setText(choix[2]);
                optionDButton.setText(choix[3]);

                questionLabel.setText("Question: " + question);
                obj.setSize(800, 600);
                gamePanel.setVisible(true);            
                }
        });
    }

        public void incommingValidation(String validation){
        var obj = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (validation.equals("1")){
                    JOptionPane.showMessageDialog(null, "Bonne reponse");
                    scoreGood++;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Mauvaise reponse");
                    scoreBad++;
                }
                stateLabel.setText("Score: " + scoreGood + " Réussie, " + scoreBad + " Ratée (" + (double)scoreGood / (scoreGood + scoreBad) * 100 + "%)");
            }
        });
    }

    public static void main(String[] args) {
        // Run the UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KahootUIClient();
            }
        });
    }
}