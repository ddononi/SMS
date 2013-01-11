package kr.go.police.sms;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	��ü ���� ������ �����´�.
 */
public class AllSendListAction implements Action {

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
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR");
			//search = search.replace("-", "");
		}
		
		String type="from";
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");		
		}
		
		// ���� ��� ( sms, lms, mms )
		String mode = "SMS";
		if(request.getParameter("mode") != null){
			mode = (String)request.getParameter("mode");		
		}			

		// ������ �ڵ尡������
		HttpSession session = request.getSession();
		int psCode =  (Integer)session.getAttribute("psCode");		
				
		int start = (page -1 ) * limit +1;			// ���� ��ȣ
		int listSize = 0;
		// ��庰 ��ü �߼� ���� ���
		if(mode.equals("SMS")){	
			 listSize = dao.getSmsSendAllListCount(search,type, psCode);		
		}else if(mode.equals("LMS")){	
			listSize = dao.getLmsSendAllListCount(search,type, psCode);
		}else{	//mms
			listSize = dao.getMmsSendAllListCount(search,type, psCode);
		}
		
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		// ������ ���̼� ó��
		String params = "limit=" +limit + "&search=" + search +"&type="+type +"&mode="+mode;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "AllSendListAction.sm", params);
		// ��庰 ��ü �߼� ���� ���
		ArrayList<?> list = null;
		if(mode.equals("SMS")){	
			list = (ArrayList<LGSMSBean>)dao.getSmsSendAllList(start, limit, search, type, psCode);
		}else if(mode.equals("LMS")){	
			list = (ArrayList<LGMMSBean>)dao.getLmsSendAllList(start, limit, search, type, psCode);
		}else{
			list = (ArrayList<LGMMSBean>)dao.getMmsSendAllList(start, limit, search, type, psCode);
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
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		request.setAttribute("mode", mode);							// �˻�Ÿ��		
		request.setAttribute("type", type);							// �˻�Ÿ��
		
		// ���������� ���� ������ �б�
	//	if(mode.equals("SMS")){	
			forward.setPath("./WEB-INF/admin/all_sms_send_list.jsp");
	//	}else{
		//	forward.setPath("./WEB-INF/admin/all_mms_send_list.jsp");
	//	}		

		return forward;
	}

}
