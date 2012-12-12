package kr.go.police.address;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.account.UserBean;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �׷��� �ּҷ�
 */
public class AddressListTableAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		// �׷� �ε���
		int groupIndex = 0;	//	0�� �⺻ �׷��̴�.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		ArrayList<AddressBean> list =
				(ArrayList<AddressBean>)dao.getAddressList(groupIndex);
		
		request.setAttribute("list", list);								// �ּҷ� ����Ʈ
		request.setAttribute("groupIndex", groupIndexStr);		
		forward.setPath("./WEB-INF/sms/address_table.jsp");
		
		return forward;
	}

}
