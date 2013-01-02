package kr.go.police.sms;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문자함에  문자 추가
 */
public class MyMessageAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		SmsDAO dao = new SmsDAO();
		request.setCharacterEncoding("euc-kr");
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		String id = session.getAttribute("id").toString();	// 아이디
		// 그룹 인덱스
		int groupIndex = 0;	//	0은 기본 그룹이다.
		String groupIndexStr = (String)request.getParameter("groupIndex");
		groupIndex = Integer.valueOf(groupIndexStr);
		// 제목
		String title = (String)request.getParameter("title");				
		// 내용
		String message = (String)request.getParameter("message");
		// 그룹명
		String groupName = (String)request.getParameter("groupName");		
		
		Message msg = new Message();
		msg.setGroupIndex(groupIndex);
		msg.setMessage(message);
		msg.setTitle(title);
		msg.setUserIndex(userIndex);
		msg.setGroup(groupName);
		msg.setId(id);
		
		if(dao.addMyMessage(msg)){	// 메세지 등록 성공
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문자함을 추가했습니다.');");
			out.println("window.location.replace('" +
					"./MyMessageAction.sm?groupIndex=" + groupIndexStr + "');");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문자함 등록 실패!!');");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
