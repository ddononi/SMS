package kr.go.police;

public class SMSUtil {

	/**
	 * �ٻ縦 ����Ʈ�� ��ȯ
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
	 * @param count
	 * 		�Խù� �Ѱ���
	 * @param currentPage
	 * 		���� ������
	 * @param pageSize
	 * 		������ ũ��
	 * @param url
	 * 		��ũ �ּ�
	 * @param params
	 * 		url �Ķ���� 
	 * @return
	 * 		���������̼� html
	 */
	public static String makePagiNation(int count, int currentPage, int pageSize, String url, String params){
		StringBuffer sb = new StringBuffer();
		String currentClass = "";		// ���� ������ ���� Ŭ����
		// ������ ����
		int pageCount = count / pageSize + (count % pageSize ==0?0:1);
		// ����������
		int startPage = (int)(currentPage /10) * 10 + 1;
		int pageBlock = 10;
		int endPage = startPage + pageBlock - 1;
		
		if(endPage > pageCount){
			endPage = pageCount;
		}
		
		// �Ķ���Ͱ� ������
		if(params != null && params.length() > 0){
			params = "&" + params; 		
		}else{
			params = "";
		}
		sb.append("<div class=\"page\">");
		// �������������� ���� 10�� �������� ������ 10�������� ������		
		if(startPage > 10){
			sb.append("<a href=\"" +url +  "?page=" + (startPage - 10) +  params + "\"><img src=\"images/notice/page_prev_btn.gif\" /></a>\r\n");
		}
		
		// ����������
		if(currentPage > 1){
			sb.append("<a href=\"" +url +  "?page=" + (currentPage - 1) +  params + "\"><img src=\"images/notice/page_prev_btn.gif\" /></a>\r\n");
		}
		
		// ������ ���ó��
		for(int i= startPage; i <= endPage; i++){
			currentClass = (currentPage == i)?"class=\"current\" ":"";//	���� �������̸� ����
			sb.append("<a  href=\"" +url +  "?page=" +  i + params + "\"><span  " + currentClass + ">" + i + "</spa></a>");
		}
		
		// ���� �������� ������ ���� �������� �����ش�.
		if(currentPage < endPage){
			sb.append("<a href=\"" +url +  "?page=" + (currentPage + 1) +  params + "\"><img src=\"images/notice/page_next_btn.gif\" /></a>\r\n");
		}		
		
		// �������������� 10���������� ������ 10���ڷ�
		if(endPage < pageCount){
			sb.append("<a href=\"" +url +  "?page=" +  (startPage + 10) + "\"><img src=\"images/notice/page_next_btn.gif\" /></a>" );
		}
		sb.append("</div>");
		
		return sb.toString();
	}
}
