package kr.go.police.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

public class UserNoApprovalListAction implements Action {

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
		// page=1 limit=10 search=""
		// 0 	10

		int start = (page -1 ) * limit +1;				// ���� ��ȣ
		int listSize = dao.getArvListCount();		// ���� ��
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		// ������ ���̼� ó��
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "UserNoApprovalListAction.ac", null);  
		ArrayList<UserBean> list = (ArrayList<UserBean>)dao.getArvList("", start, limit);
		
		request.setAttribute("no", no);									// ����Ʈ ��ȣ		
		request.setAttribute("listSize", listSize);						// ��  �ּҷϱ׷� ����
		request.setAttribute("userList", list);								// ���� ����Ʈ
		request.setAttribute("pagiNation", pagiNation);				// ���������̼�
		forward.setPath("./admin/user_no_approval_list.jsp");	
		
		return forward;		
	}

}
