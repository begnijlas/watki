import java.util.concurrent.Semaphore;

class Watek extends Thread {
    private char litera;
    private static final Semaphore semA = new Semaphore(1,true);
    private static final Semaphore semB = new Semaphore(0,true);
    private static final Semaphore semC = new Semaphore(0,true);

    public Watek(char litera) {
        this.litera = litera;
    }

    public void run() {
        switch(this.litera) {
            case 'A':
                try {
                    semA.acquire();
                    System.out.print(litera);
                    sleep((long)(Math.random()*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semB.release();
                }
                break;

            case 'B':

                try {
                    semB.acquire();
                    System.out.print(litera);
                    sleep((long)(Math.random()*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semC.release();
                }
                break;

            case 'C':
                try {
                    semC.acquire();
                    System.out.print(litera);
                    System.out.println();
                    sleep((long)(Math.random()*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semA.release();
                }
                break;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        for(int i=0;i<500;i++){
            new Watek('A').start();
            new Watek('B').start();
            new Watek('C').start();
        }
    }

}


