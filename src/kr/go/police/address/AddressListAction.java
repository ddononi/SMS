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
 *	�ּҷ� ��� action
 */
public class AddressListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AddressDAO dao = new AddressDAO();
		
		request.setCharacterEncoding("euc-kr");
		// �⺻�� ����
		int page = 1;

		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		
		int limit = 10;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}
		
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
		}	
		
		String searchWhat = "���̵�";
		if(request.getParameter("what") != null){
			searchWhat = request.getParameter("userClass");	
		}			

		
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// �׷� �ε���
		int groupIndex = 0;	//	0�� �⺻ �׷��̴�.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
		int start = (page -1 ) * limit +1;		// ���� ��ȣ
		int listSize = dao.getAddressSize(userIndex, groupIndex);		// �ּҷ� ����
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		ArrayList<AddressBean> list =
				(ArrayList<AddressBean>)dao.getAddressList(userIndex, groupIndex, start,  start * limit);
		// ������ ���̼� ó��
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "AddressListAction.ad", "groupIndex=" + groupIndex);  
		
		request.setAttribute("no", no);								// ����Ʈ ��ȣ		
		request.setAttribute("listSize", listSize);					// ��  �ּҷϱ׷� ����
		request.setAttribute("list", list);								// �ּҷ� ����Ʈ
		request.setAttribute("groupIndex", groupIndexStr);		
		request.setAttribute("pagiNation", pagiNation);			// ���������̼�
		forward.setPath("./address/address_list.jsp");
		
		return forward;
	}

}
