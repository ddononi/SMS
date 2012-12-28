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
 * 엑셀 파일을 읽어와 주소록에 추가해준다.
 */
public class ExcelReadAction implements Action, iExcelConstant {
	private HttpServletResponse response;
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.response = response;
		AddressDAO dao = new AddressDAO();
		request.setCharacterEncoding("euc-kr");
		// 파일 경로
		String uploadPath = request.getServletContext().getRealPath("uploads");
		int size = 10 * 1024 * 1024; // 엑셀 최대 파일 허용 크기
		String fileName = ""; // 파일명 이름
		// cos library
		MultipartRequest multi = new MultipartRequest(request, uploadPath,
				size, "euc-kr", new DefaultFileRenamePolicy());
		// 내 인덱스
		HttpSession session = request.getSession();
		int userIndex = Integer.valueOf(session.getAttribute("index")
				.toString());
		// 그룹인덱스
		String groupIndex = (String) multi.getParameter("groupIndex");
		// 주소록을 저장할 리스트
		try {
			Enumeration files = multi.getFileNames(); // 전송된 파일 타입의 파라미터 이름들을
														// Enumeration타입으로 반환한다.
			String file = (String) files.nextElement();
			fileName = multi.getFilesystemName(file);
			// 파일 확장자 체크
			if (!fileExtCheck(response, fileName)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("엑셀파일 에러 : " + e.getMessage());
			return null;
		}
		
		String FileAddress = uploadPath + File.separator + fileName;
		// 주소록을 저장할 리스트
		List<AddressBean> list  = getAddressFromExcel(groupIndex, FileAddress);
		// 주소록이 있으면 디비에 저장후 저장한 갯수 얻기
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
			out.println("alert('" + addedCount + "개의 주소록을 추가하였습니다.');");
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

	/**
	 * 엑셀 파일에서 주소록 뽑아오기
	 * @param groupIndex
	 * @param FileAddress
	 */
	private List<AddressBean> getAddressFromExcel(String groupIndex, String FileAddress) {
		ArrayList<AddressBean> list = null;
		try {
			// 엑셀 파일 불러오기
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					FileAddress));
			// 워크북을 생성!
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			list = new ArrayList<AddressBean>();
			// 최대 엑셀 row 허용범위를 넘었을 경우
			if(rows >= MAX_ALLOW_ROW){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('엑셀 주소록 최대 추가 갯수는 " + 
							MAX_ALLOW_ROW + "개미만으로 해야 합니다.');");
				out.println("</script>");				
				return null;
			}
			
			for (int r = 0; r < rows; r++) {
				// 시트에 대한 행을 하나씩 추출
				HSSFRow row = sheet.getRow(r);
				System.out.println("dd");
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					// 주소록 bean
					AddressBean data = new AddressBean();
					data.setGroupIndex(Integer.valueOf(groupIndex));
					for (short c = 0; c < cells; c++) {
						System.out.println("ff");
						// 행에대한 셀을 하나씩 추출하여 셀 타입에 따라 처리
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

							if (c == NAME_CMN) { // 이름 칼럼
								data.setPeople(value);
								System.out.println("name : " + value);
							} else if (c == PHONE_CMN) { // 전화번호 칼럼
								value = value.trim().replaceAll("-", "");
								data.setPhone(value);
								list.add(data); // 리스트에 담기
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
	 * 파일 확장자 체크
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean fileExtCheck(HttpServletResponse response, String fileName)
			throws IOException {
		// 파일형식이 xls경우만 체크
		if (fileName.toLowerCase().endsWith(".xls")) {
			return true;
		}

		response.setContentType("text/html;charset=euc-kr");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('정확한 엑셀파일(xls)을 등록하세요.');");
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
