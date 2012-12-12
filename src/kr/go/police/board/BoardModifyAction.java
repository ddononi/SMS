package kr.go.police.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.CommandToken;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �Խù� ���� �׼�
 */
public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		
		
		// ����� �̸�, �ε��� ���
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		
		String boardIndex = (String)request.getParameter("index");		// �Խù� �ε���		
		String title = (String)request.getParameter("title");						// ����
		String content = (String)request.getParameter("content");			// ����
		String pwd = (String)request.getParameter("board_pwd");			// ��й�ȣ
		
		// ���� �ε���
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		
		BoardBean data = new BoardBean();
		data.setIndex(Integer.valueOf(boardIndex));								// �Խù� ��ȣ
		data.setRegisterName(session.getAttribute("name").toString());	// �̸�		
		data.setRegUserIndex(userIndex);											// ���� �ε���
		data.setTitle(title);																// ����	
		data.setContent(content);														// ����
		data.setPwd(pwd);																// ���
		data.setNotice(false);															// ��������
		// ���� ó��
		if(dao.modifyBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�����Ǿ����ϴ�.');");			
			out.println("window.location.href='./BoardDetailView.bo?index=" + boardIndex  + "';");
			out.println("</script>");	
			out.close();
		}else{				// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ���� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
