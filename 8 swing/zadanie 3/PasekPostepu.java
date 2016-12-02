import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

class Okno extends JFrame {

	DateFormat df = new SimpleDateFormat("HH:mm:ss");
	Date dateobj = new Date();
	final JLabel label;

	public Okno(String nazwa) {
		super(nazwa);
		
		
		label = new JLabel(df.format(dateobj));
		JPanel panel = new JPanel();
		panel.add(label);
		new ObslugaZdarzen();
		

		add(panel);

		setSize(400, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private class ObslugaZdarzen  {
		

		public void actionPerformed(ActionEvent e) {
			

			new Thread(new Runnable() {
				public void run() {
					while(true){
						label.setText(df.format(dateobj));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
						
					}
				
			}).start();
		}
	

	}

}

public class PasekPostepu {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Okno("Pasek postêpu");
			}
		});
	}
}
