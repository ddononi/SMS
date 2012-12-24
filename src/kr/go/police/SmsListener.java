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
	private String logPath;	// �α������� ����� base path
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
		// properties path �� �о�´�.			
    	ServletContext context = sce.getServletContext();
    	logPath = (String)context.getInitParameter("logFilesPath");
    	SMSUtil.writerToLogFile(logPath, "SMS/MMS ������ �����Ǿ����ϴ�.");    	
    }

    public void sessionCreated(HttpSessionEvent she) {
    	++userCount;
    }

	/**
	 * �α׾ƿ� �αױ��
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent she) {
	    	HttpSession session =  she.getSession();
			String str = "���̵� : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t�̸� : " +session.getAttribute("name") + "���� �α׾ƿ� �Ǿ����ϴ�.";
	    	SMSUtil.writerToLogFile(logPath, str);    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	SMSUtil.writerToLogFile(logPath, "SMS/MMS ������ ����Ǿ����ϴ�.");
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
	 * ���� �α���
     */    
	@Override
	public void attributeAdded(HttpSessionBindingEvent hsbe) {
		if(hsbe.getValue().equals("loginListener") && hsbe.getName().equals("sessionListener")){
	    	HttpSession session =  hsbe.getSession();
			String str = "���̵� : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t�̸� : " +session.getAttribute("name") + "���� �α��� �Ǿ����ϴ�..";
	    	SMSUtil.writerToLogFile(logPath, str);    	
		}
	
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent hsbe) {
		// �ߺ� �α��� �α� ���
		if(hsbe.getValue().equals("duplLogin") && hsbe.getName().equals("sessionListener")){
	    	HttpSession session =  hsbe.getSession();
			String str = "���̵� : " + session.getAttribute("id") +
						    	"(IP: " + session.getAttribute("clientIp") + ")" +  
							 "\t�̸� : " +session.getAttribute("name") 
							 + "���� �ٸ� ��ǻ�Ϳ��� �α��� �Ǿ����ϴ�..";
	    	SMSUtil.writerToLogFile(logPath, str);    	
		}	
	}
	
}
