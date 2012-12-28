package kr.go.police.address;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.go.police.action.Action;
import kr.go.police.action.ActionForward;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * ���� ������ �о�� �ּҷϿ� �߰����ش�.
 */
public class ExcelReadAction implements Action, iExcelConstant {
	private HttpServletResponse response;
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.response = response;
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		// ���� ���
		String uploadPath = request.getServletContext().getRealPath("uploads");
		int size = 10 * 1024 * 1024; // ���� �ִ� ���� ��� ũ��
		String fileName = ""; // ���ϸ� �̸�
		// cos library
		MultipartRequest multi = new MultipartRequest(request, uploadPath,
				size, "euc-kr", new DefaultFileRenamePolicy());
		// �� �ε���
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index")
				.toString());
		// �׷��ε���
		String groupIndex = (String) multi.getParameter("groupIndex");
		// �ּҷ��� ������ ����Ʈ
		try {
			Enumeration files = multi.getFileNames(); // ���۵� ���� Ÿ���� �Ķ���� �̸�����
														// EnumerationŸ������ ��ȯ�Ѵ�.
			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);
			// ���� Ȯ���� üũ
			if (!fileExtCheck(response, fileName)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�������� ���� : " + e.getMessage());
			return null;
		}
		
		String FileAddress = uploadPath + File.separator + fileName;
		// �ּҷ��� ������ ����Ʈ
		List<AddressBean> list  = getAddressFromExcel(groupIndex, FileAddress);
		// �ּҷ��� ������ ��� ������ ������ ���� ���
		int addedCount = 0;
		if (list != null && list.size() > 0) {
			for (AddressBean data : list) {
				if (dao.addAddressPeople(userIndex, data)) {
					++addedCount;
				}
			}
		}

		if (addedCount > 0) {
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + addedCount + "���� �ּҷ��� �߰��Ͽ����ϴ�.');");
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

	/**
	 * ���� ���Ͽ��� �ּҷ� �̾ƿ���
	 * @param groupIndex
	 * @param FileAddress
	 */
	private List<AddressBean> getAddressFromExcel(String groupIndex, String FileAddress) {
		ArrayList<AddressBean> list = null;
		try {
			// ���� ���� �ҷ�����
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					FileAddress));
			// ��ũ���� ����!
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			list = new ArrayList<AddressBean>();
			// �ִ� ���� row �������� �Ѿ��� ���
			if(rows >= MAX_ALLOW_ROW){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('���� �ּҷ� �ִ� �߰� ������ " + 
							MAX_ALLOW_ROW + "���̸����� �ؾ� �մϴ�.');");
				out.println("</script>");				
				return null;
			}
			
			for (int r = 0; r < rows; r++) {
				// ��Ʈ�� ���� ���� �ϳ��� ����
				HSSFRow row = sheet.getRow(r);
				System.out.println("dd");
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					// �ּҷ� bean
					AddressBean data = new AddressBean();
					data.setGroupIndex(Integer.valueOf(groupIndex));
					for (short c = 0; c < cells; c++) {
						System.out.println("ff");
						// �࿡���� ���� �ϳ��� �����Ͽ� �� Ÿ�Կ� ���� ó��
						HSSFCell cell = row.getCell(c);
						if (cell != null) {
							String value = null;
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_FORMULA:
								value = cell.getCellFormula();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								value = "" + cell.getNumericCellValue(); // double
								break;
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue(); // String
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								value = null;
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = "" + cell.getBooleanCellValue(); // boolean
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "" + cell.getErrorCellValue(); // byte
								break;
							default:
							}

							if (c == NAME_CMN) { // �̸� Į��
								data.setPeople(value);
								System.out.println("name : " + value);
							} else if (c == PHONE_CMN) { // ��ȭ��ȣ Į��
								value = value.trim().replaceAll("-", "");
								data.setPhone(value);
								list.add(data); // ����Ʈ�� ���
								System.out.println("phone : " + value);
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	/**
	 * ���� Ȯ���� üũ
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean fileExtCheck(HttpServletResponse response, String fileName)
			throws IOException {
		// ���������� xls��츸 üũ
		if (fileName.toLowerCase().endsWith(".xls")) {
			return true;
		}

		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('��Ȯ�� ��������(xls)�� ����ϼ���.');");
		out.println("history.go(-1);");
		out.println("</script>");
		out.close();
		return false;
	}

	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
		}
		return false;
	}

}
