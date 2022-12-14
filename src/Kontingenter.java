import java.util.ArrayList;
import java.util.Scanner;

public class Kontingenter {
    public static void seRestance(ArrayList<Member> medlemmer) {
        for(Member m: medlemmer) {
            if (!m.getHarBetalt()) {
                System.out.println(m.printTilKonsol());
            }
        }
    }

    public static ArrayList<Member> regBetaling(ArrayList<Member> medlemmer) {
        Medlemsadministration.seMedlemsListe( medlemmer);
        System.out.println("Indtast medlemsnummer");

        Scanner scn = new Scanner(System.in);
        int mnr = -1;
        mnr=scn.nextInt();
        medlemmer.get(mnr-1).setHarBetalt();
        return medlemmer;

    }
    public static void kontingentliste(ArrayList<Member> medlemmer) {

        System.out.println("Det koster ekstra");
        for (Member m:medlemmer){
            System.out.println(m.beregnKontingent());
        }
    }
    }
