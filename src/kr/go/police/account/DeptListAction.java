package kr.go.police.account;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *  �μ� ����
 */
public class DeptListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// ������ �ڵ�
		int pscode = Integer.valueOf(request.getParameter("code").toString());
		AccountDAO dao = new AccountDAO();		
		// ������ �ڵ�� �ش� �μ� ��� ��������
		ArrayList<DeptBean> list =  (ArrayList<DeptBean>) dao.getSubDeptList(pscode);
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"no\" >--����--</option>");
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