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
 *	문자 발송 화면 처리
 */
public class MyMessageListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
				int limit = 4;
				if(request.getParameter("limit") != null){
					limit = Integer.valueOf(request.getParameter("limit"));
				}				
										
				int start = (page -1 ) * limit +1;		// 시작 번호
				int listSize = dao.getMessageListCount(groupIndex);		// 내 문자함 갯수
				//	리스트 번호
				int no = listSize - (page - 1) * limit;		
				// 페이지 네이션 처리
				String params = "limit=" +limit +"&index="+ userIndex +"&userIndex="+userIndex;
				String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "MyMessageListAction.sm", params);  		
		// 내 문자내역을 가져오기
		List<Message> messageList = (List<Message>)dao.getMessagesList(userIndex, groupIndex, start, 4);
		// 내 그룹 목록 가져오기
		List<Group> groupList = (List<Group>)dao.getMyGroupList(userIndex);
		// 문자발송 메인 화면
		request.setAttribute("list", messageList);
		request.setAttribute("pagiNation", pagiNation);
		request.setAttribute("limit", limit);
		request.setAttribute("userIndex", userIndex);
		request.setAttribute("groupIndex", groupIndex);
		request.setAttribute("groupList", groupList);
		forward.setPath("./WEB-INF/sms/my_message_list.jsp");
		return forward;
	}

}
