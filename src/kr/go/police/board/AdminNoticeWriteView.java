package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//	�������� ��� ȭ��
		forward.setPath("./admin/notice_write.jsp");
		return forward;
	}

}
