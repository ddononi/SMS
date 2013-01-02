package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	사용자 정보 수정
 */
public class MyInfoModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();
		// 유저 인덱스
		int index = Integer.valueOf(session.getAttribute("index").toString());
		// 등급
		String grade = request.getParameter("grade");
		// 이름
		String name = request.getParameter("name");
		// 비밀번호
		String pwd = request.getParameter("pwd");		
		// 부서
		String deptName = request.getParameter("deptName");
		// 경찰서
		String psName = request.getParameter("psname");
		// 전화번호		
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String phone = phone1 + phone2 + phone3; 
		// 이메일
		String email = request.getParameter("email");
		// 등급
		int userClass =  Integer.valueOf(request.getParameter("userClass"));
		// 승인여부
		//String approve = request.getParameter("approve");		
		
		// 사용자 정보를 담는다.
		UserBean data = new UserBean();
		data.setIndex(index);
		data.setGrade(grade);
		data.setDeptName(deptName.split(",")[1]);
		data.setDeptCode(Integer.valueOf(deptName.split(",")[0]));			
		data.setPhone1(phone);
		data.setName(name);
		data.setPsName(psName.split(",")[1]);
		data.setPsCode(Integer.valueOf(psName.split(",")[0]));		
		data.setEmail(email);
		data.setUserClass(userClass);
		// 일반사용자면 다시 미승인으로 처리
		data.setApprove(session.getAttribute("class").equals(3));
		//	회원 정보 수정 처리
		if(dao.modifyMyInfo(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('사용자정보를 변경하였습니다.');");
			// 관리자가 아닐경우
			if(!session.getAttribute("class").equals(3)){
				out.println("alert('관리자 승인 후 사용이 가능합니다.');");	
			}
			out.println("window.location.replace('./LogoutAction.ac');");
			out.println("</script>");	
		}else{
			// 가입 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('사용자정보 변경 실패! 관리자에게 문의하세요');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;	
	}

}
