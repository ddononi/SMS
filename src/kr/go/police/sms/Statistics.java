package kr.go.police.sms;

/*
 * ���
 * 
 */
public class Statistics {

	private int jan;							// 1��
	private int feb;							// 2��
	private int mar;						// 3��
	private int apr;							// 4��
	private int may;						// 5��
	private int jun;							// 6��
	private int jul;							// 7��
	private int aug;						// 8��
	private int sep;							// 9��
	private int oct;							// 10��
	private int nov;						// 11��
	private int dec;						// 12��
	private int total;						// �� ������
	private int pscode;					// ������ �ڵ�
	private int deptcode;				// �μ� �ڵ�
	private int index;						//	�ε���	
	private String name;				// �̸�
	private String mindate;			// ���� �������� ���� ��¥
	private String maxdate;		// ���� �ֱٿ� ���� ��¥
	private String id;						// ���� id
	private String psname;			//	������ �̸�
	private String deptname;		// �μ� �̸�
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getPsname() {
		return psname;
	}
	public void setPsname(String psname) {
		this.psname = psname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMindate() {
		return mindate;
	}
	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	public String getMaxdate() {
		return maxdate;
	}
	public void setMaxdate(String maxdate) {
		this.maxdate = maxdate;
	}
	public int getPscode() {
		return pscode;
	}
	public void setPscode(int pscode) {
		this.pscode = pscode;
	}
	public int getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(int deptcode) {
		this.deptcode = deptcode;
	}
	public int getJan() {
		return jan;
	}
	public void setJan(int jan) {
		this.jan = jan;
	}
	public int getFeb() {
		return feb;
	}
	public void setFeb(int feb) {
		this.feb = feb;
	}
	public int getMar() {
		return mar;
	}
	public void setMar(int mar) {
		this.mar = mar;
	}
	public int getApr() {
		return apr;
	}
	public void setApr(int apr) {
		this.apr = apr;
	}
	public int getMay() {
		return may;
	}
	public void setMay(int may) {
		this.may = may;
	}
	public int getJun() {
		return jun;
	}
	public void setJun(int jun) {
		this.jun = jun;
	}
	public int getJul() {
		return jul;
	}
	public void setJul(int jul) {
		this.jul = jul;
	}
	public int getAug() {
		return aug;
	}
	public void setAug(int aug) {
		this.aug = aug;
	}
	public int getSep() {
		return sep;
	}
	public void setSep(int sep) {
		this.sep = sep;
	}
	public int getOct() {
		return oct;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOct(int oct) {
		this.oct = oct;
	}
	public int getNov() {
		return nov;
	}
	public void setNov(int nov) {
		this.nov = nov;
	}
	public int getDec() {
		return dec;
	}
	public void setDec(int dec) {
		this.dec = dec;
	}
	public int getTotal() {
		total = jan+feb+mar+apr+may+jun+jul+aug+sep+oct+nov+dec;
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
