package kr.go.police.account;

import java.util.ArrayList;

public class PoliceBean {
	private int code;							// �ڵ�
	private String name;					// ��������
	private ArrayList<DeptBean> list;	// ���� �μ����
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<DeptBean> getList() {
		return list;
	}
	public void setList(ArrayList<DeptBean> list) {
		this.list = list;
	}
}
