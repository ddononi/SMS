package kr.go.police.account;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *  부서 관리
 */
public class DeptListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// 경찰서 코드
		int pscode = Integer.valueOf(request.getParameter("code").toString());
		AccountDAO dao = new AccountDAO();		
		// 경찰서 코드로 해당 부서 목록 가져오기
		ArrayList<DeptBean> list =  (ArrayList<DeptBean>) dao.getSubDeptList(pscode);
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"no\" >--선택--</option>");
		for(DeptBean data :list){	
			sb.append("<option value='" + data.getDeptCode() + "," + data.getName()  +"'>");		
			sb.append(data.getName());					
			sb.append("</option>");
		}
		
		out.println(sb.toString());
		out.close();
		
		return null;
	}		
}