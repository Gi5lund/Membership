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

public class MemberHandling {
    //Formandens muligheder og standardadministration
    public static ArrayList<Member> enrollMember(ArrayList<Member> members) {

        Scanner sc = new Scanner(System.in);
        System.out.println("indtast medlemsnavn: ");
        String name = sc.nextLine();
        System.out.println(" indtast f�dselsdag som YYYY-MM-DD: ");
        String bday = sc.next();

        //convert String to LocalDate
        LocalDate birthdate = LocalDate.parse(bday);

        System.out.println(" indtast k�n: M/K: ");
        boolean gender = false;
        if (sc.next().equalsIgnoreCase("M")) {
            gender = true;
        }
        System.out.println(" Har medlem betalt? [J/N]");
        boolean hasPaid = false;
        if (sc.next().equalsIgnoreCase("J")) {
            hasPaid = true;
        }
        System.out.println("�nsker du at v�re aktivt medlem? [J/N]");
        if (sc.next().equalsIgnoreCase("N")) {
            Member nytmedlem = new PassivMember(name, birthdate, gender, hasPaid);
            nytmedlem.setType("PassivMedlem");

                FileHandler.writeNewMemberToFile(nytmedlem);

            members.add(nytmedlem);
            return members;
        }
        System.out.println("�nsker du at v�re konkurrencesv�mmer? [J/N]");
        if (sc.next().equalsIgnoreCase("n")) {
            Member newMember = new Member(name, birthdate, gender, hasPaid);
            newMember.setType("Medlem");
            FileHandler.writeNewMemberToFile(newMember);
            members.add(newMember);
            return members;
        }
        else{
        String aktivdisciplineString = "";
        System.out.println("tast de discipliner du vil stille op i, uden komma i mellem: ");
        System.out.println("brystsv�mnin=b, crawl=c, ryg=r, butterfly=f");
        aktivdisciplineString = aktivdisciplineString.concat(sc.next());

        Member newMember = new CompetitionSwimmer(name, birthdate, gender, hasPaid, aktivdisciplineString);
        newMember.setType("Konkurrencesv�mmer");
        FileHandler.writeNewMemberToFile(newMember);
        members.add(newMember);
        }
        return members;
        // tilf�j nyt medlem til ArrayList
    }

    //Tr�nerens muligheder
    //TODO: redesign viewTop5 entirely.
    public static void viewTop5(ArrayList<Member> members, String discplinK�nAlder) {
        ArrayList<CompetitionSwimmer> top5 = new ArrayList<>();
        SortByResult sort= new SortByResult(0);
        for (Member m:members) {

            if (m.getType().equals("Konkurrencesv�mmer")) {
                CompetitionSwimmer k = (CompetitionSwimmer) m;
                if (discplinK�nAlder.contains("b")) {         //Brystsv�mning
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getActiveInDisciplin()[0] && k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getActiveInDisciplin()[0] && !k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getActiveInDisciplin()[0] && !k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getActiveInDisciplin()[0] && k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }

                    sort.setIndex(0);
                    Collections.sort(top5, sort);
                }


                if (discplinK�nAlder.contains("c")) {         //crawl
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getActiveInDisciplin()[1] && k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getActiveInDisciplin()[1] && !k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getActiveInDisciplin()[1] && !k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getActiveInDisciplin()[1] && k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    sort.setIndex(1);
                    Collections.sort(top5, sort);
                }
                if (discplinK�nAlder.contains("r")) {         //ryg
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getActiveInDisciplin()[2] && k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getActiveInDisciplin()[2] && !k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getActiveInDisciplin()[2] && !k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getActiveInDisciplin()[2] && k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    sort.setIndex(2);
                    Collections.sort(top5, sort);
                }
                if (discplinK�nAlder.contains("f")) {         //butterfly
                    if (discplinK�nAlder.contains("mj")) {
                        if (k.getActiveInDisciplin()[3] && k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("kj")) {
                        if (k.getActiveInDisciplin()[3] && !k.isMale() && k.getAge(k.getBirthday()) < 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ks")) {
                        if (k.getActiveInDisciplin()[3] && !k.isMale() && k.getAge(k.getBirthday()) >= 18) {
                            top5.add(k);
                        }
                    }
                    if (discplinK�nAlder.contains("ms")) {
                        if (k.getActiveInDisciplin()[3] && k.isMale() && k.getAge(k.getBirthday()) >= 18) {
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
                System.out.println(top5.get(i).printToScreen());
            }
        }
        else{
                for(int i=0;i<top5.size();i++){
                System.out.println(top5.get(i).printToScreen());
                }
            }
        }

    public static void viewMembersList(ArrayList<Member> members) {
        for(Member m:members){
            System.out.println(m.printToScreen());
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

    public static ArrayList<Member> editPersonalInformation(ArrayList<Member> members, Member m) throws FileNotFoundException {
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
                String newName = sc.nextLine();
                if (newName.contains(" ")) {
                    newName.replace(" ", "_");
                }
                m.setName(newName);
                FileHandler.persistChanges(members);
               // return members;
                break;
            case 2:
                System.out.println("vil �ndre til passivt medlemskab? [J/N]: ");
                rep = sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("PassivMedlem");
                    FileHandler.persistChanges(members);

                }
                    System.out.println("vil du �ndre til aktivt medlemskab (Motionist)? [J/N]: ");
                    rep = sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("Medlem");
                    FileHandler.persistChanges(members);

                }
                    System.out.println("vil du �ndre til konkurrencesv�mmer? ");
                    rep= sc.next();
                if (rep.equalsIgnoreCase("j")) {
                    m.setType("Konkurrencesv�mmer");
                    FileHandler.persistChanges(members);

                    choice=3;
                }
                else {
                    return members;
                }
                    break;
            case 3:
                System.out.println("vil du tilf�je discipliner");
                CompetitionSwimmer k=(CompetitionSwimmer) m;
                k.addDisciplines();
               // return members;
                break;

            }
        return members;
    }

    public static Member selectMember(ArrayList<Member> members){
        MemberHandling.viewMembersList(members);
        System.out.println("Indtast medlemsnummer");

        Scanner scn = new Scanner(System.in);
        int mnr = -1;
        mnr=scn.nextInt();
        Member m=members.get(mnr-1);
        return m;
    }
    public static ArrayList<Member> updateResults(ArrayList<Member> members, CompetitionSwimmer k, int disciplinnummer, int tr�nerinput) { //del af tr�nerens muligheder
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        Scanner sc= new Scanner(System.in);
        switch (tr�nerinput){
            case 1:

                System.out.println("indtast tr�ningsdato som [YYYY-MM-DD]: ");

                String trainingDay=sc.next();
                System.out.println("indtast trainingResult som [HH:MM:ss.SSS]: ");
                String trainingResult=sc.next();
                if (LocalTime.parse(trainingResult,timeFormat).compareTo(k.getDisciplines()[disciplinnummer].getResultater().getTrainingTime())<0) {
                    k.getDisciplines()[disciplinnummer].getResultater().setTr�ningsresultater(trainingDay, trainingResult);
                    FileHandler.persistChanges(members);
                }
               else{
                    System.out.println("den nye timeResultInEvent er ikke en forbedring");
                }
               // return members;
                break;
            case 2:
                System.out.println("indtast competitionEventName");
                String competitionEventName=sc.nextLine();
                System.out.println("indtast rankInEvent: ");
                int rankInEvent=sc.nextInt();
                System.out.println("indtast timeResultInEvent som [HH:MM:ss.SSS]:");
                String timeResultInEvent=sc.next();
                if (LocalTime.parse(timeResultInEvent,timeFormat).compareTo(k.getDisciplines()[disciplinnummer].getResultater().getCompetitionEventTime())<0) {
                    k.getDisciplines()[disciplinnummer].getResultater().setSt�vneresultater(competitionEventName,rankInEvent,timeResultInEvent);
                    FileHandler.persistChanges(members);
                }
                else{
                    System.out.println("den nye timeResultInEvent er ikke en forbedring");
                }

              //  return members;
                break;
        }
        return members;
    }
}
