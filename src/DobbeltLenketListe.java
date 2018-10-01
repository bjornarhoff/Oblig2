import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DobbeltLenketListe<T> implements Liste<T>
{


    @Override
    public boolean leggInn(T verdi) {
        return false;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

    }

    @Override
    public boolean inneholder(T verdi) {
        return false;
    }

    @Override
    public T hent(int indeks) {
        return null;
    }

    @Override
    public int indeksTil(T verdi) {
        return 0;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        return null;
    }

    @Override
    public boolean fjern(T verdi) {
        return false;
    }

    @Override
    public T fjern(int indeks) {
        return null;
    }

    @Override
    public int antall() {
        return 0;
    }

    @Override
    public boolean tom() {
        return false;
    }

    @Override
    public void nullstill() {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    @Override
    public boolean fjernHvis(Predicate<? super T> p) {
        return false;
    }

    @Override
    public String melding(int indeks) {
        return null;
    }

    @Override
    public void indeksKontroll(int indeks, boolean leggInn) {

    }
}
