import java.io.*;
import java.util.ArrayList;

public class Delfinen {


	public static void main(String[] args) throws Exception {
		 ArrayList<Member> medlemmer=new ArrayList<>();

		File memberlist=new File("medlemsliste.txt");
		if (memberlist.exists()) {
			System.out.println("filen findes");
			 medlemmer=Medlemsadministration.indlæsMedlemmer(medlemmer);
		}
		else {
			System.out.println("filen oprettes");
			 medlemmer=new ArrayList<>();

			medlemmer.add(new Member());

		}
		String filename="medlemmer.ser";
		for(int i=0;i<medlemmer.size();i++){
			try {
				SerializationUtil.serialize( medlemmer.get(i),filename);
			}
			catch ( IOException e) {
				e.printStackTrace();
				return;
			}

		}
		/* her vil jeg oprette 5 forskellige konkurrencesvømmere og prøve at benytte serialization/deserialization
		til at persistere mine objekter
				String filename="medlemmer.ser";
		String ksvimname="k";
		for(int i=0;i<5;i++){
		ksvimname=ksvimname+i;
		Konkurrencesvømmer k=new Konkurrencesvømmer()
		}
		*/
		//Menu.hovedmenu(medlemmer);


		System.out.println("hej");
	}
}
