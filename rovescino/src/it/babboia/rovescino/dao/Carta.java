package it.babboia.rovescino.dao;

import java.util.Comparator;

public class Carta implements Comparator
{
	private int numero = 0;
	private String seme = null;
	private double valore = 0;
		
	public Carta()
	{
		
	}
	
	public Carta(String seme, int numero, double valore)
	{
		this.setNumero(numero);
		this.setSeme(seme);
		this.setValore(valore);
	}
	
	public boolean equals(Object obj)
	{
		Carta input = (Carta)obj;
		return this.numero == input.numero && this.seme.equals(input.getSeme());
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		Carta cl = new Carta(this.seme, this.numero, this.valore);
		return cl;
	}
	
	public int compare(Object o1, Object o2)
	{
		Carta inputCart = (Carta)o2;
		Carta io = (Carta)o1;
		
		KeyStringaInt k1 = new KeyStringaInt(io.getSeme(), io.getNumero());
		KeyStringaInt k2 = new KeyStringaInt(inputCart.getSeme(), inputCart.getNumero());
		
		return -k1.compareTo(k2);
		
	}
	
	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero)
	{
		this.numero = numero;
	}

	public String getSeme()
	{
		return seme;
	}

	public void setSeme(String seme)
	{
		this.seme = seme;
	}

	public double getValore()
	{
		return valore;
	}

	public void setValore(double valore)
	{
		this.valore = valore;
	}

	
	
}
