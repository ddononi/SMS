package kr.go.police.sms;

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
public class ListDeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		// 삭제할 인덱스
		String[] indexs = request.getParameter("indexs").split(",");
		String mode = request.getParameter("mode");		// 전송모드 구분
		String from = request.getParameter("from");		// 관리자 or 사용자모드		
		int count = 0;
		for(int i=0; i<indexs.length; i++){
			if(from.equalsIgnoreCase("admin")){			// 관리자 삭제(실제 삭제)
				dao.realDelSendMessage(mode, Long.parseLong(indexs[i]));
			}else{			// 사용자 삭제(flag처리)
				dao.delSendMessage(mode, Long.parseLong(indexs[i]));
			}
			count++;
		}
						
		String backUrl = request.getHeader("referer");	// 삭제후 돌아갈 이전 페이지
		
		if(count > 0){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+count+"개의 리스트를 삭제하였습니다.');");
			out.println("window.location.replace('"+backUrl+"')");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제실패!!')");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
