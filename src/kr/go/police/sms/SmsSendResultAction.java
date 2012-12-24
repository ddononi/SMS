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
public class SmsSendResultAction implements Action {

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
		// 페이지 목록수
		int limit = 10;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}
		
		// 검색어 
		// 전송결과에서는 전화번호만 검색처리
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search").trim();
			search = search.replace("-", "");
		}	
		
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		int start = (page -1 ) * limit +1;											// 시작 번호
		int listSize = dao.getSendResultCount(userIndex, search);		// 내 발송 내역 갯수
		//	리스트 번호
		int no = listSize - (page - 1) * limit;		
		// 페이지 네이션 처리
		String params = "limit=" +limit + "&search=" + search;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "SmsSendResultAction.sm", params);  
		ArrayList<SMSBean> list = (ArrayList<SMSBean>)dao.getSendResultList(userIndex, start, limit, search);
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);	
		request.setAttribute("no", no);									// 리스트 번호		
		request.setAttribute("limit", limit);								// 한페이지수			
		request.setAttribute("page", page);								//  페이지 번호	
		request.setAttribute("search", search);							//   검색		
		request.setAttribute("listSize", listSize);						// 총  주소록그룹 갯수
		request.setAttribute("sendList", list);							// 발송 내역리스트
		request.setAttribute("pagiNation", pagiNation);				// 페이지네이션
		forward.setPath("./WEB-INF/sms/send_result_list.jsp");

		return forward;
	}

}
