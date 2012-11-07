package kr.go.police.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.police.LoginCheck;
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
		
		// �Խù�����
		if (command.equals("/boardListAction.bo")) {
			action = new NoticeBoardAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�α��� ó�� ����");
			}
		// �Խù� ����	
		} else if (command.equals("/boardDeleteAction.bo")) {
			action = new NoticeBoardAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�Խù� ���	
		} else if (command.equals("/boardWriteAction.bo")) {
			action = new NoticeBoardAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�Խù� ����
		} else if (command.equals("/boardModifyAction.bo")) {
			action = new NoticeBoardAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	�Խù� ���κ���
		} else if (command.equals("/boardDetailAction.bo")) {
			action = new BoardDetailViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	��۵��
		} else if (command.equals("/boardReplyAction.bo")) {
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