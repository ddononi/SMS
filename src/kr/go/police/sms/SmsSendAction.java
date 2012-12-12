package kr.go.police.sms;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �߼� ó�� action
 */
public class SmsSendAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ajaxó���̱⶧���� utf-8�κ�ȯ�Ѵ�. 
		request.setCharacterEncoding("utf-8");		
		// �߼۰��� dao
		SmsDAO dao = new SmsDAO();
		// �߼۹��������� ���� bean
		ArrayList<SMSBean> list = new ArrayList<SMSBean>();
		// �� ��ȭ��ȣ
		String fromPhone = (String)request.getParameter("my_phone_num");
		// �߼� �޼���
		String message = (String)request.getParameter("message");
	//	message = new String(message.getBytes("utf-8"), "euc-kr");
//		System.out.println(message);		
		// �߼� ������ ��ȭ��ȣ
		String callback = (String)request.getParameter("callback");		
		// ���� ���
		String flag = (String)request.getParameter("send_type");		
		// ���� ����
		boolean reserved = request.getParameter("reserved").toString().equals("true");		
		// ������
		String reservedDate =  (String)request.getParameter("reserved_datetime");
		if(request.getParameter("reserved_datetime") == null){
			reservedDate = "";
		}
		// ���� ����
		HttpSession sessoin = request.getSession();
		// ���� �ε���
		int userIndex = Integer.valueOf(sessoin.getAttribute("index").toString());
		// ���� ���̵�
		String id = sessoin.getAttribute("id").toString();
		
		// �߼��� ��ȭ��ȣ�� ��������
		String tmpNums = (String)request.getParameter("call_to_nums");
		System.out.println(tmpNums);
		String[] toTels = tmpNums.split(",");
		// set�� �̿��Ͽ� �ߺ� ��ȭ��ȣ�� �����Ѵ�.		
		Set<String> hs = new HashSet<String>();
		for(String tel : toTels ){
			hs.add(tel);
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
		while(it.hasNext()){
			// �߼� ���� ���
			data = new SMSBean();
			String seq = yMdHms + String.format("%04d",  ++count);
			data.setIndex(Long.valueOf(seq));
			data.setToPhone(it.next());
			data.setFromPhone(fromPhone);
			data.setMessage(message);
			data.setCallback(callback);
			data.setFlag(flag);			
			data.setId(id);
			data.setReserved(reserved);
			data.setReserveDate(reservedDate);
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
		int addCount = dao.addSmsSendList(list);
		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println(addCount);
		out.close();
		
		return null;			

	}

}
