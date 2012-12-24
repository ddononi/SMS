package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *  �μ� ����
 */
public class DeptManageViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		AccountDAO dao = new AccountDAO();		
		// ������ �ڵ尡������
		HttpSession session = request.getSession();
		int psCode =  (Integer)session.getAttribute("psCode");		
		
		// �������� �μ� ��� ��������
		ArrayList<PoliceBean> list =  (ArrayList<PoliceBean>) dao.getPsDeptList(psCode);
		request.setAttribute("list", list);									
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);				
		forward.setPath("./WEB-INF/admin/dept_manage_view.jsp");
		return forward;			
	}
	
}