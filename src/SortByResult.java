import java.util.Comparator;

public class SortByResult implements Comparator<CompetitionSwimmer> {

    public void setIndex(int index) {
        this.index = index;
    }

    private int index=-99;
    public SortByResult(int i){
        this.index= i;
    }
    //getter
    public int getIndex(){
        return index;
    }
    public int compare(CompetitionSwimmer a, CompetitionSwimmer b){

      return  a.getResults()[getIndex()].compareTo(b.getResults()[getIndex()]);
    }

}