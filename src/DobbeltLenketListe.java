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
    public void leggInn(int indeks, T verdi) {
        {
            Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

            indeksKontroll(indeks, true);  // Se Liste, true: indeks = antall er lovlig

            if (indeks == 0)                     // ny verdi skal ligge først
            {
                hode = new Node<>(verdi,null, hode);    // legges først
                if (antall == 0) hale = hode;      // hode og hale går til samme node
            } else if (indeks == antall)           // ny verdi skal ligge bakerst
            {
                hale = hale.neste = new Node<>(verdi, hale, null);  // legges bakerst
            } else {
                Node<T> p = hode;                  // p flyttes indeks - 1 ganger
                for (int i = 1; i < indeks; i++) p = p.neste;

                p.neste = new Node<>(verdi, p.forrige, p.neste);  // verdi settes inn i listen
            }

            antall++;                            // listen har fått en ny verdi
        }
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
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall ; indeks++)
        {
            if (p.verdi.equals(verdi))

                return indeks;
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

        return gammelVerdi;
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
            builder.append("]");

        } return builder.toString();
    }

    /*hello*/

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
