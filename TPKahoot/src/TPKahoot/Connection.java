package TPKahoot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Connection implements Runnable{
	
    PrintWriter out;
    Scanner in;
    
    
	public Connection(Socket clientSocket) {
		
        try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new Scanner(new InputStreamReader(clientSocket.getInputStream()));   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       

	}
	
	public void run() {	
		while (true) {
		        String incomming;
				if (in.hasNextLine()) {
					incomming = in.nextLine();
			        sendData("Conferming message: " + incomming);
				}
		}
	        	
    }
	
	public void sendData(String msg) {
		this.out.println(msg);
	}
	
}
