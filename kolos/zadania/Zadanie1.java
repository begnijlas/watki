import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class A {
	private int m;
	private int[] zasoby;
	private boolean dostepny = true;

	public A(int m) {
		this.m = m;
		zasoby = new int[m];
	}

	public synchronized int get() {
		try {
			dostepny = false;
			return zasoby[0];
		} finally {
			notify();
		}

	}

	public synchronized boolean czyDostepny() {
		if (!dostepny)
			return false;
		else
			return true;
	}

	public void czekaj() {
		try {
			wait();
			this.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class B {
		private int n;
		private double[] zasoby;
		boolean dostepny = true;

		public B(int n) {
			this.n = n;
			zasoby = new double[n];

		}

		public synchronized double get() {
			try {
				dostepny = false;
				return zasoby[0];
			} finally {
				notify();
			}
		}

		public synchronized boolean czyDostepny() {
			if (!dostepny)
				return false;
			else
				return true;
		}

		public void czekaj() {
			try {
				wait();
				this.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class Proces1 implements Runnable {
		private A a;

		public void run() {

			a.get();

		}
	}

	class Proces2 implements Runnable {
		private A a;
		private B b;

		public void run() {

			if (a.czyDostepny()) {
				a.get();
			} else if (b.czyDostepny()) {
				b.get();
			} else if (!a.czyDostepny())
				a.czekaj();
			else if (!b.czyDostepny())
				b.czekaj();

		}

	}

	class Proces3 implements Runnable {
		private A a;
		private B b;

		public void run() {
			if (a.czyDostepny()) {
				a.get();
			} else if (b.czyDostepny()) {
				b.get();
			}
		}
	}

}

public class Zadanie1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int mnoznik = 1;
		int m = 1 * mnoznik;
		int n = 1 * mnoznik * 2;
		A a = new A(m);
		//brak czasu na dokonczenie main
		ExecutorService executor = Executors.newFixedThreadPool(5);

	}

}
