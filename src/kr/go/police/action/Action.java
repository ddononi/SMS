package kr.go.police.action;

import javax.servlet.http.*;

/**
 * actionForward �޼����� ó���� ���� �������̽�
 * 
 * @author juwan
 * 
 */
public interface Action {
	
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
}

