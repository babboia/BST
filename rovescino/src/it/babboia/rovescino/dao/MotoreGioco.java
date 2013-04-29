package it.babboia.rovescino.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class MotoreGioco
{

	private static int dammiRandom(int max)
	{
		SecureRandom random;
		int ret = 0;
		try
		{
			random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed( (""+System.currentTimeMillis()).getBytes()); 
			ret = random.nextInt(max)+1;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} 
		return ret;
	}
	
	private static Carta cartaRandom(List carte)
	{
		int random = dammiRandom(carte.size());
		return (Carta)carte.get(random-1);
	}
	
	public static Carta ultimaCarta(List carte)
	{
		return cartaRandom(carte);
	}
	
	
	public static Carta primoDiMano(List carte)
	{
		if(hoCricca(true,carte)){
			
		}
		return cartaRandom(carte);
	}

	private static boolean hoCricca(boolean secca, List carte) 
	{
		if(secca)
		{
			
		}
			
	}

	public static Carta intermedio(Carta[] carteMano, List carte, List preseTotali)
	{
		String comanda = carteMano[0].getSeme();
		List carteSeme = filtraSeme(carte,comanda);
		if(carteSeme.size()>0)
		{
			return rispondi(carteSeme);
		}
		else
		{
			return vola(carte);
		}
	}
	
	
	private static Carta vola(List carte)
	{
		 return cartaRandom(carte);
	}

	private static Carta rispondi(List carteSeme)
	{
		if(carteSeme.size()==1)
		{
			return (Carta)carteSeme.get(0);
		}
		else
		{
			return cartaRandom(carteSeme);
		}
	}

	
	
	
	/* -------------------------------------------------------------------------------------------- */
	
	
	private static List filtraSeme(List carte, String comanda)
	{
		List ret = new ArrayList();
		for (int i = 0; i < carte.size(); i++)
		{
			Carta c = (Carta) carte.get(i);
			if(c.getSeme().equals(comanda))
			{
				ret.add(c);
			}
		}
		return ret;
	}

	
}
