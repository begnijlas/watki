import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

class Okno extends JFrame implements ActionListener {
	JLabel czas;
	JButton start;
	JButton stop;
	JPanel panel;
	ZegarBack zegar = new ZegarBack(this);

	public Okno(String nazwa) {

		super(nazwa);
		setSize(200, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		czas = new JLabel("", JLabel.CENTER);
		czas.setFont(new Font("ARIAL", Font.BOLD, 40));
		czas.setForeground(Color.WHITE);
		czas.setVerticalTextPosition(JLabel.CENTER);
		start = new JButton("Start");
		stop = new JButton("Stop");
		start.addActionListener(this);
		stop.addActionListener(this);
		panel = new JPanel();
		panel.add(czas);
		panel.add(start);
		panel.add(stop);
		panel.setBackground(Color.BLUE);
		add(panel);
		setVisible(true);
		zegar.start();

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == stop) {
			zegar.zatrzymaj();
		} else if (source == start) {
			zegar.wznow();
		}
	}

	public void ustawCzas(String zawartosc) {
		czas.setText(zawartosc);
	}

}

class ZegarBack extends Thread {
	SimpleDateFormat formatDaty = new SimpleDateFormat("HH:mm:ss");
	String zawartosc = "";
	private Okno okno;
	private volatile boolean pauza = false;

	public ZegarBack(Okno okno) {
		this.okno = okno;
	}

	public void zatrzymaj() {
		pauza = true;
	}

	public void wznow() {
		pauza = false;
	}

	public void run() {
		while (true) {
			while (!pauza) {

				Date data = new Date();
				zawartosc = formatDaty.format(data);
				okno.ustawCzas(zawartosc);
				System.out.println(zawartosc);
				try {
					Thread.sleep(1000);

				} catch (Exception e) {
				}
			}
		}
	}
}

public class Zegar {

	public static void main(String[] args) {

		Okno okno = new Okno("Zegar na watku");

	}
}
