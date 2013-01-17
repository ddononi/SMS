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
public class StatisticsSearchAction implements Action {

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
		
		// ������ 		
		String s_date = "";
		if(request.getParameter("s_date") != null){
			s_date = request.getParameter("s_date").trim();
		//	search = new String(search.getBytes("iso-8859-1"), "EUC-KR");
			//search = search.replace("-", "");
		}
		
		//������ ��
		String c_date="";
		if(request.getParameter("c_date") != null){
			c_date = request.getParameter("c_date").trim();
			//search = search.replace("-", "");
		}
		
		//����Ÿ��
		String type =  "SMS";
		if(request.getParameter("type") != null){
				type = (String)request.getParameter("type");
		}		
				
		// ������ ��ϼ�
			int limit = 10;
			if(request.getParameter("limit") != null){
				limit = Integer.valueOf(request.getParameter("limit"));
			}
				
		
	//	int listSize = dao.getDeptUserCount(deptcode, search, s_type);			// �μ� �� ���� ��
		int start = (page -1 ) * limit +1;		// ���� ��ȣ
		
	//	String params = "limit=" +limit + "&deptcode=" + deptcode+ "&type=" + type;
	//	String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "DeptStatisticsAction.sm", params);  
		
		ArrayList<Statistics> list = (ArrayList<Statistics>)dao.getStatisticsSearch(s_date, c_date, type, start, limit);
		System.out.println(s_date);
		System.out.println(c_date);
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("page", page);							//  ������ ��ȣ				
		request.setAttribute("list", list);									// �߼� ��������Ʈ
		request.setAttribute("type", type);							//	�˻�Ÿ��		
		request.setAttribute("s_date", s_date);					//	������
		request.setAttribute("c_date", c_date);					//	��������

		forward.setPath("./WEB-INF/admin/statistics_search.jsp");

		return forward;
	}

}
