package kr.go.police.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문의 게시물 수정 액션
 */
public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		
		
		// 사용자 이름, 인덱스 얻기
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		
		String boardIndex = (String)request.getParameter("index");		// 게시물 인덱스		
		String title = (String)request.getParameter("title");						// 제목
		String content = (String)request.getParameter("content");			// 내용
		String pwd = (String)request.getParameter("board_pwd");			// 비밀번호
		
		// 유저 인덱스
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		BoardBean data = new BoardBean();
		data.setIndex(Integer.valueOf(boardIndex));								// 게시물 번호
		data.setRegisterName(session.getAttribute("name").toString());	// 이름		
		data.setRegUserIndex(userIndex);											// 유저 인덱스
		data.setTitle(title);																// 제목	
		data.setContent(content);														// 내용
		data.setPwd(pwd);																// 비번
		data.setNotice(false);															// 공지여부
		// 수정 처리
		if(dao.modifyBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정되었습니다.');");			
			out.println("window.location.replace('./BoardDetailView.bo?index=" + boardIndex  + "');");
			out.println("</script>");	
			out.close();
		}else{				// 등록 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의 수정 실패');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
