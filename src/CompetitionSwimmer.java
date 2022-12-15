import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CompetitionSwimmer extends Member implements Serializable {
	private static final long serialVersionUID = 211326650313734248L;
	private boolean[] activeInDisciplin =new boolean[DISCIPLINE.values().length]; //True if Swimmer trains and compete in discipline

	private SwimDisciplines[] disciplines =new SwimDisciplines[DISCIPLINE.values().length];
	private LocalTime[] resultsInSwimDisciplines =new LocalTime[DISCIPLINE.values().length];
	transient DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	//constructors. in order to be able to serialize/deserialize a no-argument constructor must be available
	public CompetitionSwimmer(){
		super();
		for(int i=0;i<DISCIPLINE.values().length;i++){
			this.activeInDisciplin[i]=false;
			this.disciplines[i]=new SwimDisciplines();
			this.resultsInSwimDisciplines[i]=LocalTime.parse("23:59:59.000", timeFormat);
		}

	}
	// til at nyoprette et medlem
	public CompetitionSwimmer(String navn, LocalDate bday, boolean gender, boolean harBetalt, String disciplinset){ //til at oprette nye medlemmer
		super(navn,bday,gender, harBetalt);
		this.activeInDisciplin =setAktivDiscipliner(disciplinset);
		 DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS"); //TODO: TRY MOVE DATETIMEFORMATTER OUTSIDE AS STATIC
		String initTime="23:59:59.000";
		LocalTime initialtime=LocalTime.parse(initTime,timeFormat);
		//************* TODO: REWRITE THIS PART TO ACOMMODATE TO ENUM DISCPILINE! **********
		for (int i=0;i<DISCIPLINE.values().length;i++){
			if(activeInDisciplin[i]){
				if(i==0){    //"brystsvømning=b, crawl=c, ryg=r, butterfly=f"
					disciplines[i]=new SwimDisciplines(String.valueOf(DISCIPLINE.values()[i])); //should be breasstroke
				}
				if(i==1){
					disciplines[i]=new SwimDisciplines(String.valueOf(DISCIPLINE.values()[i]));// should be crawl
				}
				if(i==2){
					disciplines[i]=new SwimDisciplines(String.valueOf(DISCIPLINE.values()[i]));//should be backcrawl
				}
				if(i==3){
					disciplines[i]=new SwimDisciplines(String.valueOf(DISCIPLINE.values()[i])); //should be butterfly
				}
			}
			resultsInSwimDisciplines[i]=initialtime;
		}



	}

	public CompetitionSwimmer(int memberID, String name, LocalDate birthdate, boolean gender, String type, double membershipFee, boolean hasPaid, boolean[] activDisciplines, LocalTime[] bestResults){ // til at indlæse medlemmer fra fil
		super(memberID, name,birthdate,gender,type, membershipFee, hasPaid);
		this.activeInDisciplin =setAktivDiscipliner(activDisciplines);
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
		String tider="23:59:59.999";
		LocalTime initTime=LocalTime.parse(tider,timeFormat);
		for (int i=0;i<DISCIPLINE.values().length;i++){
			if(this.activeInDisciplin[i]){
				this.disciplines[i]=new SwimDisciplines(String.valueOf(DISCIPLINE.values()[i]));
				this.disciplines[i].getResultater().setBestTime(bestResults[i]);
				this.resultsInSwimDisciplines[i]=bestResults[i];
			}
			else {
				this.resultsInSwimDisciplines[i]=initTime;
			}
		}

	}

	public String printToScreen(){
		String s=Arrays.toString(disciplines);
		return super.printToScreen()+s;
	}

	public String toString(){
		String a="",d="",r="";
		for(int i=0;i<DISCIPLINE.values().length;i++){
			a=a.concat(activeInDisciplin[i]+" ");
			d=d.concat(disciplines[i]+" ");
			r=r.concat(resultsInSwimDisciplines[i]+" ");
		}
		String s=super.toString()+" "+a+" "+d+" "+r;
		return s;
	}
	public boolean[] setAktivDiscipliner(String disciplinset) { //til
		if(disciplinset.contains("b")){
			activeInDisciplin[0]=true;
		}
		if(disciplinset.contains("c")){
			activeInDisciplin[1]=true;
		}
		if(disciplinset.contains("r")){
			activeInDisciplin[2]=true;
		}
		if(disciplinset.contains("f")){
			activeInDisciplin[3]=true;
		}
		return activeInDisciplin;
	}

	public void setActiveInDisciplin(boolean[] activeInDisciplin) {
		this.activeInDisciplin = activeInDisciplin;
	}

	public boolean[] setAktivDiscipliner(boolean[] aktivdisciplins) {
		for (int i=0;i<DISCIPLINE.values().length;i++) {
			if( aktivdisciplins[i]){
				activeInDisciplin[i]=true;
			}
			else{
				activeInDisciplin[i]=false;
			}
		}
		return activeInDisciplin;
	}

	public boolean[] getActiveInDisciplin() {
		return activeInDisciplin;
	}
	public LocalTime[] getResults() {

		for (int i=0;i<DISCIPLINE.values().length;i++){
			if(activeInDisciplin[i]){
				resultsInSwimDisciplines[i]= disciplines[i].getResultater().getResult();
			}
			else{
				resultsInSwimDisciplines[i]=LocalTime.parse("23:59:59.999", timeFormat);
			}
		}
		return resultsInSwimDisciplines;
	}


	public void addDisciplines() {
		Scanner sc=new Scanner(System.in);
		System.out.println("svømmeren er aktiv i følgende discipliner");
		for(int i = 0; i< activeInDisciplin.length; i++){
			if(activeInDisciplin[i]){
				System.out.println(getDisciplines()[i].getDisciplinName());
			}
		}
		System.out.println("hvilke discpliner vil du tilføje/ændre?: ");
		for (int i=0;i<DISCIPLINE.values().length;i++) {
			System.out.println(i + 1 + ": " + DISCIPLINE.values()[i]);
		}

		int choice=99;

		boolean[] changeDisciplineStatus=getActiveInDisciplin();
		while(choice!=0) {
			choice=sc.nextInt();
			if(choice==0){
				break;
			}
			changeDisciplineStatus[choice-1]=!getActiveInDisciplin()[choice-1];
			setActiveInDisciplin(changeDisciplineStatus);// should flip the status of
			System.out.println("hvilke discpliner vil du tilføje/ændre? (0 for at afslutte): ");
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