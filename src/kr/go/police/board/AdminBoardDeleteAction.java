package kr.go.police.board;

import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	공지사항 게시물 삭제 액션
 */
public class AdminBoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		// 게시물 인덱스번호
		String tmpIndexs = (String)request.getParameter("del_index");			
		String[] indexs = tmpIndexs.split(",");
		int delCount = 0;
		for(String index : indexs){
			dao.deleteBoard(Integer.valueOf(index));
			delCount++;
		}
		// 삭제 처리알림
		if( delCount == indexs.length ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다.');");			
			out.println("window.location.replace('./AdminBoardListAction.bo');");
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
