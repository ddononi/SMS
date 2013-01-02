package kr.go.police.board;

import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	공지사항 게시물 삭제 액션
 */
public class NoticeDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		// 게시물 인덱스번호
		String tmpIndexs = (String)request.getParameter("del_index");			
		String[] indexs = tmpIndexs.split(",");
		int delCount = 0;
		for(String index : indexs){
			// 파일이 있는지 체크후 파일 삭제
			BoardBean data = dao.getDetail(Integer.valueOf(index));
			String filename = data.getFilename();
			if(filename != null && filename.length() >0){
				// 파일경로
				String downloadPath = request.getServletContext().getRealPath("uploads");
				// 파일 path 
				String filePath = downloadPath + File.separator + filename;				
				File file = new File(filePath);
				// 파일존재여부 확인후 삭제
				if(file.isFile() && file.delete()){
					System.out.println("파일 삭제");
				}
			}	 			
			dao.deleteBoard(Integer.valueOf(index));
			delCount++;
		}
		// 삭제 처리알림
		if( delCount == indexs.length ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다.');");			
			out.println("window.location.replace('./AdminNoticeListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// 등록 실패
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제 실패');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
