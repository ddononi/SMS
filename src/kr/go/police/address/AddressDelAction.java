package kr.go.police.address;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	주소록 그룹 삭제
 */
public class AddressDelAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();		
		AddressDAO dao = new AddressDAO();
		// 해당 주소록인덱스
		String index = (String)request.getParameter("index");
		// 그룹인덱스
		String groupIndex = (String)request.getParameter("groupIndex");
		// 삭제 처리
		if(dao.delAddress(Integer.valueOf(groupIndex), Integer.valueOf(index)) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 주소록을 삭제하였습니다.');");
			out.println("window.location.replace('./AddressListAction.ad?groupIndex=" + groupIndex + "')");
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
