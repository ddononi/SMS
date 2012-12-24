package kr.go.police;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SmsListener
 *
 */
@WebListener
public class SmsListener
	implements HttpSessionListener, ServletContextListener, 
	ServletRequestListener, HttpSessionAttributeListener {
	private String logPath;	// 로그파일을 기록할 base path
	private int userCount = 0;
    /**
     * Default constructor. 
     */
    public SmsListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0) {
    	--userCount;
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
		// properties path 를 읽어온다.			
    	ServletContext context = sce.getServletContext();
    	logPath = (String)context.getInitParameter("logFilesPath");
    	SMSUtil.writerToLogFile(logPath, "SMS/MMS 서버가 구동되었습니다.");    	
    }

    public void sessionCreated(HttpSessionEvent she) {
    	++userCount;
    }

	/**
	 * 로그아웃 로그기록
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent she) {
	    	HttpSession session =  she.getSession();
			String str = "아이디 : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t이름 : " +session.getAttribute("name") + "님이 로그아웃 되었습니다.";
	    	SMSUtil.writerToLogFile(logPath, str);    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	SMSUtil.writerToLogFile(logPath, "SMS/MMS 서버가 종료되었습니다.");
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * 유저 로그인
     */    
	@Override
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		if(hsbe.getValue().equals("loginListener") && hsbe.getName().equals("sessionListener")){
	    	HttpSession session =  hsbe.getSession();
			String str = "아이디 : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t이름 : " +session.getAttribute("name") + "님이 로그인 되었습니다..";
	    	SMSUtil.writerToLogFile(logPath, str);    	
		}
	
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent hsbe) {
		// 중복 로그인 로그 기록
		if(hsbe.getValue().equals("duplLogin") && hsbe.getName().equals("sessionListener")){
	    	HttpSession session =  hsbe.getSession();
			String str = "아이디 : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t이름 : " +session.getAttribute("name") 
							 + "님이 다른 컴퓨터에서 로그인 되었습니다..";
	    	SMSUtil.writerToLogFile(logPath, str);    	
		}	
	}
	
}
