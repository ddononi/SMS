package kr.go.police.address;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	주소록에 그룹 추가
 */
public class AddressAddAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index").toString());
		// 그룹인덱스
		String groupIndex = (String)request.getParameter("groupIndex");
		// 이름
		String people = (String)request.getParameter("peopleName");
		// 전화번호
		String phone = (String)request.getParameter("phoneNum");	
		AddressBean data = new AddressBean();
		data.setGroupIndex(Integer.valueOf(groupIndex));
		data.setPeople(people.trim());
		data.setPhone(phone.trim().replace("-", ""));
		
		if(dao.addAddressPeople(userIndex, data)){	
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('새로운 주소록을 추가하였습니다.');");
			out.println("window.location.replace('./AddressListAction.ad?groupIndex=" + groupIndex + "')");
			out.println("</script>");	
			out.close();
			return null;			
		}else{
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('그룹 추가 실패!!')");
			out.println("history.go(-1);");
			out.println("</script>");	
			out.close();
			return null;
		}
		
	}

}
