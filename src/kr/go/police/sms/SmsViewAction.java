package kr.go.police.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �߼� ȭ�� ó��
 */
public class SmsViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		/*
		SmsDAO dao = new SmsDAO();
		dao.getPslist();
		*/
		// ���ڹ߼� ���� ȭ��
		forward.setPath("./WEB-INF/sms/main.jsp");
		return forward;
	}

}
