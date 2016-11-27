
class Pojemnik {
	private int n;
	private boolean pusty = true;

	synchronized int get() {
		if (pusty) {
			try {
				System.out.println("Konsument: czekam na produkcjê...");
				wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Konsumpcja: " + n);
		pusty = true;
		notify();
		return n;
	}

	synchronized void put(int n) {
		if (!pusty) {
			try {
				System.out.println("Producent: czekam na konsumpcjê...");
				wait();
			} catch (InterruptedException e) {
			}
		}
		this.n = n;
		System.out.println("Produkcja:  " + n);
		pusty = false;
		notify();
	}
}

class Producent implements Runnable {
	private Pojemnik p;

	Producent(Pojemnik p) {
		this.p = p;
		new Thread(this, "Producent").start();
	}

	public void run() {
		for (int i = 1; i <= 10; i++) {
			p.put(i);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
			}
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
		for (int i = 1; i <= 10; i++) {
			p.get();
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
			}
		}
	}
}

public class Zadanie4 {
	public static void main(String args[]) {
		Pojemnik p = new Pojemnik();
		new Producent(p);
		new Konsument(p);

	}
}