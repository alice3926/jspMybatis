package survey.model.dto;

import java.sql.Timestamp;

public class SurveyDTO {
	private int no;
	private String question;
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private String status;
	private Timestamp start_date;
	private Timestamp last_date;
	private Timestamp regi_date;
	private int survey_counter;
	
	public SurveyDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAns1() {
		return ans1;
	}

	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}

	public String getAns2() {
		return ans2;
	}

	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}

	public String getAns3() {
		return ans3;
	}

	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}

	public String getAns4() {
		return ans4;
	}

	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public Timestamp getLast_date() {
		return last_date;
	}

	public void setLast_date(Timestamp last_date) {
		this.last_date = last_date;
	}

	public Timestamp getRegi_date() {
		return regi_date;
	}

	public void setRegi_date(Timestamp regi_date) {
		this.regi_date = regi_date;
	}

	public int getSurvey_counter() {
		return survey_counter;
	}

	public void setSurvey_counter(int survey_counter) {
		this.survey_counter = survey_counter;
	}


	
	
	
}
