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
			out.println("alert('로그인후 이용가능합니다.')");
			out.println("window.location.href='./login/login.jsp'; ");
			out.println("</script>");	
			out.close();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 다른 pc에서 중복 로그인을 했는지 체크하기 위한 
	 * 세션 아이디값 비교처리
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static boolean sessionIdCheck(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session  = request.getSession();
		AccountDAO dao = new AccountDAO();
		if( dao.sessionCheck( session.getAttribute("id").toString(), session.getId()) == false ){
			// 리스너에 로그인 중복 알림
			session.setAttribute("sessionListener", "duplLogin");
			session.invalidate();			// 세션 제거
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('다른 컴퓨터에서 로그인되었습니다.');");
			out.println("window.location.href='./login/login.jsp'; ");
			out.println("</script>");	
			out.close();
			return false;
		}
		
		return true;
	}	

}
