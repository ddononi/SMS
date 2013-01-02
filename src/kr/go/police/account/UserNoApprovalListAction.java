package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 * 미승인 회원 리스트
 */
public class UserNoApprovalListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		// 기본값 설정
		int page = 1;
		if (request.getParameter("page") != null) {
			page = Integer.valueOf(request.getParameter("page"));
		}
		// 페이지 목록수
		int limit = 10;
		if (request.getParameter("limit") != null) {
			limit = Integer.valueOf(request.getParameter("limit"));
		}

		// 검색어
		String search = "";
		if (request.getParameter("search") != null) {
			search = request.getParameter("search").trim();
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR").trim();
			// search = search.replace("-", "");
		}
		// 검색 타입
		String type = "id";
		if (request.getParameter("type") != null) {
			type = (String) request.getParameter("type");
		}

		// 경찰서 코드가져오기
		HttpSession session = request.getSession();
		int psCode = (Integer) session.getAttribute("psCode");

		int start = (page - 1) * limit + 1; // 시작 번호
		int listSize = dao.getArvListCount(psCode, type, search); // 유저 수
		// 리스트 번호
		int no = listSize - (page - 1) * limit;
		// 페이지 네이션 처리
		String params = "limit=" + limit + "&search=" + search + "&type="
				+ type;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit,
				"UserNoApprovalListAction.ac", params);
		ArrayList<UserBean> list = (ArrayList<UserBean>) dao.getArvList(search,
				start, limit, psCode, type);

		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);
		request.setAttribute("no", no); // 리스트 번호
		request.setAttribute("limit", limit); // 한페이지수
		request.setAttribute("page", page); // 페이지 번호
		request.setAttribute("search", search); // 검색
		request.setAttribute("listSize", listSize); // 총 주소록그룹 갯수
		request.setAttribute("userList", list); // 회원내역 리스트
		request.setAttribute("pagiNation", pagiNation); // 페이지네이션
		request.setAttribute("type", type); // 검색타입
		forward.setPath("./WEB-INF/admin/user_no_approval_list.jsp");

		return forward;
	}

}
