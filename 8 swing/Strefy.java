import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;
 
class Okno extends JFrame {
    JLabel czas;
    public Okno(String nazwa) {
        super(nazwa);
   
        setSize(500, 150);
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
    SimpleDateFormat formatDaty = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
   
    String zawartosc="";
   
    private Okno okno;
    private String miejsce;
   
    public ZegarBack(Okno okno, String miejsce){
        this.okno=okno;
        this.miejsce=miejsce;
    }
   
    public void run(){
    while(true){
    Date data = new Date();
    formatDaty.setTimeZone(TimeZone.getTimeZone(miejsce));
    zawartosc = formatDaty.format(data);
    okno.ustawCzas(zawartosc);
 
    try {
        Thread.sleep(1000);
       
    } catch(Exception e) { }
    }
    }
}
 
public class Strefy {
   
    public static void main(String[] args) {
       
        String [] nazwy = {"America/New_York", "Asia/Tokyo", "Europe/Moscow", "Europe/Warsaw"};
       
        for (int i=0; i<=nazwy.length; i++) {
            Okno okno = new Okno(nazwy[i]);
            ZegarBack zegar = new ZegarBack(okno, nazwy[i]);
            zegar.start();
        }
       
        }
}
