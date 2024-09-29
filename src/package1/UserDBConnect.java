package package1;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class UserDBConnect {
	private String driver="com.mysql.cj.jdbc.Driver";
	private String url="jdbc:mysql://127.0.0.1:3306/cookdb?serverTimeZone=UTC";
	private String user="root";
	private String password="1234";
	private Connection conn=null;
	private Statement stmt=null;
	
	public UserDBConnect() {
		// TODO Auto-generated constructor stub
	}
	
	public void initDBConnect(){		
		try {
			Class.forName(driver);
			this.conn=DriverManager.getConnection(this.url, this.user, this.password);
			this.stmt=conn.createStatement();		
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public int recordCount() {
		String sql="select count(*) as cnt from usertbl";
		int reCount=0;
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				reCount=rs.getInt("cnt");
//				reCount=rs.getInt(1);
			}
			rs.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return reCount;
	}
	
	public User[] allFetch() {
		int count=this.recordCount();
		User[] userList=new User[count];
		int userCount=0;
		String sql="select * from usertbl";
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String userid=rs.getString(1);
				String userName=rs.getString("username");
				int birthYear=rs.getInt("birthyear");
				String addr=rs.getString("addr");
				String mobile1=rs.getString("mobile1");
				String mobile2=rs.getString("mobile2");
				int height=rs.getInt("height");
				Date mdate=rs.getDate("mdate");
//				LocalDate mdate=rs.getDate("mdate").toLocalDate();	
//				LocalDate mdate=rs.getObject("mdate", LocalDate.class);
				userList[userCount++]=new User(userid, userName, birthYear, addr, mobile1, mobile2, height, mdate);
			}
			rs.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return userList;		
	}
	public void selectUser(String username) {
//		String sql="select * from usertbl where username='"+username+"'";
		
		String sql="select * from usertbl where username=?";		
		User user=null;
		try {
//			ResultSet rs=stmt.executeQuery(sql);
			
			PreparedStatement pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				String userid=rs.getString(1);
				String userName=rs.getString("username");
				int birthYear=rs.getInt("birthyear");
				String addr=rs.getString("addr");
				String mobile1=rs.getString("mobile1");
				String mobile2=rs.getString("mobile2");
				int height=rs.getInt("height");
//				Date mdate=rs.getDate("mdate");
//				LocalDate mdate=rs.getDate("mdate").toLocalDate();	
				LocalDate mdate=rs.getObject("mdate", LocalDate.class);
				System.out.println(userid);
				System.out.println(userName);
				System.out.println(birthYear);
				System.out.println(addr);	
			}
			rs.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void inputUser(User user) {
		String sql="insert into usertbl values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setInt(3, user.getBirthYear());		
			pstmt.setString(4, user.getAddr());
			pstmt.setString(5, user.getMobile1());
			pstmt.setString(6, user.getMobile2());
			pstmt.setInt(7, user.getHeight());
			pstmt.setDate(8, user.getMdate());
//			pstmt.setDate(8, Date.valueOf(user.getMdate()));
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void releaseDB() {
		try {
			this.stmt.close();
			this.conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}

}
