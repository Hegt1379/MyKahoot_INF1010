package TPKahoot;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Client implements Runnable{
	
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;

    public Client(String ip, int port) {
        try{
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void sendData(String msg) {
        out.println(msg);
    }


	public void run() {
		while(true) {
			//System.out.println("running");
	        if (in.hasNextLine()) {
				System.out.println("Incomming data");
	            String serverData = in.nextLine();
	            JOptionPane.showMessageDialog(null, serverData);
	        }
		}
		
	}

    public void stopConnection() {
		in.close();
        out.close();
        try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    
    
    
}
