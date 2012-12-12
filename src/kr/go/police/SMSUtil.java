package kr.go.police;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSUtil {

	/**
	 * �ٻ縦 ����Ʈ�� ��ȯ
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
	 * ����Ʈ�� �ٻ�� ��ȯ
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
	 * ����Ʈ���� ��Ʈ������ ��ȯ
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
	 * �Խ��� ���������̼� �����
	 * 
	 * @param count
	 *            �Խù� �Ѱ���
	 * @param currentPage
	 *            ���� ������
	 * @param pageSize
	 *            ������ ũ��
	 * @param url
	 *            ��ũ �ּ�
	 * @param params
	 *            url �Ķ����
	 * @return ���������̼� html
	 */
	public static String makePagiNation(int count, int currentPage,
			int pageSize, String url, String params) {
		StringBuffer sb = new StringBuffer();
		String currentClass = ""; // ���� ������ ���� Ŭ����
		// ������ ����
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// ����������
		int startPage = (int) ((currentPage -1) / 10) * 10 + 1;
		int pageBlock = 10;
		int endPage = startPage + pageBlock - 1;

		if (endPage > pageCount) {
			endPage = pageCount;
		}

		// �Ķ���Ͱ� ������
		if (params != null && params.length() > 0) {
			params = "&" + params;
		} else {
			params = "";
		}
		sb.append("<div class=\"page\">");
		// �������������� ���� 10�� �������� ������ 10�������� ������
		if (startPage > 10) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (startPage - 10)
					+ params
					+ "\"><img src=\"images/notice/pagefirst_next_btn.gif\" /></a>\r\n");
		}

		// ����������
		if (currentPage > 1) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (currentPage - 1)
					+ params
					+ "\"><img src=\"images/notice/page_prev_btn.gif\" /></a>\r\n");
		}

		// ������ ���ó��
		for (int i = startPage; i <= endPage; i++) {
			currentClass = (currentPage == i) ? "class=\"current\" " : "";// ����
																			// �������̸�
																			// ����
			sb.append("<a  href=\"" + url + "?page=" + i + params
					+ "\"><span  " + currentClass + ">" + i + "</span></a>");
		}

		// ���� �������� ������ ���� �������� �����ش�.
		if (currentPage < pageCount) {
			sb.append("<a href=\""
					+ url
					+ "?page="
					+ (currentPage + 1)
					+ params
					+ "\"><img src=\"images/notice/page_next_btn.gif\" /></a>\r\n");
		}

		// �������������� 10���������� ������ 10���ڷ�
		if (endPage < pageCount) {
			sb.append("<a href=\"" + url + "?page=" + (startPage + 10)
					+ "\"><img src=\"images/notice/pageend_next_btn.gif\" /></a>");
		}
		sb.append("</div>");

		return sb.toString();
	}

	/**
	 * ���ڿ����� ���ڸ� ���͸�
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
	 * html ���� �ϱ�
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
	 * Replace String str���� rep�� �ش��ϴ� String�� tok�� replace
	 * 
	 * @param str
	 *            ��ü�� ���ڸ� ������ ��
	 * @param regex
	 *            ��ü�� ����
	 * @param replacement
	 *            rep�� ��ü�� ����
	 * @return rep�� ���� replacement�� �ٲ� ��Ʈ������ ��ȯ�Ѵ�.
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
	 * parseHtmlString �� �ٲپ��� Ư�� ���� ���� �ٽ� ���� ���ڷ� �ٲپ� �ִ� �޼ҵ�
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
	 * �α׿� ��� �����
	 */
	synchronized public static void writerToLogFile(String logPath ,String msg){
		Date date = new Date();										// ���� �ð� ���
		String yearDir = checkYeaDir(logPath, date);						// �⵵ üũ
		String monthDir = checkMonthDir(logPath ,date, yearDir);		// �� üũ
		// �α����ϸ� ����
		String dir = logPath + File.separator + yearDir + File.separator + monthDir;
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd");	
		File file = new File(dir +  File.separator + "sms_" + sdf.format(date)+ ".txt" );
		FileWriter fw = null;
		try {
			fw = new FileWriter(file ,true);
			// �α� ���Ŀ� ���� ó��
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
	 *	�� ���丮�� üũ���� �����丮�� ������ �����丮�� ���� 
	 * @param yearDir
	 * @return
	 */
	synchronized private static String checkMonthDir(String logPath, Date date, String yearDir) {
		SimpleDateFormat sdf;
		File file;
		// �� ���丮 ����
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

}
