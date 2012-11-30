package kr.go.police.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.go.police.CommonCon;
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
								" f_approve, f_reg_date, f_psname) VALUES (?, password(?), ?, ?, ?, ?, ?, 'n', now(), ? )";
			
			Aria aria = Aria.getInstance();	
			// �̸� ,��ȭ��ȣ, �̸���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getId());	
			pstmt.setString(2, data.getPwd());	
			pstmt.setString(3, data.getGrade());					
			pstmt.setString(4, aria.encryptByte2HexStr(data.getName()));			
			pstmt.setString(5, aria.encryptByte2HexStr(data.getPhone1()));
			pstmt.setString(6, data.getDeptName());	
			pstmt.setString(7,  aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setString(8, data.getPsName());				
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
			String sql = "UPDATE user_info SET f_name = ?, f_password = password(?), f_phone1 = ?," +
								" f_email = ?, f_grade = ?,  f_psname = ?, f_approve = 'n',  f_deptname = ?, f_class = ? WHERE f_index = ?";
			pstmt = conn.prepareStatement(sql);
			Aria aria = Aria.getInstance();			// ��ȣȭ ó��
			pstmt.setString(1, aria.encryptByte2HexStr(data.getName()));
			pstmt.setString(2, data.getPwd());				
			pstmt.setString(3, aria.encryptByte2HexStr(data.getPhone1()));
			pstmt.setString(4, aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setString(5, data.getGrade());		
			pstmt.setString(6, data.getPsName());		
			pstmt.setString(7, data.getDeptName());					
			pstmt.setInt(8, data.getUserClass());
			pstmt.setInt(9, data.getIndex());	
			
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
								" f_approve = ?" +								
								" WHERE f_index = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aria.encryptByte2HexStr(data.getName()));
			pstmt.setString(2, data.getDeptName());		
			pstmt.setString(3, data.getGrade());			
			pstmt.setString(4, aria.encryptByte2HexStr(data.getPhone1()));			
			pstmt.setString(5, aria.encryptByte2HexStr(data.getEmail()));	
			pstmt.setInt(6, data.getUserClass());
			pstmt.setString(7, data.isApprove()?"y":"n");
			pstmt.setInt(8, data.getIndex());
			
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
			String sql = "DELTE FROM user_info WHERE f_index = ? ";
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
	 * @return
	 * 	������
	 */
	protected int getUserListCount(){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM user_info ");
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
			    data.setGrade(rs.getString("f_grade"));
			    data.setDeptName(rs.getString("f_deptname"));
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
	 * @return
	 * 	������
	 */
	protected int getArvListCount(){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM user_info where f_approve='n'");
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
	 * @return
	 * 	������
	 */
	protected int getQuserListCount(){
		int count = 0;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM user_info WHERE" +
					" f_visit_date < NOW()- interval 3 month");
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
	 * @param start
	 * 		���� ��ȣ
	 * @param end
	 * 		������ ��ȣ
	 * @return
	 */
	protected List<UserBean> getUserList(final String search, int start, int end){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE (f_id like ? OR f_name like ?) ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setString(2, "%" + search + "%");
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);			
			rs = pstmt.executeQuery();
			Aria aria = Aria.getInstance();	
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
	protected List<UserBean> getArvList(final String search, int start, int end){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE (f_approve='n') AND" +
					" (f_id like ? OR f_name like ?) ORDER BY f_index DESC LIMIT ?, ?");
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setString(2, "%" + search + "%");
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);			
			rs = pstmt.executeQuery();
			Aria aria = Aria.getInstance();	
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
	 * @return
	 */	
	protected List<UserBean> getQuserList(final String search, int start, int end){
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean data = null;	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE " +
					" f_visit_date < NOW() - interval 3 month AND (f_id like ? OR f_name like ?) " +
					" ORDER BY f_index DESC LIMIT ?, ?");
			pstmt.setString(1, "%" + search + "%");	
			pstmt.setString(2, "%" + search + "%");
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);			
			rs = pstmt.executeQuery();
			Aria aria = Aria.getInstance();	
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

}
