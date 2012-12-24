package kr.go.police.account;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *  �μ� ����
 */
public class DeptUserTableAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		String[] arr = ((String)request.getParameter("deptcode")).split(",");
		AccountDAO dao = new AccountDAO();		
		
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
			
		sb.append("<table id='board_table' width='100%' border='0' cellpadding='0' cellspacing='0'>");
		sb.append("<colgroup>");	
		sb.append("<col width='10%' />");			
		sb.append("<col width='25%' />");			
		sb.append("<col width='15%' />");		
		sb.append("<col width='20%' />");	
		sb.append("<col width='30%' />");				
		sb.append("</colgroup>");
		sb.append("<thead>");
		sb.append("<tr>");
		sb.append("<th><input id='select_all'  type='checkbox'   /></th>");
		sb.append("<th>�μ�</th>");			
		sb.append("<th>�̸�</th>");	
		sb.append("<th>���̵�</th>");			
		sb.append("<th>��ȭ��ȣ</th>");			
		sb.append("</tr>");				
		sb.append("</thead>");
		sb.append("<tbody>");
		// �ش� �μ��� ����� ���
		List<UserBean> list =
				dao.getUserListFromDept(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));
		for(UserBean data : list){
			sb.append("<tr>");
			sb.append("<td><input  type='checkbox' name='del'  value='" + data.getIndex() + "'   /></td>");	
			sb.append("<td>" + data.getDeptName()  + "</td>");			
			sb.append("<td><a href='./UserDetailAction.ac?index=" + data.getIndex() + "' >" + data.getName()  + "</a></td>");		
			sb.append("<td><a href='./UserDetailAction.ac?index=" + data.getIndex() + "' >" + data.getId()  + "</a></td>");					
			sb.append("<td>" + data.getPhone1() + "</td>");					
			sb.append("</tr>");				
		}
		
		//	�ش�μ��� ������ ������� 
		if(list == null ||  list.size() <= 0){
			sb.append("<tr>");
			sb.append("<td colspan='5'>����ڰ� �����ϴ�.</td>");			
			sb.append("</tr>");			
		}
		
		sb.append("</tbody>");
		out.println(sb.toString());
		out.close();
		
		return null;
	}
	
}