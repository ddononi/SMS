package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	����� ���� ����
 */
public class MyInfoModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();
		// ���� �ε���
		int index = Integer.valueOf(session.getAttribute("index").toString());
		// ���
		String grade = request.getParameter("grade");
		// �̸�
		String name = request.getParameter("name");
		// ��й�ȣ
		String pwd = request.getParameter("pwd");		
		// �μ�
		String deptName = request.getParameter("deptName");
		// ������
		String psName = request.getParameter("psname");
		// ��ȭ��ȣ		
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String phone = phone1 + phone2 + phone3; 
		// �̸���
		String email = request.getParameter("email");
		// ���
		int userClass =  Integer.valueOf(request.getParameter("userClass"));
		// ���ο���
		//String approve = request.getParameter("approve");		
		
		// ����� ������ ��´�.
		UserBean data = new UserBean();
		data.setIndex(index);
		data.setGrade(grade);
		data.setDeptName(deptName);
		data.setPhone1(phone);
		data.setName(name);
		data.setPwd(pwd);		
		data.setPsName(psName);
		data.setEmail(email);
		data.setApprove(false);	// �ٽ� �̽������� ó��
		data.setUserClass(userClass);
		
		//	ȸ�� ���� ���� ó��
		if(dao.modifyMyInfo(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('����������� �����Ͽ����ϴ�.');");
			out.println("window.location.href='./LogoutAction.ac';");
			out.println("</script>");	
		}else{
			// ���� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������� ���� ����! �����ڿ��� �����ϼ���');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;	
	}

}
