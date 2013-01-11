package kr.go.police.sms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.ArrayRecord;

import kr.go.police.IGwConstant;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 *	���� �߼� ó�� action
 */
public class SmsSendAction implements Action, IGwConstant{
	private HttpServletResponse response;
	private int addCount = 0;
	private String flag;
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();		
		this.response = response;
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();	
		// �߼۰��� dao
		SmsDAO dao = new SmsDAO();
		// �߼۹��������� ���� bean
		ArrayList<SMSBean> list = new ArrayList<SMSBean>();
		
		// file upload ó�� 
		// ���ε� ���� ��� 
		String uploadPath = "C:\\mms_uploads";
		boolean reserved = false;	// ���࿩��
		// �ִ� ���� ũ����� ������ ������ ���� 3���� ����
		int maxFileSize = VIDEO_MAX_SIZE  * 1024 * 3;
		try{
			// cos.jar 
			// multipartRequest lib
			MultipartRequest multi =
					new MultipartRequest(request, uploadPath, maxFileSize, "euc-kr", new DefaultFileRenamePolicy());
			
			// ���ϸ� ��������
			Enumeration files = multi.getFileNames();
			String file1 = (String)files.nextElement();
			file1 = multi.getFilesystemName(file1);
			String file2 = (String)files.nextElement();
			file2 = multi.getFilesystemName(file2);
			String file3 = (String)files.nextElement();
			file3 = multi.getFilesystemName(file3);			
			
		//	System.out.println(multi.getFile(file1).getName());
		//	System.out.println(multi.getFile(file2).getName());
			File f1 = new File(uploadPath + File.separator + file1);
			File f2 = new File(uploadPath + File.separator + file2);
			File f3 = new File(uploadPath + File.separator + file3);
			// ÷�� ������ �������� üũ
			if(!checkMmsFile(f1) || 
				!checkMmsFile(f2) || 
				!checkMmsFile(f3)){
				return null;
			}
			
			// ������ ÷�ν� 2��������(skm, k3g) ��� �� �ִ��� üũ
			/*
			if(!checkVideoFile(f1, f2, f3)){
				invalidMessageShow("������ ÷�ν� 2��������(skm, k3g) �ݵ�� ���ԵǾ�� �մϴ�.");
				return null;
			}
			*/
			
			String fromPhone = (String)multi.getParameter("my_phone_num");	// �� ��ȭ��ȣ
			fromPhone = fromPhone.replace("-", "");
			String message = (String)multi.getParameter("message");		// �߼� �޼���
			String callback = (String)session.getAttribute("phone");			// �ݹ� ��ȭ��ȣ
			flag = (String)multi.getParameter("flag");		// ���� ���
			reserved = multi.getParameter("reserved").toString().equals("true");	// ���� ����	
			String reservedDate =  (String)multi.getParameter("reserved_datetime");// ������
			// �������� ������� ���� �ð����� ����
			if(reservedDate == null || reservedDate.length() <= 0){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				reservedDate = sdf.format(new Date());	
			}else{
				// �������� �ִ� ��� ��(sec)�� �ٿ��ش�.
				reservedDate += ":00";
			}
			
			int userIndex = Integer.valueOf(session.getAttribute("index").toString());// ���� �ε���
			String id = session.getAttribute("id").toString();// ���� ���̵�
			String tmpNums = (String)multi.getParameter("call_to_nums");// �߼��� ��ȭ��ȣ��
			String[] toTels = tmpNums.split(",");
			// set�� �̿��Ͽ� �ߺ� ��ȭ��ȣ�� �����Ѵ�.		
			Set<String> hs = new HashSet<String>();
			for(String tel : toTels ){
				hs.add(tel.replace("-", ""));
			}
			
			// ���� ���������� ��� ���� ����Ͻú���
			SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
			String yMdHms = sdf.format(new Date());
			Iterator<String> it = hs.iterator();
			SMSBean data;
			int count = 0;
	    	ServletContext context = request.getServletContext();
	    	String logPath = (String)context.getInitParameter("logFilesPath");		
	    	String logMsg;
	    	List<String> fileList = sortAttachFiles(file1, file2, file3);
			while(it.hasNext()){
				// �߼� ���� ���
				data = new SMSBean();
				String seq = yMdHms + String.format("%04d",  ++count);
				data.setIndex(Long.valueOf(seq));
				data.setToPhone(it.next());
				data.setFromPhone(fromPhone);
				data.setMessage(message);
				data.setCallback(callback);
				if(fileList.size() > 0) data.setFile1(fileList.get(0));
				if(fileList.size() > 1) data.setFile2(fileList.get(1));
				if(fileList.size() > 2) data.setFile3(fileList.get(2));
				data.setFlag(flag);			
				data.setId(id);
				data.setCallback(callback);
				data.setReserved(reserved);
				data.setSendDate(reservedDate);
				data.setUserIndex(userIndex);
				// �α����Ͽ� ���
				logMsg = "���� �߼�\t-SEQ- : " + data.getIndex() +  "\t-USER_ID- : " + data.getId() + 
						 	"\t-To- : " + data.getToPhone() + 	"\t-From- : " + data.getFromPhone() +
						 	"\t-Callback- : " + data.getCallback() + "\t-Mode- : "
						 	+ (data.getFlag().equalsIgnoreCase("s")?"SMS":"MMS");
				// �α� ���
				SMSUtil.writerToLogFile(logPath, logMsg);
				list.add(data);
			}
			// �߼� ��� �߰� ���� ���
			addCount = dao.addSendList(list);

		}catch(Exception e){			// ���� �߻���
			System.out.println(e.getMessage());			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ���� �� ���� ũ�⸦ Ȯ���ϼ���.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();			
			return null;
		}
		
		//forward.setRedirect(true);
		/*
		// ���� �߼��̸� ����ȭ������		
		if(reserved){
			forward.setPath("./ReservedListAction.sm");
		}else{		//	���� ��� ȭ������
			forward.setPath("./SmsSendResultAction.sm");
		}
		*/

		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('" + addCount + "����  ���� �޼����� �߼��Ͽ����ϴ�.');");
		out.println("window.location.replace('" +
				"./SmsSendViewAction.sm');");
		out.println("</script>");	
		out.close();
		return null;			

	}
	/**
	 *	÷�� ������ ù��°���� ä���ֱ�
	 * @param file1
	 * @param file2
	 * @param file3
	 */
	private List<String> sortAttachFiles(String file1, String file2, String file3) {
		List<String> list = new ArrayList<String>(Arrays.asList(file1, file2, file3));
		// ���� �� null ���ϸ��� ����
		list.removeAll(Arrays.asList("", null));
		System.out.println(list);		
		return list;
	}
	
	/**
	 *  MMS�� ���Ǵ� ���� �� ũ�� üũ
	 * @param file
	 */
	private boolean checkMmsFile(File file) {
		if(file == null){
			return true;
		}
		
		if(file.isFile()){
			
		}
		
		// ���� ������ ���� �ִ� ��� ���� üũ
		if (file.getName().toLowerCase().endsWith(".jpg") ||
				file.getName().toLowerCase().endsWith(".jpeg")){
			System.out.println(file.getName());
			// �̹��� �ִ� ũ�⺸�� Ŭ ���
			if( file.length() > JPG_MAX_SIZE * 1024 ){
				invalidMessageShow("�̹��� ũ��� " + JPG_MAX_SIZE +"Kb �̳��� �ؾ��մϴ�.");
				return false;
			}
		}
		
		if (file.getName().toLowerCase().endsWith(".skm") ||
				file.getName().toLowerCase().endsWith(".k3g")){
			System.out.println(file.getName());
			// ������ �ִ� ũ�⺸�� Ŭ ���
			if( file.length() > VIDEO_MAX_SIZE  * 1024 ){
				invalidMessageShow("������ ũ��� " + VIDEO_MAX_SIZE +"Kb �̳��� �ؾ��մϴ�.");
				return false;
			}
		}		
		
		return true;
		
	}
	
	/**
	 * �����޼����� �������� ���� ȭ���� ���ư���.
	 */
	private void invalidMessageShow(String msg){
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg +"');");
			out.println("window.location.href='./SmsSendViewAction.sm';");
			out.println("</script>");	
			out.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * ������ ÷�ν� 2��������(skm, k3g) ��� �� �ִ��� üũ
	 * @param f1
	 * @param f2
	 * @param f3
	 * @return
	 */
	private boolean checkVideoFile(File f1, File f2, File f3) {
		// �켱 ������ ������ �ִ��� üũ��
		// ���� ������ true ó��
		if( ((f1 != null && f1.getName().toLowerCase().endsWith(".skm")) ||
			(f2 != null && f2.getName().toLowerCase().endsWith(".skm")) ||	
			(f3 != null && f3.getName().toLowerCase().endsWith(".skm")) ||
			(f1 != null && f1.getName().toLowerCase().endsWith(".k3g")) ||
			(f2 != null && f2.getName().toLowerCase().endsWith(".k3g")) ||
			(f3 != null && f3.getName().toLowerCase().endsWith(".k3g")) ) == false	){
				return true;
		}		
		
		//  skm������ ���� üũ ���� k3g�� üũ�Ѵ�.
		// ù��° ������ skm ������ �����̸�
		if(f1 != null && f1.getName().toLowerCase().endsWith(".skm")){
			if( f2 != null && f2.getName().toLowerCase().endsWith(".k3g") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}
		
		// �ι�° ������ skm ������ �����̸�
		if(f2 != null && f2.getName().toLowerCase().endsWith(".skm")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".k3g") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}		
		
		// ����° ������ skm ������ �����̸�
		if(f3 != null && f3.getName().toLowerCase().endsWith(".skm")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".k3g") ||
				f2 != null && f2.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}		
		
		// k3g���� üũ�� skm üũ
		if(f1 != null && f1.getName().toLowerCase().endsWith(".k3g")){
			if( f2 != null && f2.getName().toLowerCase().endsWith(".skm") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".skm") )
				return true;
		}
		
		if(f2 != null && f2.getName().toLowerCase().endsWith(".k3g")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".skm") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".skm") )
				return true;
		}		
		
		if(f3 != null && f3.getName().toLowerCase().endsWith(".k3g")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".skm") ||
				f2 != null && f2.getName().toLowerCase().endsWith(".skm") )
				return true;
		}		
		
		return false;
	}	

}
