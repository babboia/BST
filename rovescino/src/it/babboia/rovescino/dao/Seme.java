package it.babboia.rovescino.dao;

import java.util.HashMap;
import java.util.Map;

public class Seme
{

	public static final String DENARA  = "DENARA";
	public static final String COPPE   = "COPPE";
	public static final String BASTONI = "BASTONI";
	public static final String SPADE   = "SPADE";
	
	
	public static String decodeSeme(String seme)
	{
		if(seme.equals(DENARA))
		{
			return "d";
		}
		else if(seme.equals(COPPE))
		{
			return "c";
		}
		else if(seme.equals(BASTONI))
		{
			return "b";
		}
		else// if(seme.equals(SPADE))
		{
			return "s";
		}
	}
	
	public static String encodeSeme(String seme)
	{
		if(seme.equals("d"))
		{
			return DENARA;
		}
		else if(seme.equals("c"))
		{
			return COPPE;
		}
		else if(seme.equals("b"))
		{
			return BASTONI;
		}
		else if(seme.equals("s"))
		{
			return SPADE;
		}
		else return null;
	}
}
