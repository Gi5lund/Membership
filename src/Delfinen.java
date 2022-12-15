import java.io.*;
import java.util.ArrayList;

public class Delfinen {


	public static void main(String[] args) throws Exception {
		 ArrayList<Member> members=new ArrayList<>();

		File memberlist=new File("medlemsliste.txt");
		if (memberlist.exists()) {
			System.out.println("filen findes");
			 members= FileHandler.loadMembers(members);
		}
		else {
			System.out.println("filen oprettes");
			 members=new ArrayList<>();

			members.add(new Member());

		}/*
		//****************** TEST OF SERIALIZATIONUTIL **********************
		String filename="members.ser";
		for(int i=0;i<members.size();i++){
			try {
				SerializationUtil.serialize( members.get(i),filename);
			}
			catch ( IOException e) {
				e.printStackTrace();
				return;
			}

		}
		//***************** MENU - TO BE REDESIGNED ***************


		*/
		Menu.hovedmenu(members);
		//*************** TEST OF ENUM *************************
		//System.out.println(DISCIPLINE.values().length);
		System.out.println("hej");
	}
}
