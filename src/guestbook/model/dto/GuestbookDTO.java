package guestbook.model.dto;

import java.sql.Date;

public class GuestbookDTO {
	private int no;
	private String name;
	private String email;
	private String passwd;
	private String content;
	private Date regi_date;
	
	
	
	
	
	public GuestbookDTO() {
		// TODO Auto-generated constructor stub
	}





	public int getNo() {
		return no;
	}





	public void setNo(int no) {
		this.no = no;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getPasswd() {
		return passwd;
	}





	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}





	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
	}





	public Date getRegi_date() {
		return regi_date;
	}





	public void setRegi_date(Date regi_date) {
		this.regi_date = regi_date;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
