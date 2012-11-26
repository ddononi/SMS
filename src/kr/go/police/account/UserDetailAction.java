package kr.go.police.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class UserDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();

		// ����� �ε��� ��������
		String indexSt = (String)request.getParameter("index");
		int index = Integer.valueOf(indexSt);
		UserBean data = dao.getUserDetail(index);
		// ����� ���� ���
		request.setAttribute("userData", data);
		forward.setPath("./admin/user_view.jsp");
		return forward;			
	}
}