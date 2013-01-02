package kr.go.police.account;

import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	비밀번호 변경 action
 */
public class PwdResetAction implements Action {
	//비밀번호 설정
	private final static String[] pwd1 = 
		{"a","b","c","d","e","f","g","h","i","j","k","l","n","m","o","p","q","r","s","t","u","v","w","x","y","z",
		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private final static String[] pwd2 = {"0","9","8","7","6","5","4","3","2","1"};
	private final static String[] pwd3 = {"!","@","#","$","%","^","&","*"};	
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();		
		Random rd = new Random();
		
		// 유저 인덱스
				int userIndexs = Integer.valueOf(request.getParameter("index"));
		String newPwd = "";			//비밀번호 값
		
		for(int i=0;i<5;i++)	{
			switch (rd.nextInt(2)) {
			case 0:
				newPwd += pwd1[rd.nextInt(51)];
				break;
			case 1:
				newPwd += pwd2[rd.nextInt(10)];
				break;					
			}
		}
		
		newPwd += pwd1[rd.nextInt(pwd1.length - 1)];
		newPwd += pwd2[rd.nextInt(pwd2.length - 1)];
		newPwd += pwd3[rd.nextInt(pwd3.length -1)];		
		
		if(dao.Passwordchange(newPwd, userIndexs)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();			
			out.println(newPwd);	
			out.close();
			
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("fail");
			out.close();
			return null;
		}
		
	}

}
