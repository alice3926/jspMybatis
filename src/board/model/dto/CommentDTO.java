package board.model.dto;

import java.sql.Date;

public class CommentDTO {
	private int comment_no;
	private int board_no;
	private String writer;
	private String content;
	private String passwd;
	private int memberNo;
	private String ip;
	private Date regiDate;
	
	
	
	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}



	public int getComment_no() {
		return comment_no;
	}



	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}



	public int getBoard_no() {
		return board_no;
	}



	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}



	public String getWriter() {
		return writer;
	}



	public void setWriter(String writer) {
		this.writer = writer;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getPasswd() {
		return passwd;
	}



	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}



	public int getMemberNo() {
		return memberNo;
	}



	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public Date getRegiDate() {
		return regiDate;
	}



	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}
	
	
	
	
	
	
	
	
	
}
