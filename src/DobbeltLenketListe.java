import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

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

        {
            Node<T> h = hode;
            if (indeks < (antall/2)) {
                for (int i = 0; i<indeks; i++) {
                    h = h.neste;
                }
            } else
            {
                Node <T> t = hale;
                for (int j = 0; j<indeks; j--)
                {
                    t = t.forrige;
                } return t;
            }
            return h;
        }
        /*throw new UnsupportedOperationException("Ikke laget ennå!");*/





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
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
            hode = new Node<T>(verdi, null, null);
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

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int indeksTil(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
            builder.append("]");

        } return builder.toString();
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
            builder.append("]");

        } return builder.toString();
    }





    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
