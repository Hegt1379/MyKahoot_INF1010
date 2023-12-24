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
	KahootUIClient ui;

    public Client(String ip, int port, KahootUIClient ui) {
		this.ui = ui;
        try{
	        clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
			String nom = JOptionPane.showInputDialog(null, "Entrez votre nom:");
			sendData("NC:" + nom);
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
				if (serverData.startsWith("NQ:")){
					serverData = serverData.substring(3).trim();
					String[] data = serverData.split("/");
					String[] choix = {data[1], data[2], data[3], data[4]};
					ui.incommingQuestion(data[0], choix);
				}
				if (serverData.startsWith("VAL:")){
	            	String rep = serverData.substring(4).trim();
					ui.incommingValidation(rep);
				}
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
