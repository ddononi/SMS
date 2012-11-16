package kr.go.police.address;

/**
 * �ּҷ� 
 */
public class AddressBean {
	private int index;					// �ε���
	private String people;			// �޴»���̸�
	private String phone;			// ��ȭ��ȣ
	private int groupIndex;			// �׷��ε���
	private String groupName;	// �׷��

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone.trim();
	}

	/**
	 * ��ȭ��ȣ ù��°
	 * 
	 * @return
	 */
	public String getPhoneTop() {
		return phone.substring(0, 3);
	}

	/**
	 * ��ȭ��ȣ �߰�
	 * 
	 * @return
	 */
	public String getPhoneMiddle() {
		return phone.substring(3, 7);
	}

	/**
	 * ��ȭ��ȣ ������
	 * 
	 * @return
	 */
	public String getPhoneBottom() {
		return phone.substring(7).trim();
	}
}
