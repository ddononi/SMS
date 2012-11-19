package kr.go.police.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.LoginCheck;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class BoardFrontController extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	
	static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 로그인 여부 확인
		if(!LoginCheck.checkLogin(request, response)){
			return;
		}
		
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		// 공지사항
		if (command.equals("/NoticeListAction.bo")) {
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		// 문의보기
		}else if (command.equals("/BoardListAction.bo")) {
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	문의하기
		}else if (command.equals("/BoardWriteAction.bo")) {
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		// 게시물 삭제	
		} else if (command.equals("/BoardDeleteAction.bo")) {
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	게시물 등록	
		} else if (command.equals("/BoardWriteAction.bo")) {
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	게시물 수정
		} else if (command.equals("/BoardModifyAction.bo")) {
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	게시물 세부보기
		} else if (command.equals("/BoardDetailAction.bo")) {
			action = new BoardDetailViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	댓글등록
		} else if (command.equals("/BoardReplyAction.bo")) {
			action = new BoardReplyWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}