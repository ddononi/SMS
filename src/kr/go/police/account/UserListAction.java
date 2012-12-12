package kr.go.police.account;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.address.AddressBean;

public class UserListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		AccountDAO dao = new AccountDAO();
		request.setCharacterEncoding("euc-kr");
		// �⺻�� ����
		int page = 1;
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		// ������ ��ϼ�
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
		
		// �׷� �ε���
		int groupIndex = 0;	//	0�� �⺻ �׷��̴�.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		if(groupIndexStr != null){
			groupIndex = Integer.valueOf(groupIndexStr);
		}
		
		int start = (page -1 ) * limit +1;				// ���� ��ȣ
		int listSize = dao.getUserListCount();		// ���� ��
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		// ������ ���̼� ó��
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "UserListAction.ac", null);  
		ArrayList<UserBean> list = (ArrayList<UserBean>)dao.getUserList("", start, limit);
		
		request.setAttribute("no", no);									// ����Ʈ ��ȣ		
		request.setAttribute("listSize", listSize);						// ��  �ּҷϱ׷� ����
		request.setAttribute("userList", list);								// ���� ����Ʈ
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		forward.setPath("./WEB-INF/admin/all_user_list.jsp");
		
		return forward;			
	}

}
