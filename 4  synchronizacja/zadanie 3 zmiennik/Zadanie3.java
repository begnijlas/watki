class Schowek {

private int wartosc;
public synchronized int zmien() {
wartosc += 10;
wartosc -= 10;
return wartosc;
}

public String toString() {
return ("Aktualna wartoœæ przechowywana w schowku: " + wartosc);
}
}

class Zmiennik extends Thread {
Schowek schowek;
public Zmiennik(Schowek schowek) {
this.schowek = schowek;
}
public void run() {


for(int i = 0 ; i < 10000000; i++) {
schowek.zmien();
}

}
}

public class Zadanie3 {

public static void main (String[] args ) {
Schowek schowek = new Schowek();

System.out.println(schowek);

new Zmiennik(schowek).start();
new Zmiennik(schowek).start();
new Zmiennik(schowek).start();
new Zmiennik(schowek).start();

System.out.println(schowek);

}
}