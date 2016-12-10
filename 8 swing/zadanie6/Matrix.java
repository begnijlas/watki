package matrix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;

class Opcje extends JFrame implements ActionListener {
	JButton dodaj;
	JButton odejmij;
	JLabel stan;
	JPanel panelOpcji;
	int mnoznik = 50;

	public Opcje() {
		panelOpcji = new JPanel();
		dodaj = new JButton("dodaj");
		odejmij = new JButton("odejmij");
		stan = new JLabel(Integer.toString(mnoznik));
		dodaj.addActionListener(this);
		odejmij.addActionListener(this);
		panelOpcji.add(stan);
		panelOpcji.add(dodaj);
		panelOpcji.add(odejmij);
		panelOpcji.add(new JLabel("<html>UWAGA <br>ponizej 3 mnoznika<br>  komputer wybucha</html>"));
		add(panelOpcji);
		setBounds(1000, 100, 200, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == dodaj) {
			if (mnoznik > 20)
				mnoznik += 10;
			else
				mnoznik++;
			if (mnoznik > 1)
				odejmij.setEnabled(true);
			stan.setText(Integer.toString(mnoznik));

		} else if (source == odejmij) {
			if (mnoznik > 20)
				mnoznik -= 10;
			else
				mnoznik--;
			if (mnoznik == 1)
				odejmij.setEnabled(false);
			stan.setText(Integer.toString(mnoznik));
		}
	}

	public int pobierzMnoznik() {
		return mnoznik;
	}
}

class Okno extends JFrame {
	private int wysokosc = 400;
	private int szerokosc = 1000;
	private int liczbaKolumn = szerokosc / 10;
	private int liczbaWierszy = wysokosc / 10;
	private JLabel[][] tablica = new JLabel[liczbaKolumn][liczbaWierszy];
	private Semaphore[] semafory = new Semaphore[liczbaKolumn];
	JPanel panel;

	public Okno(String nazwa) {
		super(nazwa);
		panel = new JPanel();
		for (int i = 0; i < liczbaKolumn; i++) {
			semafory[i] = new Semaphore(1);
			for (int j = 0; j < liczbaWierszy; j++) {
				tablica[i][j] = new JLabel("" + (char) (i + 40));
				tablica[i][j].setBounds(i * 10, j * 10, 10, 10);
				tablica[i][j].setVisible(false);
				tablica[i][j].setForeground(Color.GREEN);
				panel.add(tablica[i][j]);
			}
		}
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		add(panel);
		setResizable(false);
		setSize(szerokosc, wysokosc);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public int getLiczbaKolumn() {
		return liczbaKolumn;
	}

	public int getLiczbaWierszy() {
		return liczbaWierszy;
	}

	public JLabel getTablica(int i, int j) {
		return tablica[i][j];
	}

	public Semaphore getSemaphore(int numer) {
		return semafory[numer];
	}

}

class Watek extends Thread {
	private Opcje opcje;
	private Okno okno;
	private int kolumna;

	public Watek(int numer, Okno okno, Opcje opcje) {
		this.kolumna = numer;
		this.okno = okno;
		this.opcje = opcje;
	}

	public void run() {
		while (true) {
			try {
				okno.getSemaphore(kolumna).acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for (int wiersz = 0; wiersz < okno.getLiczbaWierszy(); wiersz++) {
				okno.getTablica(kolumna, wiersz).setVisible(true);
				okno.getTablica(kolumna, wiersz).setText("" + (char) (Math.random() * 250));
				try {
					Thread.sleep((int) (Math.random() * (10 * opcje.pobierzMnoznik())));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			new Cleaner().start();
			okno.getSemaphore(kolumna).release();
		}
	}

	private class Cleaner extends Thread {

		public void run() {
			for (int wiersz = 0; wiersz < okno.getLiczbaWierszy(); wiersz++) {
				okno.getTablica(kolumna, wiersz).setVisible(false);
				try {
					Thread.sleep(5 * opcje.pobierzMnoznik());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

public class Matrix {

	public static void main(String[] args) throws InterruptedException {
		Okno okno = new Okno("nazwa");
		Opcje opcje = new Opcje();
		Watek[] watek = new Watek[100];
		for (int i = 0; i < 100; i++) {
			watek[i] = new Watek(i, okno, opcje);
			// Thread.sleep((int) (300));
			watek[i].start();
		}

	}
}
