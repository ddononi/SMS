package kr.go.police.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class UserDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		
		// �⺻�� ����
		if(request.getParameter("index") == null){
			return null;
		}
		// ����� �ε��� ��������
		int index = Integer.valueOf(request.getParameter("index"));
		UserBean data = dao.getUserDetail(index);
		// ����� ���� ���
		request.setAttribute("userData", data);
		forward.setPath("./admin/userDetail.jsp");
		
		return forward;			
	}

}
