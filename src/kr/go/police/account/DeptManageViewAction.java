package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *  부서 관리
 */
public class DeptManageViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");
		AccountDAO dao = new AccountDAO();		
		// 경찰서 코드가져오기
		HttpSession session = request.getSession();
		int psCode =  (Integer)session.getAttribute("psCode");		
		
		// 경찰서별 부서 목록 가져오기
		ArrayList<PoliceBean> list =  (ArrayList<PoliceBean>) dao.getPsDeptList(psCode);
		request.setAttribute("list", list);									
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);				
		forward.setPath("./WEB-INF/admin/dept_manage_view.jsp");
		return forward;			
	}
	
}