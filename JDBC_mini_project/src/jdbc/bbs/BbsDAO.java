package jdbc.bbs;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//게시판 관련 crud 수행 => data layer
public class BbsDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	/** 글쓰기 */
	public int insertBbs(BbsVO vo) throws SQLException {
		try {
			con = DBUtil.getCon();

			String sql = "insert into bbs(no, title, writer, content, wdate) values("
					+ "bbs_seq.nextval, ?, ?, ?, sysdate)";

			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getWriter());
			ps.setString(3, vo.getContent());

			int n = ps.executeUpdate();
			return n;
		} finally {
			close();
		}
	}// --------------------

	/** 글 목록 가져오기 */
	public ArrayList<BbsVO> selectAll() throws SQLException {
		try {
			con = DBUtil.getCon();
			String sql = "SELECT no, title, writer, content, wdate FROM bbs ORDER BY no DESC";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			return makeList(rs);

		} finally {
			close();
		}
	}

	private ArrayList<BbsVO> makeList(ResultSet rs2) throws SQLException {
		ArrayList<BbsVO> arr = new ArrayList<>();
		while (rs.next()) {
			int no = rs.getInt("no");
			String title = rs.getString("title");
			String writer = rs.getString("writer");
			String content = rs.getString("content");
			java.sql.Date wdate = rs.getDate("wdate");

			BbsVO vo = new BbsVO(no, title, writer, content, wdate);
			arr.add(vo);
		} // while -----------------------------------

		return arr;
	}
	
	// 게시글 삭제
	public int deleteBbs(int no, String login_id) throws SQLException{
		try {
			System.out.println("1");
			con = DBUtil.getCon();
			// delete문 작성
			String sql = "delete from bbs where no=? and writer=?";
			// ps 얻기
			ps = con.prepareStatement(sql);
			System.out.println("2");

			// ? setting
			ps.setInt(1, no);
			ps.setString(2, login_id);
			System.out.println("3");
			// 실행 -> 실행결과 반환
			int n = ps.executeUpdate();
			System.out.println("12");
			return n;
		}finally {
			close();
		}
	}

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// -------------------------------
}
