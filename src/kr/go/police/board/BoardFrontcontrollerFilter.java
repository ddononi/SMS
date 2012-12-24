package kr.go.police.board;

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
@WebFilter(description = "게시판관련필터", urlPatterns = { "*.bo" })
public class BoardFrontcontrollerFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BoardFrontcontrollerFilter() {
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

		// 로그인 여부 확인
		if(!LoginCheck.checkLogin(request, response)){
			return;
		}
		
		// 중복 접속을 막기 위한 세션아이디값 비교 
		if(!LoginCheck.sessionIdCheck(request, response)){
			return;
		}			
		
		//	토큰 검사할 command
		if(command.equals("/BoardWriteAction.bo") ||
				command.equals("/BoardReplyWriteAction.bo") ||
				command.equals("/BoardModifyAction.bo") ||
				command.equals("/BoardModifyAction.bo") ||
				command.equals("/BoardDeleteAction.bo") ||
				command.equals("/NoticeDeleteAction.bo") ||
				command.equals("/AdminBoardDeleteAction.bo") ||
				command.equals("/AdminBoardReplyWriteAction.bo") ||
				command.equals("/BoardReplyWriteAction.bo") ){ 
			
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
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
