package it.babboia.rovescino.dao;

import java.util.Comparator;

public class KeyStringaInt
      extends AbstractOrdinatore
      implements Comparator, Comparable
{
      private String key1 = null;
      private int key2 = 0;

      public int getKey2()
      {
            return key2;
      }

      public void setKey1(String key1)
      {
            this.key1 = key1;
      }

      public void setKey2(int key2)
      {
            this.key2 = key2;
      }

      public String getKey1()
      {
            return key1;
      }

      public KeyStringaInt(String key1,
                           int key2)
      {
            this.key1 = key1;
            this.key2 = key2;
      }

      //BERAN 06/08/2008 non va bene, perchè non tiene conto del key2
      //      public int compare(Object o1, Object o2)
      //      {
      //            int ret = 0;
      //            if (o1 == null)
      //            {
      //                  if (o2 != null)
      //                  {
      //                        ret = -1;
      //                  }
      //            }
      //            else
      //            {
      //                  if (o2 == null)
      //                  {
      //                        ret = +1;
      //                  }
      //                  else
      //                  {
      //                        KeyStringaInt r1 = (KeyStringaInt)o1;
      //                        KeyStringaInt r2 = (KeyStringaInt)o2;
      //                        if (r1.getKey1() != null)
      //                        {
      //                              if (r2.getKey1() == null)
      //                              {
      //                                    ret = +1;
      //                              }
      //                              else
      //                              {
      //                                    ret = r1.getKey1().compareTo(r2.getKey1());
      //                                    if (ret == 0)
      //                                    {
      //                                          ret = r1.getKey2() - r2.getKey2();
      //                                    }
      //                              }
      //                        }
      //                        else
      //                        {
      //                              if (r2.getKey1() != null)
      //                              {
      //                                    ret = -1;
      //                              }
      //                        }
      //                  }
      //            }
      //            return ret;
      //      }
      public int compare(Object o1, Object o2)
      {
            KeyStringaInt p1 = (KeyStringaInt)o1;
            KeyStringaInt p2 = (KeyStringaInt)o2;
            if (p1 == null)
            {
                  if (p2 == null)
                  {
                        return 0;
                  }
                  else
                  {
                        return -1;
                  }
            }
            else
            {
                  if (p2 == null)
                  {
                        return +1;
                  }
                  else
                  {
                        int ret = confronta(p1.getKey1(), p2.getKey1());
                        if (ret == 0)
                        {
                              ret = confronta(p1.getKey2(), p2.getKey2());
                        }
                        return ret;
                  }
            }
      }
      //BERAN 06/08/2008 stop

      public int compareTo(Object o2)
      {
            return compare(this, o2);
      }

      public boolean equals(Object o2)
      {
            return (compare(this, o2) == 0);
      }

      public int hashCode()
      {
            int ret = 0;
            if (key1 != null)
            {
                  ret = (key1 + "(==" + key2).hashCode();
            }
            else
            {
                  ret = ("(==" + key2).hashCode();
            }

            return ret;
      }

      public String toString()
      {
            return "(" + key1 + "," + key2 + ")";
      }
}
