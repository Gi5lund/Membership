import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Result implements Serializable {

	private LocalDate trainingDate =LocalDate.now();
	private LocalTime trainingTime;
	private String competitionEventName ="ikke deltaget i st�vne endnu";
	private int competitionEventRank =-99;
	private LocalTime competitionEventTime;
	private LocalTime bestTime;
	transient DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");






	public Result(){

		String initTime="23:59:59.999";
		LocalTime initialTime=LocalTime.parse(initTime, timeFormat);
		this.trainingTime =initialTime;
		this.competitionEventTime =initialTime;
		this.bestTime =initialTime;
	}

	public Result(String trainingDate, String trainingTime){
		this.trainingDate =LocalDate.parse(trainingDate);
		this.trainingTime =LocalTime.parse(trainingTime);
		if(this.trainingTime.compareTo(this.competitionEventTime)<0){
			this.bestTime =this.trainingTime;
		}
		else{
			this.bestTime =this.competitionEventTime;
		}
	}
	public void setSt�vneresultater(String st�vnenavn, int placering, String st�vnetid){
		this.competitionEventName =st�vnenavn;
		this.competitionEventRank =placering;
		this.competitionEventTime =LocalTime.parse(st�vnetid);
		if(this.trainingTime.compareTo(this.competitionEventTime)<0){
			this.bestTime =this.trainingTime;
		}
		else{
			this.bestTime =this.competitionEventTime;
		}
	}
	public void setTr�ningsresultater(String tr�ningsdato, String tr�ningstid){
		this.trainingDate =LocalDate.parse(tr�ningsdato);
		this.trainingTime =LocalTime.parse(tr�ningstid);
		if(this.trainingTime.compareTo(this.competitionEventTime)<0){
			this.bestTime =this.trainingTime;
		}
		else{
			this.bestTime =this.competitionEventTime;
		}
	}
	public LocalTime getResult(){
		return bestTime;
	}
	public String toString(){
		return this.bestTime.format(timeFormat);

	}
	public LocalTime getTrainingTime() {
		return trainingTime;
	}
	public void setBestTime(LocalTime res){
		this.bestTime =res;
	}
	public LocalTime getCompetitionEventTime() {
		return competitionEventTime;
	}




}