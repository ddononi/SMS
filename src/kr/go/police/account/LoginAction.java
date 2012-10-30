package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		
		// �α��� ���� ��������
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		//  �α��� ó�� Ȯ��
		boolean result = dao.loginUser(id, pwd);
		if(result){	// ���� �α��� ó����
			forward.setRedirect(true);
			forward.setPath("./sms/index.jsp"); 
			return forward;	
		}else{			// ����� ������ ���� ������
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('����� ������ Ȯ���ϼ���');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
