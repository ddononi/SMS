package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	ȸ�� ���� action
 */
public class JoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ���� DAO 
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		// ����� ���� ��������
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

		//---- ���� ���������� �Է� ���� ó���� �ؾ��Ѵ�.
		
		// ����� ������ ��´�.
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
		
		// ȸ�� ����ó��
		if(dao.joinUser(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ�������� ����ó�� �Ǿ����ϴ�. ������ ������ �̿��� �����մϴ�.');");
			out.println("window.location.href='./login/login.jsp';");
			out.println("</script>");	
			out.close();			
		}else{
			// ���� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ������ ����! �����ڿ��� �����ϼ���');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
