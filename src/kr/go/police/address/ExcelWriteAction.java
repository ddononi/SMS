package kr.go.police.address;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import kr.go.police.SMSUtil;
import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

/**
 * ���� �ٿ�ε� �ٿ�ε�ڽ��� ��µǰ� ó��
 */
public class ExcelWriteAction implements Action , iExcelConstant {
	
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
		
		// �����Ͱ� �ִ��� üũ
		if(list.size() <= 0){
			throw new Exception(); 
		}
		
		String data[][] = new String[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			bean = (AddressBean) list.get(i);
			data[i][NAME_CMN] = bean.getPeople().trim();
			data[i][PHONE_CMN] = SMSUtil.addPhoneHyphens(bean.getPhone().trim());
		}

		try {
			File tempFile = new File("address.xls");
			ExcelWriteAction.simpleExcelWrite(tempFile, data);
			byte b[] = new byte[4096];		
			FileInputStream in = new FileInputStream(tempFile);
			// Ÿ�� ����
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "inline; filename = " + "address.xls");
			ServletOutputStream out = response.getOutputStream();
			int len;
			// ����Ʈ �迭 b�� 0������ numRead���� ���
			while((len = in.read(b, 0, b.length)) != -1){
				out.write(b, 0, len);
			}
			
			out.flush();
			out.close();
			in.close();

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

	/**
	 *  �������Ͽ� ����ó��
	 * @param file
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public final static void simpleExcelWrite(File file, String data[][])
			throws Exception {
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label;
		try {
			workbook = Workbook.createWorkbook(file); // ������ ���ϸ� ��η� �������� ����
			workbook.createSheet("Sheet", 0); // ������ ���Ͽ� ��Ʈ ����
			sheet = workbook.getSheet(0); // ��Ʈ �ε�
		    sheet.setColumnView(NAME_CMN, NAME_WIDTH);		
			sheet.setColumnView(PHONE_CMN, PHONE_WIDTH);		    
			WritableCellFormat cellFormat = new WritableCellFormat(); // ���� ��Ÿ��
																		// ����
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // ���� ��Ÿ��
																	// ����(�׵θ�
																	// ����)
			// ù��° ���� �̸�, ��ȭ��ȣ Ÿ��Ʋ�� �־��ش�.
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			label = new jxl.write.Label(NAME_CMN, 0, "�̸�", cellFormat);
			sheet.addCell(label);					
			label = new jxl.write.Label(PHONE_CMN, 0, "��ȭ��ȣ", cellFormat);
			sheet.addCell(label);			
			
			// �������Ͽ� �����͸� �ۼ�
			for (int row = 1; row <= data.length; row++) {
				for (int col = 0; col < data[0].length; col++) {
					label = new jxl.write.Label(col, row,
							(String) data[row-1][col], cellFormat);
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
}
