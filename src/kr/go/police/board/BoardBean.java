package kr.go.police.board;

/**
 *	�Խ��� bean
 */
public class BoardBean {
	private int num;					//	��ȣ
	private String registerName;		//	�����
	private String title;					//	����
	private String content;				//	����
	private String filename;			//	÷������
	private int viewCount;				//	�� ī��Ʈ
	private boolean isNotice;			//	��������
	private int index;					//  �Խ��� �ε�����ȣ
	private int parentIndex;			// ����ϰ�� �θ� �ε���
	private String regDate;				// ��� ��¥
	private String modiDate;			// ���� ��¥
	private int regUserIndex;			// ����� �ε���	
	
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
		return isNotice;
	}

	public void setNotice(boolean isNotice) {
		this.isNotice = isNotice;
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
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
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
	
	
	
}
