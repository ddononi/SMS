package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	 문자함 수정
 */
public class MyMessageModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SmsDAO dao = new SmsDAO();
		request.setCharacterEncoding("euc-kr");
		// 선택 문자함 인덱스
		String indexStr = (String)request.getParameter("index");
		int index = Integer.valueOf(indexStr);
		// 그룹 인덱스
		String groupIndexStr = (String)request.getParameter("groupIndex");
		// 제목
		String title = (String)request.getParameter("title");				
		// 내용
		String message = (String)request.getParameter("message");
		
		Message msg = new Message();
		msg.setIndex(index);
		msg.setMessage(message);
		msg.setTitle(title);
		
		if(dao.modifyMyMessage(msg)){	// 메세지 등록 성공
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문자함을 수정했습니다.');");
			out.println("window.location.replace('" +
					"./MyMessageAction.sm?groupIndex=" + groupIndexStr + "');");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문자함 수정 실패!!');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
