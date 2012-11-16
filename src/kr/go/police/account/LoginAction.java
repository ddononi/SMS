package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.aria.Aria;

public class LoginAction implements Action {
	private AccountDAO dao = new AccountDAO();
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		// �α��� ���� ��������
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		//  �α��� ó�� Ȯ��
		boolean result = dao.loginUser(id, pwd);
		if(result){	// ���� �α��� ó����
			// ����� ���ο��� Ȯ��
			if(dao.checkApprove(id) == false){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('������ ������ �̿� �����մϴ�.');");
				out.println("history.go(-1);");
				out.println("</script>");	
				out.close();
				return null;
			}
			
			// ����� ���� ���� ����
			initUserInfoSession(request,  id);
			forward.setRedirect(true);
			forward.setPath("./SmsSendViewAction.sm"); 
			return forward;	
		}else{			// ����� ������ ���� ������
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('����� ������ Ȯ���ϼ���');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

	/**
	 * 	���ǿ� ����� ���� ���
	 * @param request
	 * @param id
	 * 	����� ���̵�
	 */
	private void initUserInfoSession(HttpServletRequest request, String id) {
		// ���̵𸦰��� �̿��Ͽ� ����������� �����´�.
		UserBean data = dao.getUserInfo(id);
		Aria aria = Aria.getInstance();	
		// ���ǿ� ����� ������ ��´�.
		HttpSession session = request.getSession();
		session.setAttribute("name",  data.getName());
		session.setAttribute("id", data.getId());
		session.setAttribute("class", data.getUserClass());
		session.setAttribute("index", data.getIndex());
		session.setAttribute("phone",  data.getPhone1());		
		session.setAttribute("sendLimit", data.getMonthSendLimit() - data.getMonthSend());
		session.setAttribute("logined", true);		
	}

}
