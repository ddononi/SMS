package kr.go.police.address;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 * 파일 다운로드 다운로드박스가 출력되게 처리
 */
public class ExcelWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// String fileName = request.getParameter("file"); // 파일명
		AddressDAO dao = new AddressDAO();
		AddressBean bean = new AddressBean();

		int groupIndex = Integer.valueOf(request.getParameter("groupIndex"));

		ArrayList<AddressBean> list = (ArrayList<AddressBean>) dao
				.getAddressList(groupIndex);
		String data[][] = new String[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			bean = (AddressBean) list.get(i);
			data[i][0] = bean.getPeople().trim();
			data[i][1] = bean.getPhone().trim();
		}

		try {
			ExcelWriteAction.simpleExcelWrite(new File(fileOpen()), data);
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("window.location.href='./AddressListAction.ad?groupIndex="
					+ groupIndex + "'");
			out.println("</script>");
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('저장실패!!')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public final static void simpleExcelWrite(File file, String data[][])
			throws Exception {
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;

		try {
			workbook = Workbook.createWorkbook(file); // 지정된 파일명 경로로 엑셀파일 생성
			workbook.createSheet("Sheet", 0); // 지정한 파일에 시트 생성
			sheet = workbook.getSheet(0); // 시트 로드
			WritableCellFormat cellFormat = new WritableCellFormat(); // 셀의 스타일
																		// 지정
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일
																	// 지정(테두리
																	// 라인)
			// 엑셀파일에 데이터를 작성
			for (int row = 0; row < data.length; row++) {
				for (int col = 0; col < data[0].length; col++) {
					Label label = new jxl.write.Label(col, row,
							(String) data[row][col], cellFormat);
					sheet.addCell(label);
				}
			}
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// java.awt.FileDialog를 이용한 파일 저장 경로 설정
	public static String fileOpen() {
		Frame f = new Frame("Save as..");
		f.setSize(0, 0);
		FileDialog fileOpen = new FileDialog(f, "Save as..", FileDialog.SAVE);
		fileOpen.setFile("address.xls"); // 기본 파일명
		f.setVisible(false);
		fileOpen.setVisible(true);
		return fileOpen.getDirectory() + fileOpen.getFile();
	}

}
