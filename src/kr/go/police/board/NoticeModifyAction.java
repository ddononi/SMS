package kr.go.police.board;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�������� �Խù� ���� �׼�
 */
public class NoticeModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
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
			String filename = "";
			if(files.hasMoreElements()){
				filename = (String)files.nextElement();
				filename = multi.getFilesystemName(filename);
			}
			String boardIndex = (String)multi.getParameter("index");		// �Խù� �ε���		
			String title = (String)multi.getParameter("title");					// ����
			String content = (String)multi.getParameter("content");			// ����
			String pwd = (String)multi.getParameter("board_pwd");			// ��й�ȣ
			// ���ο� ���Ϸ� ���������� ���� ������ �����ϰ� ���ο� ���Ϸ� �����Ѵ�.
			if(filename != null && filename.length() > 0){
				data.setFilename(filename);
				// �Խù����� ��������
				BoardBean oldData = dao.getDetail(Integer.valueOf(boardIndex));
				String filePath = uploadPath + File.separator + oldData.getFilename();
				File file = new File(filePath);
				if(file.isFile()){		// ������ ���� �ϸ� ����
					file.delete();
					System.out.println("���� ���� -->" +  filePath);
				}
			}
			data.setIndex(Integer.valueOf(boardIndex));
			data.setRegisterName(session.getAttribute("name").toString());	// �̸�
			int userIndex = Integer.valueOf(session.getAttribute("index").toString());
			data.setRegUserIndex(userIndex);											// ���� �ε���
			data.setTitle(SMSUtil.removeHTML(title));
			data.setContent(content);
			data.setPwd(pwd);
			data.setNotice(true);		// ��������	
		}catch(Exception e){			//	����ũ�Ⱑ 10�ް� �̻�
			System.out.println("error : " + e.getMessage());			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('10�ް� �̳��� ��Ȯ�� ������ ����ϼ���.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();			
			return null;
		}

		
		// ���� ó��
		if(dao.modifyBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���������� �����Ͽ����ϴ�.');");			
			out.println("window.location.replace('./AdminNoticeListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�������� ���� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
