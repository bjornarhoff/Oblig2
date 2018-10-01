public class Main {

    public static void main(String[] args) {

        /* Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " "+ liste.tom()); */

        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " "+ liste.tom());
    }
}
