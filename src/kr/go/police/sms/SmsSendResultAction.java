package kr.go.police.sms;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	��ü ���� ������ �����´�.
 */
public class SmsSendResultAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
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
		int limit = 10;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}
		
		// �˻��� 
		// ���۰�������� ��ȭ��ȣ�� �˻�ó��
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search").trim();
			search = search.replace("-", "");
		}	
		
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		int start = (page -1 ) * limit +1;											// ���� ��ȣ
		int listSize = dao.getSendResultCount(userIndex, search);		// �� �߼� ���� ����
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		// ������ ���̼� ó��
		String params = "limit=" +limit + "&search=" + search;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "SmsSendResultAction.sm", params);  
		ArrayList<SMSBean> list = (ArrayList<SMSBean>)dao.getSendResultList(userIndex, start, limit, search);
		
		request.setAttribute("no", no);									// ����Ʈ ��ȣ		
		request.setAttribute("limit", limit);								// ����������			
		request.setAttribute("page", page);								//  ������ ��ȣ	
		request.setAttribute("search", search);							//   �˻�		
		request.setAttribute("listSize", listSize);						// ��  �ּҷϱ׷� ����
		request.setAttribute("sendList", list);							// �߼� ��������Ʈ
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		forward.setPath("./sms/send_result_list.jsp");

		return forward;
	}

}