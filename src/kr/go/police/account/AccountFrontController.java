package kr.go.police.account;

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
 *	����� �������� ó�� ��Ʈ�ѷ�
 */
public class AccountFrontController extends javax.servlet.http.HttpServlet
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

	
	
/*
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		// �α��� �׼�
		if (command.equals("/LoginAction.ac")) {
			action = new LoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�α��� ó�� ����");
			}
		// ȸ�� ���� �׼�	
		} else if (command.equals("/JoinAction.ac")) {
			action = new JoinAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		// ����� ���� �׼�	
		} else if (command.equals("/ApproveAction.ac")) {
			if(LoginCheck.checkLogin(request, response)){
				action = new LoginAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		// ����� ���� �׼�	
		} else if (command.equals("/UserDelAction.ac")) {
			if(LoginCheck.checkLogin(request, response)){			
				action = new LoginAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		// ����� ��ȸ �׼�	
		} else if (command.equals("/UserListAction.ac")) {
			action = new UserListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		// ��������� ���� ����	
		} else if(command.equals("/UserDetailAction.ac")){
			if(LoginCheck.checkLogin(request, response)){					
				action = new UserDetailAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		// ��������� ���� ����	
		} else if(command.equals("/MyInfoAction.ac")){
			if(LoginCheck.checkLogin(request, response)){					
				action = new MyInfoAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}			
		// �ű� ���� ����	
		}else if(command.equals("/RecentJoinUsersAction.ac")){	
			if(LoginCheck.checkLogin(request, response)){					
				//action = new RecentJoinUserAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}else if(command.equals("/MyInfoModify.ac")){
			action = new MyInfoModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		//  ������ ����� ���� ����	
		}else if(command.equals("/AdminModifyUserAction.ac")){
			action = new AdminModifyUserAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		//  �α� �ƿ�
		}else if(command.equals("/LogoutAction.ac")){
			action = new LogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}

		// ���̵� �ߺ� üũ
		if (command.equals("/IdCheckAction.ac")) {
			action = new  IdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	*/

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}