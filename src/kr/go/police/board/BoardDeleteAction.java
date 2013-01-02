package kr.go.police.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	게시물  삭제 액션
 */
public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		BoardDAO dao = new BoardDAO();
		// 게시물 인덱스번호
		String index = (String)request.getParameter("index");			
		
		// 삭제 처리
		if( dao.deleteBoard(Integer.valueOf(index)) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다.');");			
			out.println("window.location.replace('./BoardListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// 등록 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제 실패');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
