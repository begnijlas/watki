import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Pisarz extends Thread {
	private Zasob ksiazka;

	public Pisarz(Zasob m) {
		ksiazka = m;
	}
	
	

	public void run() {
		int x = 0;
		while (x<10) {
			System.out.println("dopisuje : " + x + " ");
			ksiazka.napisz( x + " " );
			try {
				Thread.sleep(((int)Math.random()*15000-2000)+2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x++;
			
		}
	}

}

class Czytelnik extends Thread {
	private Zasob ksiazka;

	public Czytelnik(Zasob m) {
		ksiazka = m;
	}

	public void run() {
		int x=0;
		while (x<10) {
			System.out.print("Odczytuje : \t\t");
			ksiazka.odczytaj();
			try {
				Thread.sleep(((int)Math.random()*15000-2000)+2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			x++;
		}
	}
}

class Zasob {
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private Lock rl = rwl.readLock();
	private Lock wl = rwl.writeLock();
	
	
	private String pismo = "poczatek ";

	public void napisz(String tekst) {
		
		wl.lock();
		pismo += tekst;
		wl.unlock();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void odczytaj() {
		rl.lock();
		System.out.println(pismo);
		rl.unlock();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

public class Czytelnia {

	public static void main(String[] args) {
		Zasob zasob = new Zasob();
		int liczba = 1;
		for(int i=0; i<liczba ; i++){
			new Pisarz(zasob).start();
			
		}
		
		for(int i=0; i<liczba ; i++){
			new Czytelnik(zasob).start();
			
		}
		

	}
}