package kr.go.police.sms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �߼� ȭ�� ó��
 */
public class MyMessageListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		// �׷� �ε���
		// �� �⺻ �׷� ��������		
		int groupIndex = dao.getMyBaseGroup(userIndex);
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
		// ��ü  ���ڳ����� ��������
				request.setCharacterEncoding("euc-kr");
				// �⺻�� ����
				int page = 1;
				if(request.getParameter("page") != null){
					try{
						page = Integer.valueOf(request.getParameter("page"));
					}catch(NumberFormatException e){
						page =1;
					}
				}
				// ������ ��ϼ�
				int limit = 4;
				if(request.getParameter("limit") != null){
					limit = Integer.valueOf(request.getParameter("limit"));
				}				
										
				int start = (page -1 ) * limit +1;		// ���� ��ȣ
				int listSize = dao.getMessageListCount(groupIndex);		// �� ������ ����
				//	����Ʈ ��ȣ
				int no = listSize - (page - 1) * limit;		
				// ������ ���̼� ó��
				String params = "limit=" +limit +"&index="+ userIndex +"&userIndex="+userIndex;
				String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "MyMessageListAction.sm", params);  		
		// �� ���ڳ����� ��������
		List<Message> messageList = (List<Message>)dao.getMessagesList(userIndex, groupIndex, start, 4);
		// �� �׷� ��� ��������
		List<Group> groupList = (List<Group>)dao.getMyGroupList(userIndex);
		// ���ڹ߼� ���� ȭ��
		request.setAttribute("list", messageList);
		request.setAttribute("pagiNation", pagiNation);
		request.setAttribute("limit", limit);
		request.setAttribute("userIndex", userIndex);
		request.setAttribute("groupIndex", groupIndex);
		request.setAttribute("groupList", groupList);
		forward.setPath("./WEB-INF/sms/my_message_list.jsp");
		return forward;
	}

}
