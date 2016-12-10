import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;



class Okno extends JFrame {
	JLabel czas;
	public Okno(String nazwa) {
		super(nazwa);
	
		setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        getContentPane().setBackground(Color.BLUE);
        czas = new JLabel("", JLabel.CENTER);
        czas.setFont(new Font("ARIAL",Font.BOLD, 40));
    	czas.setForeground(Color.WHITE);
        czas.setVerticalTextPosition(JLabel.CENTER);
        add(czas);   
        setVisible(true);
		
		
}
	public void ustawCzas(String zawartosc){
		czas.setText(zawartosc);
}
	
}

class ZegarBack extends Thread{
	SimpleDateFormat formatDaty = new SimpleDateFormat("HH:mm:ss");
	
	String zawartosc="";
	private Okno okno;
	
	public ZegarBack(Okno okno){
		this.okno=okno;
	}
	
	public void run(){
	while(true){
	Date data = new Date();	
	zawartosc = formatDaty.format(data);
	okno.ustawCzas(zawartosc);
	System.out.println(zawartosc);
	try {
		Thread.sleep(1000);
		
	} catch(Exception e) { }
	}
	}
}


public class Zegar {
	
	public static void main(String[] args) {
		
		Okno okno = new Okno("Zegar na watku");
		ZegarBack zegar = new ZegarBack(okno);
		zegar.start();
			
}
}


