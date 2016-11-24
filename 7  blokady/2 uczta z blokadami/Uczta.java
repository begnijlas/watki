import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Uczta {

	public final static int liczbaFilozofow = 5;

	public final static Filozof[] filozofowie = new Filozof[liczbaFilozofow];

	public final static Semaphore semafor = new Semaphore(1);

	public static void main(String[] args) {

		filozofowie[0] = new Filozof(0);

		for (int i = 1; i < liczbaFilozofow; i++) {
			filozofowie[i] = new Filozof(i);
		}

		for (Thread t : filozofowie) {
			t.start();
		}
	}

	public static class Filozof extends Thread {
		private ReentrantLock widelec = new ReentrantLock();
		private ReentrantLock filozof = new ReentrantLock();

		private enum Stan {
			MYSLI, GLODNY, JE
		};

		private final int numer;
		private Stan stan;
		private final Semaphore sem = new Semaphore(0);

		public Filozof(int numer) {
			this.numer = numer;
			stan = Stan.MYSLI;
		}

		private Filozof lewo() {
			return filozofowie[numer == 0 ? liczbaFilozofow - 1 : numer - 1];
		}

		private Filozof prawo() {
			return filozofowie[(numer + 1) % liczbaFilozofow];
		}

		public void run() {

			while (true) {
				wyswietlStan();
				switch (stan) {

				case MYSLI:
					mysliLubJe();
					widelec.lock();
					stan = Stan.GLODNY;
					break;

				case GLODNY:
					// nabywa oba widelce, tylko jeœli ¿aden s¹siad nie je w tym
					// momencie w innym wypadku czeka
					filozof.lock();
					sprawdz(this);
					widelec.unlock();
					stan = Stan.JE;
					break;

				case JE:
					mysliLubJe();
					widelec.lock();
					stan = Stan.MYSLI;
					// jesli glodny sasiad moze teraz zjesc, powiadom go
					sprawdz(lewo());
					sprawdz(prawo());
					widelec.unlock();
					break;
				}
			}
		}

		private static void sprawdz(Filozof f) {
			if (f.lewo().stan != Stan.JE && f.stan == Stan.GLODNY && f.prawo().stan != Stan.JE) {
				f.stan = Stan.JE;
				f.filozof.unlock();
			}
		}

		private void mysliLubJe() {
			try {
				Thread.sleep((long) Math.round(Math.random() * 5000));
			} catch (InterruptedException e) {
			}
		}

		private void wyswietlStan() {
			System.out.println("Stan Filozofa numer: " + numer + " " + stan);
		}
	}
}