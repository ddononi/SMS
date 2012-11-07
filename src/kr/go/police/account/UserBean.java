package kr.go.police.account;

/**
 *	���� ���� bean
 */
public class UserBean {
	private int index;	// ���� �ε���
	private String id; // ���̵�
	private String pwd; // ��й�ȣ
	private String psName; // ��������
	private String grade; // ���
	private String name; // �̸�
	private String phone1; // �޴��� ��ȣ
	private String phone2;
	private String deptName; // �μ���
	private int deptCode; // �μ��ڵ�
	private String tel; // ��ȭ��ȣ
	private String email; // �̸���
	private boolean approve; // ���ο���
	private String regDate; // ��ϳ�¥
	private int totalSendCount; // �߼� Ƚ��
	private int monthSendLimit; // �� �߼� ���� ��
	private int monthSend; // �� �߼� Ƚ��
	private int userClass;	// ����� ���
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPsName() {
		return psName;
	}

	public void setPsName(String psName) {
		this.psName = psName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone1() {
		return phone1;
	}
	
	/**	 
	 * ��ȭ��ȣ ù��°
	 * @return
	 */
	public String getPhoneTop1() {
		return phone1.substring(0, 3);
	}	
	
	/**
	 * ��ȭ��ȣ �߰�
	 * @return
	 */
	public String getPhoneMiddle1() {
		return phone1.substring(3, 7);
	}	
	
	/**
	 * ��ȭ��ȣ ������
	 * @return
	 */
	public String getPhoneBottom1() {
		return phone1.substring(7);
	}		
	

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(int deptCode) {
		this.deptCode = deptCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getTotalSendCount() {
		return totalSendCount;
	}

	public void setTotalSendCount(int totalSendCount) {
		this.totalSendCount = totalSendCount;
	}

	public int getMonthSendLimit() {
		return monthSendLimit;
	}

	public void setMonthSendLimit(int monthSendLimit) {
		this.monthSendLimit = monthSendLimit;
	}

	public int getMonthSend() {
		return monthSend;
	}

	public void setMonthSend(int monthSend) {
		this.monthSend = monthSend;
	}

	public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}

}
