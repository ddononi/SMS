package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�Խù� ��������ȭ��
 */
public class BoardModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  �ε����� �ش� �Խù��� �̾ƿ´�.
		int index = Integer.valueOf((String)request.getParameter("index"));
		// �Խù����� ��������
		BoardBean data = dao. getDetail(index);
		request.setAttribute("data", data);
		// �Խù� ���� �������� �̵�
		forward.setPath("./board/board_modify.jsp"); 
		return forward;
	}

}
