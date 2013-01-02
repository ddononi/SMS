package kr.go.police.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	´ñ±Û µî·Ï ¾×¼Ç
 */
public class AdminBoardReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");

		BoardDAO dao = new BoardDAO();
		//  ÀÎµ¦½º·Î ÇØ´ç °Ô½Ã¹°À» »Ì¾Æ¿Â´Ù.
		String index = (String)request.getParameter("parentIndex");
		if(index == null){
			// µî·Ï ½ÇÆÐ
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('´ñ±Û µî·Ï ½ÇÆÐ');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		// ºÎ¸ð ÀÎµ¦½º
		int parentIndex = Integer.valueOf(index);
		// ´ñ±Û ³»¿ë
		String content = (String)request.getParameter("reply_content");
		HttpSession session = request.getSession();
		// ´ñ±Û ³»¿ë
		BoardBean data = new BoardBean();
		data.setParentIndex(parentIndex);
		data.setRegisterName((String)session.getAttribute("id"));
		data.setContent(content);
		// ´ñ±Û µî·Ï Ã³¸®
		if(dao.replyBoard(data, parentIndex)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("window.location.replace('./AdminBoardDetailView.bo?index=" +index + "');");
			out.println("</script>");	
			out.close();
		}else{
			// µî·Ï ½ÇÆÐ
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('´ñ±Û µî·Ï ½ÇÆÐ');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
