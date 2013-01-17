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
public class AllStatisticsAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// 전체  문자내역을 가져오기
		request.setCharacterEncoding("euc-kr");
		// 기본값 설정		}			
		
	
		
		
		//전송타입
		String type =  "SMS";
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");
		}
		
		//최대 년도 and 최소년도
		int maxd = Integer.valueOf(dao.Maxdate(type).substring(0, 4));		
		int mind = Integer.valueOf(dao.Mindate(type).substring(0, 4));				
								
		//년도
		String date =  dao.Maxdate(type);
		date = date.substring(0, 4);
		if(request.getParameter("date") != null){
			date = (String)request.getParameter("date");
			}
						
		// 경찰서 코드가져오기
			HttpSession session = request.getSession();
			int psCode =  (Integer)session.getAttribute("psCode");
			int deptcode =  (Integer)session.getAttribute("deptCode");
			String psname =  (String)session.getAttribute("psName");
						
		
		// 해당 유저 인덱스
		int userIndex = Integer.valueOf( session.getAttribute("index").toString());
		int userclass = Integer.valueOf( session.getAttribute("class").toString());	
		
		// 유저 정보 입력
		ArrayList<Statistics> list = new ArrayList<Statistics>();
		if(psCode==100){
		list = (ArrayList<Statistics>)dao.getPsStatisticsList(date, type);
		}else{
		list = (ArrayList<Statistics>)dao.getDeptStatisticsList(date, type, psCode);
		}
		
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("list", list);							// 발송 내역리스트
		request.setAttribute("uindex", userIndex);					//유저인덱스
		request.setAttribute("type", type);							//전송타입
		request.setAttribute("psname", psname);							//경찰서이름
		request.setAttribute("psCode", psCode);							//경찰서이름
		request.setAttribute("maxd", maxd);								//	가장 최근 발송 년도
		request.setAttribute("mind", mind);									//	가장 예전 발송 년도
		request.setAttribute("date", date);									//	날짜
		
		if(psCode==100 && userclass==3){
		forward.setPath("./WEB-INF/admin/all_statistics.jsp");
		}else {
		forward.setPath("./WEB-INF/admin/ps_statistics.jsp");
		}

		return forward;
	}

}
