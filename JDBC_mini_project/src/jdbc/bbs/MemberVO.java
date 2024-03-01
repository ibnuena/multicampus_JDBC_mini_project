package jdbc.bbs;
//VO (Value Object) : 값을 담는 객체 , 도메인 객체 ===>Model에 해당
import java.sql.*;

public class MemberVO {
	
	private String id;
	private String pw;
	private String name;
	private String tel;
	private java.sql.Date indate;
	//기본 생성자
	public MemberVO() {
		
	}
	//인자 생성자
	public MemberVO(String id, String pw, String name, String tel, Date indate) {
		this.id=id;
		this.pw=pw;
		this.name=name;
		this.tel=tel;
		this.indate=indate;
	}
	//setter, getter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public java.sql.Date getIndate() {
		return indate;
	}
	public void setIndate(java.sql.Date indate) {
		this.indate = indate;
	}
}/////////////////////////////
