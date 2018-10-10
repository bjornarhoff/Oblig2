import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        Node<T> h;
        if (indeks < (antall/2)) {
            h = hode;
            for (int i = 0; i<indeks; i++) {
                h = h.neste;
            }
        } else
            {
                h = hale;
                for (int j = antall-1; j > indeks; j--)
                {
                    h = h.forrige;
                } return h;
            }
            return h;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        this();   // Kaller på default konstruktør
        Objects.requireNonNull(a,"Tabellen a er null!"); // Sjekker om det er tom liste

        int i = 0;
        while (i < a.length && a[i] == null) i++;

        if (i < a.length) {
            Node<T> node = hode = hale = new Node<>(a[i], null, null);
            antall++;
            i++;


            for (; i < a.length; i++) {
                if (a[i] != null)
                {
                    node = node.neste = new Node<>(a[i],node,null);
                    antall++;
                }
            } hale = node;
        }
    }

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall, fra, til);

        DobbeltLenketListe liste = new DobbeltLenketListe();

        for(int i=fra; i<til;i++)
        {
            liste.leggInn(hent(i));
        }

        return liste;
    }


    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall (" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }
    @Override
    public boolean leggInn(T verdi)
    {

        Objects.requireNonNull(verdi, "Liste verdien er 0");

        if(antall == 0){
            hode = hale = new Node<T>(verdi, null, null);
        } else {
            Node p = hode;

            for (int i = 0; i < antall-1; i++){
                p = p.neste;
            }

            Node<T> q = new Node(verdi, p,null);

            q.verdi = verdi;
            p.neste = q;
            q.forrige = p;

            hale = q;

        }

        antall++;
        endringer++;

        return true;
    }


    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);  // true: indeks = antall er lovlig

        if (indeks == 0)                     // ny verdi skal ligge først
        {
            hode = new Node<>(verdi,null, hode); // legges først

            if(antall == 0)
            {
             hale = hode;
            }

            if (antall != 0)
            {
                hode.neste.forrige = hode; // hode og hale går til samme node
            }

        } else if (indeks == antall)           // ny verdi skal ligge bakerst
            {
            hale = hale.neste = new Node<>(verdi, hale, null);  // legges bakerst

            } else
            {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++)
            {
                p = p.neste;
            }

            p.neste = new Node<>(verdi, p, p.neste);  // verdi settes inn i listen
                p.neste.neste.forrige = p.neste;
        }

        antall++;                            // listen har fått en ny verdi
        endringer++;
    }



    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks, false);

        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        if (verdi == null) {
            return -1;
        }

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall ; indeks++)
        {
            if (p.verdi.equals(verdi))
            {
                return indeks;
            }
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi, "Ikke lov med null-verdier");

        indeksKontroll(indeks,false);

        Node <T> d = finnNode(indeks);
        T gammelVerdi = d.verdi;

        d.verdi = nyverdi;
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null)              // Sjekker at om ingen null verdier
        {
            return false;
        }

        Node<T> n = hode;              //  Pekere
        Node<T> m = null;

        while (n != null)                  // n skal finne verdien T
        {
            if (n.verdi.equals(verdi))     // Hvis funnet, stopp
            {
                break;
            }

            m = n;                  // Setter m til gamle n
            n = n.neste;            // Setter n til neste 
        }

        if (n == null) {
            return false;

        } else if (n == hode) {
            hode = hode.neste;
            if (hode != null) {
                hode.forrige=null;
            }
        } else {
            m.neste = n.neste;
            if(n == hale) {
                 hale = m;
            }
            else {
                n.neste.forrige = m;
            }
    }    

        n.verdi = null;
        n.neste = null;

        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks)
    {
        indeksKontroll(indeks,false);    // indeks = antall er ulovlig


        Node<T> m = finnNode(indeks);
        Node<T> o = m.neste;
        Node<T> n = m.forrige;

        T tmp;                      // Hjelpevariabel
        tmp = m.verdi;    // Lagrer verdien som skal fjernes

        if(n == null)      // Sjekker første verdi
        {

            hode = hode.neste;     // flytter hode-peker til neste
            m.verdi = null;

            if (o == null)        // Om antallet i listen hadde bare en verdi
            {
                hale = null;

            }

            else
            {
                o.forrige = null;
            }


        }
          else {

            m.neste = null;
            m.forrige = null;


            if (m == hale)
            {
                hale = n;                       // n er siste node
                n.neste = null;
                m.verdi= null;
            }
            else
                {
                    o.forrige = n;
                    n.neste = o;
                    m.verdi= null;
                }

          }
          antall--;                                  // Reduserer antallet
          endringer++;                              // Øker endringer

          return tmp;

    }

    @Override
    public void nullstill()
    {
        Node<T> p = hode, q = null;

        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer++;
    }

    public T nullstill(int indeks)
    {
        indeksKontroll(indeks, false);

        Iterator<T> i = iterator();

        for (int k = 0; k < indeks; k++) i.next();

        T temp = i.next();
        i.remove();

        return temp;
    }

    @Override
    public String toString()
    {
        if (tom())
        {
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        if (!tom()) {
            Node<T> h = hode;
            builder.append(h.verdi);

            h = h.neste;

        while (h != null)  // Sjekker om det er noe mer å ta med
        {
            builder.append(",").append(" ").append(h.verdi);
            h = h.neste;
        }

        }  builder.append("]");
        return builder.toString();
    }


    public String omvendtString()
    {
        if (tom())
        {
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append('[');

        if (!tom())
        {
            Node <T> t = hale;
            builder.append(t.verdi);

            t = t.forrige;

            while (t != null)  // Sjekker om det er noe mer å ta med
            {
                builder.append(',').append(' ').append(t.verdi);
                t = t.forrige;
            }

        } builder.append("]");
        return builder.toString();
    }


    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        if (liste.antall() < 2)    // Om listen har ett element, er listen sortert
        {
            return;
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            indeksKontroll(indeks,false);
            denne = finnNode(indeks);
            iteratorendringer = endringer;
            fjernOK = false;


        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            if (iteratorendringer != endringer){
                throw new ConcurrentModificationException("Endringer og iteratorendringer er ulike.");
            }
            if(!hasNext()){
                throw new NoSuchElementException("Det finnes ikke en neste node");
            }

            fjernOK = true;
            T temp = denne.verdi;
            denne = denne.neste;


            return temp;
        }


        @Override
        public void remove()
        {


            if (!fjernOK)
            {
                throw new IllegalStateException("Ulovlig tilstand!");
            }
            if (endringer != iteratorendringer)
            {
                throw new ConcurrentModificationException("Endringer og iteratorendringer er ulike.");
            }
            fjernOK = false;        // remove() kan ikke kalles på nytt

            Node<T> q = hode;              // hjelpevariabel


            if (antall == 1)           // Inneholder listen bare et element?
            {
                hode = hale = null;           // Både hodet og halen settes til null. Listen er tom
            }
            else if (denne == null)
            {
                q = hale;
                hale = hale.forrige;
                hale.neste = null;
            }
            else if(denne.forrige == hode)
            {
                hode = hode.neste;
                hode.forrige = null;

            }
            else
            {
                Node<T> p = hode;

                while(p.neste.neste != denne){
                    p = p.neste;
                }
                q = p.neste;
                p.neste = denne;
                denne.forrige = p;
            }
            q.verdi = null;
            q.neste = null;
            q.forrige = null;

            antall--;
            endringer++;
            iteratorendringer++;                    // en node mindre i listen
        }
        }

    } // DobbeltLenketListeIterator


 // DobbeltLenketListe
