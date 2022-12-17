import java.io.Serializable;
import java.time.LocalDate;

public class PassivMember extends Member implements Serializable {
    private static final long serialVersionUID = -4483556479614794657L;
    public PassivMember(String name, LocalDate birthdate, boolean gender, boolean hasPaid){
        super(name, birthdate, gender, hasPaid);
    }
    public PassivMember(){
        super();
    }

    public PassivMember(int memberID, String memberName, LocalDate bday, boolean isMale, String memberType, double fee, boolean hasPaid) {
   super(memberID,memberName,bday,isMale,memberType,fee,hasPaid);
    }


    @Override
    public double calculateMembershipFee() {
        return 500;
    }
}