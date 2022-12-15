import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CompetitionSwimmer extends Member implements Serializable {
	private static final long serialVersionUID = 211326650313734248L;
	private boolean[] aktiveInDisciplin =new boolean[DISCIPLINE.values().length]; //True if Swimmer trains and compete in discipline

	private SwimDisciplines[] disciplines =new SwimDisciplines[DISCIPLINE.values().length];
	private LocalTime[] resultsInSwimDisciplines =new LocalTime[DISCIPLINE.values().length];
	transient DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	//constructors. in order to be able to serialize/deserialize a no-argument constructor must be available
	public CompetitionSwimmer(){
		super();
		for(int i=0;i<DISCIPLINE.values().length;i++){
			this.aktiveInDisciplin[i]=false;
			this.disciplines[i]=new SwimDisciplines();
			this.resultsInSwimDisciplines[i]=LocalTime.parse("23:59:59.000", timeFormat);
		}

	}
	// til at nyoprette et medlem
	public CompetitionSwimmer(String navn, LocalDate bday, boolean gender, boolean harBetalt, String disciplinset){ //til at oprette nye medlemmer
		super(navn,bday,gender, harBetalt);
		this.aktiveInDisciplin =setAktivDiscipliner(disciplinset);
		 DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS"); //TODO: TRY MOVE DATETIMEFORMATTER OUTSIDE AS STATIC
		String tider="23:59:59.000";
		LocalTime initialtider=LocalTime.parse(tider,timeFormat);
		//************* TODO: REWRITE THIS PART TO ACOMMODATE TO ENUM DISCPILINE! **********
		for (int i=0;i<DISCIPLINE.values().length;i++){
			if(aktiveInDisciplin[i]){
				if(i==0){    //"brystsvømning=b, crawl=c, ryg=r, butterfly=f"
					disciplines[i]=new SwimDisciplines("brystsvømning");
				}
				if(i==1){
					disciplines[i]=new SwimDisciplines("crawl");
				}
				if(i==2){
					disciplines[i]=new SwimDisciplines("ryg");
				}
				if(i==3){
					disciplines[i]=new SwimDisciplines("butterfly");
				}
			}
			resultsInSwimDisciplines[i]=initialtider;
		}



	}

	public CompetitionSwimmer(int medlemsnummer, String navn, LocalDate foedselsdag, boolean gender, String type, double kontingent, boolean harBetalt, boolean[] aktivdisciplins, LocalTime[] bedsteresultater){ // til at indlæse medlemmer fra fil
		super(medlemsnummer, navn,foedselsdag,gender,type, kontingent, harBetalt);
		this.aktiveInDisciplin =setAktivDiscipliner(aktivdisciplins);
		DateTimeFormatter tidsformat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
		String tider="23:59:59.999";
		LocalTime initialtider=LocalTime.parse(tider,tidsformat);
		for (int i=0;i<4;i++){
			if(this.aktiveInDisciplin[i]){
				if(i==0){    //"brystsvømning=b, crawl=c, ryg=r, butterfly=f"
					this.disciplines[i]=new SwimDisciplines("brystsvømning");
					this.disciplines[i].getResultater().setBedsteTid(bedsteresultater[i]);
					this.resultsInSwimDisciplines[i]=bedsteresultater[i];
				}
				if(i==1){
					this.disciplines[i]=new SwimDisciplines("crawl");
					this.disciplines[i].getResultater().setBedsteTid(bedsteresultater[i]);
					this.resultsInSwimDisciplines[i]=bedsteresultater[i];
				}
				if(i==2){
					this.disciplines[i]=new SwimDisciplines("ryg");
					this.disciplines[i].getResultater().setBedsteTid(bedsteresultater[i]);
					this.resultsInSwimDisciplines[i]=bedsteresultater[i];
				}
				if(i==3){
					this.disciplines[i]=new SwimDisciplines("butterfly");
					this.disciplines[i].getResultater().setBedsteTid(bedsteresultater[i]);
					this.resultsInSwimDisciplines[i]=bedsteresultater[i];
				}
			}
			else {
				this.resultsInSwimDisciplines[i]=initialtider;
			}


		}

	}

	public String printToScreen(){
		String s=Arrays.toString(disciplines);
		return super.printToScreen()+s;
	}

	public String toString(){
		String a="",d="",r="";
		for(int i=0;i<4;i++){
			a=a.concat(aktiveInDisciplin[i]+" ");
			d=d.concat(disciplines[i]+" ");
			r=r.concat(resultsInSwimDisciplines[i]+" ");
		}
		String s=super.toString()+" "+a+" "+d+" "+r;
		return s;
	}
	public boolean[] setAktivDiscipliner(String disciplinset) { //til
		if(disciplinset.contains("b")){
			aktiveInDisciplin[0]=true;
		}
		if(disciplinset.contains("c")){
			aktiveInDisciplin[1]=true;
		}
		if(disciplinset.contains("r")){
			aktiveInDisciplin[2]=true;
		}
		if(disciplinset.contains("f")){
			aktiveInDisciplin[3]=true;
		}
		return aktiveInDisciplin;
	}

	public boolean[] setAktivDiscipliner(boolean[] aktivdisciplins) {
		for (int i=0;i<4;i++) {
			if( aktivdisciplins[i]){
				aktiveInDisciplin[i]=true;
			}
			else{
				aktiveInDisciplin[i]=false;
			}
		}
		return aktiveInDisciplin;
	}

	public boolean[] getAktiveInDisciplin() {
		return aktiveInDisciplin;
	}
	public LocalTime[] getResults() {

		for (int i=0;i<4;i++){
			if(aktiveInDisciplin[i]){
				resultsInSwimDisciplines[i]= disciplines[i].getResultater().getResult();
			}
			else{
				resultsInSwimDisciplines[i]=LocalTime.parse("23:59:59.999", timeFormat);
			}
		}
		return resultsInSwimDisciplines;
	}


	public void tilføjDisciplin() {
		Scanner sc=new Scanner(System.in);
		System.out.println("svømmeren er aktiv i følgende discipliner");
		for(int i = 0; i< aktiveInDisciplin.length; i++){
			if(aktiveInDisciplin[i]){
				System.out.println(getDisciplines()[i].getDisciplinName());
			}
		}
		System.out.println("hvilke discpliner vil du tilføje/ændre?: ");
		System.out.println("1: brystsvømning");
		System.out.println("2: crawl");
		System.out.println("3: rygsvømning");
		System.out.println("4: Butterfly");
		int valg;
		valg=sc.nextInt();
		switch (valg){
			case 1:
				System.out.println();
				break;
			case 2:
				System.out.println();
				break;
			case 3:
				System.out.println();
				break;
			case 4:
				System.out.println();
				break;
		}
	}
	public SwimDisciplines[] getDisciplines() {
		return disciplines;
	}

	public static void konkurrencesvoemmerliste(ArrayList<Member> medlemmer) {
		for(Member m: medlemmer){
			if(m.getType().equals("Konkurrencesvømmer")) {
				System.out.println(m.printToScreen());
			}
		}

	}
}