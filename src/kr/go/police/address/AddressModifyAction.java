package kr.go.police.address;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�ּҷ� ����
 */
public class AddressModifyAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ���ڵ�
		request.setCharacterEncoding("euc-kr");
		AddressDAO dao = new AddressDAO();
		// �Ķ���� ����
		String groupIndex = (String)request.getParameter("groupIndex");		// �׷��ε���	
		String index = (String)request.getParameter("index");						// �ش��ε���		
		String peopleName = (String)request.getParameter("peopleName");	// �̸�
		String phoneNum = (String)request.getParameter("phoneNum");		// ��ȭ��ȣ
		// ����ó��
		if(dao.modifyAddress(Integer.valueOf(index), peopleName, phoneNum.replace("-", "")) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ּҷ��� �����Ͽ����ϴ�.');");
			out.println("window.location.href='./AddressListAction.ad?groupIndex=" + groupIndex + "'");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������!!");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
