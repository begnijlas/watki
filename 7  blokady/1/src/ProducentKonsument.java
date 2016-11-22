package src;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Towar {
	private int kod;
	private double cena;

	public Towar() {
		kod = (int)(Math.random() * 100000);
		cena = (Math.random() * 1000);
	}

	public String toString() {
		return "Kod towaru:" + kod + " Cena: " + cena;
	}
}

class Pojemnik {
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private Lock rl = rwl.readLock();
	private Lock wl = rwl.writeLock();
	private LinkedList<Towar> polka;
	private int max;
	private boolean pusty = true;
	
	
	public Pojemnik(int max){
		polka = new LinkedList<Towar>();
		this.max = max;
	}

	 Towar get() {
		rl.lock();
		try{
		if(pusty){
			try {
				System.out.println ("Konsument: czekam na produkcjê...");
				wait();
			} catch (InterruptedException e) { }
		}
		Towar towar = polka.removeFirst();
		System.out.println(polka);
		System.out.println("Konsumpcja: " + towar);
		if (polka.size() == 0) pusty = true;
		notify();
		return towar;
		}finally{
		rl.unlock();
		}
		
	}

	void put(Towar towar) {
		wl.lock();
		if( !pusty ) {
			try {
				System.out.println ("Producent: czekam na konsumpcjê...");
				wait();
			} catch (InterruptedException e) {}
		}
		polka.add(towar);
		System.out.println(polka);
		System.out.println("Produkcja: " + towar);
		if( polka.size() == max) pusty = false;
		notify();
		wl.unlock();
	}
}

class Producent implements Runnable {
	private Pojemnik p;

	Producent(Pojemnik p) {
		this.p = p;
		new Thread(this, "Producent").start();
	}

	public void run() {
		while(true) {
			p.put(new Towar());
			try {
				Thread.sleep((int)(Math.random() * 1000));
			} catch (InterruptedException e) {}
		}
	}
}

class Konsument implements Runnable {
	private Pojemnik p;

	Konsument(Pojemnik p) {
		this.p = p;
		new Thread(this, "Konsument").start();
	}

	public void run() {
		while(true) {
			p.get();
			try {
				Thread.sleep((int)(Math.random() * 1000));
			} catch (InterruptedException e) { }
		}
	}
}

public class ProducentKonsument {
	public static void main(String args[]) {
		Pojemnik p = new Pojemnik(10);

		new Producent(p);
		new Konsument(p);
		new Konsument(p);
	}
}
