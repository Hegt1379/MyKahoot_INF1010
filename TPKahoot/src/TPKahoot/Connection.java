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
	Player player;
	KahootUIServer ui;
    
    
	public Connection(Socket clientSocket, KahootUIServer ui) {
		this.ui = ui;
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
					if (incomming.startsWith("NC:")){
						ui.addPlayer(incomming.substring(3).trim(), this);
					}
					if (incomming.startsWith("RP:")){
						ui.gotAnswer(incomming.substring(3).trim(), this);
					}
				}
		}
	        	
    }
	
	public void sendData(String msg) {
		this.out.println(msg);
	}

	public void sendData(String[] msg){
		this.out.println("NQ:" + msg[0] + "/" + msg[1]);
	}

	public void setPlayer(Player player){
		this.player = player;
	}

	public Player getPlayer(){
		return player;
	}
	
}
