/**
 * 
 */
package it.babboia.rovescino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import sun.net.www.content.image.gif;

import it.babboia.rovescino.dao.Carta;
import it.babboia.rovescino.dao.Giocatore;
import it.babboia.rovescino.dao.Posizione;
import it.babboia.rovescino.dao.Seme;
import it.babboia.rovescino.dao.Valori;

/**
 * @author fsavoia
 * @ics
 * @date
 * 
 */
public class Rovescino
{

	public static void main(String[] args) throws IOException
	{
		Giocatore gNord = new Giocatore("gNord", true, Posizione.NORD);
		Giocatore gOvest = new Giocatore("gOvest", true, Posizione.OVEST);
		Giocatore gSud = new Giocatore("gSud", true, Posizione.SUD);
		Giocatore gEst = new Giocatore("gEst(IO)", true, Posizione.EST);

		Giocatore[] giocatoriOrdineCardinale = { gNord, gOvest, gSud, gEst };

		List mazzo = creaMazzo();

		Giocatore gInizia = daiCarte(gNord, gOvest, gSud, gEst, mazzo, new Carta(Seme.DENARA, 5, Valori.UN_TERZO));

		//out("[Carte Nord ]:" + toStringArrCarte(gNord.getCarte().toArray()));
		//out("[Carte Ovest]:" + toStringArrCarte(gOvest.getCarte().toArray()));
		//out("[Carte Sud  ]:" + toStringArrCarte(gSud.getCarte().toArray()));
		//out("[Carte Est  ]:" + toStringArrCarte(gEst.getCarte().toArray()));

		Giocatore[] giocatori = faiOrdineGioco(gInizia, gNord, gOvest, gSud, gEst);

		int moltiplicatorePartita = 1;

		int mani = 1;
		while (mani <= 10)
		{
			Carta[] carteMano = new Carta[4];
			String gg = "";
			for (int i = 0; i < giocatori.length; i++)
			{
				Giocatore g = giocatori[i];
				gg += g.getNome() + " - ";
				if (g.isComputer())
				{
					carteMano[i] = g.gioca(carteMano);
					out("["+g.getNome()+"] gioca seme,carta: "+ carteMano[i].getSeme()+","+carteMano[i].getNumero());
				}
				else
				{
					out("["+g.getNome()+"] Carte in mano: "+toStringArrCarte(g.getCarte().toArray()));
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					
					int indiceCarta = -1;
					while(indiceCarta<0)
					{
						out("["+g.getNome()+"] gioca seme,carta: ");
						String semeCarta = reader.readLine();
						StringTokenizer st = new StringTokenizer(semeCarta,",");
						String seme = st.nextToken();
						int carta = Integer.parseInt(st.nextToken());
						Carta cartaU = new Carta();
						cartaU.setNumero(carta);
						cartaU.setSeme(Seme.encodeSeme(seme));
						indiceCarta = g.getCarte().indexOf(cartaU);
						if(indiceCarta<0)
						{
							out("carta non presente");
						}
					}
					carteMano[i] = g.gioca( (Carta)g.getCarte().get(indiceCarta) );
				}
			}
			out("mano[" + mani + "]: " + gg);
			out("mano[" + mani + "]: " + toStringArrCarte(carteMano));

			Giocatore gPrende = calcolaMano(carteMano, gNord, gOvest, gSud, gEst, (mani + 1 > 10 ? true : false), moltiplicatorePartita, giocatoriOrdineCardinale, giocatori);
			refreshPreseTotali(giocatoriOrdineCardinale, carteMano);
			giocatori = faiOrdineGioco(gPrende, gNord, gOvest, gSud, gEst);
			mani++;
		}

		int[] conto = conta(gNord, gOvest, gSud, gEst);
		out("TotaleMano: " + gNord.getNome() + "[" + conto[0] + "] - " + gOvest.getNome() + "[" + conto[1] + "] - " + gSud.getNome() + "[" + conto[2] + "] - " + gEst.getNome() + "[" + conto[3] + "]");
		out("TotaleCippiniMano: " + gNord.getNome() + "[" + gNord.getCippini() + "] - " + gOvest.getNome() + "[" + gOvest.getCippini() + "] - " + gSud.getNome() + "[" + gSud.getCippini() + "] - " + gEst.getNome() + "[" + gEst.getCippini() + "]");
	}

	public static String toStringArrCarte(Object[] carte)
	{
		String ret = "";
		for (int i = 0; i < carte.length; i++)
		{
			ret += (Seme.decodeSeme(((Carta) carte[i]).getSeme())) + "[" + ((Carta) carte[i]).getNumero() + "] - ";
		}
		return ret;
	}

	private static void refreshPreseTotali(Giocatore[] giocatoriOrdineCardinale, Carta[] carteMano)
	{
		for (int i = 0; i < giocatoriOrdineCardinale.length; i++)
		{
			giocatoriOrdineCardinale[i].refreshPreseTotali(carteMano);
		}
	}

	private static Giocatore calcolaMano(Carta[] carteMano, Giocatore gNord, Giocatore gOvest, Giocatore gSud, Giocatore gEst, boolean ultimaPresa, int moltiplicatorePartita, Giocatore[] giocatoriOrdineCardinale, Giocatore[] giocatoriOrdineGiocata)
	{
		Giocatore ret = null;
		String semeComanda = carteMano[0].getSeme();

		int[] haSgaciatoUnTre = { 0, 0, 0, 0 };

		int numeroPiuAlto = 0;
		int indicePiuAlto = 0;
		for (int i = 0; i < carteMano.length; i++)
		{
			Carta c = carteMano[i];
			if (c.getSeme().equals(semeComanda) && c.getNumero() > numeroPiuAlto)
			{
				numeroPiuAlto = c.getNumero();
				indicePiuAlto = i;
			}
			if (c.getSeme().equals(semeComanda) == false && c.getNumero() == 300)
			{
				if (c.getSeme().equals(Seme.DENARA))
				{
					haSgaciatoUnTre[i] = 2;
				}
				else
				{
					haSgaciatoUnTre[i] = 1;
				}
			}
		}

		int cippiniDaDistribuire = distribuisciCippini(haSgaciatoUnTre, moltiplicatorePartita, giocatoriOrdineGiocata);

		Giocatore gg = giocatoriOrdineGiocata[indicePiuAlto];
		gg.addPresa(carteMano, ultimaPresa);
		gg.daiCippini(cippiniDaDistribuire);
		ret = gg;

		return ret;
	}

	private static int distribuisciCippini(int[] haSgaciatoUnTre, int moltiplicatorePartita, Giocatore[] giocatoriOrdineCardinale)
	{
		int ret = 0;
		for (int i = 0; i < haSgaciatoUnTre.length; i++)
		{
			int valCip = haSgaciatoUnTre[i] * moltiplicatorePartita;
			if (valCip > 0)
			{
				giocatoriOrdineCardinale[i].aggiungiCippini(valCip);
				ret += valCip;
			}
		}
		return ret;
	}

	private static int[] conta(Giocatore gNord, Giocatore gOvest, Giocatore gSud, Giocatore gEst)
	{

		int[] gNordConto = gNord.conta();
		int[] gOvestConto = gOvest.conta();
		int[] gSudConto = gSud.conta();
		int[] gEstConto = gEst.conta();

		int puntiN = gNordConto[0];
		int puntiO = gOvestConto[0];
		int puntiS = gSudConto[0];
		int puntiE = gEstConto[0];

		int puntiDaTerziN = getPuntiDaTerzi(gNordConto[1]);
		puntiN += puntiDaTerziN;

		int puntiDaTerziO = getPuntiDaTerzi(gOvestConto[1]);
		puntiO += puntiDaTerziO;

		int puntiDaTerziS = getPuntiDaTerzi(gSudConto[1]);
		puntiS += puntiDaTerziS;

		int puntiDaTerziE = getPuntiDaTerzi(gEstConto[1]);
		puntiE += puntiDaTerziE;

		if (gNord.isUltimaPresa())
		{
			puntiN = 11 - (puntiO + puntiE + puntiS);
		}
		else if (gOvest.isUltimaPresa())
		{
			puntiO = 11 - (puntiN + puntiE + puntiS);
		}
		else if (gSud.isUltimaPresa())
		{
			puntiS = 11 - (puntiO + puntiE + puntiN);
		}
		else if (gEst.isUltimaPresa())
		{
			puntiE = 11 - (puntiO + puntiN + puntiS);
		}
		return new int[] { puntiN, puntiO, puntiS, puntiE };
	}

	/**
	 * 
	 * @param i
	 * @return [0]=punti [1]=resto
	 */
	private static int getPuntiDaTerzi(int i)
	{
		int ret = 0;
		if (i > 0)
		{
			ret = i / 3;
		}
		return ret;
	}

	private static Giocatore[] faiOrdineGioco(Giocatore gInizia, Giocatore gNord, Giocatore gOvest, Giocatore gSud, Giocatore gEst)
	{
		Giocatore[] ret = null;
		if (gInizia.getPosizione() == 1)
		{
			ret = new Giocatore[] { gNord, gOvest, gSud, gEst };
		}
		else if (gInizia.getPosizione() == 2)
		{
			ret = new Giocatore[] { gOvest, gSud, gEst, gNord };
		}
		else if (gInizia.getPosizione() == 3)
		{
			ret = new Giocatore[] { gSud, gEst, gNord, gOvest };
		}
		else if (gInizia.getPosizione() == 4)
		{
			ret = new Giocatore[] { gEst, gNord, gOvest, gSud };
		}
		return ret;

	}

	private static Giocatore daiCarte(Giocatore gN, Giocatore gO, Giocatore gS, Giocatore gE, List mazzo, Carta cartaInizia)
	{
		Giocatore ret = null;

		//out(toStringArrCarte(mazzo.toArray()));

		for (int i = 0; i < mazzo.size(); i++)
		{

			Carta ca = (Carta) mazzo.get(i);
			Carta c = null;
			try
			{
				c = (Carta) ca.clone();
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
			boolean inizia = cartaInizia != null && c.equals(cartaInizia);
			if (i < 5)
			{
				gN.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gN;
				}
			}
			else if (i < 10)
			{
				gO.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gO;
				}
			}
			else if (i < 15)
			{
				gS.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gS;
				}
			}
			else if (i < 20)
			{
				gE.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gE;
				}
			}
			else if (i < 25)
			{
				gN.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gN;
				}
			}
			else if (i < 30)
			{
				gO.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gO;
				}
			}
			else if (i < 35)
			{
				gS.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gS;
				}
			}
			else if (i < 40)
			{
				gE.addCarta(c);
				if (inizia && ret == null)
				{
					ret = gE;
				}
			}
		}
		return ret;
	}

	private static List creaMazzo()
	{
		List mazzo = new ArrayList();
		mazzo.addAll(creaMazzo(Seme.DENARA));
		mazzo.addAll(creaMazzo(Seme.BASTONI));
		mazzo.addAll(creaMazzo(Seme.SPADE));
		mazzo.addAll(creaMazzo(Seme.COPPE));

		//out("savoia: " + toStringArrCarte(mazzo.toArray()));

		long seed = System.currentTimeMillis();
		Collections.shuffle(mazzo, new Random(seed));
		Collections.shuffle(mazzo, new Random(seed));

		return mazzo;
	}

	private static List creaMazzo(String seme)
	{
		List mazzo = new ArrayList();
		for (int i = 1; i <= 10; i++)
		{
			int numero = i;

			double valore = 0;
			switch (i)
			{
			case 1:
				valore = Valori.UNO;
				numero = 100;
				break;
			case 2:
				valore = Valori.UN_TERZO;
				numero = 200;
				break;
			case 3:
				valore = Valori.UN_TERZO;
				numero = 300;
				break;
			case 8:
			case 9:
			case 10:
				valore = Valori.UN_TERZO;
				break;
			default:
				break;
			}
			Carta c = new Carta(seme, numero, valore);
			mazzo.add(c);
		}
		return mazzo;
	}

	private static void out(String s)
	{
		System.out.println(s);
	}

	private static void out(boolean b)
	{
		System.out.println("" + b);
	}

	private static void out(int s)
	{
		System.out.println("" + s);
	}

}
