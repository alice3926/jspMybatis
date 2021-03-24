package memo.model.dto;

import java.sql.Timestamp;

import com.mysql.jdbc.Clob;

public class MemoDTO {
	private int no;
	private String writerId;
	private String writerName;
	private String memo;
	private Timestamp writeDate;
	
	
	public MemoDTO() {
		// TODO Auto-generated constructor stub
	}


	public String getWriterId() {
		return writerId;
	}


	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}


	public String getWriterName() {
		return writerName;
	}


	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public Timestamp getWriteDate() {
		return writeDate;
	}


	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}
	
	
	
	
	
}
