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
 *	��ü ���� ������ �����´�.
 */
public class PoliceStatisticsAction implements Action {

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
		
		// �˻��� 
		// ���۰�������� ��ȭ��ȣ�� �˻�ó��
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search").trim();
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR");
			//search = search.replace("-", "");
		}
				
	
		//����Ÿ��
		String type =  "SMS";
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");
		}

		//�ִ� �⵵ and �ּҳ⵵
		int maxd = Integer.valueOf(dao.Maxdate(type).substring(0, 4));		
		int mind = Integer.valueOf(dao.Mindate(type).substring(0, 4));				
		
		
		//�⵵
		String date =  dao.Maxdate(type);
		date = date.substring(0, 4);
		if(request.getParameter("date") != null){
			date = (String)request.getParameter("date");
			}
		
		int pscode = Integer.valueOf(request.getParameter("pscode"));
		
		ArrayList<Statistics> list = (ArrayList<Statistics>)dao.getDeptStatisticsList(date, type, pscode);
		
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("page", page);									//  ������ ��ȣ	
		request.setAttribute("search", search);							//   �˻�		
		request.setAttribute("list", list);											// �߼� ��������Ʈ
		request.setAttribute("type", type);									//�˻�Ÿ��
		request.setAttribute("pscode", pscode);							//�������ڵ�
		request.setAttribute("maxd", maxd);								//	���� �ֱ� �߼� �⵵
		request.setAttribute("mind", mind);									//	���� ���� �߼� �⵵
		request.setAttribute("date", date);									//	��¥

		forward.setPath("./WEB-INF/admin/ps_statistics.jsp");

		return forward;
	}

}
