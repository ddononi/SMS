package kr.co.police;

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
}
