package kr.go.police.address;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.sms.Group;

/**
 *	주소록 목록 action
 */
public class AddressGroupListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AddressDAO dao = new AddressDAO();
		
		request.setCharacterEncoding("euc-kr");
		// 기본값 설정
		int page = 1;

		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 10;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}
		
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
		}	
		
		String searchWhat = "아이디";
		if(request.getParameter("what") != null){
			searchWhat = request.getParameter("userClass");	
		}			
		
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// 그룹 인덱스
		int groupIndex = 0;	//	0은 기본 그룹이다.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
		int start = (page -1 ) * limit +1;		// 시작 번호
		// 주소록 그룹 가져오기
		ArrayList<Group> list =
				(ArrayList<Group>)dao.getGroupList(userIndex, start,  start * limit);
		int listSize = dao.getGroupSize(userIndex);	// 그룹 갯수
		//	리스트 번호
		int no = listSize - (page - 1) * limit;		
		// 페이지 네이션 처리
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "AddressGroupListAction.ad", null);   
		
		request.setAttribute("no", no);								// 리스트 번호		
		request.setAttribute("listSize", listSize);					// 총  주소록그룹 갯수
		request.setAttribute("groups", list);							// 주소록그룹 리스트
		request.setAttribute("pagiNation", pagiNation);			// 페이지네이션
		forward.setPath("./WEB-INF/address/address_group_list.jsp");

		//  캐쉬 없애기
		response.setHeader("Cache-Control","no-store");   
		response.setHeader("Pragma","no-cache");   
		response.setDateHeader("Expires",0);   
		if (request.getProtocol().equals("HTTP/1.1")) {
		        response.setHeader("Cache-Control", "no-cache");
		}
		
		return forward;
	}

}
