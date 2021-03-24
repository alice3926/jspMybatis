package shop.product.model.dto;

import java.sql.Date;

public class ProductDTO {
	private int no;
	private String name;
	private int price;
	private String description;
	private String product_img;
	private Date regi_date;
	private int preNo;
	private String preSubject;
	private int nxtNo;
	private String nxtSubject;
	private int buy_counter;	
	
	public ProductDTO() {
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




	public int getPrice() {
		return price;
	}




	public void setPrice(int price) {
		this.price = price;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getProduct_img() {
		return product_img;
	}




	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}




	public Date getRegi_date() {
		return regi_date;
	}




	public void setRegi_date(Date regi_date) {
		this.regi_date = regi_date;
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




	public int getBuy_counter() {
		return buy_counter;
	}




	public void setBuy_counter(int buy_counter) {
		this.buy_counter = buy_counter;
	}
	
	
	
	
	
	
	
}
