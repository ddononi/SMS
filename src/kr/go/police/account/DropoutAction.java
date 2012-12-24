package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	회원 탈퇴 처리
 */
public class DropoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();
		// 유저 인덱스
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// 확인할 비밀번호
		String pwd = request.getParameter("confirm_pwd");
		//	회원 정보 수정 처리
		if(dao.dropout(userIndex, pwd)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정상적으로 탈퇴처리 되었습니다.');");
			out.println("window.location.href='./LogoutAction.ac';");
			out.println("</script>");	
		}else{
			// 가입 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('탈퇴 실패! 비밀번호를 확인하세요');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;	
	}

}
