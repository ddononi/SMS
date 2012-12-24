package kr.go.police.address;

import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;
import java.io.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import jxl.*;

public class ExcelReadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		String uploadPath = request.getRealPath("uploads");
		boolean result = false;

		// ���� ���ε�
		int size = 10 * 1024 * 1024;
		String fileName = ""; // ���ϸ� �̸�
		MultipartRequest multi = new MultipartRequest(request, uploadPath,
				size, "euc-kr", new DefaultFileRenamePolicy()); // COS���̺귯����
																// �����ϴ� ���ε�
																// Ŭ�����̴�.
		try {
			Enumeration files = multi.getFileNames(); // ���۵� ���� Ÿ���� �Ķ���� �̸�����
														// EnumerationŸ������ ��ȯ�Ѵ�.

			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);

			System.out.println(fileName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���Ͼ��ε� ���� : " + e.getMessage());
		}

		String FileAddress = request.getServletContext().getRealPath("uploads"); // �ּ�����
		FileAddress += File.separator + fileName;

		Workbook myWorkbook = Workbook.getWorkbook(new File(FileAddress)); // ����
																			// �ҷ�����

		Sheet mySheet = myWorkbook.getSheet(0); // ��Ʈ�ҷ�����
		int totalCol = 2;
		int totalRow = mySheet.getRows();

		// excel�迭�� ���� ���� �߰�
		String excel[][] = new String[totalCol][totalRow];
		for (int i = 0; i < totalCol; i++) {
			for (int j = 0; j < totalRow; j++) {
				excel[i][j] = mySheet.getCell(i, j).getContents();

			}
		}
		// ���� ����
		File delete = new File(FileAddress);
		if (delete.exists())
			delete.delete();

		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index")
				.toString());
		// �׷��ε���
		String groupIndex = (String) multi.getParameter("groupIndex");
		AddressBean data = new AddressBean();
		data.setGroupIndex(Integer.valueOf(groupIndex));

		for (int i = 0; i < totalRow; i++) {
			if (excel[0][i].isEmpty() || excel[1][i].isEmpty()) {
			}

			else if (excel[1][i].trim().substring(0, 2).equals("01")) {
				if (excel[1][i].trim().contains("-")) {
					excel[1][i] = excel[1][i].trim().replaceAll("-", ""); // ��������-��ȣ�� ������ ����
				}

				data.setPeople(excel[0][i].trim());
				data.setPhone(excel[1][i].trim());
				if (dao.addAddressPeople(userIndex, data))
					result = true;
				else
					result = false;

			}
		}

		if (result) {
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���ο� �ּҷ��� �߰��Ͽ����ϴ�.');");
			out.println("window.location.href='./AddressListAction.ad?groupIndex="
					+ groupIndex + "'");
			out.println("</script>");
			out.close();
			return null;
			
		} else {
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�ּҷ� �߰� ����!!')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		}

	}
}
