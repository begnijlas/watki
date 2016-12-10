import java.util.Random;
import java.util.concurrent.Semaphore;

class Klient extends Thread {
	private String nazwa;
	private int liczbaProduktow;
	private int dlugoscKolejki;
	private static Semaphore kasa1 = new Semaphore(4);
	private static Semaphore kasa2 = new Semaphore(4);
	private static Semaphore kasa3 = new Semaphore(4);
	private static Semaphore kasa4 = new Semaphore(4);

	public Klient(String nazwa, int liczbaProduktow) {
		this.nazwa = nazwa;
		this.liczbaProduktow = liczbaProduktow;
		

	}

	public void run(){
		if(liczbaProduktow>20 || (kasa4.getQueueLength() < kasa3.getQueueLength() && kasa4.getQueueLength() < kasa2.getQueueLength() && kasa4.getQueueLength() < kasa1.getQueueLength())){
			this.kasa4();
				if(!kasa4.tryAcquire()){
					System.out.println("Kasa 4 zajeta, ide do kasy 3");
					this.kasa3();
				}	
			}
		else if(liczbaProduktow>15|| (kasa3.getQueueLength() < kasa4.getQueueLength() && kasa3.getQueueLength() < kasa2.getQueueLength() && kasa3.getQueueLength() < kasa1.getQueueLength())){
			this.kasa3();
				if(!kasa3.tryAcquire()){
					System.out.println("Kasa 3 zajeta, ide do kasy 2");
					this.kasa2();
				}	
			}else if(liczbaProduktow>10|| (kasa2.getQueueLength() < kasa4.getQueueLength() && kasa2.getQueueLength() < kasa3.getQueueLength() && kasa2.getQueueLength() < kasa1.getQueueLength())){
			this.kasa2();
				if(!kasa2.tryAcquire()){
					System.out.println("Kasa 2 zajeta, ide do kasy 1");
					this.kasa1();
				}
				else if(liczbaProduktow<=10|| (kasa1.getQueueLength() < kasa4.getQueueLength() && kasa1.getQueueLength() < kasa3.getQueueLength() && kasa1.getQueueLength() < kasa2.getQueueLength())){
					this.kasa1();
					if(!kasa1.tryAcquire()){
						System.out.println("Wszystkie kasy sa równie pe³ne, zostaje "+ nazwa);
					}	
				}	
			}
			
		}

	public void kasa4() {
		try {
			kasa4.acquire();
			System.out.println("Stoje przy kasie 4 " + nazwa );
			Thread.sleep(100 * liczbaProduktow);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			kasa4.release();
		}
	}
	
		public void kasa3() {
			try {
				kasa3.acquire();
				System.out.println("Stoje przy kasie 3 " + nazwa );
				Thread.sleep(100 * liczbaProduktow);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				kasa3.release();
			}
		}
		
			public void kasa2() {
				try {
					kasa2.acquire();
					System.out.println("Stoje przy kasie 2 " + nazwa );
					Thread.sleep(100 * liczbaProduktow);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					kasa2.release();
				}
			}
			
				public void kasa1() {
					try {
						kasa1.acquire();
						System.out.println("Stoje przy kasie 1 " + nazwa );
						Thread.sleep(100 * liczbaProduktow);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						kasa1.release();
					}
	}
}

public class Zadanie2 {

	public static void main(String[] args) throws InterruptedException {
		Random rand = new Random();
		for (int i = 0; i < 50; i++) {
			Thread.sleep(100);
			 int los = rand.nextInt(40);
			new Klient("K" + i, los).start();
		}

	}

}