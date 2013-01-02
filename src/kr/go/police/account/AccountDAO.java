package kr.go.police.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.go.police.CommonCon;
import kr.go.police.IGwConstant;
import kr.go.police.aria.Aria;

/**
 * ���� (login, regsiter etc)���� Dao
 */
public class AccountDAO extends CommonCon {
	DataSource dataSource;
	public ResultSet rs;
	public PreparedStatement pstmt;
	public Connection conn;
	
	// �α��� ���� ���°�
	public final static int CHECK_OK = 1;
	public final static int ERROR_ID = -1;
	public final static int CHECK_PWD = -2;
	
	public AccountDAO(){
		dataSource = getDataSource();
		if(dataSource == null){
			return;
		}
	}
	
	
	/**
	 * ���ҽ� ��ȯ ��ȯ ������� �ݾ��ش�.
	 */
	public void connClose() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}		
	
	/**	 
	 * �α��� ó��
	 * @return
	 */
	protected boolean loginUser(String id, String pwd) {
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM user_info WHERE f_id = ? AND f_password =password(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if(rs.next()){
				//	���� �α����̶�� ���ӽð� ������Ʈó��
				sql = "UPDATE user_info SET f_visit_date = now()" +
						" WHERE f_id = ? AND f_password =password(?) ";
				pstmt = conn.prepareStatement(sql);				
				pstmt.setString(1, id);		
				pstmt.setString(2, pwd);				
				pstmt.executeUpdate();
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("loginUser ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return false;
	}
	
	/**
	 * ȸ������ ó��
	 * ���Խ� �������� �̽������� ó������
	 * ���� �����ڰ� ���κ���ó���� ����Ҽ� �ֵ��� �Ѵ�.
	 * ȸ���� �����ϸ� �⺻������ �׷� �� �ּҷ� �⺻ �׷쵵 �����Ѵ�.
	 * @return
	 */
	protected boolean joinUser(UserBean data){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO user_info ( f_id, f_password, f_grade, f_name, f_phone1, f_deptname, f_email, " + 
								" f_approve, f_reg_date, f_psname, f_visit_date, f_pscode, f_deptcode, f_class, f_last_pwd_modify) " +
								"VALUES (?, password(?), ?, ?, ?, ?, ?, 'n', now(), ?, now(), ?, ?, ?, now() )";
			Aria aria = Aria.getInstance();	
			// ���� ����
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getId());	
			pstmt.setString(2, data.getPwd());	
			pstmt.setString(3, data.getGrade());					
			pstmt.setString(4, aria.encryptByte2HexStr(data.getName()));			
			pstmt.setString(5, aria.encryptByte2HexStr(data.getPhone1()));
			pstmt.setString(6, data.getDeptName());	
			pstmt.setString(7,  aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setString(8, data.getPsName());			
			pstmt.setInt(9, data.getPsCode());			
			pstmt.setInt(10, data.getDeptCode());	
			pstmt.setInt(11, data.getUserClass());				
			// update
			result = pstmt.executeUpdate();
			
			// ȸ������ ���и�
			if(result <= 0){
				return false;
			}
			
			//  ������ �⺻�׷� ���� �ϱ�
			sql = "INSERT INTO message_group (f_user_index, f_group) VALUES( " +
					"(SELECT f_index FROM user_info WHERE f_id = ?), '�⺻�׷�' )";		
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, data.getId());	
			pstmt.executeUpdate();
			//  �ּҷ� �⺻�׷� ���� �ϱ�
			sql = "INSERT INTO address_book_group (f_user_index, f_group) VALUES( " +
					"(SELECT f_index FROM user_info WHERE f_id = ?), '�⺻�׷�' )";		
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, data.getId());	
			pstmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("joinUser ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * ����� ���� ����
	 * @param data
	 * @return
	 *	����ó�� ����
	 */
	protected boolean modifyMyInfo(UserBean data){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE user_info SET f_name = ?, f_phone1 = ?," +
								" f_email = ?, f_grade = ?,  f_psname = ?, f_approve = ?, " +
								" f_deptname = ?, f_class = ?, f_pscode = ?, f_deptcode = ? WHERE f_index = ?";
			pstmt = conn.prepareStatement(sql);
			Aria aria = Aria.getInstance();			// ��ȣȭ ó��
			pstmt.setString(1, aria.encryptByte2HexStr(data.getName()));
			pstmt.setString(2, aria.encryptByte2HexStr(data.getPhone1()));
			pstmt.setString(3, aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setString(4, data.getGrade());		
			pstmt.setString(5, data.getPsName());		
			pstmt.setString(6, data.isApprove()?"y":"n");				
			pstmt.setString(7, data.getDeptName());					
			pstmt.setInt(8, data.getUserClass());
			pstmt.setInt(9, data.getPsCode());	
			pstmt.setInt(10, data.getDeptCode());				
			pstmt.setInt(11, data.getIndex());	
			
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyUserInfo ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**	 
	 * �н����� üũ
	 * @return
	 */
	protected boolean CheckPwd(int index, String pwd, String newPwd) {
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM user_info WHERE f_index = ? AND f_password =password(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if(rs.next()){
				//	��ȣ�� ���ٸ� �ش��ε����� ��ȣ�� ��ȣ���泯¥ �Է�
				sql = "UPDATE user_info SET f_last_pwd_modify = now(), f_password =password(?)" +
						" WHERE f_index = ?";
				pstmt = conn.prepareStatement(sql);	
				pstmt.setString(1, newPwd);
				pstmt.setInt(2, index);	
				pstmt.executeUpdate();
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("CheckPwd ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return false;
	}
	/**	 
	 * �н����� ������¥ üũ
	 * @return
	 */
	protected boolean PwdModifydate(String index) {
		int count;
		boolean result=false;
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE f_id = ? AND f_last_pwd_modify > NOW()- interval 3 month ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, index);
			rs = pstmt.executeQuery();
			if(rs.next()){				
				count = rs.getInt(1);
				if(count>0){			
				return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("CheckPwd ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return false;
	}
	
	
	/**
	 * �����ڸ�忡�� ����� ���� ����
	 * @param data
	 * @return
	 *	����ó�� ����
	 */
	protected boolean modifyUserInfoFromAdmin(UserBean data){
		Aria aria = Aria.getInstance();	
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE user_info SET" +
								" f_name = ?," +
								" f_deptname = ?," +
								" f_grade = ?," +								
								" f_phone1 = ?," +
								" f_email = ?, " +
								" f_class = ?, " +
								" f_approve = ?, " +	
								" f_psname = ?, " +									
								" f_pscode = ?, " +	
								" f_deptcode = ?, " +	
								" f_deptname = ? " +									
								" WHERE f_index = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aria.encryptByte2HexStr(data.getName()));
			pstmt.setString(2, data.getDeptName());		
			pstmt.setString(3, data.getGrade());			
			pstmt.setString(4, aria.encryptByte2HexStr(data.getPhone1()));			
			pstmt.setString(5, aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setInt(6, data.getUserClass());
			pstmt.setString(7, data.isApprove()?"y":"n");
			pstmt.setString(8, data.getPsName());				
			pstmt.setInt(9, data.getPsCode());	
			pstmt.setInt(10, data.getDeptCode());			
			pstmt.setString(11, data.getDeptName());				
			pstmt.setInt(12, data.getIndex());	
			
			// update
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyUserInfoFromAdmin ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		
	
	/**
	 * ����� ���� ó��
	 * @return
	 */

	protected boolean changeApproveUser(UserBean data, boolean approve){
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			// ����ڰ� ����̳� ��Ÿ ������ ����Ȯ�ϰ� �Է��Ҽ� �����Ƿ� �����ڰ� ���� ó���� �ϸ鼭
			// ����� ������ �����Ͽ� �����Ҽ� �ֵ��� �Ѵ�.
			String sql = "UPDATE user_info SET f_grade = ?, f_phone1 = ?, f_deptname = ?, f_psname = ?, " +
								"f_arrpove = ?, f_send_limit = ?, f_class = ? WHERE f_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, data.getGrade());					
			pstmt.setString(2, data.getPhone1());
			pstmt.setString(3, data.getDeptName());	
			pstmt.setString(4, data.getPsName());	
			pstmt.setString(5, data.getPsName());				
			pstmt.setString(6, approve?"y":"n");
			pstmt.setInt(7, data.getUserClass());						
			pstmt.setString(8, data.getId());				
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("approveUser ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * ���� ���̵� �ߺ� üũ
	 * @return
	 */
	protected boolean checkDupleUserId(String userId){
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE f_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) > 0){
					return false;
				}else{
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("checkDupleUserId ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
		return false;
	}
	

	/**
	 *  ����� ����
	 * @param index
	 * 	����� �ε���
	 * @return
	 * 	���� ����
	 */
	protected boolean deleteUser(int index){
		int result = 0;
		try{
			conn = dataSource.getConnection();
			String sql = "DELETE FROM user_info WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteUser ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 *	���������ϱ�
	 * @param psCode 
	 * 	������ �ڵ�
	 * @return
	 * 	������
	 */
	protected int getUserListCount(int psCode,String type, String search){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE ";
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_phone1 like ? ";
			}
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��
			if(psCode != 100){
				sql += " AND f_pscode = " + psCode;
			}			
			pstmt = conn.prepareStatement(sql);
			
			//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
			Aria aria = Aria.getInstance();				
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserListCount ���� : " +  e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
	/**
	 * 	�ű� ���� ���� ��ϼ�
	 */
	public int getRecentJoinUserSize(){
		int size = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) as cnt FROM user_info " +
					" WHERE f_reg_date > SUBDATE(now(), 7) ");
			rs = pstmt.executeQuery();
			if(rs.next())	{
				size =  rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getRecentJoinUserSize ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return size;
	}	
	
	/**
	 * 	�ε����� �������� ���� ��������
	 * @param index
	 * @return
	 */
	protected UserBean getUserDetail(int index){
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE f_index = ? ");
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			
			if(rs.next())	{
				Aria aria = Aria.getInstance();					
				// ����������� ��� ���� ������ �����´�.
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  	
			    //data.setDeptCode(deptCode);
			    data.setEmail(aria.encryptHexStr2DecryptStr(rs.getString("f_email")));
			    data.setMonthSend(rs.getInt("f_month_send"));
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));			    
			    data.setRegDate(rs.getString("f_reg_date"));
			    data.setApprove(rs.getString("f_approve").equalsIgnoreCase("y"));
			    data.setTotalSendCount(rs.getInt("f_total_send"));
			    data.setMonthSendLimit(rs.getInt("f_send_limit"));
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setUserClass(rs.getInt("f_class"));
			    data.setPsCode(rs.getInt("f_pscode"));
			    data.setDeptCode(rs.getInt("f_deptcode"));
			    data.setPwdreset(rs.getString("f_last_pwd_modify"));
			    System.out.println(data.getName());
			}		
			
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserDetail ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	

	/**
	 *	����� ���� ���� ���� Ȯ��
	 * @param id
	 * @return
	 */
	public boolean checkApprove(String id) {
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT f_approve FROM user_info WHERE f_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(1).equalsIgnoreCase("y"))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("checkDupleUserId ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
		return false;
	}

	/**
	 * 	���̵�� ����� ���� ��������
	 * @param id
	 * @return
	 */
	public UserBean getUserInfo(String id) {
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE f_id = ? ");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next())	{
				Aria aria = Aria.getInstance();	
				// ����������� ��� ���� ������ �����´�.
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  	
			    //data.setDeptCode(deptCode);
			    data.setEmail(aria.encryptHexStr2DecryptStr(rs.getString("f_email")));
			    data.setMonthSend(rs.getInt("f_month_send"));
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));
			    data.setRegDate(rs.getString("f_reg_date"));
			    data.setApprove(rs.getString("f_approve").equalsIgnoreCase("y"));
			    data.setTotalSendCount(rs.getInt("f_total_send"));
			    data.setMonthSendLimit(rs.getInt("f_send_limit"));
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setPsCode(rs.getInt("f_pscode"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setDeptCode(rs.getInt("f_deptcode"));			    
			    data.setUserClass(rs.getInt("f_class"));
			}		
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserInfo ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	/**
	 *	�̽��� ���������ϱ�
	 * @param psCode 
	 * 		������ �ڵ�
	 * @return
	 * 	������
	 */
	protected int getArvListCount(int psCode, String type, String search){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE f_approve='n' AND ";
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_phone1 like ? ";
			}
			String psWhat = "";
			if(psCode != 100){
				psWhat = " AND f_pscode = " + psCode;
			}			
			pstmt = conn.prepareStatement(sql+psWhat);
			//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
			Aria aria = Aria.getInstance();				
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getArvListCount ���� : " +  e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}
	
	/**
	 *	�޸����������ϱ�
	 * @param psCode 
	 * @return
	 * 	������
	 */
	protected int getQuserListCount(int psCode, String type, String search){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE f_visit_date < NOW()- interval 3 month AND ";
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�
				sql += " f_phone1 like ? ";
			}
			String psWhat = "";
			if(psCode != 100){
				psWhat = " AND f_pscode = " + psCode;
			}			
			pstmt = conn.prepareStatement(sql+psWhat);
			//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
			Aria aria = Aria.getInstance();				
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getQuserListCount ���� : " +  e.getMessage());
		}finally{
			connClose();
		}
		return count;
	}

	/**
	 * ���� ��� ��������
	 * @param search
	 * 		�˻���
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ��ȣ
	 * @param psCode 
	 * 		������ �ڵ�
	 * @param type
	 * 		�˻�Ÿ��
	 * @return
	 */
	protected List<UserBean> getUserList(String search, int start, int end, int psCode, String type){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			Aria aria = Aria.getInstance();	
			String sql = "SELECT * FROM user_info WHERE ";
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�				
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�				
				sql += " f_phone1 like ? ";
			}
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��
			if(psCode != 100){
				sql += " AND f_pscode = " + psCode;
			}
			
			pstmt = conn.prepareStatement(sql+ " ORDER BY f_index DESC LIMIT ?, ? ");			
			
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");			
			pstmt.setInt(2, start -1);	
			pstmt.setInt(3, end);			
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  			    
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setUserClass(rs.getInt("f_class"));
			    data.setApprove(rs.getString("f_approve").equalsIgnoreCase("y"));			    
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	
	/**
	 * �̽��� ���� ��� ��������
	 * @param search
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ��ȣ
	 * @return
	 */	
	protected List<UserBean> getArvList(String search, int start, int end, int psCode, String type){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM user_info WHERE f_approve='n' AND ";
			Aria aria = Aria.getInstance();	
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�				
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�				
				sql += " f_phone1 like ? ";
			}
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��			
			if(psCode != 100){
				sql += " AND f_pscode = " + psCode;
			}			
			pstmt = conn.prepareStatement(sql+" ORDER BY f_index DESC LIMIT ?, ?");
			
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);	
			pstmt.setInt(3, end);			
			rs = pstmt.executeQuery();

			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  			    
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setUserClass(rs.getInt("f_class"));
			    data.setRegDate(rs.getString("f_reg_date"));			    
			    data.setApprove(rs.getString("f_approve").equalsIgnoreCase("y"));			    
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getArvList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	
	/**
	 * �޸���� ��� ��������
	 * @param search
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ��ȣ
	 * @param psCode 
	 * 		������ �ڵ�
	 * @return
	 */	
	protected List<UserBean> getQuserList(String search, int start, int end, int psCode, String type){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;	
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM user_info WHERE f_visit_date < NOW() - interval 3 month AND ";
			Aria aria = Aria.getInstance();	
			if(type.equalsIgnoreCase("name")){	// �̸����� �˻�				
				sql += "  f_name like ? ";
			}else if(type.equalsIgnoreCase("id")){		// ���̵�� �˻�
				sql += " f_id like ? ";
			}else if(type.equalsIgnoreCase("police")){		// ������������ �˻�
				sql += " f_psname like ? ";
			}else if(type.equalsIgnoreCase("department")){		// �μ������� �˻�
				sql += " f_deptname like ? ";
			}else if(type.equalsIgnoreCase("grade")){		// ������� �˻�
				sql += " f_grade like ? ";
			}else{			// �޴� ��ȭ��ȣ�� �˻�				
				sql += " f_phone1 like ? ";
			}
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��			
			if(psCode != 100){
				sql += " AND f_pscode = " + psCode;
			}			
			pstmt = conn.prepareStatement(sql+" ORDER BY f_index DESC LIMIT ?, ?");
			
			if(type.equalsIgnoreCase("name") || type.equalsIgnoreCase("phone"))	//�̸��̳� ��ȭ��ȣ �˻��� �˻�� ��ȣȭ ó��
				search=aria.encryptByte2HexStr(search);
			
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setInt(2, start -1);	
			pstmt.setInt(3, end);			
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  			    
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setUserClass(rs.getInt("f_class"));
			    data.setVisitDate(rs.getString("f_visit_date"));			    
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getQuserList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}

	/**
	 * �ߺ� ���̵� �������� ���� üũ
	 * @param id
	 * @return 
	 */
	public boolean sessionCheck(String userId, String sessionId) {
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) FROM user_info WHERE f_id = ? AND f_session_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, sessionId);			
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) > 0){
					return true;
				}else{
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sessionCheck ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		return false;
	}

	/**
	 * �ߺ� �α��ι����� ���� ���� ���� ���̵� ������Ʈ
	 * @param sessionId
	 * 		���� ���̵�
	 * @param id
	 * 		���� ���̵�
	 */
	public void updateUserSession(String sessionId, String id) {
		try {
			conn = dataSource.getConnection();
			// ����ڰ� ����̳� ��Ÿ ������ ����Ȯ�ϰ� �Է��Ҽ� �����Ƿ� �����ڰ� ���� ó���� �ϸ鼭
			// ����� ������ �����Ͽ� �����Ҽ� �ֵ��� �Ѵ�.
			String sql = "UPDATE user_info SET f_session_id =? WHERE f_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sessionId);				
			pstmt.setString(2, id);				
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateUserSession ���� : " + e.getMessage());
		}finally{
			connClose();
		}
	}		
	
	/**
	 * ������ ��� ��������
	 */
	public List<PoliceBean> getPsList(){
		ResultSet subRs = null;				
		PreparedStatement subPstmt = null;		
		List<PoliceBean> list = new ArrayList<PoliceBean>();
		PoliceBean data = null;	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM pscode ORDER BY f_pscode ASC "); 
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new PoliceBean();	
			    data.setCode(rs.getInt("f_pscode"));
			    data.setName(rs.getString("f_psname"));	
			    // �ش� ���� �μ� ��������
				subPstmt = conn.prepareStatement("SELECT * FROM organ WHERE" +
						" f_pscode = ? ORDER BY f_deptcode DESC ");
				subPstmt.setInt(1, data.getCode());
				subRs = subPstmt.executeQuery();
				ArrayList<DeptBean> deptList = new ArrayList<DeptBean>();
				DeptBean deptData;
				while(subRs.next()){
					deptData = new DeptBean();
					deptData.setPsCode(subRs.getInt("f_pscode"));					
					deptData.setDeptCode(subRs.getInt("f_deptcode"));
					deptData.setName(subRs.getString("f_deptname"));	
					deptList.add(deptData);
				}
				data.setList(deptList);
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getPsList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �������� ��ü �μ� ��� ��������
	 * @param psCode 
	 * 		������ �ڵ�
	 */
	public List<PoliceBean> getDeptList(){
		ResultSet subRs = null;				
		PreparedStatement subPstmt = null;		
		List<PoliceBean> list = new ArrayList<PoliceBean>();
		PoliceBean data = null;	
		try {
			conn = dataSource.getConnection();
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��
			String sql = "SELECT * FROM pscode WHERE 1 = 1 " 
							+ " ORDER BY f_pscode ASC ";
			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new PoliceBean();	
			    data.setCode(rs.getInt("f_pscode"));
			    data.setName(rs.getString("f_psname"));	
			    // �ش� ���� �μ� ��������
				subPstmt = conn.prepareStatement("SELECT * FROM organ WHERE" +
						" f_pscode = ? ORDER BY f_deptcode DESC ");
				subPstmt.setInt(1, data.getCode());
				subRs = subPstmt.executeQuery();
				ArrayList<DeptBean> deptList = new ArrayList<DeptBean>();
				DeptBean deptData;
				while(subRs.next()){
					deptData = new DeptBean();
					deptData.setPsCode(subRs.getInt("f_pscode"));					
					deptData.setDeptCode(subRs.getInt("f_deptcode"));
					deptData.setName(subRs.getString("f_deptname"));	
					deptList.add(deptData);
				}
				data.setList(deptList);
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getQuserList ���� : " + e.getMessage());
			return null;
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if (subRs != null) {
				try {
					subRs.close();
				} catch (SQLException e) {
				}
			}			

			if (subPstmt != null) {
				try {
					subPstmt.close();
				} catch (SQLException e) {
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}			
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}			

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}


	/**
	 * ������ �ڵ�� �ش� �μ� ��� ��������
	 * @param code
	 * 		������ �ڵ�
	 * @return
	 * 		�μ� ���
	 */
	public List<DeptBean> getSubDeptList(int code) {
		List<DeptBean> list = new ArrayList<DeptBean>();
		DeptBean data = null;	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM organ WHERE f_pscode = ?" +
								" ORDER BY f_deptcode ASC "); 
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			while(rs.next())	{
				data = new DeptBean();
				data.setPsCode(rs.getInt("f_pscode"));					
				data.setDeptCode(rs.getInt("f_deptcode"));
				data.setName(rs.getString("f_deptname"));	
				list.add(data);
  			}		
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getSubDeptList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	
	public List<UserBean> getUserListFromDept(int pscode, int deptcode){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			// �μ� ����
			String deptWhere = "";
			// �μ� ������ ������츸
			if(deptcode !=0){
				deptWhere = " AND f_deptcode = " + deptcode;
			} 
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE f_pscode = ? " + deptWhere);
			pstmt.setInt(1, pscode);
			rs = pstmt.executeQuery();
			while(rs.next())	{
				Aria aria = Aria.getInstance();					
				// ����������� ��� ���� ������ �����´�.
			    data = new UserBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  	
			    //data.setDeptCode(deptCode);
			    data.setEmail(aria.encryptHexStr2DecryptStr(rs.getString("f_email")));
			    data.setMonthSend(rs.getInt("f_month_send"));
			    data.setPhone1(aria.encryptHexStr2DecryptStr(rs.getString("f_phone1")));			    
			    data.setRegDate(rs.getString("f_reg_date"));
			    data.setApprove(rs.getString("f_approve").equalsIgnoreCase("y"));
			    data.setTotalSendCount(rs.getInt("f_total_send"));
			    data.setMonthSendLimit(rs.getInt("f_send_limit"));
			    data.setName(aria.encryptHexStr2DecryptStr(rs.getString("f_name")));
			    data.setPsName(rs.getString("f_psname"));
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
			    data.setUserClass(rs.getInt("f_class"));
			    data.setPsCode(rs.getInt("f_pscode"));
			    data.setDeptCode(rs.getInt("f_deptcode"));
			    list.add(data);
			}		
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getUserListFromDept ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}


	public List<PoliceBean> getPsDeptList(int psCode) {
		ResultSet subRs = null;				
		PreparedStatement subPstmt = null;		
		List<PoliceBean> list = new ArrayList<PoliceBean>();
		PoliceBean data = null;	
		try {
			conn = dataSource.getConnection();
			// ����û �����ڰ� �ƴϰ� �� ������ ������ �ϰ��
			String psWhat = "";
			if(psCode != 100){
				psWhat = " AND f_pscode = " + psCode;
			}				
			String sql = "SELECT * FROM pscode WHERE 1 = 1 " 
							+ psWhat + " ORDER BY f_pscode ASC ";
			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			while(rs.next())	{
				// �ε���, ���̵�, �̸�, ��������, ���, �μ���, ���
			    data = new PoliceBean();	
			    data.setCode(rs.getInt("f_pscode"));
			    data.setName(rs.getString("f_psname"));	
			    // �ش� ���� �μ� ��������
				subPstmt = conn.prepareStatement("SELECT * FROM organ WHERE" +
						" f_pscode = ? ORDER BY f_deptcode DESC ");
				subPstmt.setInt(1, data.getCode());
				subRs = subPstmt.executeQuery();
				ArrayList<DeptBean> deptList = new ArrayList<DeptBean>();
				DeptBean deptData;
				while(subRs.next()){
					deptData = new DeptBean();
					deptData.setPsCode(subRs.getInt("f_pscode"));					
					deptData.setDeptCode(subRs.getInt("f_deptcode"));
					deptData.setName(subRs.getString("f_deptname"));	
					deptList.add(deptData);
				}
				data.setList(deptList);
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getQuserList ���� : " + e.getMessage());
			return null;
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if (subRs != null) {
				try {
					subRs.close();
				} catch (SQLException e) {
				}
			}			

			if (subPstmt != null) {
				try {
					subPstmt.close();
				} catch (SQLException e) {
				}
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}			
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}			

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * �н����� ���� 
	 * @param data
	 * @return
	 *	���� ����
	 */
	protected boolean Passwordchange(String pwd, int index){
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE user_info SET" +
								" f_last_pwd_modify = now()," +
								" f_password = password(?) " +
								"WHERE f_index = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, IGwConstant.PWD_SALT  + pwd.trim() + IGwConstant.PWD_SALT );		
			pstmt.setInt(2, index);
			// update
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Passwordchange ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}


	/**
	 * ȸ�� Ż�� ó��
	 * @param userIndex
	 * 		���� �ε���
	 * @param pwd
	 * 		Ȯ���� ��й�ȣ
	 * @return
	 */
	public boolean dropout(int userIndex, String pwd) {
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			// ȸ���� �´��� ����
			String sql = "SELECT count(*) FROM user_info WHERE f_index = ? AND f_password = password(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);
			pstmt.setString(2, IGwConstant.PWD_SALT +  pwd + IGwConstant.PWD_SALT);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) <= 0)
					return false;
			}			
			
			pstmt.close();
			rs.close();
			// �ٽ� �̽��� ó��
			sql = "UPDATE user_info SET f_approve = 'n' WHERE f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);		
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("dropout ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
		
	}		
	
}
