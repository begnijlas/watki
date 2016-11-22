class Watek extends Thread {
private String napis = "";

public Watek(String tekst, int liczbaTabulacji) {
for (int i = 0; i < liczbaTabulacji; i++) {
napis += "\t";
}
napis = napis + tekst;
}

public void run() {

synchronized (System.out) {

for (int i = 1; i <= 100; i++) {
for (int x = 0; x < napis.length(); x++)
System.out.print(napis.charAt(x));
System.out.println(i);
yield();
// try {
// sleep(1000);
// } catch(InterruptedException e) {}
}
}
}
}

public class Zadanie4 {
public static void main(String[] args) {
new Watek("Marek", 1).start();
new Watek("Kasia", 2).start();
new Watek("Andrzej", 3).start();
new Watek("Natalia", 4).start();
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