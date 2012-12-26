package kr.go.police.account;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	������ ���� action
 */
public class PwdChangeAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();				
		
		// ���� �ε���
				int userIndexs = Integer.valueOf(request.getParameter("index"));
		
		//��й�ȣ
		String Pwd = request.getParameter("pwd");
		
		//�ּ�
		String url = request.getParameter("url");
		
		String newPwd = request.getParameter("new_pwd");
		if(newPwd!=null){			
			if(dao.CheckPwd(userIndexs, IGwConstant.PWD_SALT  + Pwd + IGwConstant.PWD_SALT,
														IGwConstant.PWD_SALT  + newPwd + IGwConstant.PWD_SALT )){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();			
				out.println("<script>");				
				out.println("alert('��й�ȣ�� ���� �Ǿ����ϴ�.');");	
				out.println("window.location.href='./"+url+"'");
				out.println("</script>");	
				out.close();;
				return null;					
			}else{
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('���� ��й�ȣ�� �ٸ��ϴ�.');");
				out.println("history.go(-1);");
				out.println("</script>");	
				out.close();
				return null;	
			}
		}
		else{
		if(dao.Passwordchange(Pwd, userIndexs))
			{
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();			
				out.println("<script>");				
				out.println("alert('��й�ȣ�� ���� �Ǿ����ϴ�.');");	
				out.println("window.location.href='./MyInfoAction.ac?index="+userIndexs+"'");
				out.println("</script>");	
				out.close();
				return null;			
			}
			else
			{
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('��й�ȣ ���� ����!!.');");
				out.println("history.go(-1);");
				out.println("</script>");	
				out.close();
				return null;		
			}
		}
	}
	}