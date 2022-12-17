

import java.io.*;
import java.time.LocalDate;

public class Member implements Serializable {
	private static int memberTotal = 0;

	@Serial
	private static final long serialVersionUID = -9177640809256836733L;
	private final int memberID;
	private PersonalInformation personaldata;


	private String type = "";
	private double membershipFee;
	private boolean membershipPaid;


	//Constructors. no-arg constructor must be available
	public Member() {
		this.memberID = 1;
		this.personaldata=new PersonalInformation();
		this.membershipPaid = false;
		this.type = "Medlemstype";
		this.membershipFee = this.calculateMembershipFee();
	}

	public Member(String name, LocalDate birthday, boolean genderIsMale, boolean membershipPaid) {
		memberID = memberTotal + 1;
		memberTotal++;
		this.personaldata=new PersonalInformation();
		personaldata.setFirstname(name);
		personaldata.setBirthdate(birthday);
		personaldata.setGenderIsMale(genderIsMale);
		this.membershipPaid = membershipPaid;
		this.membershipFee = this.calculateMembershipFee();
	}

	public Member(int memberID, String name, LocalDate birthday, boolean genderIsMale, String type, double membershipFee, boolean membershipPaid) {
		this.memberID = memberID;
		memberTotal++;
		this.personaldata=new PersonalInformation();
		personaldata.setFirstname(name);
		personaldata.setBirthdate(birthday);
		personaldata.setGenderIsMale(genderIsMale);
		this.type = type;
		this.membershipFee = membershipFee;
		this.membershipPaid = membershipPaid;
	}

	public boolean isMale() {
		return personaldata.isMale();
	}

	public String getType() {
		return type;
	}

	public static int getMemberTotal() {
		return memberTotal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getBirthday() {
		return personaldata.getBirthdate();
	}

	public boolean getMembershipPaid() {
		return membershipPaid;
	}

	public double calculateMembershipFee() {
		double discount = 0.75;
		double fee = 1600;
		double feeYouthmember = 1000;
		if (PersonalInformation.getAge(getBirthday()) > 60) {
			return discount * fee;
		} else if (PersonalInformation.getAge(getBirthday()) < 18) {
			return feeYouthmember;
		} else {
			return fee;
		}
	}

	public String toString() {
		String s = memberTotal + " " + memberID + " " + personaldata + " "  + type + " " + membershipFee + " " + membershipPaid;
		return s;
	}

	public String printToScreen() {
		String gender = "";
		if (this.isMale()) {
			gender = "Male";
		} else {
			gender = "Female";
		}
		String dues="";
		if (getMembershipPaid()){
			dues="Paid";
		}
		else{
			dues="Overdue";
		}
		String displayName= personaldata.getFirstname();
		if(displayName.contains("_")){
			displayName.replace("_"," ");
		}
		return memberID + " | " + displayName + " | " + PersonalInformation.getAge(this.getBirthday()) + " | " + gender + " | " + dues;

	}


	public int getMemberID() {
		return memberID;
	}

	public void setName(String nytnavn) {
		this.personaldata.setFirstname(nytnavn);
	}
	public void setFullName(String firstname,String surname){
		this.personaldata.setFirstname(firstname);
		this.personaldata.setSurname(surname);
	}
	public void setFullName(String firstname,String middlename,String surname){
		this.personaldata.setFirstname(firstname);
		this.personaldata.setMiddlenames(middlename);
		this.personaldata.setSurname(surname);
	}
	public void setHasPaid(){
		membershipPaid =true;
	}


	public String getMemberName() {
		return personaldata.getFirstname()+" "+personaldata.getSurname();
	}
}


