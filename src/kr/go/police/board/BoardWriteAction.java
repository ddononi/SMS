package kr.go.police.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�Խù�  ��� �׼�
 */
public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// ����� �̸�, �ε��� ���
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		BoardBean data = new BoardBean();
		
		String title = (String)request.getParameter("title");						// ����
		String content = (String)request.getParameter("content");			// ����
		String pwd = (String)request.getParameter("board_pwd");			// ��й�ȣ
		
		System.out.println("title : " +  title);
		data.setRegisterName(session.getAttribute("name").toString());	// �̸�
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		data.setRegUserIndex(userIndex);											// ���� �ε���
		data.setTitle(title);
		data.setContent(content);
		data.setPwd(pwd);
		data.setFilename("");			// ���� �Խ����� ���Ͼ��ε� ��� ����
		data.setNotice(false);		// ���������� �ƴϹǷ�
		
		// ��� ó��
		if(dao.insertBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���Ǹ� ����Ͽ����ϴ�.');");			
			out.println("window.location.href='./BoardListAction.bo';");
			out.println("</script>");	
			out.close();
		}else{				// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ��� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
