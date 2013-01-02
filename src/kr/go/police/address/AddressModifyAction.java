package kr.go.police.address;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	주소록 수정
 */
public class AddressModifyAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 인코딩
		request.setCharacterEncoding("euc-kr");
		AddressDAO dao = new AddressDAO();
		// 파라미터 설정
		String groupIndex = (String)request.getParameter("groupIndex");		// 그룹인덱스	
		String index = (String)request.getParameter("index");						// 해당인덱스		
		String peopleName = (String)request.getParameter("peopleName");	// 이름
		String phoneNum = (String)request.getParameter("phoneNum");		// 전화번호
		// 수정처리
		if(dao.modifyAddress(Integer.valueOf(index), peopleName, phoneNum.replace("-", "")) ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('주소록을 수정하였습니다.');");
			out.println("window.location.replace('./AddressListAction.ad?groupIndex=" + groupIndex + "')");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정실패!!");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
