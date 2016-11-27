import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Pisarz extends Thread {
	private Zasob ksiazka;

	public Pisarz(Zasob m) {
		ksiazka = m;
	}
	
	public boolean obliczPrawdopodobienstwo(){
		int liczba = ((int) Math.random() * 100 )-7;
		if(liczba < 93) return false;
		else
		return true;
	}

	public void run() {
		int x = 0;
		while (true) {
			if(obliczPrawdopodobienstwo()) System.out.println("dopisuje : " + x + " nie zdalismy i piszemy jutro");
			ksiazka.napisz( x + " " );
			try {
				this.sleep((int)Math.random()*5000);
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
		while (true) {
			System.out.print("Odczytuje : \t\t");
			ksiazka.odczytaj();
			try {
				this.sleep((int)Math.random()*5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	}

	public void odczytaj() {
		rl.lock();
		System.out.println(pismo);
		rl.unlock();
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