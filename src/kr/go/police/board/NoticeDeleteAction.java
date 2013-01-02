package kr.go.police.board;

import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	�������� �Խù� ���� �׼�
 */
public class NoticeDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		// �Խù� �ε�����ȣ
		String tmpIndexs = (String)request.getParameter("del_index");			
		String[] indexs = tmpIndexs.split(",");
		int delCount = 0;
		for(String index : indexs){
			// ������ �ִ��� üũ�� ���� ����
			BoardBean data = dao.getDetail(Integer.valueOf(index));
			String filename = data.getFilename();
			if(filename != null && filename.length() >0){
				// ���ϰ��
				String downloadPath = request.getServletContext().getRealPath("uploads");
				// ���� path 
				String filePath = downloadPath + File.separator + filename;				
				File file = new File(filePath);
				// �������翩�� Ȯ���� ����
				if(file.isFile() && file.delete()){
					System.out.println("���� ����");
				}
			}	 			
			dao.deleteBoard(Integer.valueOf(index));
			delCount++;
		}
		// ���� ó���˸�
		if( delCount == indexs.length ){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�����Ǿ����ϴ�.');");			
			out.println("window.location.replace('./AdminNoticeListAction.bo');");
			out.println("</script>");	
			out.close();
		}else{				// ��� ����
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ����');");
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();
		}
		
		return null;
	}

}
