import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

class TestPierwszosci implements Callable<Boolean> {
	private int n;
	private String name;

	public TestPierwszosci(int liczba, String name) {
		this.n = liczba;
		this.name = name;
	}

	@Override
	public Boolean call() throws Exception {
		System.out.println( Thread.currentThread().getName()); 
		if (n > 2) {
			double sq = Math.sqrt(n);
			if (n % 2 == 0)
				return false;
			else {
				for (int i = 3; i <= sq; i += 2) {
					if (n % i == 0) {
						return false;
					}
				}
				return true;
			}
		} else if (n == 2)
			return true;
		return false;
		
	}
	
	public String getName(){
		return name;
	}

}

public class Zadanie4 {

	public static void main(String[] args) {
		int liczba = 100;  // podaj liczbe jaka ma byc sprawdzona od 1  do lcizba
		ExecutorService executor = Executors.newFixedThreadPool(liczba);
		TestPierwszosci[] watek = new TestPierwszosci[liczba + 1];
		for (int i = 1; i < liczba + 1; i++) {
			watek[i] = new TestPierwszosci(i, "Watek" + i);
			Future<Boolean> future = executor.submit(watek[i]);
			try {
				if (future.get() == true) {
					System.out.println(watek[i].getName() + " Znalazl liczbe \t\t\t\t\t\t " + i);
				}else System.out.println(watek[i].getName() + " Nie znalazl liczby pierwszej");
				
			} catch (InterruptedException | ExecutionException ex) {
				// TODO Auto-generated catch block
			}

		}
	}

}
