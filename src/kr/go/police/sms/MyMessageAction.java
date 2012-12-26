package kr.go.police.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class MyMessageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");		
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		// 그룹 인덱스
		// 내 기본 그룹 가져오기		
		int groupIndex = dao.getMyBaseGroup(userIndex);
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
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
		int limit = 8;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}				
								
		int start = (page -1 ) * limit +1;		// 시작 번호
		int listSize = dao.getMessageListCount(groupIndex);		// 내 문자함 갯수
		//	리스트 번호
		//int no = listSize - (page - 1) * limit;		
		// 페이지 네이션 처리
		String params = "limit=" +limit +"&index="+ userIndex +"&userIndex="+userIndex;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "MyMessageAction.sm", params);  	
		// 내 문자내역을 가져오기
		List<Message> messageList = (List<Message>)dao.getMessagesList(userIndex, groupIndex, start, limit);
		// 내 그룹 목록 가져오기
		List<Group> groupList = (List<Group>)dao.getMyGroupList(userIndex);
		
		request.setAttribute("pagiNation", pagiNation);
		request.setAttribute("limit", limit);		
		request.setAttribute("messages", messageList);
		request.setAttribute("groupIndex", groupIndexStr);		
		request.setAttribute("groups", groupList);		
		forward.setPath("./WEB-INF/sms/my_message.jsp"); 
		return forward;
	}

}
