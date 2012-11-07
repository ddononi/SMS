package kr.go.police.board;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *	�Խ��� bean
 */
public class BoardBean {
	private int num;						//	��ȣ
	private String registerName;		//	�����
	private String title;					//	����
	private String content;				//	����
	private String filename;			//	÷������
	private int viewCount;				//	�� ī��Ʈ
	private boolean notice;			//	��������
	private int index;						//  �Խ��� �ε�����ȣ
	private int parentIndex;			// ����ϰ�� �θ� �ε���
	private String regDate;				// ��� ��¥
	private String modiDate;			// ���� ��¥
	private int regUserIndex;			// ����� �ε���	
	private String pwd;					// ��й�ȣ
	private boolean newIcon = false;			// �ű� ��Ͽ���
	private boolean hasFile;			// ���Ͽ���
	
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public boolean isNotice() {
		return notice;
	}

	public void setNotice(boolean isNotice) {
		this.notice = isNotice;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	public String getRegDate() {
		return regDate.substring(0, 10);
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
		setNewIcon();
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public int getRegUserIndex() {
		return regUserIndex;
	}

	public void setRegUserIndex(int regUserIndex) {
		this.regUserIndex = regUserIndex;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * �ű� �� ������� �Ǻ�
	 * 7���̳� ����̸� �űԷ� ó��
	 * @return
	 */
	public boolean setNewIcon() {
		if(this.regDate == null){
			return false;
		}
		// ��ϳ�¥ ����
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(regDate.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(regDate.substring(5, 7)) -1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(regDate.substring(8, 10)));
		// ���� ��¥���� 7���� ���ش�.
		Calendar currentCal = Calendar.getInstance();
		currentCal.add(Calendar.DAY_OF_MONTH, -7);
		// ���� ��¥���� ��� ��¥�� ū�� ���Ѵ�.
		if(cal.getTimeInMillis() - currentCal.getTimeInMillis() > 0){
			 newIcon = true;			
			 return true;
		}
		return false;
	}

	public boolean isHasFile() {
		return filename != null && filename.length() > 0;
	}

	public boolean isNewIcon() {
		return newIcon;
	}

	public void setNewIcon(boolean newIcon) {
		this.newIcon = newIcon;
	}

}
