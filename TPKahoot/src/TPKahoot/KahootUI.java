package TPKahoot;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class KahootUI {
	private Server server ;
	
    public KahootUI() {
        server = new Server(5555);
    	
        JFrame frame = new JFrame("Kahoot");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3,1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        JButton gamerBotton = new JButton("GAMER");
        JButton hostBotton = new JButton("HOST");
        JButton quitBotton = new JButton("QUIT");
        
    
        
        ActionListener menuBottonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(e.getSource()== hostBotton) {
            		if(isServerisRunning()) {
            			JOptionPane.showMessageDialog(null, "Server is already running");
            			return;
            		}
            	}showView();
            }
        };
        gamerBotton.addActionListener(menuBottonListener);
        hostBotton.addActionListener(menuBottonListener);
        quitBotton.addActionListener(menuBottonListener);
        frame.add(gamerBotton);
        frame.add(hostBotton);
        frame.add(quitBotton);    
        
        frame.setVisible(true);
    }
    
    public void showView() {
    	server.run();
    	server.setVisible(true);
    }
    
    public boolean isServerisRunning() {
    	return server.stillRunning();
    }
     public static void main(String[] args) {
    	 new KahootUI();
     }
}
