import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Zadanie3 {
	public static void main(String[] args) {
		System.out.println("Start");
		Stoper.start();

		int suma = 0;
		int[] tablicaLiczb = new int[1000];

		for (int i = 0; i < tablicaLiczb.length; i++) {
			tablicaLiczb[i] = (int) (Math.random() * 11);
		}
		ExecutorService executor = Executors.newFixedThreadPool(10);

		Zadanie[] zadania = new Zadanie[10];

		int podzial = tablicaLiczb.length / zadania.length;
		for (int i = 0; i < zadania.length; i++) {
			zadania[i] = new Zadanie(tablicaLiczb, (i * podzial), ((i + 1) * podzial));
			Future<Integer> future = executor.submit(zadania[i]);
			try {
				suma += future.get();
			} catch (InterruptedException | ExecutionException ex) {
				System.out.println("B³¹d");
			}
		}
		executor.shutdown();

		while (!executor.isTerminated()) {
		}
		System.out.println("Wszystkie w¹tki zakoñczy³y pracê.");

		System.out.println(suma);
		double wynik =  Stoper.stop();
		System.out.println( wynik + " milisekund");
	}
}

class Zadanie implements Callable<Integer> {
	private int poczatek;
	private int koniec;
	private int[] tablica;

	public Zadanie(int[] tablica, int poczatek, int koniec) {
		this.poczatek = poczatek;
		this.koniec = koniec;
		this.tablica = tablica;
	}

	public Integer call() throws Exception {
		int wynik = 0;

		for (int i = poczatek; i < koniec; i++) {
			wynik += tablica[i];
			
			 try { Thread.sleep(1); } catch (InterruptedException e) {
			  System.out.println("B³¹d"); }
			 
		}
		System.out.println("Liczy³em sumê od zakresu: " + poczatek + " do zakresu: " + koniec + " Wynik: " + wynik);
		return wynik;
	}
}

class Stoper {
	private static double start;
	private static double koniec;

	public static void start() {
		start = System.currentTimeMillis();
	}

	public static double stop(){
		koniec= System.currentTimeMillis();
		return koniec - start;
	}
}