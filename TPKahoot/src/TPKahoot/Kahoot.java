package TPKahoot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Kahoot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JOptionPane.showInputDialog(null, "Entrez l'adresse IP pour se connecter");
		String[] options = {"Client", "Serveur"};
		int choisie = JOptionPane.showOptionDialog(null, "Choisissez serveur ou client", null, 0, 0, null, options, args);
		
		if (choisie == 0) {
			CreatClient();
		}
		else {
			CreatServer();
		}
		
	}
	
	static void CreatClient() {
/*
		Client client = new Client("127.0.0.1", 5555);
		Thread clientThread = new Thread(client);
		clientThread.start();
		String message = JOptionPane.showInputDialog("Entrez le message a envoyer au serveur");
		
		client.sendData(message);//Envoie le message au seveur*/
		
	}
	
	static void CreatServer() {
		/*
		Server server = new Server(5555);
		Thread serverThread = new Thread(server);
		serverThread.start();
		JOptionPane.showMessageDialog(null, "click okay to send question");
		server.sendQuestion();
		JOptionPane.showMessageDialog(null, "Click okay to close the server");
		server.closeServer();*/

		KahootUIServer serverWindow = new KahootUIServer();
	}

}
