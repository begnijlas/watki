import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

class Okno extends JFrame {
	String zawartosc;
	
	public Okno(String nazwa) {
		super(nazwa);
	
		setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        setVisible(true);
        getContentPane().setBackground(Color.PINK);
        
        JLabel czas = new JLabel(zawartosc, JLabel.CENTER);
        czas.setVerticalTextPosition(JLabel.CENTER);
        add(czas);    
		
		new Thread(new Runnable() {
			public void run() {
				while(true) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {	
					SimpleDateFormat formatDaty = new SimpleDateFormat("HH:mm:ss");
					Date data = new Date();	
					zawartosc = formatDaty.format(data);
					czas.setText(zawartosc);
					czas.setFont(new Font("ARIAL",Font.BOLD, 40));
					czas.setForeground(Color.WHITE);
				}
			});
				try {
					Thread.sleep(1000);
					
				} catch(Exception e) { }
	}
			}
	}).start();
		
}
}

public class Zegar {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Okno("Zegar na watku");
			}
			});
	}
}

