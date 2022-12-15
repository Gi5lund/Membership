import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    public static ArrayList<Member> indlæsMedlemmer(ArrayList<Member> medlemmer) throws FileNotFoundException {
        DateTimeFormatter tidsformat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        int antalMedlemmer;
        int medlemnr;
        String medlemnavn;
        LocalDate bday;
        boolean isMale;
        String memberType;
        double fee;
        boolean hasPaid;
        String line = "";
       // ArrayList<Medlem> medlemmer = null;
        try {
            Scanner sc;
            sc = new Scanner(new File("medlemsliste.txt"));
            medlemmer = new ArrayList<>();


            while (sc.hasNextLine()) {
                line = sc.nextLine();
                Scanner nsc = new Scanner(line);
                while (nsc.hasNext()) {

                    antalMedlemmer = Integer.parseInt(String.valueOf(nsc.next()));
                    medlemnr = Integer.parseInt(String.valueOf(nsc.next()));
                    medlemnavn = nsc.next();
                    bday = LocalDate.parse(nsc.next());
                    isMale = Boolean.parseBoolean(nsc.next());
                    memberType = nsc.next();
                    fee = Double.parseDouble(nsc.next()); //int medlemsnummer, String navn, LocalDate foedselsdag, boolean gender, String type, double kontingent, boolean harBetalt
                    hasPaid = Boolean.parseBoolean(nsc.next());
                    if (memberType.equals("Medlem")) {
                        Member nytMedlem = new Member(medlemnr, medlemnavn, bday, isMale, memberType, fee, hasPaid);
                        medlemmer.add(nytMedlem);
                    }
                    if (memberType.equals("PassivMedlem")) {
                        PassivMember nytmedlem = new PassivMember(medlemnr, medlemnavn, bday, isMale, memberType, fee, hasPaid);

                    } else if (memberType.equals("Konkurrencesvømmer")){
                        boolean[] aktivediscipliner = new boolean[4];
                        LocalTime[] res = new LocalTime[4];

                        for (int i = 0; i < 4; i++) {
                            aktivediscipliner[i] = Boolean.parseBoolean(nsc.next());
                        }
                        for (int j = 0; j < 4; j++) {
                            if (aktivediscipliner[j]) {
                                nsc.next();
                                res[j] = LocalTime.parse(nsc.next(), tidsformat);
                            } else {
                                nsc.next();
                                res[j] = LocalTime.parse("23:59:59.999", tidsformat);
                            }
                        }
                        Member nytMedlem = new CompetitionSwimmer(medlemnr, medlemnavn, bday, isMale, memberType, fee, hasPaid, aktivediscipliner, res);
                        medlemmer.add(nytMedlem);
                    }
                    if (nsc.hasNext()) {
                        nsc.nextLine();//tømmer linjen for input
                    }

                }

            }

            return medlemmer;
        } catch (FileNotFoundException e) {
            System.out.println("filen medlemsliste.txt blev ikke fundet");
        }

        return medlemmer;
    }

    public static void skrivMedlemmerTilFil(Member m){
        try {
            File medlemsliste = new File("medlemsliste.txt");
            PrintStream medlemprint = new PrintStream(new FileOutputStream(medlemsliste, true));
            medlemprint.println(m);
        }
        catch (Exception e){
            System.out.println("noget gik galt i skriv til fil"+ e);
        }



    }

    public static ArrayList<Member> persistChanges(ArrayList<Member> members){
        File medlemsliste = new File("medlemsliste.txt");
        PrintStream medlemprint = null;//den gamle fil overskrives
        try {
            medlemprint = new PrintStream(new FileOutputStream(medlemsliste));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Member m : members) {
            medlemprint.println(m);
        }
        medlemprint.close();
        try {
         return indlæsMedlemmer(members);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
