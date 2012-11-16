package kr.go.police.address;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�ּҷ� �׷� ����
 */
public class GroupDelAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AddressDAO dao = new AddressDAO();
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// �׷��ε���
		String groupIndex = (String)request.getParameter("groupIndex");
		if(dao.delAddressGroup(userIndex, Integer.valueOf(groupIndex)) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�׷��� �����Ͽ����ϴ�.');");
			out.println("window.location.href='./AddressGroupListAction.ad' ");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������!!')");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
