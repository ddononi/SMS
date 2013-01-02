package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�׷� ���� action
 */
public class GroupModifyAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// �׷��ε���
		String groupIndex = (String)request.getParameter("groupIndex");
		String groupName = (String)request.getParameter("groupName");		
		if(dao.modifyGroup(userIndex, Integer.valueOf(groupIndex), groupName) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�׷��� �����Ͽ����ϴ�.');");
			out.println("window.location.replace('./MyGroupListAction.sm')");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������!!");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
