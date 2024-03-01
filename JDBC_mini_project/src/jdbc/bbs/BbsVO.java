package jdbc.bbs;
import java.sql.*;
//게시판과 관련된 도메인 객체
public class BbsVO {
	
	private int no;
	private String title;
	private String writer;
	private String content;
	private Date wdate;
	
	public BbsVO() {
		super();
	}

	public BbsVO(int no, String title, String writer, String content, Date wdate) {
		super();
		this.no = no;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.wdate = wdate;
	}
	//setter, getter
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Date getWdate() {
		return wdate;
	}

	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}
	
	
	

}////////////////////////////////
