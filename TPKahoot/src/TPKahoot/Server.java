package TPKahoot;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server extends JFrame implements Runnable , Updatable{

	private ArrayList<Thread> connectionThread;
	private ArrayList<Connection> connections;
	private int portNumber;
	private ServerSocket serverSocket;
	private boolean isRunning = false;
	
	public Server(int portNumber) {
		connectionThread = new ArrayList<Thread>();
		connections = new ArrayList<Connection>();
		this.portNumber = portNumber;
		
		setTitle("sss");
		setBounds(200,200,700,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void run() {
		if(isRunning) {
			return;
		}
		isRunning = true;
		portNumber = 5555;
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server opened on port " + portNumber);

            this.serverSocket = serverSocket;
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                JOptionPane.showMessageDialog(null, "New connection from " + clientSocket.getInetAddress());

                // Handle each client in a separate thread
                Connection connection = new Connection(clientSocket);
                Thread connectionThread = new Thread(connection);
                connectionThread.start();
                this.connectionThread.add(connectionThread);
                this.connections.add(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void sendQuestion() {
		for (Connection connection : connections) {
			connection.sendData("This is a question");
		}
	}
	
	public void closeServer() {
		try {
			serverSocket.close();
			for (Thread clientThread : connectionThread) {
				try {
					clientThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Server was closed");
	}
	public boolean stillRunning() {return isRunning;}

}
