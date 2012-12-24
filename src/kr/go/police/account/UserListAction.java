package kr.go.police.account;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.address.AddressBean;

public class UserListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		// 기본값 설정
		int page = 1;
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		// 페이지 목록수
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
		
		// 그룹 인덱스
		int groupIndex = 0;	//	0은 기본 그룹이다.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
		// 경찰서 코드가져오기
		HttpSession session = request.getSession();
		int psCode =  (Integer)session.getAttribute("psCode");
		
		int start = (page -1 ) * limit +1;							// 시작 번호
		int listSize = dao.getUserListCount(psCode);		// 유저 수
		//	리스트 번호
		int no = listSize - (page - 1) * limit;		
		// 페이지 네이션 처리
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "UserListAction.ac", null);
		ArrayList<UserBean> list = (ArrayList<UserBean>)dao.getUserList("", start, limit, psCode);
		
		request.setAttribute("no", no);									// 리스트 번호		
		request.setAttribute("listSize", listSize);						// 총  주소록그룹 갯수
		request.setAttribute("userList", list);								// 유저 리스트
		request.setAttribute("pagiNation", pagiNation);				// 페이지네이션
		forward.setPath("./WEB-INF/admin/all_user_list.jsp");
		
		return forward;			
	}

}
