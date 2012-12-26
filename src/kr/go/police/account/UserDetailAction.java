package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class UserDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();

		// 사용자 인덱스 가져오기
		String indexSt = (String)request.getParameter("index");
		int index = Integer.valueOf(indexSt);
		UserBean data = dao.getUserDetail(index);
		// 경찰서 목록
		ArrayList<PoliceBean> psList = (ArrayList<PoliceBean>)dao.getPsList();
		// 부서 목록
		ArrayList<DeptBean> deptList = (ArrayList<DeptBean>)dao.getSubDeptList(data.getPsCode());		
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		// 경찰서 및 부서 정보
		request.setAttribute("psList", psList);	
		request.setAttribute("deptList", deptList);	
		// 사용자 정보 담기				
		request.setAttribute("userData", data);
		forward.setPath("./WEB-INF/admin/user_view.jsp");
		return forward;			
	}
}