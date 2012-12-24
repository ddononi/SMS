package kr.go.police.account;

import java.util.ArrayList;

public class DeptBean {
	private int psCode; // 경찰서 코드
	private int deptCode; // 부서코드
	private String name; // 경찰서명 혹은 부서명

	public int getPsCode() {
		return psCode;
	}

	public void setPsCode(int psCode) {
		this.psCode = psCode;
	}

	public int getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(int deptCode) {
		this.deptCode = deptCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
