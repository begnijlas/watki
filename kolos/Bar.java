package palacze;

class Palacz extends Thread{
	private String nazwa;
	private boolean tyton , papier , zapalki;
	
	public Palacz(String nazwa,boolean tyton,boolean papier,boolean zapalki){
		this.nazwa=nazwa;
		this.tyton=tyton;
		this.papier=papier;
		this.zapalki=zapalki;
		
	}
	
	public void run(){
		
			System.out.println(nazwa + " Oczekuje i ma w rece: tyton= " + tyton + " papier= " + papier + " zapalki= "+zapalki);
		
		
		
	}
	
}
class Stol{
	
}

class Agent extends Thread{
	private boolean tyton , papier , zapalki;
	
	public void run(){
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private boolean losuj(){
		int liczba = (int)Math.random()*4;
		if(liczba == 1)return tyton = true;
		if(liczba == 2)return papier = true;
		if(liczba == 1)return zapalki = true;
		return false;
		
	}
	
	
	
	
}
public class Bar {

	public static void main(String[] args) {
		Palacz tyton = new Palacz("tyton", true, false, false);
		Palacz papier = new Palacz("papier", false, true, false);
		Palacz zapalki= new Palacz("zapalki", false, false, true);
		tyton.setDaemon(true);
		papier.setDaemon(true);
		papier.setDaemon(true);
		tyton.start();
		papier.start();
		zapalki.start();

	}

}
