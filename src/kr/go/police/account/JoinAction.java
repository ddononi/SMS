package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	회원 가입 action
 */
public class JoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 계정 DAO 
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		// 사용자 정보 가져오기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String grade = request.getParameter("grade");
		String userCalss = request.getParameter("userClass");		
		String name = request.getParameter("name");
		String deptName = request.getParameter("deptName");
		String psName = request.getParameter("psname");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String phone = phone1 + phone2 + phone3; 
		String email = request.getParameter("email");

		//---- 추후 서블릿에서도 입력 검증 처리를 해야한다.
		
		// 사용자 정보를 담는다.
		UserBean data = new UserBean();
		data.setId(id);
		data.setPwd(IGwConstant.PWD_SALT + pwd + IGwConstant.PWD_SALT);
		data.setGrade(grade);
		data.setDeptName(deptName.split(",")[1]);
		data.setDeptCode(Integer.valueOf(deptName.split(",")[0]));				
		data.setPhone1(phone);
		data.setName(name);
		data.setUserClass(Integer.valueOf(userCalss));		
		data.setPsName(psName.split(",")[1]);
		data.setPsCode(Integer.valueOf(psName.split(",")[0]));		
		data.setEmail(email);
		
		// 회원 가입처리
		if(dao.joinUser(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입이 정상처리 되었습니다. 관리자 승인후 이용이 가능합니다.');");
			out.println("window.location.href='./login/login.jsp';");
			out.println("</script>");	
			out.close();			
		}else{
			// 가입 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 실패! 관리자에게 문의하세요');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
