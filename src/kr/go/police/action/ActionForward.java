package kr.go.police.action;

/**
 * ������ ������, action�������̽� ó��
 * @author juwan
 *
 */
public class ActionForward {
	/**
	 * �����̷�Ʈ ����
	 */
	private boolean isRedirect = false;
	/**
	 * �����̷�Ʈ path
	 */
	private String path = null;

	/**
	 * 
	 * @return
	 * 	�����̷�Ʈ ����
	 */
	public boolean isRedirect() {
		return isRedirect;
	}

	
	public String getPath() {
		return path;
	}

	public void setRedirect(boolean b) {
		isRedirect = b;
	}

	public void setPath(String string) {
		path = string;
	}
}