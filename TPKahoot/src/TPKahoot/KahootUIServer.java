package TPKahoot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KahootUIServer extends JFrame {
    private Server server;
    private Thread serverThread;
    private static int portNumber = 5555;
    private int questionNumber = 0;
    private ArrayList<String[]> Questions;
    private ArrayList<Player> Players;
    private JTextArea participant;

    public KahootUIServer() {
        setSize(400, 300);

        server = new Server(portNumber, this);
        serverThread = new Thread(server);
        serverThread.start();

        Players = new ArrayList<Player>();



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        participant = new JTextArea("Players:");
        participant.setEditable(false);  // Make it non-editable

        JButton button1 = new JButton("Send next question");
        JButton button2 = new JButton("Restart");
        JButton button3 = new JButton("End Kahoot");

        buttonPanel.add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendNextQuestion();
            }
        });
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        add(new JScrollPane(participant), BorderLayout.CENTER);  // Use a JScrollPane for the JTextArea

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            Questions = readQuestionFile("C:\\Users\\gab77\\Desktop\\ \\MyKahoot_INF1010\\TPKahoot\\src\\TPKahoot\\Questions.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void sendNextQuestion() {
        String[] send = {Questions.get(questionNumber)[0], Questions.get(questionNumber)[1]};
        server.sendQuestion(send);
        questionNumber++;
    }

    public void addPlayer(String name, Connection connection) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Player newPlayer = new Player(name);
                connection.setPlayer(newPlayer);
                Players.add(newPlayer);
                participant.append("\n" + name);  // Use append for JTextArea
            }
        });
    }

    public void gotAnswer(String answer, Connection connection) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (answer.equals(Questions.get(questionNumber - 1)[2])){
                    connection.sendData("VAL:" + "1");
                    connection.getPlayer().bonneReponse();
                }
                else{
                    connection.sendData("VAL:" + "0");
                    connection.getPlayer().mauvaiseReponse();
                }
                refresh();
            }
        });
        
    }

    private static ArrayList<String[]> readQuestionFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] currentQuestion = null;
            ArrayList<String[]> list = new ArrayList<String[]>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Q:")) {
                    currentQuestion = new String[3];
                    currentQuestion[0] = line.substring(2).trim();
                } else if (line.startsWith("C:")) {
                    currentQuestion[1] = line.substring(2).trim();
                } else if (line.startsWith("R:")) {
                    currentQuestion[2] = line.substring(2).trim();
                    list.add(currentQuestion);
                }
            }

            return list;
        }
    }

    private void refresh(){
        String text = "Players:";
        for (int i = 0; i < Players.size(); i++){
            text += "\n" + Players.get(i).getText();
        }
        participant.setText(text);
    }

    public static void main(String[] args) {
        new KahootUIServer();
    }
}
