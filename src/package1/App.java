package package1;

import java.time.LocalDate;
import java.sql.Date;

public class App {

	public static void main(String[] args) {
		User[] user=null;
		UserDBConnect dbcn=new UserDBConnect();
		dbcn.initDBConnect();
		user=dbcn.allFetch();
		for(int i=0; i<user.length; i++) {
			System.out.println(user[i].getUserId());
			System.out.println(user[i].getBirthYear());
			System.out.println(user[i].getAddr());
			System.out.println(user[i].getHeight());			
		}
		
		System.out.println("=====================================");
		dbcn.selectUser("유재석");
		dbcn.inputUser(new User("lbg","이병규",1999,"경기",null,null,163, Date.valueOf("2024-09-27")));
		dbcn.releaseDB();
	}

}
