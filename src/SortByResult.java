import java.util.Comparator;

public class SortByResult implements Comparator<Konkurrencesvømmer> {

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
    public int compare(Konkurrencesvømmer a, Konkurrencesvømmer b){

      return  a.getResults()[getIndex()].compareTo(b.getResults()[getIndex()]);
    }

}