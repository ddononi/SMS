package kr.go.police.address;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.sms.Group;

/**
 *	�ּҷ� ���� ������
 */
public class AddressBookWindow implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		// ����� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// ����� ��ü �ּҷ� �׷� ��������
		ArrayList<Group> list =
				(ArrayList<Group>)dao.getGroupList(userIndex);
		request.setAttribute("groups", list);							// �ּҷϱ׷� ����Ʈ
		forward.setPath("./WEB-INF/sms/address_book.jsp");

		return forward;
	}

}
