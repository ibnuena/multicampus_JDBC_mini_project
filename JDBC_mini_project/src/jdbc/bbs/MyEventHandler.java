package jdbc.bbs;

//이벤트 핸들러 ==> Application Layer == Controller
//UI<=====Application Layter ===> Data Layer ===> DB
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MyEventHandler implements ActionListener {

	private MyBoardApp gui;// View
	private MemberDAO userDao;// Model
	private BbsDAO bbsDao;// Model

	public MyEventHandler(MyBoardApp app) {
		this.gui = app;
		userDao = new MemberDAO();
		bbsDao = new BbsDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == gui.btJoin) {// 회원가입
			joinMember();
		} else if (obj == gui.btClear) {// 지우기
			gui.clear1();
		} else if (obj == gui.btList) {// 회원목록
			listMember();
		} else if (obj == gui.btDel) {// 회원 탈퇴
			removeMember();
		} else if (obj == gui.bbsWrite) { // 게시판 글쓰기
			bbs_write();
			bbs_list();
			bbs_my_list();
		} else if (obj == gui.btLogin) { // 로그인 처리
			login();
			bbs_my_list();
		} else if (obj == gui.bbsList) { // 게시판 글 목록
			bbs_list();
		} else if (obj == gui.bbsDel) { // 게시글 삭제
			// 로그인한 사람이 자신이 쓴 글만 삭제
			remove_bbs();
		} else if (obj == gui.bbsFind) {
			// title로 검색
			search_bbs();
		}

	}// -------------------------------

	private void search_bbs() {
		// 검색어 받기
		String search = gui.tfSearch.getText();

		// 유효성 체크
		if (search == null || search.trim().isEmpty()) {
			gui.showMsg("검색어(제목)를 입력하세요.");
			gui.tfSearch.requestFocus();
			return;
		}

		// 3. bbsDao의 searchBbs(search) 호출
		try {
			ArrayList<BbsVO> searchList = bbsDao.searchBbs(search);

			gui.showList(searchList);
			//
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	private void remove_bbs() {
		// 1. 입력한 id값 받기
		String delNo = JOptionPane.showInputDialog("삭제할 글 번호를 입력하세요");

		String login_id = gui.tfWriter.getText(); // 현재 로그인중인 아이디

		// 2. 유효성 체크
		if (delNo == null || delNo.trim().equals("")) {
			gui.showMsg("삭제할 글 번호를 입력하세요");
			return;
		}

		// 3. bbsDao의 deleteBbs(no, login_id) 호출
		try {
			int n = bbsDao.deleteBbs(Integer.parseInt(delNo), login_id);

			// 4. 결과 메세지 처리
			String msg = (n > 0) ? "글 삭제 완료!!" : "글 삭제 실패(없는 글번호이거나 본인이 작성한 게시글이 아닙니다.)";
			gui.showMsg(msg);

			if (n > 0) {
				bbs_my_list();
				bbs_list();
				gui.tabbedPane.setSelectedIndex(4);
			}
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}

	}

	private void bbs_list() {
		try {
			// bbsDao의 selectAll() 호출
			ArrayList<BbsVO> bbsList = bbsDao.selectAll();

			gui.showList(bbsList);
			//
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	private void bbs_my_list() {
		try {
			// userDao의 selectAll() 호출
			ArrayList<BbsVO> bbsList = bbsDao.selectAll();

			gui.showMyList(bbsList, gui.tfWriter.getText());
			//
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	private void bbs_write() {
		// 1. 입력값 받기
		String title = gui.tfTitle.getText();
		String writer = gui.tfWriter.getText();
		String content = gui.taContent.getText();

		// 2. 유효성 체크
		if (title == null || content == null || title.trim().isEmpty() || content.trim().isEmpty()) {
			gui.showMsg("글제목, 내용은 필수 입력사항입니다");
			gui.tfId.requestFocus();
			return;
		}

		// 3. 입력값들을 BbsVO객체에 담아주기
		BbsVO bbs = new BbsVO(0, title, writer, content, null);

		// 4. bbsDao의 insertBbs()호출
		try {
			int n = bbsDao.insertBbs(bbs);

			// 5. 결과에 따른 메시지 처리
			String msg = (n > 0) ? "글쓰기 성공" : "글쓰기 실패";
			gui.showMsg(msg);
			if (n > 0) {
				gui.tabbedPane.setSelectedIndex(3);
				// 로그인 탭 선택
				gui.clear2();
			}

		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	public void login() {
		// id, pw값 받기
		String id = gui.loginId.getText();
		char[] ch = gui.loginPwd.getPassword();
		String pw = new String(ch);

		// 유효성 체크
		if (id == null || ch == null || id.trim().isEmpty() || pw.trim().isEmpty()) {
			gui.showMsg("로그인 아이디와 비밀번호를 입력하세요");
			gui.loginId.requestFocus();
			return;
		}

		try {
			// userDao의 loginCheck(id, pw) 호출
			int result = userDao.loginCheck(id, pw);
			System.out.println("result : " + result);
			if (result > 0) {
				// 결과 값이 1이면 로그인 성공
				gui.showMsg(id + "님 환영합니다");
				gui.tabbedPane.setEnabledAt(2, true); // 게시판 탭 활성화
				gui.tabbedPane.setEnabledAt(3, true);
				gui.tabbedPane.setEnabledAt(4, true);
				gui.setTitle(id + "님 로그인 중...");
				gui.tfWriter.setText(id);
				gui.tabbedPane.setSelectedIndex(3);
			} else {
				// 음수값이면 로그인 실패
				gui.showMsg("아이디 또는 비밀번호가 일치하지 않습니다");
				gui.tabbedPane.setEnabledAt(2, false);
				gui.tabbedPane.setEnabledAt(3, false);
				gui.tabbedPane.setEnabledAt(4, false);
			}
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	private void listMember() {
		try {
			// userDao의 selectAll() 호출
			ArrayList<MemberVO> userList = userDao.selectAll();

			// 반환 받은 ArrayList에서 회원정보 꺼내서 taMembers에 출력
			gui.showMemers(userList);
			//
		} catch (SQLException e) {
			gui.showMsg(e.getMessage());
		}
	}

	private void removeMember() {
		// 1. 입력한 id값 받기
		String delId = gui.tfId.getText();

		// 2. 유효성 체크
		if (delId == null || delId.trim().equals("")) {
			gui.showMsg("탈퇴할 회원의 ID를 입력하세요");
			gui.tfId.requestFocus();
			return;
		}

		// 3. userDao의 deleteMember(id) 호출
		try {
			int n = userDao.deleteMember(delId.trim());

			// 4. 결과 메세지 처리

			String msg = (n > 0) ? "회원탈퇴 완료!!" : "탈퇴 실패-없는 ID입니다";
			gui.showMsg(msg);

			if (n > 0) {
				gui.tabbedPane.setEnabledAt(2, false);
				gui.tabbedPane.setEnabledAt(3, false);
				gui.clear1();
				gui.tabbedPane.setSelectedIndex(0); // 로그인 탭 선택
			}
		} catch (SQLException e) {

		}

	}

	private void joinMember() {
		// 1. 입력값 받기
		String id = gui.tfId.getText();
		String name = gui.tfName.getText();
		String pw = gui.tfPw.getText();
		String tel = gui.tfTel.getText();

		// 2. 유효성 체크 (id,pw,name)
		if (id == null || name == null || pw == null || id.trim().isEmpty() || name.trim().isEmpty()
				|| pw.trim().isEmpty()) {
			gui.showMsg("ID,Name,Password는 필수 입력사항입니다");
			gui.tfId.requestFocus();
			return;
		}

		// 3. 입력값들을 MemberVO객체에 담아주기
		MemberVO user = new MemberVO(id, pw, name, tel, null);

		// 4. userDao의 insertMember()호출
		try {
			int n = userDao.insertMember(user);

			// 5. 결과에 따른 메시지 처리
			String msg = (n > 0) ? "회원가입 완료-로그인으로 이동합니다" : "회원가입 실패";
			gui.showMsg(msg);
			if (n > 0) {
				gui.tabbedPane.setSelectedIndex(0);
				// 로그인 탭 선택
				gui.clear1();
			}

		} catch (SQLException e) {
			gui.showMsg("아이디는 이미 사용 중 입니다: " + e.getMessage());
		}
	}// -------------------

}////////////////////////////////
