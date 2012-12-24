package kr.go.police.sms;

/**
 * ���� ���� dto
 */
public class SMSBean {
	private long index; // �ε���
	private int userIndex;
	private String id; // ���̵�
	private String deptCode; // �μ��ڵ�
	private String toPhone;	// �޴� ��ȭ��ȣ 
	private String fromPhone;  // ������ ��ȭ��ȣ
	private String message; // �޽���
	private boolean sendState; // ���ۻ���
	private int requestResult; // ��û���۰���ڵ尪
	private int responseResult; // �������۰���ڵ尪
	private boolean resreved;		// ���࿩��
	private String reserveDate; // ����ð�
	private String callback; // �ݹ�
	private String deptName; // �μ���
	private String flag; // sms or mms
	private String regDate;			// �߼۵�� �ð�
	private String deliverTime;	// ����� ó�� ��� �ð�
	private String vender;			// ����� ����
	private String resultMsg;		// ��û��� �޼���
	private String file1;				// ÷�� ����1
	private String file2;				// ÷�� ����2
	private String file3;				// ÷�� ����3	
	
	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}
	
	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSendState() {
		return sendState;
	}

	public void setSendState(boolean sendState) {
		this.sendState = sendState;
	}

	public int getRequestResult() {
		return requestResult;
	}

	public void setRequestResult(int requestResult) {
		this.requestResult = requestResult;
	}

	public int getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(int responseResult) {
		this.responseResult = responseResult;
	}

	public boolean isResreved() {
		return resreved;
	}

	public void setReserved(boolean resreved) {
		this.resreved = resreved;
	}

	public String getReserveDate() {
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRegDate() {
		if(regDate != null){
			return regDate.substring(5, 16);
		}
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public String getFile3() {
		return file3;
	}

	public void setFile3(String file3) {
		this.file3 = file3;
	}

	public void setResreved(boolean resreved) {
		this.resreved = resreved;
	}

}
