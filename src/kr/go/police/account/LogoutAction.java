package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�α׾ƿ� �׼�
 */
public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			//  ������ �������� �ٽ� �α����������� �̵�			
			HttpSession session = request.getSession();
			session.invalidate();
		
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α׾ƿ� �Ǿ����ϴ�.');");
			out.println("window.location.href='./login/login.jsp'");
			out.println("</script>");	
			out.close();

		return null;
	}

}
