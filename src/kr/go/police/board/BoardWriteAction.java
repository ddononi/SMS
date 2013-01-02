package kr.go.police.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	게시물  등록 액션
 */
public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("euc-kr");
		// 사용자 이름, 인덱스 얻기
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		BoardBean data = new BoardBean();
		
		String title = (String)request.getParameter("title");						// 제목
		String content = (String)request.getParameter("content");			// 내용
		String pwd = (String)request.getParameter("board_pwd");			// 비밀번호
		
		System.out.println("title : " +  title);
		data.setRegisterName(session.getAttribute("name").toString());	// 이름
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		data.setRegUserIndex(userIndex);											// 유저 인덱스
		data.setTitle(title);
		data.setContent(content);
		data.setPwd(pwd);
		data.setFilename("");			// 문의 게시판은 파일업로드 사용 안함
		data.setNotice(false);		// 공지사항이 아니므로
		
		// 등록 처리
		if(dao.insertBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의를 등록하였습니다.');");			
			out.println("window.location.replace('./BoardListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// 등록 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의 등록 실패');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
