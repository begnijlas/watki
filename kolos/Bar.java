class Palacz extends Thread {
	private Stol stol;
	private String nazwa;
	public boolean tyton, papier, zapalki;

	public Palacz(Stol stol, String nazwa, boolean tyton, boolean papier, boolean zapalki) {
		this.stol = stol;
		this.nazwa = nazwa;
		this.tyton = tyton;
		this.papier = papier;
		this.zapalki = zapalki;
		System.out.println("przychodzi "+nazwa);
	}

	

	public void run() {
		while(true){
		wez();
		if(tyton==true && papier == true && zapalki == true){
			System.out.println("Pale "+ nazwa);
			tyton=false;
			papier=false;
			zapalki=false;
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}

	}
	
	private void wez(){
		
		synchronized(stol){
		if (tyton == false || papier == false || zapalki == false) {
			if(stol.papier == true && papier == false){
				stol.papier=false;
				papier = true;
				System.out.println("biore papier powiedzial " + nazwa);
			}else if(stol.tyton ==true && tyton == false){
				stol.tyton=false;
				tyton = true;
				System.out.println("biore tyton powiedzial " + nazwa);
			}else if(stol.zapalki ==true && zapalki == false){
				stol.zapalki=false;
				zapalki = true;
				System.out.println("biore zapalki powiedzial "+nazwa);
			}
				
			}
		}	
		

		
	}

}

class Stol {
	public boolean tyton, papier, zapalki;
	

	

	// tyton,papier,zapalki
	public void poloz() {
		losuj();
		losuj();
		System.out.println("Agent polozyl. Stan to: tyton-" + tyton + " papier-" + papier + " zapalki-" + zapalki);

	}

	public void losuj() {
		int losuj = (int) (Math.random()*(4-1)+1);
		System.out.print(losuj + "  ");

		switch (losuj) {
		case 1:
			tyton = true;
			break;
		case 2:
			papier = true;
			break;
		case 3:
			zapalki = true;
			break;
		}
	}

}

class Agent extends Thread {
	private Stol stol;

	public Agent(Stol stol) {
		this.stol = stol;

	}

	public void run() {
		while(true){
		try {
			Thread.sleep(5000);
			stol.poloz();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		

	}

}

public class Bar {
	

	public static void main(String[] args) {
		Stol stol = new Stol();
		Palacz tyton = new Palacz(stol, "Marek", true, false, false);
		Palacz papier = new Palacz(stol, "Jarek", false, true, false);
		Palacz zapalki = new Palacz(stol, "Czesiek", false, false, true);
		Agent agent = new Agent(stol);
		agent.setDaemon(true);
		agent.start();
		tyton.start();
		papier.start();
		zapalki.start();
		

	}

}