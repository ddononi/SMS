package kr.go.police.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.CommandToken;
import kr.go.police.LoginCheck;
import kr.go.police.action.Action;

/**
 * Servlet Filter implementation class SmsFrontcontrollerFilter
 */
@WebFilter(description = "계정관련필터", urlPatterns = { "*.ac" })
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
		// httpservlet 으로 변환
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		// url 얻기
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		request.setCharacterEncoding("EUC-KR");
		
		// 토큰 처리
		if(command.equals("/AdminModifyUserAction.ac") ||
			command.equals("/MyInfoModifyAction.ac") ){
			// 토큰 검사
			if (!CommandToken.isValid(request)) {
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('비정상적인 요청입니다.');");
				out.println("history.back(-1);");
				out.println("</script>");	
				CommandToken.set(request);			
				return;
			}
		}
		
		// 로그인정보가 필요없는  command를 제외한
		// 나머지 액션에서는 로그인여부 확인
		if (!command.equals("/LoginAction.ac") &&
				!command.equals("/JoinAction.ac") &&
				!command.equals("/LogoutAction.ac") &&
				!command.equals("/DeptListAction.ac") &&				
				!command.equals("/IdCheckAction.ac") ){

			if(!LoginCheck.checkLogin(request, response)){
				return;
			}
			
			// 중복 접속을 막기 위한 세션아이디값 비교 
			if(!LoginCheck.sessionIdCheck(request, response)){
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
