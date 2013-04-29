package it.babboia.rovescino.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Giocatore
{

	private String nome = "";
	private List carte = new ArrayList();
	private List miePrese = new ArrayList();
	private List preseTotali = new ArrayList();
	private int cippini = 20;
	private boolean computer = true;
	private int posizione = 0;
	private boolean ultimaPresa = false;
    private int[] punteggioMioMano = new int[]{0,0};
    private int punteggioMioPartita = 0;
	
	public Giocatore(String nome, boolean computer, int posizione)
	{
		this.nome = nome;
		this.setComputer(computer);
		this.setPosizione(posizione);
	}
	
	public void refreshPreseTotali(Carta[] carteMano)
	{
		this.preseTotali.addAll(Arrays.asList(carteMano));
	}
	
	public void daiCippini(int num)
	{
		this.cippini-= num;
	}
	
	public void aggiungiCippini(int num)
	{
		this.cippini+= num;
	}
	
	public void addCarta(Carta c)
	{
		this.carte.add(c);
		Collections.sort(this.carte, new Carta());
	}
	
	public void removeCarta(Carta c)
	{
		carte.remove(c);
	}
	
	public void addPresa(Carta c)
	{
		miePrese.add(c);
	}
	
	public void addPresa(Carta[] carteMano, boolean ultimaPresa)
	{
		List listaMano = Arrays.asList(carteMano);
		
		int[] valoreMano = conta(listaMano);
		this.punteggioMioMano[0]+=valoreMano[0];
		this.punteggioMioMano[1]+=valoreMano[1];
		
		miePrese.addAll(listaMano);
		this.ultimaPresa = ultimaPresa;
	}
	

	/**
	 * 
	 * @return [0]=punti interi [1]=terzi
	 */
	public int[] conta()
	{
		return conta(this.miePrese);
	}
	
	
	/**
	 * 
	 * @return [0]=punti interi [1]=terzi
	 */
	private int[] conta(List prese)
	{
		int uno = 0;
		int unterzo = 0;
		for (int i = 0; i < prese.size(); i++)
		{
			Carta c = (Carta) prese.get(i);
			if(c.getValore()==Valori.UNO)
			{
				uno++;
			}
			else if(c.getValore()==Valori.UN_TERZO)
			{
				unterzo++;
			}
		}
		return new int[]{uno,unterzo};
	}
	
	
	public Carta gioca(Carta carta)
	{
		Carta returnCard = null;
		try
		{
			returnCard = (Carta)carta.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		removeCarta(carta);
		return returnCard;
	}
	
	public Carta gioca(Carta[] carteMano)
	{
		Carta ret = null;
		Carta returnCard = null;
		if(carte.size()==1)
		{
			ret = MotoreGioco.ultimaCarta(carte);
		}
		else
		{
			if(carteMano[0]==null)
			{
				ret = MotoreGioco.primoDiMano(carte);
			}
			else
			{
				ret = MotoreGioco.intermedio(carteMano, carte,preseTotali);
			}
		}
		try
		{
			returnCard = (Carta)ret.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		removeCarta(ret);
		return returnCard;
	}
	
	

	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public List getCarte()
	{
		return carte;
	}
	public void setCarte(List carte)
	{
		this.carte = carte;
	}
	public int getCippini()
	{
		return cippini;
	}
	public void setCippini(int cippini)
	{
		this.cippini = cippini;
	}

	public boolean isComputer()
	{
		return computer;
	}

	public void setComputer(boolean computer)
	{
		this.computer = computer;
	}

	public int getPosizione()
	{
		return posizione;
	}

	public void setPosizione(int posizione)
	{
		this.posizione = posizione;
	}

	public boolean isUltimaPresa()
	{
		return ultimaPresa;
	}

	public void setUltimaPresa(boolean ultimaPresa)
	{
		this.ultimaPresa = ultimaPresa;
	}

	public int getPunteggioMioPartita()
	{
		return punteggioMioPartita;
	}

	public void setPunteggioMioPartita(int punteggioMioPartita)
	{
		this.punteggioMioPartita = punteggioMioPartita;
	}

	
}
