package kr.go.police.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�Խù� ���� ���� �׼�
 */
public class BoardReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  �ε����� �ش� �Խù��� �̾ƿ´�.
		String index = (String)request.getParameter("parentIndex");
		if(index == null){
			// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��� ��� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		int parentIndex = Integer.valueOf(index);
		String content = (String)request.getParameter("reply_content");
		
		HttpSession session = request.getSession();
		// ��� ����
		BoardBean data = new BoardBean();
		data.setParentIndex(parentIndex);
		data.setRegisterName((String)session.getAttribute("id"));
		data.setContent(content);
		if(dao.replyBoard(data, parentIndex)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}else{
			// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��� ��� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
