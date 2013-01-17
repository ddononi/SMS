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
public class AllStatisticsAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// ��ü  ���ڳ����� ��������
		request.setCharacterEncoding("euc-kr");
		// �⺻�� ����		}			
		
	
		
		
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
						
		// ������ �ڵ尡������
			HttpSession session = request.getSession();
			int psCode =  (Integer)session.getAttribute("psCode");
			int deptcode =  (Integer)session.getAttribute("deptCode");
			String psname =  (String)session.getAttribute("psName");
						
		
		// �ش� ���� �ε���
		int userIndex = Integer.valueOf( session.getAttribute("index").toString());
		int userclass = Integer.valueOf( session.getAttribute("class").toString());	
		
		// ���� ���� �Է�
		ArrayList<Statistics> list = new ArrayList<Statistics>();
		if(psCode==100){
		list = (ArrayList<Statistics>)dao.getPsStatisticsList(date, type);
		}else{
		list = (ArrayList<Statistics>)dao.getDeptStatisticsList(date, type, psCode);
		}
		
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("list", list);							// �߼� ��������Ʈ
		request.setAttribute("uindex", userIndex);					//�����ε���
		request.setAttribute("type", type);							//����Ÿ��
		request.setAttribute("psname", psname);							//�������̸�
		request.setAttribute("psCode", psCode);							//�������̸�
		request.setAttribute("maxd", maxd);								//	���� �ֱ� �߼� �⵵
		request.setAttribute("mind", mind);									//	���� ���� �߼� �⵵
		request.setAttribute("date", date);									//	��¥
		
		if(psCode==100 && userclass==3){
		forward.setPath("./WEB-INF/admin/all_statistics.jsp");
		}else {
		forward.setPath("./WEB-INF/admin/ps_statistics.jsp");
		}

		return forward;
	}

}
