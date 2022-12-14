import java.io.Serializable;
import java.time.LocalDate;

public class PassivMember extends Member implements Serializable {
    private static final long serialVersionUID = -4483556479614794657L;
    public PassivMember(String navn, LocalDate f�dseldag, boolean gender, boolean harBetalt){
        super(navn, f�dseldag, gender, harBetalt);
    }
    public PassivMember(){
        super();
    }

    public PassivMember(int medlemnr, String medlemnavn, LocalDate bday, boolean isMale, String memberType, double fee, boolean hasPaid) {
   super(medlemnr,medlemnavn,bday,isMale,memberType,fee,hasPaid);
    }


    @Override
    public double calculateMembershipFee() {
        return 500;
    }
}