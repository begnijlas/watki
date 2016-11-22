import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Klient implements Runnable {
	private String nazwa;

	public Klient(String nazwa) {
		this.nazwa = nazwa;
	}

	public void run() {

		System.out.println(nazwa + " Wchodzi do resteuracji");
		try {
			Thread.sleep(1000);
			System.out.println("Watek " + nazwa + " siada");
			Thread.sleep(500);
			System.out.println("Watek " + nazwa + " wybiera z menu");
			Thread.sleep(500);
			System.out.println("Watek " + nazwa + " konsumuje");
			Thread.sleep(3000);
			System.out.println("Watek " + nazwa + " placi");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("                                                   Watek " + nazwa + " wychodzi");

		}

	}
}

public class Zadanie2 {
	public static void main(String[] args) {
		int mnoznik = 2;
		ExecutorService executor = Executors.newFixedThreadPool(1 * mnoznik);

		for (int i = 1; i < 4 * mnoznik; i++) {
			Runnable watek = new Klient("K" + i);
			executor.execute(watek);
		}
		executor.shutdown();
	}
}
