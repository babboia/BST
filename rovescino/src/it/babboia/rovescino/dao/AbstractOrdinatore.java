package it.babboia.rovescino.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Datamanagement S.p.A.</p>
 * @author not attributable
 * @version 1.0
 */

public abstract class AbstractOrdinatore
{
      /**
       * se vale 1 fa in maniera tale che gli oggetti null siano minori (vengano prima) degli oggetti istanziati.
       * se vale -1 fa in maniera tale che gli oggetti null siano maggiori (vengano dopo) degli oggetti istanziati (molto utile quando si ordinano array o matrici di GLOBO).
       */
      int ordineNull = 1;

      public AbstractOrdinatore()
      {

      }
      public AbstractOrdinatore(boolean nullMaggiori)
      {
            if (nullMaggiori)
            {
                  ordineNull = -1;
            }
      }
      
      /**
       * 19010 savoia 22/10/2012
       * @param a
       * @param b
       * @param ordinamentoAscendente
       * @return
       */
      protected int confronta(Comparable a, Comparable b, boolean ordinamentoAscendente)
      {
    	  return confronta(a, b, ordinamentoAscendente, true);
      }
      
      
      //BERAN 05/02/2010 seg.15762
      protected int confronta(Comparable a, Comparable b)
      {
    	  //19010 savoia 22/10/2012 start
    	  //return confronta(a, b, true);
    	  return confronta(a, b, true,true);
       	  //19010 savoia 22/10/2012 stop
      }
      
      //19010 savoia 22/10/2012 start
      //protected int confronta(Comparable a, Comparable b, boolean ordinamentoAscendente)
      protected int confronta(Comparable a, Comparable b, boolean ordinamentoAscendente, boolean caseSensitive)
      //19010 savoia 22/10/2012 stop
      //BERAN 05/02/2010 stop
      {
            int ret = 0;
            if (a != null)
            {
                  if (b != null)
                  {
                	    if(caseSensitive==true)
                	    {
                	    	ret = a.compareTo(b);
                	    }
                	    else if(a instanceof String)
                	    {
                	    	ret = ((String)a).toUpperCase().compareTo( ((String)b).toUpperCase() ); 
                	    }
                        
                        //BERAN 05/02/2010 seg.15762
                        if (ordinamentoAscendente == false)
                        {
                        	ret = -ret;
                        }
                        //BERAN 05/02/2010 stop
                  }
                  else
                  {
                        ret = ordineNull * 1;
                  }
            }
            else
            {
                  if (b != null)
                  {
                        ret = ordineNull * -1;
                  }
            }

            return ret;
      }

      protected int confronta(int a, int b)
      {
            return (a - b);
      }
}
