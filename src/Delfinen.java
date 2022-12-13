import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Delfinen {


	public static void main(String[] args) throws Exception {
		 ArrayList<Medlem> medlemmer=new ArrayList<>();

		File memberlist=new File("medlemsliste.txt");
		if (memberlist.exists()) {
			System.out.println("filen findes");
			 medlemmer=Medlemsadministration.indlæsMedlemmer(medlemmer);
		}
		else {
			System.out.println("filen oprettes");
			 medlemmer=new ArrayList<>();

			medlemmer.add(new Medlem());

		}
		String filename="medlemmer.ser";
		String ksvimname="k";
		for(int i=0;i<5;i++){

		}

		//Menu.hovedmenu(medlemmer);


		System.out.println("hej");
	}
}
