package kr.go.police.board;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import kr.go.police.address.AddressBean;

/**
 *	�������� action
 */
public class NoticeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("euc-kr");		
		BoardDAO dao = new BoardDAO();
		// �⺻�� ����
		int page = 1;
		if(request.getParameter("page") != null){
			page = Integer.valueOf(request.getParameter("page"));
		}
		// �������� ��ϼ�
		int limit = 10;
		if(request.getParameter("limit") != null){
			limit = Integer.valueOf(request.getParameter("limit"));
		}
		// �˻���
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
		}	
		// �˻� ����
		String searchWhat = "���̵�";
		if(request.getParameter("what") != null){
			searchWhat = request.getParameter("what");	
		}			
		
		int start = (page -1 ) * limit +1;					// ���� ��ȣ
		int listSize = dao.getNoticeListCount();		// �Խù� ����
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		ArrayList<BoardBean> list =
				(ArrayList<BoardBean>)dao.getNoticeList(start,  start * limit, "");
		// ������ ���̼� ó��
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "NoticeListAction.bo", null);  
		
		request.setAttribute("no", no);								// ����Ʈ ��ȣ		
		request.setAttribute("listSize", listSize);					// ��  �Խù� ����
		request.setAttribute("list", list);								// �Խù� ����Ʈ
		request.setAttribute("pagiNation", pagiNation);			// ���������̼�
		forward.setPath("./board/notice_list.jsp"); 
		return forward;
	}

}
