package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class MyInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();

		// ����� �ε��� ��������
		HttpSession session = request.getSession();
		String indexStr= session.getAttribute("index").toString();
		
		int index = Integer.valueOf(indexStr);
		UserBean data = dao.getUserDetail(index);
		// ������ ���
		ArrayList<PoliceBean> psList = (ArrayList<PoliceBean>)dao.getPsList();
		// �μ� ���
		ArrayList<DeptBean> deptList = (ArrayList<DeptBean>)dao.getSubDeptList(data.getPsCode());		
		// ����� ���� ���
		request.setAttribute("user", data);
		request.setAttribute("psList", psList);	
		request.setAttribute("deptList", deptList);			
		forward.setPath("./WEB-INF/account/myInfo.jsp");
		return forward;			
	}
}