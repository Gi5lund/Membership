import java.io.Serializable;
import java.time.LocalTime;

public class SwimDisciplines implements Serializable {
	private String disciplinName;
	private Result resultater;
	private LocalTime currentbest;

	//constructors...noarg constructor must be found
	public SwimDisciplines(){
		this.disciplinName ="NyDisciplin";
		this.resultater=new Result();
		this.currentbest=this.resultater.getResult();
	}
	public SwimDisciplines(String disciplinName){
		this.disciplinName = disciplinName;
		this.resultater=new Result();
		this.currentbest=this.resultater.getResult();
	}
	public SwimDisciplines(String disciplinnavn, LocalTime resultat){
		this.disciplinName = disciplinName;
		this.resultater= new Result();
		this.currentbest=resultat;
		this.resultater.setBestTime(resultat);
	}
	public String getDisciplinName() {
		return disciplinName;
	}

	public Result getResultater() {
		return resultater;
	}
	public String toString(){
		return " "+ this.getDisciplinName()+" "+getResultater().getResult()+" ";
	}


}