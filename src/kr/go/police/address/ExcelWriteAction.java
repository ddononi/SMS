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
 * ���� �ٿ�ε� �ٿ�ε�ڽ��� ��µǰ� ó��
 */
public class ExcelWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("euc-kr");
		// String fileName = request.getParameter("file"); // ���ϸ�
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
			out.println("alert('�������!!')");
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
			workbook = Workbook.createWorkbook(file); // ������ ���ϸ� ��η� �������� ����
			workbook.createSheet("Sheet", 0); // ������ ���Ͽ� ��Ʈ ����
			sheet = workbook.getSheet(0); // ��Ʈ �ε�
			WritableCellFormat cellFormat = new WritableCellFormat(); // ���� ��Ÿ��
																		// ����
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // ���� ��Ÿ��
																	// ����(�׵θ�
																	// ����)
			// �������Ͽ� �����͸� �ۼ�
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

	// java.awt.FileDialog�� �̿��� ���� ���� ��� ����
	public static String fileOpen() {
		Frame f = new Frame("Save as..");
		f.setSize(0, 0);
		FileDialog fileOpen = new FileDialog(f, "Save as..", FileDialog.SAVE);
		fileOpen.setFile("address.xls"); // �⺻ ���ϸ�
		f.setVisible(false);
		fileOpen.setVisible(true);
		return fileOpen.getDirectory() + fileOpen.getFile();
	}

}
