package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	공지사항 게시물 수정보기 화면
 */
public class NoticeModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  인덱스로 해당 게시물을 뽑아온다.
		int index = Integer.valueOf((String)request.getParameter("index"));
		// 게시물내용 가져오기
		BoardBean data = dao. getDetail(index);
		String content = data.getContent();
		content = content.replaceAll("<br/>", "\n");
		data.setContent(content);
		request.setAttribute("data", data);
		// 공지사항 수정 보기 페이지로 이동
		forward.setPath("./WEB-INF/admin/notice_modify.jsp"); 
		return forward;
	}

}
