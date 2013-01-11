package kr.go.police.sms;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
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
		
		// ���� ��� ( sms, lms, mms )
		String mode = "SMS";
		if(request.getParameter("mode") != null){
			mode = (String)request.getParameter("mode");		
		}			
		
		// �˻��� 
		// ���۰�������� ��ȭ��ȣ�� �˻�ó��
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
			search = new String(search.trim().getBytes("iso-8859-1"), "EUC-KR");			
			search = search.replace("-", "");
		}	
		
		String type="to";
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");		
		}		
		
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		int start = (page -1 ) * limit +1;														// ���� ��ȣ
		// ���� ��庰 �߼� ���� ���
		int listSize = 0;
		if(mode.equals("SMS")){	
			listSize = dao.getUserSmsSentCount(userIndex, search, type);	
		}else if(mode.equals("LMS")){	
			listSize = dao.getUserLmsSentCount(userIndex, search, type);
		}else{
			listSize = dao.getUserMmsSentCount(userIndex, search, type);	
		}	
		
		
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		// ������ ���̼� ó��
		String params = "limit=" +limit + "&search=" + search +"&type="+type +"&mode="+mode;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "SmsSendResultAction.sm", params);
		ArrayList<?> list = null;
		if(mode.equals("SMS")){	
			list = (ArrayList<LGSMSBean>)dao.getUserSmsSentList(userIndex, start, limit, search, type);
		}else if(mode.equals("LMS")){	
			list = (ArrayList<LGMMSBean>)dao.getUserLmsSentList(userIndex, start, limit, search, type);
		}else{
			list = (ArrayList<LGMMSBean>)dao.getUserMmsSentList(userIndex, start, limit, search, type);
		}			
		
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);	
		request.setAttribute("no", no);									// ����Ʈ ��ȣ		
		request.setAttribute("limit", limit);								// ����������			
		request.setAttribute("page", page);								//  ������ ��ȣ	
		request.setAttribute("search", search);							//   �˻�		
		request.setAttribute("listSize", listSize);						// ��  �ּҷϱ׷� ����
		request.setAttribute("sendList", list);							// �߼� ��������Ʈ
		request.setAttribute("type", type);								// �˻�Ÿ��		
		request.setAttribute("mode", mode);							// �߼�����				
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		forward.setPath("./WEB-INF/sms/send_result_list.jsp");

		return forward;
	}

}
