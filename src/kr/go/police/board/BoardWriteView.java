package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �ϱ� ȭ��
 */
public class BoardWriteView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		//	�����ϱ� ȭ��
		forward.setPath("./board/board_write.jsp");
		return forward;
	}

}
