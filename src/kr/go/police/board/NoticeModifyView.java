package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�������� �Խù� �������� ȭ��
 */
public class NoticeModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  �ε����� �ش� �Խù��� �̾ƿ´�.
		int index = Integer.valueOf((String)request.getParameter("index"));
		// �Խù����� ��������
		BoardBean data = dao. getDetail(index);
		String content = data.getContent();
		content = content.replaceAll("\r\n", "<br/>");
		data.setContent(content);
		request.setAttribute("data", data);
		// �������� ���� ���� �������� �̵�
		forward.setPath("./admin/notice_modify.jsp"); 
		return forward;
	}

}
