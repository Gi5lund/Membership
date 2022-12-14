import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Medlemsadministration {
    //Formandens muligheder og standardadministration
    public static ArrayList<Member> opretMedlem(ArrayList<Member> medlemmer) {

        Scanner sc = new Scanner(System.in);
        System.out.println("indtast medlemsnavn: ");
        String navn = sc.nextLine();
        System.out.println(" indtast f�dselsdag som YYYY-MM-DD: ");
        //DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        String date = sc.next();

        //convert String to LocalDate
        LocalDate bday = LocalDate.parse(date);

        System.out.println(" indtast k�n: M/K: ");
        boolean gender = false;
        if (sc.next().equalsIgnoreCase("M")) {
            gender = true;
        }
        System.out.println(" Har medlem betalt? [J/N]");
        boolean harBetalt = false;
        if (sc.next().equalsIgnoreCase("J")) {
            harBetalt = true;
        }
        System.out.println("�nsker du at v�re aktivt medlem? [J/N]");
        if (sc.next().equalsIgnoreCase("N")) {
            Member nytmedlem = new PassivMember(navn, bday, gender, harBetalt);
            nytmedlem.setType("PassivMedlem");

                skrivMedlemmerTilFil(nytmedlem);

            medlemmer.add(nytmedlem);
            return medlemmer;
        }
        System.out.println("�nsker du at v�re konkurrencesv�mmer? [J/N]");
        if (sc.next().equalsIgnoreCase("n")) {
            Member nytmedlem = new Member(navn, bday, gender, harBetalt);
            nytmedlem.setType("Medlem");
            skrivMedlemmerTilFil(nytmedlem);
            medlemmer.add(nytmedlem);
            return medlemmer;
        }
        else{
        String aktivdisciplin = "";
        System.out.println("tast de discipliner du vil stille op i, uden komma i mellem: ");
        System.out.println("brystsv�mnin=b, crawl=c, ryg=r, butterfly=f");
        aktivdisciplin = aktivdisciplin.concat(sc.next());

        Member nytmedlem = new Konkurrencesv�mmer(navn, bday, gender, harBetalt, aktivdisciplin);
        nytmedlem.setType("Konkurrencesv�mmer");
        skrivMedlemmerTilFil(nytmedlem);
        medlemmer.add(nytmedlem);
        }
        return medlemmer;
        // tilf�j nyt medlem til ArrayList
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
    public static ArrayList<Member> indl�sMedlemmer(ArrayList<Member> medlemmer) throws FileNotFoundException {
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

                    } else if (memberType.equals("Konkurrencesv�mmer")){
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
                        Member nytMedlem = new Konkurrencesv�mmer(medlemnr, medlemnavn, bday, isMale, memberType, fee, hasPaid, aktivediscipliner, res);
                        medlemmer.add(nytMedlem);
                    }
                    if (nsc.hasNext()) {
                        nsc.nextLine();//t�mmer linjen for input
                    }

                }

            }

            return medlemmer;
        } catch (FileNotFoundException e) {
            System.out.println("filen medlemsliste.txt blev ikke fundet");
        }

        return medlemmer;
    }
    //Tr�nerens muligheder
    public static void seTop5(ArrayList<Member> medlemmer, String discplinK�nAlder) {
        ArrayList<Konkurrencesv�mmer> top5 = new ArrayList<>();
        SortByResult sort= new SortByResult(0);
        for (Member m:medlemmer) {

            if (m.getType().equals("Konkurrencesv�mmer")) {
                Konkurrencesv�mmer k = (Konkurrencesv�mmer) m;
                if (discplinK�nAlder.contains("b")) {         //Brystsv�mning
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getAktivdisciplin()[0] && k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getAktivdisciplin()[0] && !k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getAktivdisciplin()[0] && !k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getAktivdisciplin()[0] && k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }

                    sort.setIndex(0);
                    Collections.sort(top5, sort);
                }


                if (discplinK�nAlder.contains("c")) {         //crawl
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getAktivdisciplin()[1] && k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getAktivdisciplin()[1] && !k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getAktivdisciplin()[1] && !k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getAktivdisciplin()[1] && k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    sort.setIndex(1);
                    Collections.sort(top5, sort);
                }
                if (discplinK�nAlder.contains("r")) {         //ryg
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getAktivdisciplin()[2] && k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getAktivdisciplin()[2] && !k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getAktivdisciplin()[2] && !k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getAktivdisciplin()[2] && k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    sort.setIndex(2);
                    Collections.sort(top5, sort);
                }
                if (discplinK�nAlder.contains("f")) {         //butterfly
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getAktivdisciplin()[3] && k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getAktivdisciplin()[3] && !k.isGender() && k.getAlder(k.getFoedselsdag()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getAktivdisciplin()[3] && !k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getAktivdisciplin()[3] && k.isGender() && k.getAlder(k.getFoedselsdag()) >= 18) {
                            top5.add(k);
                        }
                    }
                    sort.setIndex(3);
                    Collections.sort(top5, sort);
                }


            }


        }
        if(top5.size()>=5) {
            for (int i = 0; i < 5; i++) {
                System.out.println(top5.get(i).printTilKonsol());
            }
        }
        else{
                for(int i=0;i<top5.size();i++){
                System.out.println(top5.get(i).printTilKonsol());
                }
            }
        }

    public static void seMedlemsListe(ArrayList<Member> members) {
        for(Member m:members){
            System.out.println(m.printTilKonsol());
        }
    }

    public static ArrayList<Member> deleteMember(ArrayList<Member> members, int mnr) { //opret en ny fil og rename istedet
        File medlemsliste = new File("medlemsliste.txt");
        PrintStream medlemprint = null;//den gamle fil overskrives
        try {
            medlemprint = new PrintStream(new FileOutputStream(medlemsliste));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        members.remove(mnr-1);
        for(Member m:members){
            medlemprint.println(m);
        }
        return members;

    }

    public static ArrayList<Member> redigerStamoplysninger(ArrayList<Member> members, Member m) throws FileNotFoundException {
        Scanner sc=new Scanner(System.in);
        System.out.println(" hvilke stamoplysninger �snker du at �ndre?: ");
        System.out.println("1: �ndre navn");
        System.out.println("2: �ndre medlemskab");
        System.out.println("3: tilf�je eller fjerne sv�mmediscipliner");
        System.out.println("4: afslut og returner til hovedmenu");
        int choice=sc.nextInt();
        String rep;
        switch (choice) {
            case 1:
                System.out.println("indtast det nye navn");
                String nytNavn = sc.nextLine();
                if (nytNavn.contains(" ")) {
                    nytNavn.replace(" ", "_");
                }
                m.setNavn(nytNavn);
                persistChanges(members);
               // return members;
                break;
            case 2:
                System.out.println("vil �ndre til passivt medlemskab? [J/N]: ");
                rep = sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("PassivMedlem");
                    persistChanges(members);

                }
                    System.out.println("vil du �ndre til aktivt medlemskab (Motionist)? [J/N]: ");
                    rep = sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("Medlem");
                    persistChanges(members);

                }
                    System.out.println("vil du �ndre til konkurrencesv�mmer? ");
                    rep= sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("Konkurrencesv�mmer");
                    persistChanges(members);

                    choice=3;
                }
                else {
                    return members;
                }
                    break;
            case 3:
                System.out.println("vil du tilf�je discipliner");
                Konkurrencesv�mmer k=(Konkurrencesv�mmer) m;
                k.tilf�jDisciplin();
               // return members;
                break;

            }
        return members;
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
         return indl�sMedlemmer(members);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Member selectMember(ArrayList<Member> medlemmer){
        Medlemsadministration.seMedlemsListe(medlemmer);
        System.out.println("Indtast medlemsnummer");

        Scanner scn = new Scanner(System.in);
        int mnr = -1;
        mnr=scn.nextInt();
        Member m=medlemmer.get(mnr-1);
        return m;
    }
    public static ArrayList<Member> opdaterResultater(ArrayList<Member> medlemmer, Konkurrencesv�mmer k, int disciplinnummer, int tr�nerinput) { //del af tr�nerens muligheder
        DateTimeFormatter tidsformat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        Scanner sc= new Scanner(System.in);
        switch (tr�nerinput){
            case 1:

                System.out.println("indtast tr�ningsdato som [YYYY-MM-DD]: ");

                String dato=sc.next();
                System.out.println("indtast tr�ningstid som [HH:MM:ss.SSS]: ");
                String tr�ningstid=sc.next();
                if (LocalTime.parse(tr�ningstid,tidsformat).compareTo(k.getDiscipliner()[disciplinnummer].getResultater().getTr�ningstid())<0) {
                    k.getDiscipliner()[disciplinnummer].getResultater().setTr�ningsresultater(dato, tr�ningstid);
                    persistChanges(medlemmer);
                }
               else{
                    System.out.println("den nye tid er ikke en forbedring");
                }
               // return medlemmer;
                break;
            case 2:
                System.out.println("indtast st�vnenavn");
                String st�vnenavn=sc.nextLine();
                System.out.println("indtast placering: ");
                int placering=sc.nextInt();
                System.out.println("indtast tid som [HH:MM:ss.SSS]:");
                String tid=sc.next();
                if (LocalTime.parse(tid,tidsformat).compareTo(k.getDiscipliner()[disciplinnummer].getResultater().getSt�vnetid())<0) {
                    k.getDiscipliner()[disciplinnummer].getResultater().setSt�vneresultater(st�vnenavn,placering,tid);
                    persistChanges(medlemmer);
                }
                else{
                    System.out.println("den nye tid er ikke en forbedring");
                }

              //  return medlemmer;
                break;
        }
        return medlemmer;
    }
}
