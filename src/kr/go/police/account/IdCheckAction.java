package kr.go.police.account;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class IdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		
		// �α��� ���� ��������
		String userId = request.getParameter("id");

		//  �α��� ó�� Ȯ��
		boolean result = dao.checkDupleUserId(userId);
		if(result){	// ���� �α��� ó����
			PrintWriter out = response.getWriter();
			out.println("true");
			out.close();
		}else{			// ����� ������ ���� ������
			PrintWriter out = response.getWriter();
			out.println("false");
			out.close();
		}
		
		return null;
	}

}
