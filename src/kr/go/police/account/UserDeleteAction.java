package kr.go.police.account;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문자함 삭제 action
 */
public class UserDeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountDAO dao = new AccountDAO();
		
		// 유저 인덱스
		String userIndexs = request.getParameter("del_index");
		String[] indexs = userIndexs.split(",");
		
		// 처리후 돌아갈 url주소(사용자보기에서 넘어온경우 다시 회원목록으로 넘기기 위해)
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
			// 사용자보기에서 탈퇴시
			if(backUrl != null && !backUrl.isEmpty()){
				out.println("alert('해당사용자를 탈퇴시켰습니다.');");
				out.println("window.location.href='" + backUrl + "'");			
			}else{
				out.println("alert('"+count+"명의 유저를 탈퇴시켰습니다.');");
				out.println("window.location.href='./QuiescenceListAction.ac'");
			}
			out.println("</script>");	
			out.close();
			
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('유저 탈퇴실패!!')");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			
			return null;
		}
		
	}

}
