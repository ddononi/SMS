package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	 ������ ����
 */
public class MyMessageModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		request.setCharacterEncoding("euc-kr");
		// ���� ������ �ε���
		String indexStr = (String)request.getParameter("index");
		int index = Integer.valueOf(indexStr);
		// �׷� �ε���
		String groupIndexStr = (String)request.getParameter("groupIndex");
		// ����
		String title = (String)request.getParameter("title");				
		// ����
		String message = (String)request.getParameter("message");
		
		Message msg = new Message();
		msg.setIndex(index);
		msg.setMessage(message);
		msg.setTitle(title);
		
		if(dao.modifyMyMessage(msg)){	// �޼��� ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�������� �����߽��ϴ�.');");
			out.println("window.location.href='" +
					"./MyMessageAction.sm?groupIndex=" + groupIndexStr + "';");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('������ ���� ����!!');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
