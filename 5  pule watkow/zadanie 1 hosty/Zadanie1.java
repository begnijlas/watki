import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Zadanie1 {
	public static void main(String[] args) {
		String[] hosty = {"onet.pl", "gazeta.pl", "pudelek.pl", "facebook.com", "bla.c"};
		ExecutorService executor = Executors.newFixedThreadPool(hosty.length);
		for (int i = 0; i < hosty.length; i++) {
			Runnable watek = new Watek("W" + i,i, hosty);
			executor.execute(watek);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Wszystkie w¹tki zakoñczy³y pracê.");
	}
}

class Watek implements Runnable {
	private String nazwa;
	private String[] hosty;
	private int liczba;
	public Watek(String nazwa,int liczba, String[] hosty) {
		this.nazwa = nazwa;
		this.liczba = liczba;
		this.hosty=hosty;
		
	}

	public void run() {
		String x = hosty[liczba];
		Socket socket = null;
		try {
		 socket = new Socket(x, 80);
		 System.out.println("Watek nr "+ nazwa + " Sprawdza Strona: "+ x + " Dzia³a");
		} catch (UnknownHostException e) {
			 System.out.println("Watek nr "+ nazwa + " Sprawdza Strona: "+ x + " Nie Dzia³a");
		} catch (IOException e) {
		} finally {
		 if (socket != null)
		 try {
		 socket.close();
		 } catch(IOException e) {}
		}
		

		
	}
}