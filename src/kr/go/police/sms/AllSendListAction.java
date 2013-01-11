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
 *	전체 문자 내역을 가져온다.
 */
public class AllSendListAction implements Action {

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
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR");
			//search = search.replace("-", "");
		}
		
		String type="from";
		if(request.getParameter("type") != null){
			type = (String)request.getParameter("type");		
		}
		
		// 전송 모드 ( sms, lms, mms )
		String mode = "SMS";
		if(request.getParameter("mode") != null){
			mode = (String)request.getParameter("mode");		
		}			

		// 경찰서 코드가져오기
		HttpSession session = request.getSession();
		int psCode =  (Integer)session.getAttribute("psCode");		
				
		int start = (page -1 ) * limit +1;			// 시작 번호
		int listSize = 0;
		// 모드별 전체 발송 갯수 얻기
		if(mode.equals("SMS")){	
			 listSize = dao.getSmsSendAllListCount(search,type, psCode);		
		}else if(mode.equals("LMS")){	
			listSize = dao.getLmsSendAllListCount(search,type, psCode);
		}else{	//mms
			listSize = dao.getMmsSendAllListCount(search,type, psCode);
		}
		
		//	리스트 번호
		int no = listSize - (page - 1) * limit;		
		// 페이지 네이션 처리
		String params = "limit=" +limit + "&search=" + search +"&type="+type +"&mode="+mode;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "AllSendListAction.sm", params);
		// 모드별 전체 발송 갯수 얻기
		ArrayList<?> list = null;
		if(mode.equals("SMS")){	
			list = (ArrayList<LGSMSBean>)dao.getSmsSendAllList(start, limit, search, type, psCode);
		}else if(mode.equals("LMS")){	
			list = (ArrayList<LGMMSBean>)dao.getLmsSendAllList(start, limit, search, type, psCode);
		}else{
			list = (ArrayList<LGMMSBean>)dao.getMmsSendAllList(start, limit, search, type, psCode);
		}		
		
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
		request.setAttribute("mode", mode);							// 검색타입		
		request.setAttribute("type", type);							// 검색타입
		
		// 전송종류에 따른 페이지 분기
	//	if(mode.equals("SMS")){	
			forward.setPath("./WEB-INF/admin/all_sms_send_list.jsp");
	//	}else{
		//	forward.setPath("./WEB-INF/admin/all_mms_send_list.jsp");
	//	}		

		return forward;
	}

}
