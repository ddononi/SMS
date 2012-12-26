package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class PwdModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		int index = Integer.valueOf(request.getParameter("index"));
		String id = request.getParameter("id");
				
		// 사용자 정보 담기		
		request.setAttribute("index", index);
		request.setAttribute("id", id);
		forward.setPath("./WEB-INF/account/pwd_modify.jsp");
		return forward;			
	}
}