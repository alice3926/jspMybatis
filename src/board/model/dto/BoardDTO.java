package board.model.dto;

import java.sql.Date;

public class BoardDTO {
	private int no;  //일련번호
	private int num;
	private String tbl;
	private String writer;
	private String subject;
	private String content;
	private String email;
	private String passwd;
	private int refNo;
	private int stepNo;
	private int levelNo;
	private int parentNo;
	private int hit;
	private String ip;
	private int memberNo;
	private int noticeNo;
	private String secretGubun;
	private Date regiDate; //import.java.util.Date;
	
	private String noticeGubun;
	private int child_counter; //답변글 수
	
	private int preNo; //이전글 번호
	private String preSubject; //이전근 제목
	private int nxtNo; //다음글 번호
	private String nxtSubject;  //다음글 제목
	
	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTbl() {
		return tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getRefNo() {
		return refNo;
	}

	public void setRefNo(int refNo) {
		this.refNo = refNo;
	}

	public int getStepNo() {
		return stepNo;
	}

	public void setStepNo(int stepNo) {
		this.stepNo = stepNo;
	}

	public int getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}

	public int getParentNo() {
		return parentNo;
	}

	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getSecretGubun() {
		return secretGubun;
	}

	public void setSecretGubun(String secretGubun) {
		this.secretGubun = secretGubun;
	}

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	public String getNoticeGubun() {
		return noticeGubun;
	}

	public void setNoticeGubun(String noticeGubun) {
		this.noticeGubun = noticeGubun;
	}

	public int getChild_counter() {
		return child_counter;
	}

	public void setChild_counter(int child_counter) {
		this.child_counter = child_counter;
	}

	public int getPreNo() {
		return preNo;
	}

	public void setPreNo(int preNo) {
		this.preNo = preNo;
	}

	public String getPreSubject() {
		return preSubject;
	}

	public void setPreSubject(String preSubject) {
		this.preSubject = preSubject;
	}

	public int getNxtNo() {
		return nxtNo;
	}

	public void setNxtNo(int nxtNo) {
		this.nxtNo = nxtNo;
	}

	public String getNxtSubject() {
		return nxtSubject;
	}

	public void setNxtSubject(String nxtSubject) {
		this.nxtSubject = nxtSubject;
	}
	
	
	
	
	
	
	
	
}
