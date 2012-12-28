package kr.go.police.account;

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
public class UserDeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		
		// ���� �ε���
		String userIndexs = request.getParameter("del_index");
		String[] indexs = userIndexs.split(",");
		
		// ó���� ���ư� url�ּ�(����ں��⿡�� �Ѿ�°�� �ٽ� ȸ��������� �ѱ�� ����)
		String backUrl = request.getParameter("back_url");
		
		int count = 0;
		boolean chack = false;
		for(int i=0;i<indexs.length;i++){
			chack=dao.deleteUser(Integer.valueOf(indexs[i]));
			count++;
		}
						
		if(chack){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			// ����ں��⿡�� Ż���
			if(backUrl != null && !backUrl.isEmpty()){
				out.println("alert('�ش����ڸ� Ż����׽��ϴ�.');");
				out.println("window.location.href='" + backUrl + "'");			
			}else{
				out.println("alert('"+count+"���� ������ Ż����׽��ϴ�.');");
				out.println("window.location.href='./QuiescenceListAction.ac'");
			}
			out.println("</script>");	
			out.close();
			
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� Ż�����!!')");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			
			return null;
		}
		
	}

}
