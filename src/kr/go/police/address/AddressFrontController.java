package kr.go.police.address;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.LoginCheck;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�ּҷ�, �ּҷ� �׷� ��Ʈ�ѷ�
 */
public class AddressFrontController extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	// key = command , value = Object
	private Map<String, Object> commandMap = new HashMap<String, Object>();
	static final long serialVersionUID = 1L;
	
	/**
	 * init ȣ��� config ������ �о�� �������� ����
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("configFile");
		Properties prop = new Properties();
		FileInputStream fis = null;
		try{
			// properties path �� �о�´�.			
			ServletContext context = config.getServletContext();
			String path = (String)context.getInitParameter("propertiesPath");
			System.out.println("properties Path : " + path);				
			fis = new FileInputStream(path + configFile);
			prop.load(fis);
		}catch(IOException ioe){
			System.out.println("config ������ ������ �����ϴ�.");			
			throw new ServletException();
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){}
			}
		}
		// command & class ����ó��
		Iterator<Object> keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String command = (String)keyIter.next();
			// �ش� Ŭ�����̸� ���
			String className = prop.getProperty(command);
			System.out.println(className  + " ");		
			try{
				// map�� ����ó��
				Class<?> actionClass = Class.forName(className);
				Object actionInstance = actionClass.newInstance();
				commandMap.put(command, actionInstance);
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("init complete!");		
		
	}

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// command action ���
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		//	command ���������� �ش� action ����
		System.out.println("command : " + command);
		action = (Action)commandMap.get(command);
		// ���ε� �׼��� ������� ������������ �̵�
		if(action == null){
			response.sendRedirect("./error/error_404.jsp");
			return;
		}		
		System.out.println("class  : " + action.getClass().toString());	
		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}