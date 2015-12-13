import java.awt.Container;
import java.util.Date;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Generator extends JFrame implements KeyListener
{
	static Random r=new Random(new Date().getTime());
	JTextField[][] tab;
	static Plansza plansza=null;
	
	Generator(Plansza gotowa)
	{ 
		super("Generator sudoku");
		Pole[][] plansza=gotowa.plansza;
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp=getContentPane();
		JPanel panel=new JPanel(new GridLayout(9,9));
		tab=new JTextField[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
			{
				tab[i][j]=new JTextField();
				tab[i][j].addKeyListener(this);
				tab[i][j].setHorizontalAlignment(JTextField.CENTER);
				int war=((i/3)+1)*((j/3)+1);
				if(war==2 || war==6)
					tab[i][j].setBackground(Color.lightGray);
				else
					tab[i][j].setBackground(Color.white);
				if(plansza[i][j].wartosc!=0)
				{
					tab[i][j].setText(java.lang.Integer.toString(plansza[i][j].wartosc));
					tab[i][j].setFont(new Font("Verdana",Font.BOLD,30));
					tab[i][j].setEditable(false);
				}
				else
				{
					if(plansza[i][j].ileMozliwosci()==1) tab[i][j].setBackground(Color.yellow);
					tab[i][j].setToolTipText(plansza[i][j].mozliwosci.toString());
					tab[i][j].setFont(new Font("Verdana",Font.BOLD,30));
					tab[i][j].setForeground(Color.blue);
				}
				panel.add(tab[i][j]);
			}
		cp.add(panel,BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main(String args[])
	{
		boolean poprawna=false;
		long start=new Date().getTime();
		while(!poprawna)
		{
			plansza=new Plansza();
			poprawna=plansza.rozwiaz(true);
		}
		plansza.wypisz();
		System.out.println();
		plansza.usuwaj();
		plansza.wypisz();
		long end=new Date().getTime();
		System.out.println("Czas: "+(end-start)/1000.);
		new Generator(plansza);
	}

	public void keyPressed(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) 
	{
		JTextField gdzie=(JTextField)e.getSource();
		int x=0,y=0;
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(tab[i][j]==gdzie)
				{x=i;y=j;break;}
		String s=gdzie.getText();
		try
		{
			int wpis=java.lang.Integer.parseInt(s);
			if(wpis>0 && wpis<10)
			{
				if(plansza.plansza[x][y].wartosc!=0) //juz cos wpisalem w okienku
				{
					plansza.usunPole(x,y);
				}
				plansza.plansza[x][y].wartosc=wpis;
				if(plansza.plansza[x][y].mozliwosci.contains(new Integer(wpis)))
				{
					tab[x][y].setForeground(Color.blue);
				}
				else
					tab[x][y].setForeground(Color.red);
				
				plansza.plansza[x][y].usun(wpis);
				plansza.przeliczMozliwosci(x,y);
			}
			else 
			{
				gdzie.setText("");
				plansza.usunPole(x,y);
			}
		}
		catch(NumberFormatException ex) {gdzie.setText(""); plansza.usunPole(x,y);}
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
			{
				int war=((i/3)+1)*((j/3)+1);
				if(war==2 || war==6)
					tab[i][j].setBackground(Color.lightGray);
				else
					tab[i][j].setBackground(Color.white);
				if(plansza.plansza[i][j].wartosc!=0)
				{
					tab[i][j].setText(java.lang.Integer.toString(plansza.plansza[i][j].wartosc));
					tab[i][j].setToolTipText(null);
				}
				else
				{
					if(plansza.plansza[i][j].ileMozliwosci()==1) tab[i][j].setBackground(Color.yellow);
					tab[i][j].setToolTipText(plansza.plansza[i][j].mozliwosci.toString());
				}
			}
	}

	public void keyTyped(KeyEvent e) {}
}

