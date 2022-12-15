import java.util.ArrayList;

public class Kontingenter {
    public static void seRestance(ArrayList<Member> medlemmer) {
        for(Member m: medlemmer) {
            if (!m.getMembershipPaid()) {
                System.out.println(m.printToScreen());
            }
        }
    }

    public static ArrayList<Member> registerPayment(ArrayList<Member> medlemmer) {

             MemberHandling.selectMember(medlemmer).setHasPaid();
             MemberHandling.persistChanges(medlemmer);
        return medlemmer;

    }
    public static void membershipFeesList(ArrayList<Member> medlemmer) {

        System.out.println("Det koster ekstra");
        for (Member m:medlemmer){
            System.out.print(m.getMemberID()+" "+m.getMemberName()+": "+m.calculateMembershipFee());
        }
    }
    }
