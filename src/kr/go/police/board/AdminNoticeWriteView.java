package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	������ �������� ��� ȭ��
 */
public class AdminNoticeWriteView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);		
		//	�������� ��� ȭ��
		forward.setPath("./WEB-INF/admin/notice_write.jsp");
		return forward;
	}

}
