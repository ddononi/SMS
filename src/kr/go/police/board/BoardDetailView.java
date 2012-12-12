package kr.go.police.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�Խù� ���� ���� �׼�
 */
public class BoardDetailView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  �ε����� �ش� �Խù��� �̾ƿ´�.
		int index = Integer.valueOf((String)request.getParameter("index"));
		
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// ��ȸ�� ����
		dao.updateReadCount(index, userIndex);
		// �Խù����� ��������
		BoardBean data = dao. getDetail(index);
		// �ش� �Խù��� ��� ���
		List<BoardBean>replyList =(List<BoardBean>)dao.getReplyList(index);
		
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);				
		request.setAttribute("replyList", replyList);
		request.setAttribute("data", data);
		// �Խù� ���� �������� �̵�
		forward.setPath("./WEB-INF/board/board_view.jsp"); 
		return forward;
	}

}
