package kr.go.police;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.account.AccountDAO;

public class LoginCheck {
	
	public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session  = request.getSession();
		Boolean result = (Boolean)session.getAttribute("logined");
		if(  result == null || result == false ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α����� �̿밡���մϴ�.')");
			out.println("window.location.href='./login/login.jsp'; ");
			out.println("</script>");	
			out.close();
			return false;
		}
		
		return true;
	}
	
	/**
	 * �ٸ� pc���� �ߺ� �α����� �ߴ��� üũ�ϱ� ���� 
	 * ���� ���̵� ��ó��
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static boolean sessionIdCheck(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session  = request.getSession();
		AccountDAO dao = new AccountDAO();
		if( dao.sessionCheck( session.getAttribute("id").toString(), session.getId()) == false ){
			// �����ʿ� �α��� �ߺ� �˸�
			session.setAttribute("sessionListener", "duplLogin");
			session.invalidate();			// ���� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ٸ� ��ǻ�Ϳ��� �α��εǾ����ϴ�.');");
			out.println("window.location.href='./login/login.jsp'; ");
			out.println("</script>");	
			out.close();
			return false;
		}
		
		return true;
	}	

}
