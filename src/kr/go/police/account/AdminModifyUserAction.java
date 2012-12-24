package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class AdminModifyUserAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		
		request.setCharacterEncoding("euc-kr");
		// 사용자 정보 가져오기
		int index = Integer.valueOf(request.getParameter("index"));
		// 등급
		String grade = request.getParameter("grade");
		// 이름
		String name = request.getParameter("name");
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
		String approve = request.getParameter("approve");		
		System.out.println("approve : " + approve);
		// 사용자 정보를 담는다.
		UserBean data = new UserBean();
		data.setIndex(index);
		data.setGrade(grade);
		data.setDeptName(deptName);
		data.setPhone1(phone);
		data.setName(name);
		data.setPsName(psName);
		data.setEmail(email);
		data.setApprove(approve.equalsIgnoreCase("y"));
		data.setPsName(psName.split(",")[1]);
		data.setPsCode(Integer.valueOf(psName.split(",")[0]));			
		data.setDeptName(deptName.split(",")[1]);
		data.setDeptCode(Integer.valueOf(deptName.split(",")[0]));					
		data.setUserClass(userClass);
		
		//	회원 정보 수정 처리
		if(dao.modifyUserInfoFromAdmin(data)){
			// 정상 처리면 다시 회원 리스트로 이동
			forward.setPath("./UserListAction.ac"); 
			return forward;
		}else{
			// 가입 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('승인처리 실패!');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;	
	}

}
