import java.awt.Point;
import java.util.Random;

public class Plansza
{
	Pole[][] plansza;
	Random r;
	
	public Plansza()
	{
		plansza=new Pole[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				plansza[i][j]=new Pole();
		r=Generator.r;
	}
	
	boolean rozwiaz(boolean bruteforce)
	{
		Point najmniejMozliwosci;
		int x,y;
		while(!rozwiazana())
		{
			najmniejMozliwosci=znajdzNajmniejMozliwosci();
			x=najmniejMozliwosci.x;
			y=najmniejMozliwosci.y;
			int ileTamJest=plansza[x][y].ileMozliwosci();
			//System.out.println(x+", "+y+": "+ileTamJest);
			//wypisz();
			if(ileTamJest==1)
			{
				plansza[x][y].wpisz();
				przeliczMozliwosci(x, y);
			}
			else
			{
				if(bruteforce)
				{
					if(plansza[x][y].wylosuj())
					{
						przeliczMozliwosci(x, y);
					}
					else
					{
						return false;
					}
					//if(status==0) {System.out.println("przerwalo w "+(x+1)+", "+(y+1)); return false;}
				}
				else
					return false;
			}
		}
		return true;
	}
	
	void przeliczMozliwosci(int x, int y)
	{
		int coTamJest=plansza[x][y].wartosc;
		for(int i=0;i<9;i++)
		{
			if(i!=y && plansza[x][i].wartosc==0) plansza[x][i].usun(coTamJest); //wiersz
			if(i!=x && plansza[i][y].wartosc==0) plansza[i][y].usun(coTamJest); //kolumna
		}
		int xMalego=x-(x%3);
		int yMalego=y-(y%3);
		for(int i=xMalego;i<xMalego+3;i++)
			for(int j=yMalego;j<yMalego+3;j++)
			{
				if(i!=x && j!=y && plansza[i][j].wartosc==0) plansza[i][j].usun(coTamJest);
			}
	}
	
	void usunPole(int x, int y)
	{
		plansza[x][y]=new Pole();
		policzMozliwosci(x,y);
		for(int a=0;a<9;a++)
			for(int b=0;b<9;b++)
				if(plansza[a][b].wartosc==0)
				{
					plansza[a][b]=new Pole();
					policzMozliwosci(a, b);
				}
	}
	
	void policzMozliwosci(int x, int y)
	{
		for(int i=0;i<9;i++)
		{
			if(i!=y)
				if(plansza[x][i].wartosc!=0) plansza[x][y].usun(plansza[x][i].wartosc);
			if(i!=x)
				if(plansza[i][y].wartosc!=0) plansza[x][y].usun(plansza[i][y].wartosc);
		}
		int xMalego=x-(x%3);
		int yMalego=y-(y%3);
		for(int i=xMalego;i<xMalego+3;i++)
			for(int j=yMalego;j<yMalego+3;j++)
			{
				if(i!=x && j!=y)
					if(plansza[i][j].wartosc!=0) plansza[x][y].usun(plansza[i][j].wartosc);
			}
	}
	
	Point znajdzNajmniejMozliwosci()
	{
		int najmniej=10;
		Point gdzieNajmniej=new Point();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
			{
				if(plansza[i][j].wartosc==0) //sprawdzaj tylko dla pustych
				{
					int ile=plansza[i][j].ileMozliwosci();
					if(ile<najmniej)
					{
						najmniej=ile;
						gdzieNajmniej.x=i;
						gdzieNajmniej.y=j;
					}
					if(najmniej==1) return gdzieNajmniej;
				}
			}
		return gdzieNajmniej;
	}
	
	boolean rozwiazana()
	{
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(plansza[i][j].wartosc==0) return false;
		return true;
			
	}
	
	void usuwaj()
	{
		int x,y;
		Pole[][] przed=null,po=null;
		int bledy=0;
		while(bledy<10)
		{
			x=r.nextInt(9);
			y=r.nextInt(9);
			if(plansza[x][y].wartosc!=0)
			{
				przed=kopia(plansza);
				usunPole(x,y);
				po=kopia(plansza);
				boolean result=rozwiaz(false);
				if(result)
				{
					plansza=kopia(po);
					bledy=0;
				}
				else
				{
					plansza=kopia(przed);
					bledy++;
				}
			}
		}
		
		for(x=0;x<9;x++)
			for(y=0;y<9;y++)
			{
				if(plansza[x][y].wartosc!=0)
				{
					przed=kopia(plansza);
					usunPole(x,y);
					po=kopia(plansza);
					if(rozwiaz(false))
					{
						plansza=kopia(po);
					}
					else
					{
						plansza=kopia(przed);
					}
				}
			}
	}
	
	Pole[][] kopia(Pole[][] zrodlo)
	{
		Pole[][] temp=new Pole[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				temp[i][j]=(Pole)zrodlo[i][j].clone();
		return temp;
	}
	
	void wypisz()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(plansza[i][j].wartosc!=0) System.out.print(plansza[i][j].wartosc+" ");
				else System.out.print("  ");
				if(j%3==2 && j!=8) System.out.print("| ");
			}
			System.out.println();
			if(i%3==2 && i!=8) System.out.println("---------------------");
		}
	}
	void wypisz(Pole[][] co)
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(co[i][j].wartosc!=0) System.out.print(co[i][j].wartosc+" ");
				else 
					System.out.print(co[i][j].mozliwosci+" ");
					//System.out.print("  ");
				if(j%3==2 && j!=8) System.out.print("| ");
			}
			System.out.println();
			if(i%3==2 && i!=8) System.out.println("---------------------");
		}
	}
}
