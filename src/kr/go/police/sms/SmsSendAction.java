package kr.go.police.sms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.go.police.IGwConstant;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	문자 발송 처리 action
 */
public class SmsSendAction implements Action, IGwConstant{
	private HttpServletResponse response;
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();		
		this.response = response;
		request.setCharacterEncoding("euc-kr");
		HttpSession session = request.getSession();	
		// 발송관련 dao
		SmsDAO dao = new SmsDAO();
		// 발송문자정보를 담을 bean
		ArrayList<SMSBean> list = new ArrayList<SMSBean>();
		
		// file upload 처리 
		// 업로드 파일 경로 
		String uploadPath = "C:\\mms_uploads";
		boolean reserved = false;	// 예약여부
		// 최대 파일 크기허용 범위를 동영상 파일 3개로 가정
		int maxFileSize = VIDEO_MAX_SIZE  * 1024 * 3;
		try{
			// cos.jar 
			// multipartRequest lib
			MultipartRequest multi =
					new MultipartRequest(request, uploadPath, maxFileSize, "euc-kr", new DefaultFileRenamePolicy());
			
			// 파일명 가져오기
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
			// 첨부 가능한 파일인지 체크
			if(!checkMmsFile(f1) || 
				!checkMmsFile(f2) || 
				!checkMmsFile(f3)){
				return null;
			}
			
			// 동영상 첨부시 2개형식이(skm, k3g) 모두 들어가 있는지 체크
			if(!checkVideoFile(f1, f2, f3)){
				invalidMessageShow("동영상 첨부시 2개형식이(skm, k3g) 반드시 포함되어야 합니다.");
				return null;
			}
			
			String fromPhone = (String)multi.getParameter("my_phone_num");	// 내 전화번호
			fromPhone = fromPhone.replace("-", "");
			String message = (String)multi.getParameter("message");		// 발송 메세지
			String callback = (String)session.getAttribute("phone");			// 콜백 전화번호
			String flag = (String)multi.getParameter("flag");		// 전송 모드
			reserved = multi.getParameter("reserved").toString().equals("true");	// 예약 여부	
			String reservedDate =  (String)multi.getParameter("reserved_datetime");// 예약일
			// 예약일이 없을경우 현재 시간으로 설정
			if(reservedDate == null || reservedDate.length() <= 0){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				reservedDate = sdf.format(new Date());	
			}else{
				// 예약일이 있는 경우 초(sec)를 붙여준다.
				reservedDate += ":00";
			}
			
			int userIndex = Integer.valueOf(session.getAttribute("index").toString());// 유저 인덱스
			String id = session.getAttribute("id").toString();// 유저 아이디
			String tmpNums = (String)multi.getParameter("call_to_nums");// 발송할 전화번호들
			String[] toTels = tmpNums.split(",");
			// set을 이용하여 중복 전화번호는 제거한다.		
			Set<String> hs = new HashSet<String>();
			for(String tel : toTels ){
				hs.add(tel.replace("-", ""));
			}
			
			// 고유 시퀸스값을 얻기 위한 년월일시분초
			SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
			String yMdHms = sdf.format(new Date());
			Iterator<String> it = hs.iterator();
			SMSBean data;
			int count = 0;
	    	ServletContext context = request.getServletContext();
	    	String logPath = (String)context.getInitParameter("logFilesPath");		
	    	String logMsg;
			while(it.hasNext()){
				// 발송 정보 담기
				data = new SMSBean();
				String seq = yMdHms + String.format("%04d",  ++count);
				data.setIndex(Long.valueOf(seq));
				data.setToPhone(it.next());
				data.setFromPhone(fromPhone);
				data.setMessage(message);
				data.setCallback(callback);
				data.setFile1(file1);
				data.setFile2(file2);
				data.setFile3(file3);				
				data.setFlag(flag);			
				data.setId(id);
				data.setCallback(callback);
				data.setReserved(reserved);
				data.setSendDate(reservedDate);
				data.setUserIndex(userIndex);
				// 로그파일에 기록
				logMsg = "문자 발송\t-SEQ- : " + data.getIndex() +  "\t-USER_ID- : " + data.getId() + 
						 	"\t-To- : " + data.getToPhone() + 	"\t-From- : " + data.getFromPhone() +
						 	"\t-Callback- : " + data.getCallback() + "\t-Mode- : "
						 	+ (data.getFlag().equalsIgnoreCase("s")?"SMS":"MMS");
				// 로그 기록
				SMSUtil.writerToLogFile(logPath, logMsg);
				list.add(data);
			}
			
			// 발송 대기 추가 갯수 얻기
			int addCount = dao.addSmsSendList(list);

		}catch(Exception e){			// 오류 발생시
			System.out.println(e.getMessage());			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('파일 종류 및 파일 크기를 확인하세요.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();			
			return null;
		}
		
		forward.setRedirect(true);
		// 예약 발송이면 예약화면으로		
		if(reserved){
			forward.setPath("./ReservedListAction.sm");
		}else{		//	전송 결과 화면으로
			forward.setPath("./SmsSendResultAction.sm");
		}
		return forward;			

	}

	/**
	 *  MMS에 허용되는 파일 및 크기 체크
	 * @param file
	 */
	private boolean checkMmsFile(File file) {
		if(file == null){
			return true;
		}
		
		if(file.isFile()){
			
		}
		
		// 파일 종류에 따른 최대 허용 범위 체크
		if (file.getName().toLowerCase().endsWith(".jpg") ||
				file.getName().toLowerCase().endsWith(".jpeg")){
			System.out.println(file.getName());
			// 이미지 최대 크기보다 클 경우
			if( file.length() > JPG_MAX_SIZE * 1024 ){
				invalidMessageShow("이미지 크기는 " + JPG_MAX_SIZE +"Kb 이내로 해야합니다.");
				return false;
			}
		}
		
		if (file.getName().toLowerCase().endsWith(".skm") ||
				file.getName().toLowerCase().endsWith(".k3g")){
			System.out.println(file.getName());
			// 동영상 최대 크기보다 클 경우
			if( file.length() > VIDEO_MAX_SIZE  * 1024 ){
				invalidMessageShow("동영상 크기는 " + VIDEO_MAX_SIZE +"Kb 이내로 해야합니다.");
				return false;
			}
		}		
		
		return true;
		
	}
	
	/**
	 * 에러메세지를 보여준후 이전 화면을 돌아간다.
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
	 * 동영상 첨부시 2개형식이(skm, k3g) 모두 들어가 있는지 체크
	 * @param f1
	 * @param f2
	 * @param f3
	 * @return
	 */
	private boolean checkVideoFile(File f1, File f2, File f3) {
		// 우선 동영상 파일이 있는지 체크후
		// 전부 없으면 true 처리
		if( ((f1 != null && f1.getName().toLowerCase().endsWith(".skm")) ||
			(f2 != null && f2.getName().toLowerCase().endsWith(".skm")) ||	
			(f3 != null && f3.getName().toLowerCase().endsWith(".skm")) ||
			(f1 != null && f1.getName().toLowerCase().endsWith(".k3g")) ||
			(f2 != null && f2.getName().toLowerCase().endsWith(".k3g")) ||
			(f3 != null && f3.getName().toLowerCase().endsWith(".k3g")) ) == false	){
				return true;
		}		
		
		//  skm형식을 먼저 체크 한후 k3g를 체크한다.
		// 첫번째 파일이 skm 동영상 파일이면
		if(f1 != null && f1.getName().toLowerCase().endsWith(".skm")){
			if( f2 != null && f2.getName().toLowerCase().endsWith(".k3g") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}
		
		// 두번째 파일이 skm 동영상 파일이면
		if(f2 != null && f2.getName().toLowerCase().endsWith(".skm")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".k3g") ||
				f3 != null && f3.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}		
		
		// 세번째 파일이 skm 동영상 파일이면
		if(f3 != null && f3.getName().toLowerCase().endsWith(".skm")){
			if( f1 != null && f1.getName().toLowerCase().endsWith(".k3g") ||
				f2 != null && f2.getName().toLowerCase().endsWith(".k3g") )
				return true;
		}		
		
		// k3g형식 체크후 skm 체크
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
