package kr.go.police.address;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.account.UserBean;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	주소록 목록 action
 */
public class AddressListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AddressDAO dao = new AddressDAO();
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
			try{
				limit = Integer.valueOf(request.getParameter("limit"));
			}catch(NumberFormatException e){
				limit = 10;
			}
		}
		
		// 검색 종류
		String what = "이름";
		if(request.getParameter("what") != null){
			what = request.getParameter("what");	
		}			
		
		// 검색어 
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search").trim().replace("-", "");
			// 한글 처리
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR"); 
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
		int listSize = dao.getAddressSize(userIndex, groupIndex, what, search);		// 주소록 갯수
		//	리스트 번호
		int no = listSize - (page - 1) * limit;		
		ArrayList<AddressBean> list =
				(ArrayList<AddressBean>)dao.getAddressList(userIndex, groupIndex, start,  limit, what, search);
		// 페이지 네이션 처리
		String params = "limit=" +limit +  "&search=" + search + "&groupIndex=" + groupIndex;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "AddressListAction.ad", params);  
		
		request.setAttribute("no", no);								// 리스트 번호		
		request.setAttribute("listSize", listSize);					// 총  주소록그룹 갯수
		request.setAttribute("list", list);								// 주소록 리스트
		request.setAttribute("limit", limit);							// 한페이지 목록수	
		request.setAttribute("what", what);							// 검색종류			
		request.setAttribute("search", search);						// 검색				
		request.setAttribute("groupIndex", groupIndexStr);	// 그룹 인덱스	
		request.setAttribute("pagiNation", pagiNation);			// 페이지네이션
		forward.setPath("./WEB-INF/address/address_list.jsp");
		
		return forward;
	}

}
