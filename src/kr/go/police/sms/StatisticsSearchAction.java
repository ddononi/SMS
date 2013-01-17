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
 *	전체 문자 내역을 가져온다.
 */
public class StatisticsSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// 전체  문자내역을 가져오기
		request.setCharacterEncoding("euc-kr");
		
		// 기본값 설정
		int page = 1;
		if(request.getParameter("page") != null){
			try{
				page = Integer.valueOf(request.getParameter("page"));
			}catch(NumberFormatException e){
				page =1;
			}
		}	
		
		// 시작일 		
		String s_date = "";
		if(request.getParameter("s_date") != null){
			s_date = request.getParameter("s_date").trim();
		//	search = new String(search.getBytes("iso-8859-1"), "EUC-KR");
			//search = search.replace("-", "");
		}
		
		//마지막 일
		String c_date="";
		if(request.getParameter("c_date") != null){
			c_date = request.getParameter("c_date").trim();
			//search = search.replace("-", "");
		}
		
		//전송타입
		String type =  "SMS";
		if(request.getParameter("type") != null){
				type = (String)request.getParameter("type");
		}		
				
		// 페이지 목록수
			int limit = 10;
			if(request.getParameter("limit") != null){
				limit = Integer.valueOf(request.getParameter("limit"));
			}
				
		
	//	int listSize = dao.getDeptUserCount(deptcode, search, s_type);			// 부서 총 유저 수
		int start = (page -1 ) * limit +1;		// 시작 번호
		
	//	String params = "limit=" +limit + "&deptcode=" + deptcode+ "&type=" + type;
	//	String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "DeptStatisticsAction.sm", params);  
		
		ArrayList<Statistics> list = (ArrayList<Statistics>)dao.getStatisticsSearch(s_date, c_date, type, start, limit);
		System.out.println(s_date);
		System.out.println(c_date);
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("page", page);							//  페이지 번호				
		request.setAttribute("list", list);									// 발송 내역리스트
		request.setAttribute("type", type);							//	검색타입		
		request.setAttribute("s_date", s_date);					//	시작일
		request.setAttribute("c_date", c_date);					//	마지막일

		forward.setPath("./WEB-INF/admin/statistics_search.jsp");

		return forward;
	}

}
