package kr.go.police.sms;

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

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문자 관련 필터 
 */
public class SmsFrontController extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	// key = command , value = Object
	private Map<String, Object> commandMap = new HashMap<String, Object>();
	static final long serialVersionUID = 1L;
	
	
	/**
	 * init 호출시 config 파일을 읽어와 매핑정보 저장
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("configFile");
		Properties prop = new Properties();
		FileInputStream fis = null;
		try{
			// properties path 를 읽어온다.			
			ServletContext context = config.getServletContext();
			String path = (String)context.getInitParameter("propertiesPath");
			System.out.println("properties Path : " + path);				
			fis = new FileInputStream(path + configFile);
			prop.load(fis);
		}catch(IOException ioe){
			System.out.println("config 파일을 읽을수 없습니다.");			
			throw new ServletException();
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){}
			}
		}
		// command & class 매핑처리
		Iterator<Object> keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()){
			String command = (String)keyIter.next();
			// 해당 클래스이름 얻기
			String className = prop.getProperty(command);
			System.out.println(className  + " ");		
			try{
				// map에 저장처리
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
		// command action 얻기
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		//	command 매핑정보로 해당 action 수행
		System.out.println("command : " + command);
		action = (Action)commandMap.get(command);
		// 매핑된 액션이 없을경우 에러페이지로 이동
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