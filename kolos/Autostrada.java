package autostrada;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Samochod extends Thread {
	private String nazwa;
	private boolean kierunek;
	private boolean karetka = false;
	private static final Semaphore polnoc = new Semaphore(2, true);
	private static final Semaphore poludnie = new Semaphore(2, true);
	private Lock alarm = new ReentrantLock();
	

	public Samochod(String nazwa, boolean kierunek, boolean karetka) {
		this.nazwa = nazwa;
		this.kierunek = kierunek;
		this.karetka = karetka;
	}

	public void run() {
		
			
		if(this.karetka==true) {
			alarm.lock();
			System.out.println("\n\n\n\nijo ijo \n\n\n\n");
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			if (kierunek) {
				System.out.println("Jade na polnoc " + nazwa);
				if (polnoc.tryAcquire())
					System.out.println("Nie ma korku " + nazwa);
				else
					System.out.println("Czekam " + nazwa);

				try {
					polnoc.acquire();
				} catch (InterruptedException e) {
					System.out.println("wypadek");
				}
				System.out.println("Wjechalem w zwezenie " + nazwa);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				polnoc.release();
				System.out.println("No i wyjechalem " + nazwa);
			}

			else {
				System.out.println("			Jade na poludnie " + nazwa);
				if (poludnie.tryAcquire())
					System.out.println("			Nie ma korku " + nazwa);
				else
					System.out.println("Czekam " + nazwa);
				try {
					polnoc.acquire();
				} catch (InterruptedException e) {
					System.out.println("			wypadek");
				}
				System.out.println("			Wjechalem w zwezenie " + nazwa);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				poludnie.release();
				System.out.println("			No i wyjechalem " + nazwa);
				
			}
			if(this.karetka==true) {
				alarm.unlock();	
				this.interrupt();
			}
	}

}

public class Autostrada {

	public static void main(String[] args) throws InterruptedException {
		boolean kierunek = true;
		for (int i = 0; i < 100; i++) {
			Thread.sleep(1000);
			if (i % 2 == 0)
				kierunek = true;
			else
				kierunek = false;
			
			if(i%5==0)new Samochod("Karetka "+ i , kierunek , true).start();
			
			
			
			new Samochod("Watek " + i, kierunek,false).start();
		}
		
		
			
		

	}

}
