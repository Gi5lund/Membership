import java.util.ArrayList;

public class MembershipFeesHandler {
    public static void viewFeeOverdueList(ArrayList<Member> members) {
        for(Member m: members) {
            if (!m.getMembershipPaid()) {
                System.out.println(m.printToScreen());
            }
        }
    }

    public static ArrayList<Member> registerPayment(ArrayList<Member> members) {

             MemberHandling.selectMember(members).setHasPaid();
             FileHandler.persistChanges(members);
        return members;

    }
    public static void membershipFeesList(ArrayList<Member> members) {

        System.out.println("Det koster ekstra");
        for (Member m:members){
            System.out.print(m.getMemberID()+" "+m.getMemberName()+": "+m.calculateMembershipFee());
        }
    }
    }
