

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

public class Member implements Serializable {
	private static int medlemmer = 0;

	private static final long serialVersionUID = -9177640809256836733L;
	private int medlemsnummer;
	private String navn;
	private LocalDate foedselsdag;
	private boolean gender;
	private String type = "";
	private double kontingent = 0;
	private boolean harBetalt;


	//Constructors. no-arg constructor must be available
	public Member() {
		this.medlemsnummer = 1;

		this.navn = "Fornavn";
		this.foedselsdag = LocalDate.now();
		this.gender = false;
		this.harBetalt = false;
		this.type = "Medlemstype";
		this.kontingent = this.beregnKontingent();
	}

	public Member(String navn, LocalDate foedselsdag, boolean gender, boolean harBetalt) {
		medlemsnummer = medlemmer + 1;
		medlemmer++;
		this.navn = navn;
		this.foedselsdag = foedselsdag;
		this.gender = gender;
		this.harBetalt = harBetalt;
		this.kontingent = this.beregnKontingent();
	}

	public Member(int medlemsnummer, String navn, LocalDate foedselsdag, boolean gender, String type, double kontingent, boolean harBetalt) {
		this.medlemsnummer = medlemsnummer;
		medlemmer++;
		this.navn = navn;
		this.foedselsdag = foedselsdag;
		this.gender = gender;
		this.type = type;
		this.kontingent = kontingent;
		this.harBetalt = harBetalt;
	}

	public boolean isGender() {
		return gender;
	}

	public String getType() {
		return type;
	}

	public static int getMedlemmer() {
		return medlemmer;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param foedselsdag
	 */
	public static int getAlder(LocalDate foedselsdag) {
		// TODO - implement Medlem.getAlder

		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(foedselsdag.getYear(), foedselsdag.getMonth(), foedselsdag.getDayOfMonth());
		Period period = Period.between(birthday, today);
		return period.getYears();
	}

	public LocalDate getFoedselsdag() {
		return foedselsdag;
	}

	public boolean getHarBetalt() {
		return harBetalt;
	}

	public double beregnKontingent() {
		double rabat = 0.75;
		double kontingent = 1600;
		double kontingentUng = 1000;
		if (getAlder(getFoedselsdag()) > 60) {
			return rabat * kontingent;
		} else if (getAlder(getFoedselsdag()) < 18) {
			return kontingentUng;
		} else {
			return kontingent;
		}
	}

	public String toString() {
		String s = medlemmer + " " + medlemsnummer + " " + navn + " " + foedselsdag + " " + gender + " " + type + " " + kontingent + " " + harBetalt;
		return s;
	}

	public String printTilKonsol() {
		String køn = "";
		if (this.isGender()) {
			køn = "mand";
		} else {
			køn = "kvinde";
		}
		String s = medlemsnummer + " | " + navn + " | " + Member.getAlder(this.getFoedselsdag()) + " | " + køn + " | " + harBetalt;
		return s;
	}


	public int getMedlemsnummer() {
		return medlemsnummer;
	}

	public void setNavn(String nytnavn) {
		this.navn=nytnavn;
	}
	public void setHarBetalt(){
		harBetalt=true;
	}
//Skriv til fil og indlæs fra fil

	}


