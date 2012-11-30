package kr.go.police.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 *	���� �ٿ�ε� 
 *	�ٿ�ε�ڽ��� ��µǰ� ó��
 */
public class FileDownAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");				
		String fileName = request.getParameter("file");		// ���ϸ�		
		// �ѱ� ó��
		String enFileName = new String(fileName.getBytes("iso-8859-1"), "EUC-KR"); 
		System.out.println(enFileName);	
		// ���ϰ��
		String downloadPath = request.getServletContext().getRealPath("uploads");
		// ���� path 
		String filePath = downloadPath + File.separator + enFileName;
		
		File file = new File(filePath);
		// ������ �ִ��� Ȯ��
		if(file.isFile() == false){
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ش� ������ �����ϴ�.');");			
			out.println("history.back(-1);");
			out.println("</script>");	
			out.close();		
			return null;
		}
		
		byte b[] = new byte[4096];		
		FileInputStream in = new FileInputStream(file);
		// ����Ÿ��
		String mimeType = request.getServletContext().getMimeType(filePath);
		System.out.println(mimeType);
		// �������� ���� ���� ����ó��
		if(mimeType == null){
			mimeType = "application/octet-stream";
		}
		// Ÿ�� ����
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "attachment; filename = " + fileName);
		ServletOutputStream out = response.getOutputStream();
		int len;
		// ����Ʈ �迭 b�� 0������ numRead���� ���
		while((len = in.read(b, 0, b.length)) != -1){
			out.write(b, 0, len);
		}
		
		out.flush();
		out.close();
		in.close();

		return null;
	}

}

