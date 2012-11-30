package kr.go.police.account;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.LoginCheck;
import kr.go.police.action.Action;

/**
 * Servlet Filter implementation class SmsFrontcontrollerFilter
 */
@WebFilter(description = "������������", urlPatterns = { "*.ac" })
public class AccountFrontcontrollerFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AccountFrontcontrollerFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("do filter~");
		// httpservlet ���� ��ȯ
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		// url ���
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		request.setCharacterEncoding("EUC-KR");
		
		// �α��������� �ʿ����  command�� ������
		// ������ �׼ǿ����� �α��ο��� Ȯ��
		if (!command.equals("/LoginAction.ac") &&
				!command.equals("/JoinAction.ac") &&
				!command.equals("/LogoutAction.ac") &&
				!command.equals("/IdCheckAction.ac") ){

			if(!LoginCheck.checkLogin(request, response)){
				return;
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
