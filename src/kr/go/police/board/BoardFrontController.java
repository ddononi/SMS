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

		// �α��� ���� Ȯ��
		if(!LoginCheck.checkLogin(request, response)){
			return;
		}
		
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		// ��������
		if (command.equals("/NoticeListAction.bo")) {
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		// ���Ǻ���
		}else if (command.equals("/BoardListAction.bo")) {
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�����ϱ�
		}else if (command.equals("/BoardWriteViewAction.bo")) {
			try {
				forward =  new ActionForward();
				forward.setPath("./board/board_write.jsp"); 				
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�����ϱ�ó��
		}else if (command.equals("/BoardWriteAction.bo")) {
			action = new BoardWriteAction();			
			try {
				forward = action.execute(request, response);		
			} catch (Exception e) {
				e.printStackTrace();
			}			
		// ���Ǽ�������ȭ��	
		} else if (command.equals("/BoardModifyViewAction.bo")) {
			action = new BoardModifyView();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		// �� ���� ����ó��	
		} else if (command.equals("/BoardDeleteAction.bo")) {
			action = new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�Խù� ����
		} else if (command.equals("/BoardModifyAction.bo")) {
			action = new BoardModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�Խù� ���κ���
		} else if (command.equals("/BoardDetailView.bo")) {
			action = new BoardDetailView();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	��۵��
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