package kr.go.police.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	게시물 수정보기화면
 */
public class BoardModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		BoardDAO dao = new BoardDAO();
		//  인덱스로 해당 게시물을 뽑아온다.
		int index = Integer.valueOf((String)request.getParameter("index"));
		// 게시물내용 가져오기
		BoardBean data = dao. getDetail(index);
		// token 설정
		String token = CommandToken.set(request);
		request.setAttribute("token", token);			
		request.setAttribute("data", data);
		String content = data.getContent();
		content = content.replaceAll("<br/>", "\n");
		data.setContent(content);		
		// 게시물 보기 페이지로 이동
		forward.setPath("./WEB-INF/board/board_modify.jsp"); 
		return forward;
	}

}
