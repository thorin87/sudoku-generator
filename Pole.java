import java.util.Random;
import java.util.Vector;

public class Pole implements Cloneable
{
	Vector<Integer> mozliwosci;
	int wartosc;
	Random r;
	
	Pole()
	{
		wartosc=0;
		mozliwosci=new Vector<Integer>();
		for(int i=1;i<=9;i++)
			mozliwosci.add(new Integer(i));
		r=Generator.r;
	}
	
	public Pole clone()
	{
        Pole nowe=null;
        try 
        {
        	nowe = (Pole)super.clone();
        	nowe.mozliwosci=(Vector<Integer>)mozliwosci.clone();
        }
        catch (CloneNotSupportedException e) {nowe=new Pole();}
        return nowe;
    }

	
	boolean wylosuj()
	{
		int mozliwych=this.ileMozliwosci();
		if(mozliwych!=0)
		{
			int indeks=r.nextInt(mozliwych);
			wartosc=mozliwosci.get(indeks).intValue();
			mozliwosci.removeAllElements();
			return true;
		}
		else
		 return false;
	}
	
	void wpisz()
	{
		wartosc=mozliwosci.get(0).intValue();
		mozliwosci.removeAllElements();
	}
	
	void usun(int co)
	{
		mozliwosci.remove(new Integer(co));
	}
	
	int ileMozliwosci()
	{
		return mozliwosci.size();
	}
}
