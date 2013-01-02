package kr.go.police.board;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;

import kr.go.police.CommandToken;
import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 *	공지사항 등록 액션
 */
public class NoticeWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// 사용자 이름, 인덱스 얻기
		HttpSession session = request.getSession();
		BoardDAO dao = new BoardDAO();
		BoardBean data = new BoardBean();
		
		// file upload 처리 
		int size = 10 * 1024 * 1024;		// 최대 허용 파일 크기 10Mb
		// 업로드 파일 경로 
		String uploadPath = request.getServletContext().getRealPath("uploads");
		try{
			// cos.jar 
			// multipartRequest lib
			MultipartRequest multi =
					new MultipartRequest(request, uploadPath, size, "euc-kr", new DefaultFileRenamePolicy());
			
			// 파일명 가져오기
			Enumeration files = multi.getFileNames();
			String filename = (String)files.nextElement();
			filename = multi.getFilesystemName(filename);
			
			String title = (String)multi.getParameter("title");					// 제목
			String content = (String)multi.getParameter("content");			// 내용
			String pwd = (String)multi.getParameter("board_pwd");			// 비밀번호
			
			data.setFilename(filename);
			data.setRegisterName(session.getAttribute("name").toString());	// 이름
			int userIndex = Integer.valueOf(session.getAttribute("index").toString());
			data.setRegUserIndex(userIndex);											// 유저 인덱스
			data.setTitle(SMSUtil.removeHTML(title));
			data.setContent(SMSUtil.removeHTML(content));
			data.setPwd(pwd);
			data.setNotice(true);		// 공지사항	
		}catch(Exception e){			//	파일크기가 10메가 이상
			System.out.println(e.getMessage());			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('10메가 이내에 정확한 파일을 등록하세요.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();			
			return null;
		}

		
		// 등록 처리
		if(dao.insertBoard(data)){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('공지사항을 등록하였습니다.');");			
			out.println("window.location.replace('./AdminNoticeListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// 등록 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('공지사항 등록 실패');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
