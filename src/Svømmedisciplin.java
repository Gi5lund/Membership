import java.io.Serializable;
import java.time.LocalTime;

public class Sv�mmedisciplin implements Serializable {
	private String disciplinNavn;
	private Result resultater;
	private LocalTime currentbest;

	//constructors...noarg constructor must be found
	public Sv�mmedisciplin(){
		this.disciplinNavn="NyDisciplin";
		this.resultater=new Result();
		this.currentbest=this.resultater.getResult();
	}
	public Sv�mmedisciplin(String disciplinNavn){
		this.disciplinNavn=disciplinNavn;
		this.resultater=new Result();
		this.currentbest=this.resultater.getResult();
	}
	public Sv�mmedisciplin(String disciplinnavn, LocalTime resultat){
		this.disciplinNavn=disciplinNavn;
		this.resultater= new Result();
		this.currentbest=resultat;
		this.resultater.setBedsteTid(resultat);
	}
	public String getDisciplinNavn() {
		return disciplinNavn;
	}

	public Result getResultater() {
		return resultater;
	}
	public String toString(){
		return " "+ this.getDisciplinNavn()+" "+getResultater().getResult()+" ";
	}


}