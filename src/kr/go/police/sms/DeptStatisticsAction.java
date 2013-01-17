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
public class DeptStatisticsAction implements Action {

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
		
		//�˻� Ÿ��
		String s_type="id";
		if(request.getParameter("s_type") != null){
			s_type = request.getParameter("s_type").trim();
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
				
				
		// ������ ��ϼ�
			int limit = 10;
			if(request.getParameter("limit") != null){
				limit = Integer.valueOf(request.getParameter("limit"));
			}
		
		int deptcode = Integer.valueOf(request.getParameter("deptcode"));
		
		int listSize = dao.getDeptUserCount(deptcode, search, s_type);			// �μ� �� ���� ��
		int start = (page -1 ) * limit +1;		// ���� ��ȣ
		
		String params = "limit=" +limit + "&deptcode=" + deptcode+ "&type=" + type+ "&search=" + search+ "&s_type=" + s_type;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "DeptStatisticsAction.sm", params);  

		ArrayList<Statistics> list = (ArrayList<Statistics>)dao.getUserStatisticsList(date, type, deptcode, start, limit, search, s_type);
		
		// token ����
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("page", page);								//  ������ ��ȣ	
		request.setAttribute("search", search);							//   �˻�		
		request.setAttribute("list", list);							// �߼� ��������Ʈ
		request.setAttribute("type", type);							//�˻�Ÿ��
		request.setAttribute("s_type", s_type);							//�˻�Ÿ��
		request.setAttribute("deptcode", deptcode);							//�μ��ڵ�
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		request.setAttribute("maxd", maxd);								//	���� �ֱ� �߼� �⵵
		request.setAttribute("mind", mind);									//	���� ���� �߼� �⵵
		request.setAttribute("date", date);									//	��¥

		forward.setPath("./WEB-INF/admin/dept_statistics.jsp");

		return forward;
	}

}
