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

		// 파일 업로드
		int size = 10 * 1024 * 1024;
		String fileName = ""; // 파일명 이름
		MultipartRequest multi = new MultipartRequest(request, uploadPath,
				size, "euc-kr", new DefaultFileRenamePolicy()); // COS라이브러리가
																// 제공하는 업로드
																// 클레스이다.
		try {
			Enumeration files = multi.getFileNames(); // 전송된 파일 타입의 파라미터 이름들을
														// Enumeration타입으로 반환한다.

			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);

			System.out.println(fileName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("파일업로드 에러 : " + e.getMessage());
		}

		String FileAddress = request.getServletContext().getRealPath("uploads"); // 주소지정
		FileAddress += File.separator + fileName;

		Workbook myWorkbook = Workbook.getWorkbook(new File(FileAddress)); // 파일
																			// 불러오기

		Sheet mySheet = myWorkbook.getSheet(0); // 시트불러오기
		int totalCol = 2;
		int totalRow = mySheet.getRows();

		// excel배열에 엑셀 내용 추가
		String excel[][] = new String[totalCol][totalRow];
		for (int i = 0; i < totalCol; i++) {
			for (int j = 0; j < totalRow; j++) {
				excel[i][j] = mySheet.getCell(i, j).getContents();

			}
		}
		// 파일 삭제
		File delete = new File(FileAddress);
		if (delete.exists())
			delete.delete();

		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index")
				.toString());
		// 그룹인덱스
		String groupIndex = (String) multi.getParameter("groupIndex");
		AddressBean data = new AddressBean();
		data.setGroupIndex(Integer.valueOf(groupIndex));

		for (int i = 0; i < totalRow; i++) {
			if (excel[0][i].isEmpty() || excel[1][i].isEmpty()) {
			}

			else if (excel[1][i].trim().substring(0, 2).equals("01")) {
				if (excel[1][i].trim().contains("-")) {
					excel[1][i] = excel[1][i].trim().replaceAll("-", ""); // 엑셀값에-부호가 있으면 제거
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
			out.println("alert('새로운 주소록을 추가하였습니다.');");
			out.println("window.location.href='./AddressListAction.ad?groupIndex="
					+ groupIndex + "'");
			out.println("</script>");
			out.close();
			return null;
			
		} else {
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('주소록 추가 실패!!')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		}

	}
}
