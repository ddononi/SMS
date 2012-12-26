package kr.go.police.account;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.IGwConstant;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문자함 삭제 action
 */
public class PwdChangeAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();				
		
		// 유저 인덱스
				int userIndexs = Integer.valueOf(request.getParameter("index"));
		
		//비밀번호
		String Pwd = request.getParameter("pwd");
		
		//주소
		String url = request.getParameter("url");
		
		String newPwd = request.getParameter("new_pwd");
		if(newPwd!=null){			
			if(dao.CheckPwd(userIndexs, IGwConstant.PWD_SALT  + Pwd + IGwConstant.PWD_SALT,
														IGwConstant.PWD_SALT  + newPwd + IGwConstant.PWD_SALT )){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();			
				out.println("<script>");				
				out.println("alert('비밀번호가 변경 되었습니다.');");	
				out.println("window.location.href='./"+url+"'");
				out.println("</script>");	
				out.close();;
				return null;					
			}else{
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('현재 비밀번호와 다릅니다.');");
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
				out.println("alert('비밀번호가 변경 되었습니다.');");	
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
				out.println("alert('비밀번호 변경 실패!!.');");
				out.println("history.go(-1);");
				out.println("</script>");	
				out.close();
				return null;		
			}
		}
	}
	}