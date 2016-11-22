import java.util.Random;
import java.util.concurrent.Semaphore;

 class Samochody extends Thread {
	private static final  Semaphore semafor = new Semaphore(20, true);
	Random rand = new Random();
	private int numerSamochodu;
	
	public Samochody(int numerSamochodu) {
		this.numerSamochodu = numerSamochodu;
		System.out.println("Samochod " + numerSamochodu + " wjezdza na parking");
	}
	
	public void run() {
		try {
			
			if(semafor.tryAcquire() == false){
				if (rand.nextDouble() > 0.75) { 
					System.out.println("Wyjezdzam " + numerSamochodu);
					this.interrupt();
				//	return;	
					sleep(10);
				}
				else{
					System.out.println("Zostaje " + numerSamochodu);
					semafor.acquire();
				}
			}
			
			System.out.println("Samochod " + numerSamochodu + " zajmuje miejsce.");
			Thread.sleep((long)(Math.random()*100000));
			
		} catch (InterruptedException e){
			System.out.println("Przerwano");
			
		}finally{
			System.out.println("Samochod " + numerSamochodu + " opuscil parking.");
			semafor.release();
		}

		
	}
	
	public static int getPermits(){
		return semafor.availablePermits();
	}
	
	public static int getLength(){
		return semafor.getQueueLength();
	}
}

class WatekDemon extends Thread {
	private Samochody s;
	
	public void run() {
		while (true) {
			System.out.println("Liczba wolnych miejsc: "+ s.getPermits() + " Liczba samochodow w kolejce: "+ s.getLength());
			try {
				sleep(5000);
			} catch (InterruptedException e) {	
			}
		}
	}
}

public class Parking {
	
	public static void main(String [] args)  {
		
		WatekDemon demon = new WatekDemon();
		demon.setDaemon(true);
		demon.start();
		
		for (int i = 1; i<=100; i++) {
			new Samochody(i).start();
			try {
				Thread.sleep((long)(Math.random()*1000));
			} catch(InterruptedException e) {
				
			}
		}
		
	}
}