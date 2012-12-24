package kr.go.police.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	공지사항 게시물 수정보기 화면
 */
public class AdminBoardDetailView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  인덱스로 해당 게시물을 뽑아온다.
		int index = Integer.valueOf((String)request.getParameter("index"));
		
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// 조회수 증가
		dao.updateReadCount(index, userIndex);
		// 게시물내용 가져오기
		BoardBean data = dao. getDetail(index);
		data.setContent(data.getContent().replaceAll("\n", "<br/>"));		
		// 해당 게시물의 댓글 목록
		List<BoardBean>replyList =(List<BoardBean>)dao.getReplyList(index);
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);				
		request.setAttribute("replyList", replyList);
		request.setAttribute("data", data);
		forward.setPath("./WEB-INF/admin/board_view.jsp"); 
		return forward;
	}

}
