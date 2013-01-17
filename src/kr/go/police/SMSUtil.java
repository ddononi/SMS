package kr.go.police;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSUtil {

	/**
	 * 핵사를 바이트로 변환
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer
					.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	/**
	 * 바이트를 핵사로 변환
	 * 
	 * @param ba
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	/**
	 * 바이트에서 스트링으로 변환
	 * 
	 * @param byteInput
	 * @return
	 */
	public static String getStringFromByte(final byte[] byteInput) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteInput.length; i++) {
			// Processing Korean
			if ((byteInput[i] & 0x80) == 0x80) {
				byte[] byteKorean = new byte[2];
				byteKorean[0] = byteInput[i];
				byteKorean[1] = byteInput[i++];
				sb.append(new String(byteKorean));
			} else if ((byteInput[i] & 0xFF) == 0) {

			} else {
				sb.append((char) byteInput[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 게시판 페이지네이션 만들기
	 * 
	 * @param count
	 *            게시물 총갯수
	 * @param currentPage
	 *            현재 페이지
	 * @param pageSize
	 *            페이지 크기
	 * @param url
	 *            링크 주소
	 * @param params
	 *            url 파라미터
	 * @return 페이지네이션 html
	 */
	public static String makePagiNation(int count, int currentPage,
			int pageSize, String url, String params) {
		StringBuffer sb = new StringBuffer();
		String currentClass = ""; // 현재 페이지 강조 클래슨
		// 페이지 갯수
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 시작페이지
		int startPage = (int) ((currentPage -1) / 10) * 10 + 1;
		int pageBlock = 10;
		int endPage = startPage + pageBlock - 1;

		if (endPage > pageCount) {
			endPage = pageCount;
		}

		// 파라미터가 있으면
		if (params != null && params.length() > 0) {
			params = "&" + params;
		} else {
			params = "";
		}
		sb.append("<div class=\"page\">");
		// 현재페이지에서 이전 10개 페이지가 있으면 10개페이지 앞으로
		if (startPage > 10) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (startPage - 10)
					+ params
					+ "\"><img src=\"images/notice/pagefirst_next_btn.gif\" /></a>\r\n");
		}

		// 이전페이지
		if (currentPage > 1) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (currentPage - 1)
					+ params
					+ "\"><img src=\"images/notice/page_prev_btn.gif\" /></a>\r\n");
		}

		// 페이지 목록처리
		for (int i = startPage; i <= endPage; i++) {
			currentClass = (currentPage == i) ? "class=\"current\" " : "";// 현재
																			// 페이지이면
																			// 강조
			sb.append("<a  href=\"" + url + "?page=" + i + params
					+ "\"><span  " + currentClass + ">" + i + "</span></a>");
		}

		// 다음 페이지가 있으면 다음 페이지를 보여준다.
		if (currentPage < pageCount) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (currentPage + 1)
					+ params
					+ "\"><img src=\"images/notice/page_next_btn.gif\" /></a>\r\n");
		}

		// 현재페이지에서 10개페이지가 넘으면 10개뒤로
		if (endPage < pageCount) {
			sb.append("<a href=\"" 
					+ url
					+ "?page="
					+ (startPage + 10)
					+ params					
					+ "\"><img src=\"images/notice/pageend_next_btn.gif\" /></a>");
		}
		sb.append("</div>");

		return sb.toString();
	}

	/**
	 * 문자열에서 숫자만 필터링
	 * 
	 * @param str
	 * @return
	 */
	public static String getOnlyNumberString(String str) {
		if (str == null)
			return str;

		StringBuffer sb = new StringBuffer();
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char curChar = str.charAt(i);
			if (Character.isDigit(curChar))
				sb.append(curChar);
		}
		return sb.toString();
	}

	/**
	 * html 제거 하기
	 * 
	 * @param htmlString
	 * @return
	 */
	public static String removeHTML(String htmlString) {
		// Remove HTML tag from java String
		String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");
		// Remove Carriage return from java String
		noHTMLString = noHTMLString.replaceAll("\r", "<br/>");
		// Remove New line from java string and replace html break
		noHTMLString = noHTMLString.replaceAll("\n", " ");
		noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
		noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
		return noHTMLString;
	}

	/**
	 * Replace String str에서 rep에 해당하는 String을 tok로 replace
	 * 
	 * @param str
	 *            대체될 문자를 포함한 값
	 * @param regex
	 *            대체할 문자
	 * @param replacement
	 *            rep가 대체된 문자
	 * @return rep의 값이 replacement로 바뀐 스트링값을 반환한다.
	 */
	public static String getReplaceALL(String str, String regex,
			String replacement) {
		String retStr = "";
		if (str == null || (str != null && str.length() < 1)) {
			return "";
		}
		if (regex == null || (regex != null && regex.length() < 1)) {
			return str;
		}
		if ((str.indexOf(regex) == -1)) {
			return str;
		}
		for (int i = 0, j = 0; (j = str.indexOf(regex, i)) > -1; i = j
				+ regex.length()) {
			retStr += (str.substring(i, j) + replacement);
		}
		return retStr
				+ str.substring(str.lastIndexOf(regex) + regex.length(),
						str.length());
	}

	/**
	 * parseHtmlString 로 바꾸어준 특수 문자 값을 다시 원래 문자로 바꾸어 주는 메소드
	 * 
	 * @param strText
	 * @return
	 */
	public static String reverseHtmlString(String strText) {
		if (strText == null) {
			strText = "";
		}
		strText = getReplaceALL(strText, "&lt;", "<");
		strText = getReplaceALL(strText, "&gt;", ">");
		strText = getReplaceALL(strText, "&amp;", "&");
		strText = getReplaceALL(strText, "&#37;", (char) 37 + "");
		strText = getReplaceALL(strText, "&quot;", (char) 34 + "");
		strText = getReplaceALL(strText, "&#39;", (char) 39 + "");
		strText = getReplaceALL(strText, "&#35;", "\n");
		strText = getReplaceALL(strText, "\n", "<br>");
		strText = getReplaceALL(strText, " ", "&nbsp;");
		return strText;
	}
	
	/**
	 * 로그에 기록 남기기
	 */
	synchronized public static void writerToLogFile(String logPath ,String msg){
		Date date = new Date();										// 현재 시간 얻기
		String yearDir = checkYeaDir(logPath, date);						// 년도 체크
		String monthDir = checkMonthDir(logPath ,date, yearDir);		// 월 체크
		// 로그파일명 생성
		String dir = logPath + File.separator + yearDir + File.separator + monthDir;
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd");	
		File file = new File(dir +  File.separator + "sms_" + sdf.format(date)+ ".txt" );
		FileWriter fw = null;
		try {
			fw = new FileWriter(file ,true);
			// 로그 형식에 따라 처리
			sdf = new SimpleDateFormat("HH:mm:ss");
			file = new File(dir +  File.separator + sdf.format(new Date())+ ".txt" );			
			msg = "[" + sdf.format(date) +"] \t" + msg + "\r\n";
			System.out.println(msg);
			fw.append(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {}						
			}
		}
	}

	/**
	 *	월 디렉토리를 체크한후 월디렉토리가 없으면 월디렉토리를 생성 
	 * @param yearDir
	 * @return
	 */
	synchronized private static String checkMonthDir(String logPath, Date date, String yearDir) {
		SimpleDateFormat sdf;
		File file;
		// 월 디렉토리 생성
		sdf = new SimpleDateFormat("MM");			
		String monthDir = sdf.format(date);
		file = new File(logPath + File.separator + yearDir + File.separator + monthDir);
		if(!file.isDirectory()){
			file.mkdir();
		}
		return monthDir;
	}

	synchronized private static String checkYeaDir(String logPath, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");		
		String yearDir = sdf.format(date);
		File file = new File(logPath + File.separator + yearDir);
		if(!file.isDirectory()){
			file.mkdir();
		}
		return yearDir;
	}	

	
	/**
	 * 전화번호 하이픈 넣기
	 * @param phone
	 * @return
	 */
	public static String addPhoneHyphens(String phone){
		if(phone.isEmpty()){
			return "없음";
		}
		
		phone = phone.replaceAll("-", "").trim();
		if (phone.length() < 11 && phone.length()  > 7) {
			phone = phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-"
					+ phone.substring(6);
		} else if (phone.length()  >= 11) {
			phone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-"
					+ phone.substring(7);
		}else{
			phone =  "없음";
		}		
		
		return phone;
	} 
	
	//	join
	public static String implodeArray(String[] inputArray, String glueString) {
		/** Output variable */
		String output = "";
		if (inputArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(inputArray[0]);

			for (int i=1; i<inputArray.length; i++) {
				sb.append(glueString);
				sb.append(inputArray[i]);
			}

			output = sb.toString();
		}

		return output;
		}	
	
}
