package kr.go.police.board;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 *	�������� ��� �׼�
 */
public class NoticeWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// ����� �̸�, �ε��� ���
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		BoardBean data = new BoardBean();
		
		// file upload ó�� 
		int size = 10 * 1024 * 1024;		// �ִ� ��� ���� ũ�� 10Mb
		// ���ε� ���� ��� 
		String uploadPath = request.getServletContext().getRealPath("uploads");
		try{
			// cos.jar 
			// multipartRequest lib
			MultipartRequest multi =
					new MultipartRequest(request, uploadPath, size, "euc-kr", new DefaultFileRenamePolicy());
			// ���ϸ� ��������
			Enumeration files = multi.getFileNames();
			String filename = (String)files.nextElement();
			filename = multi.getFilesystemName(filename);
			
			String title = (String)multi.getParameter("title");					// ����
			String content = (String)multi.getParameter("content");			// ����
			String pwd = (String)multi.getParameter("board_pwd");			// ��й�ȣ
			
			data.setFilename(filename);
			data.setRegisterName(session.getAttribute("name").toString());	// �̸�
			int userIndex = Integer.valueOf(session.getAttribute("index").toString());
			data.setRegUserIndex(userIndex);											// ���� �ε���
			data.setTitle(SMSUtil.removeHTML(title));
			data.setContent(SMSUtil.removeHTML(content));
			data.setPwd(pwd);
			data.setNotice(true);		// ��������	
		}catch(Exception e){			//	����ũ�Ⱑ 10�ް� �̻�
			System.out.println(e.getMessage());			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('10�ް� �̳��� ��Ȯ�� ������ ����ϼ���.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();			
			return null;
		}

		
		// ��� ó��
		if(dao.insertBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���������� ����Ͽ����ϴ�.');");			
			out.println("window.location.href='./AdminNoticeListAction.bo';");
			out.println("</script>");	
			out.close();
		}else{				// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�������� ��� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
