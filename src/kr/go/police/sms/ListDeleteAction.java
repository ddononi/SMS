package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	������ ���� action
 */
public class ListDeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		// �׷��ε���
		String[] indexs = request.getParameter("indexs").split(",");
		int count = 0;
		for(int i=0;i<indexs.length;i++){
			dao.delSendMessage(Long.parseLong(indexs[i]));
			count++;
		}
		String page = (String)request.getParameter("page");
						
		if(count > 0){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+count+"���� ����Ʈ�� �����Ͽ����ϴ�.');");
			out.println("window.location.replace('"+page+"')");
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
