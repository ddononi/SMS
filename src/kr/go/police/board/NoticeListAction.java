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
			try{
				page = Integer.valueOf(request.getParameter("page"));
			}catch(NumberFormatException e){
				page =1;
			}
		}
		
		// ������ ��ϼ�
		int limit = 10;
		if(request.getParameter("limit") != null){
			try{
				limit = Integer.valueOf(request.getParameter("limit"));
			}catch(NumberFormatException e){
				limit = 10;
			}
		}
		
		// �˻� ����
		/*
		String what = "�̸�";
		if(request.getParameter("what") != null){
			what = request.getParameter("what");	
		}
		*/			
		// �˻��� 
		String search = "";
		if(request.getParameter("search") != null){
			search = request.getParameter("search").trim().replace("-", "");
			// �ѱ� ó��
			search = new String(search.getBytes("iso-8859-1"), "EUC-KR"); 
		}			
		
		int start = (page -1 ) * limit +1;							// ���� ��ȣ
		int listSize = dao.getNoticeListCount(search);		// �Խù� ����
		//	����Ʈ ��ȣ
		int no = listSize - (page - 1) * limit;		
		ArrayList<BoardBean> list =
				(ArrayList<BoardBean>)dao.getNoticeList(start,  start * limit, search);
		// ������ ���̼� ó��
		String params = "limit=" +limit +  "&search=" + search;
		String pagiNation = SMSUtil.makePagiNation(listSize, page, limit, "NoticeListAction.bo", params);  
		
		request.setAttribute("no", no);								// ����Ʈ ��ȣ		
		request.setAttribute("listSize", listSize);					// ��  �Խù� ����
		request.setAttribute("list", list);								// �Խù� ����Ʈ
		request.setAttribute("limit", limit);							// �������� ��ϼ�	
		request.setAttribute("search", search);						// �˻�				
		request.setAttribute("pagiNation", pagiNation);			// ���������̼�
		forward.setPath("./WEB-INF/board/notice_list.jsp"); 
		return forward;
	}

}
