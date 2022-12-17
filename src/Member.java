

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {
	private static int memberTotal = 0;

	@Serial
	private static final long serialVersionUID = -9177640809256836733L;
	private final int memberID;
	private String name;
	private final LocalDate birthday;
	private final boolean genderIsMale;
	private String type = "";
	private double membershipFee;
	private boolean membershipPaid;


	//Constructors. no-arg constructor must be available
	public Member() {
		this.memberID = 1;

		this.name = "Fornavn";
		this.birthday = LocalDate.now();
		this.genderIsMale = false;
		this.membershipPaid = false;
		this.type = "Medlemstype";
		this.membershipFee = this.calculateMembershipFee();
	}

	public Member(String name, LocalDate birthday, boolean genderIsMale, boolean membershipPaid) {
		memberID = memberTotal + 1;
		memberTotal++;
		this.name = name;
		this.birthday = birthday;
		this.genderIsMale = genderIsMale;
		this.membershipPaid = membershipPaid;
		this.membershipFee = this.calculateMembershipFee();
	}

	public Member(int memberID, String name, LocalDate birthday, boolean genderIsMale, String type, double membershipFee, boolean membershipPaid) {
		this.memberID = memberID;
		memberTotal++;
		this.name = name;
		this.birthday = birthday;
		this.genderIsMale = genderIsMale;
		this.type = type;
		this.membershipFee = membershipFee;
		this.membershipPaid = membershipPaid;
	}

	public boolean isMale() {
		return genderIsMale;
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

	/**
	 * @param birthday
	 */
	public static int getAge(LocalDate birthday) {

		LocalDate today = LocalDate.now();
		//LocalDate birthday = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDayOfMonth());
		Period period = Period.between(birthday, today);
		return period.getYears();
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public boolean getMembershipPaid() {
		return membershipPaid;
	}

	public double calculateMembershipFee() {
		double discount = 0.75;
		double fee = 1600;
		double feeYouthmember = 1000;
		if (getAge(getBirthday()) > 60) {
			return discount * fee;
		} else if (getAge(getBirthday()) < 18) {
			return feeYouthmember;
		} else {
			return fee;
		}
	}

	public String toString() {
		String s = memberTotal + " " + memberID + " " + name + " " + birthday + " " + genderIsMale + " " + type + " " + membershipFee + " " + membershipPaid;
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
		String displayName= name;
		if(name.contains("_")){
			displayName.replace("_"," ");
		}
		return memberID + " | " + displayName + " | " + Member.getAge(this.getBirthday()) + " | " + gender + " | " + dues;

	}


	public int getMemberID() {
		return memberID;
	}

	public void setName(String nytnavn) {
		this.name =nytnavn;
	}
	public void setHasPaid(){
		membershipPaid =true;
	}


	public String getMemberName() {
		return name;
	}
}


