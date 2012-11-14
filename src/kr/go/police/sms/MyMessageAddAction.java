package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�����Կ�  ���� �߰�
 */
public class MyMessageAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		request.setCharacterEncoding("euc-kr");
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		String id = session.getAttribute("id").toString();	// ���̵�
		// �׷� �ε���
		int groupIndex = 0;	//	0�� �⺻ �׷��̴�.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		groupIndex = Integer.valueOf(groupIndexStr);
		// ����
		String title = (String)request.getParameter("title");				
		// ����
		String message = (String)request.getParameter("message");
		// �׷��
		String groupName = (String)request.getParameter("groupName");		
		
		Message msg = new Message();
		msg.setGroupIndex(groupIndex);
		msg.setMessage(message);
		msg.setTitle(title);
		msg.setUserIndex(userIndex);
		msg.setGroup(groupName);
		msg.setId(id);
		
		if(dao.addMyMessage(msg)){	// �޼��� ��� ����
			forward.setPath("./MyMessageAction.sm?groupIndex=" + groupIndexStr);
			return forward;
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('������ ��� ����!!");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
