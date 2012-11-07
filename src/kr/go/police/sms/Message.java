package kr.go.police.sms;

/**
 * ���� Dto
 */
/*
DROP TABLE IF EXISTS `sms2`.`send_sms_info`;
CREATE TABLE  `sms2`.`send_sms_info` (
  `f_index` int(10) unsigned NOT NULL auto_increment COMMENT '�ε���',
  `f_id` varchar(20) character set euckr default NULL COMMENT '�������̵�',
  `f_deptcode` varchar(5) character set euckr NOT NULL COMMENT '�μ��ڵ�',
  `f_to_phone` varchar(11) character set euckr NOT NULL COMMENT '�޴���ȭ��ȣ',
  `f_from_phone` varchar(11) character set euckr NOT NULL COMMENT '��������ȭ��ȣ',
  `f_message` varchar(255) character set euckr default NULL COMMENT '�޼���',
  `f_inwon` int(10) default NULL,
  `f_send_state` varchar(1) character set euckr NOT NULL default '',
  `f_send_result` varchar(8) character set euckr default NULL,
  `f_reserve_date` varchar(8) character set euckr default NULL,
  `f_reserve_time` varchar(4) character set euckr default NULL COMMENT '����ð�',
  `f_callback` varchar(11) character set euckr NOT NULL COMMENT '�ݹ���ȭ��ȣ',
  `f_deptname` varchar(45) default NULL COMMENT '�μ���',
  `f_mms` char(1) NOT NULL default 'n' COMMENT 'MMS ����',
  PRIMARY KEY  USING BTREE (`f_index`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
*/

public class Message {
	private String id; // ���� ���̵�
	private int index; // �޼��� �ε���
	private int userIndex; // ���� �ε���
	private String title; // ����
	private String message; // ����
	private String group; // �׷�
	private String groupIndex; // �׷� �ε���

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(String groupIndex) {
		this.groupIndex = groupIndex;
	}

}
