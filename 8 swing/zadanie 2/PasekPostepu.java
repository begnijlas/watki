import java.awt.event.*;
import javax.swing.*;

class Okno extends JFrame {

	
	JButton [] przycisk = new JButton[4];
	JProgressBar [] pasek = new JProgressBar[4];

	public Okno(String nazwa) {
		super(nazwa);

		for(int i=0 ; i<4 ; i++){
		przycisk[i] = new JButton("Start");
		
		pasek[i] = new JProgressBar();
		}

		JPanel panel = new JPanel();

		for(int i=0 ; i<4 ; i++){
		panel.add(przycisk[i]);
		panel.add(pasek[i]);
		przycisk[i].addActionListener(new ObslugaZdarzen(i));
		}

		add(panel);

		setSize(400, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private class ObslugaZdarzen implements ActionListener {
		int i;
		
		
		public ObslugaZdarzen(int i){
			this.i = i;
		}

		public void actionPerformed(ActionEvent e) {
			pasek[i].setValue(0);
			pasek[i].setStringPainted(true);

			new Thread(new Runnable() {
				public void run() {
					przycisk[i].setEnabled(false);
					for (int j = 0; j <= 100; j++) {
						final int postep = j;
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								pasek[i].setValue(postep);
							}
							});
						try {
							Thread.sleep(100 * (i+1));
						} catch (Exception e) {
						}
						if(j==100)przycisk[i].setEnabled(true);
						
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
