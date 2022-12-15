import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    public static ArrayList<Member> loadMembers(ArrayList<Member> members) throws FileNotFoundException {
        DateTimeFormatter tidsformat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        int antalMedlemmer;
        int memberID;
        String memberName;
        LocalDate bday;
        boolean isMale;
        String memberType;
        double fee;
        boolean hasPaid;
        String line = "";
       // ArrayList<Medlem> members = null;
        try {
            Scanner sc;
            sc = new Scanner(new File("medlemsliste.txt"));
            members = new ArrayList<>();


            while (sc.hasNextLine()) {
                line = sc.nextLine();
                Scanner nsc = new Scanner(line);
                while (nsc.hasNext()) {

                    antalMedlemmer = Integer.parseInt(String.valueOf(nsc.next()));
                    memberID = Integer.parseInt(String.valueOf(nsc.next()));
                    memberName = nsc.next();
                    bday = LocalDate.parse(nsc.next());
                    isMale = Boolean.parseBoolean(nsc.next());
                    memberType = nsc.next();
                    fee = Double.parseDouble(nsc.next()); //int medlemsnummer, String navn, LocalDate foedselsdag, boolean gender, String type, double kontingent, boolean harBetalt
                    hasPaid = Boolean.parseBoolean(nsc.next());
                    if (memberType.equals("Medlem")) {
                        Member newMember = new Member(memberID, memberName, bday, isMale, memberType, fee, hasPaid);
                        members.add(newMember);
                    }
                    if (memberType.equals("PassivMedlem")) {
                        PassivMember newMember = new PassivMember(memberID, memberName, bday, isMale, memberType, fee, hasPaid);

                    } else if (memberType.equals("Konkurrencesvømmer")){
                        boolean[] aktivediscipliner = new boolean[DISCIPLINE.values().length];
                        LocalTime[] res = new LocalTime[DISCIPLINE.values().length];

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
                        Member newMember = new CompetitionSwimmer(memberID, memberName, bday, isMale, memberType, fee, hasPaid, aktivediscipliner, res);
                        members.add(newMember);
                    }
                    if (nsc.hasNext()) {
                        nsc.nextLine();//tømmer linjen for input
                    }

                }

            }

            return members;
        } catch (FileNotFoundException e) {
            System.out.println("filen medlemsliste.txt blev ikke fundet");
        }

        return members;
    }

    public static void writeNewMemberToFile(Member m){
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
        File medlemsliste = new File("medlemsliste.ny");
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
        File memberslist=new File("medlemsliste.txt");
        File backup=new File("medlemsliste.old");
        if(medlemsliste.exists()&&memberslist.exists()&& backup.exists()){
            Path oldsource= Paths.get("medlemsliste.txt");
            Path target=Paths.get("medlemsliste.old");
            Path newsource=Paths.get("medlemsliste.ny");
            try{
                Files.move(oldsource,target, StandardCopyOption.REPLACE_EXISTING);
                Files.move(newsource,oldsource,StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
         return loadMembers(members);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
