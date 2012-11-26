package kr.go.police.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�� ������ ����
 */
public class MyMessageView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		// ������ �ε���
		int index = 0;	
		String indexStr = (String)request.getParameter("index");
		if(indexStr != null){
			index = Integer.valueOf(indexStr);
		}
		// �� ������ �޼��� ��������
		Message data = (Message)dao.getMyMessage(index);
		// �� �׷� ��� ��������
		List<Group> groupList = (List<Group>)dao.getMyGroupList(userIndex);
		request.setAttribute("groups", groupList);		
		request.setAttribute("message", data);
		forward.setPath("./sms/message_view.jsp"); 
		return forward;
		
	}

}
