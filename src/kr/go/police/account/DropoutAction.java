package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	ȸ�� Ż�� ó��
 */
public class DropoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();
		// ���� �ε���
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// Ȯ���� ��й�ȣ
		String pwd = request.getParameter("confirm_pwd");
		//	ȸ�� ���� ���� ó��
		if(dao.dropout(userIndex, pwd)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���������� Ż��ó�� �Ǿ����ϴ�.');");
			out.println("window.location.href='./LogoutAction.ac';");
			out.println("</script>");	
		}else{
			// ���� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Ż�� ����! ��й�ȣ�� Ȯ���ϼ���');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;	
	}

}
