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
public class AddressDelAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();		
		AddressDAO dao = new AddressDAO();
		// �ش� �ּҷ��ε���
		String index = (String)request.getParameter("index");
		// �׷��ε���
		String groupIndex = (String)request.getParameter("groupIndex");
		// ���� ó��
		if(dao.delAddress(Integer.valueOf(groupIndex), Integer.valueOf(index)) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ش� �ּҷ��� �����Ͽ����ϴ�.');");
			out.println("window.location.replace('./AddressListAction.ad?groupIndex=" + groupIndex + "')");
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
