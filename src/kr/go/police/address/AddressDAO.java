package kr.go.police.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.go.police.CommonCon;
import kr.go.police.aria.Aria;
import kr.go.police.sms.Group;

/**
 * �ּҷ� ���� Dao
 */
public class AddressDAO extends CommonCon {
	DataSource dataSource;
	public ResultSet rs, rs2;
	public PreparedStatement pstmt;
	public Connection conn;
	
	public AddressDAO(){
		dataSource = getDataSource();
		if(dataSource == null){
			return;
		}
	}
	

	/**
	 * �� �ּҷ� ����Ʈ
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupndex
	 * 		�׷� �ε���
	 * @param start
	 * 		���۹�ȣ
	 * @param end
	 * 		����ȣ
	 * @return
	 */
	public List<AddressBean> getAddressList(int userIndex, int groupIndex, int start, int end) {
		List<AddressBean> list = new ArrayList<AddressBean>();
		AddressBean data = null;		
		try {
			
			Aria aria = Aria.getInstance();				
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book WHERE" +
					" f_user_index = ? AND f_group_index = ? ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, groupIndex);
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);						
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new AddressBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setPeople(aria.encryptHexStr2DecryptStr(rs.getString("f_people")));
			    data.setPhone(aria.encryptHexStr2DecryptStr(rs.getString("f_phone")));		
			    data.setGroupIndex(rs.getInt("f_group_index"));
				list.add(data);
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * 	���� �׷��� ��ü �ּҷ� ��������
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @return
	 */
	public List<AddressBean> getAddressList(int groupIndex) {
		List<AddressBean> list = new ArrayList<AddressBean>();
		AddressBean data = null;		
		try {
			
			Aria aria = Aria.getInstance();				
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book WHERE" +
					"  f_group_index = ? ORDER BY f_index DESC ");
			pstmt.setInt(1, groupIndex);
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new AddressBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setPeople(aria.encryptHexStr2DecryptStr(rs.getString("f_people")));
			    data.setPhone(aria.encryptHexStr2DecryptStr(rs.getString("f_phone")));		
			    data.setGroupIndex(rs.getInt("f_group_index"));
				list.add(data);
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	

	/**
	 * 	�ش� �ּҷ� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @return
	 */
	public  int getAddressSize(int userIndex, int groupIndex) {
		int size = 0;	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM address_book WHERE" +
					" f_user_index = ? AND f_group_index = ?  ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, groupIndex);				
			rs = pstmt.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressSize ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return size;		
	}	
	
	
	/**
	 *	�ּҷ� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @return
	 */
	public boolean delAddress(int groupIndex, int addressIndex) {
		int result = 0;
		int subResult = 0;		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM address_book WHERE 1 =1 AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, addressIndex);					// �ּ� �ε���			
			// update
			result = pstmt.executeUpdate();
			
			// �ش� �׷쿡 �ο��� ����
			pstmt = conn.prepareStatement("UPDATE address_book_group SET f_size = f_size - 1" +
					" WHERE 1 = 1 AND f_index = ? ORDER BY f_index DESC ");
			pstmt.setInt(1, groupIndex);	
			subResult = pstmt.executeUpdate();	
			
			return result > 0 && subResult > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delAddress ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �� �ּҷ� �߰�
	 * @param data
	 * 		AddressBean
	 */
	public boolean addAddressPeople(int userIndex, AddressBean data) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO address_book " +
					" ( f_user_index, f_group_index,  f_people, f_phone)" +
					" VALUES (?, ?, ?, ?) ";
			Aria aria = Aria.getInstance();		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);															// ���� �ε���
			pstmt.setInt(2, data.getGroupIndex());											// �׷� �ε���
			pstmt.setString(3, aria.encryptByte2HexStr(data.getPeople()));			// ����̸�
			pstmt.setString(4, aria.encryptByte2HexStr(data.getPhone()));			// �޼���	
			pstmt.executeUpdate();
			
			// �ش� �׷쿡 �ο��� ����
			pstmt = conn.prepareStatement("UPDATE address_book_group SET f_size = f_size + 1" +
					" WHERE 1 = 1 AND f_index = ? ORDER BY f_index DESC ");
			pstmt.setInt(1, data.getGroupIndex());	
			result = pstmt.executeUpdate();					
			
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addAddressPeople ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}
	
	/**
	 * 	�ּҷ�  ����
	 * @param index
	 * 		�ε���
	 * @param people
	 * 		����ڸ�
	 * @param phone
	 * 		��ȭ��ȣ
	 * @return
	 */
	public boolean modifyAddress(int index, String people, String phone) {
		int result = 0;
		try {
			Aria aria = Aria.getInstance();					
			conn = dataSource.getConnection();
			String sql = "UPDATE address_book SET f_people = ?, f_phone = ?" +
					" WHERE 1 =1 AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aria.encryptByte2HexStr(people));			// ����̸�
			pstmt.setString(2, aria.encryptByte2HexStr(phone));			// �޼���	
			pstmt.setInt(3, index);													// �׷� �ε���			
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyAddress ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		
	
	/**
	 * �� �ּҷ� �׷츮��Ʈ
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Group> getGroupList(int userIndex, int start, int end) {
		List<Group> list = new ArrayList<Group>();
		Group data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book_group WHERE" +
					" f_user_index = ? ORDER BY f_size DESC LIMIT ?, ? ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, start -1);	
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
			    data.setCount(rs.getInt("f_size"));			    
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * �� �ּҷ� ��ü �׷츮��Ʈ
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 */
	public List<Group> getGroupList(int userIndex) {
		List<Group> list = new ArrayList<Group>();
		Group data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book_group WHERE" +
					" f_user_index = ? ORDER BY f_size DESC ");
			pstmt.setInt(1, userIndex);	
			rs = pstmt.executeQuery();
			while(rs.next()){
				// ������ �׷�
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
			    data.setCount(rs.getInt("f_size"));			    
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * �� �ּҷ� �׷� ����
	 * @param userIndex
	 * 	���� �ε���
	 * @return
	 * 	�ּҷ� �׷� ����
	 */
	public  int getGroupSize(int userIndex) {
		int size = 0;	
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT count(*) FROM address_book_group " +
					" WHERE 1 = 1 AND f_user_index = ?  ");
			pstmt.setInt(1, userIndex);	
			rs = pstmt.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getGroupSize ���� : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return size;		
	}	

	/**
	 * 	�ּҷ� �׷� �߰�
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupName
	 * 		�׷��
	 * @return
	 */
	public boolean addAddressGroup(int userIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO address_book_group ( f_user_index, f_group) VALUES (?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);							// ���� �ε���
			pstmt.setString(2, groupName);					// �׷� �ε���		
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addAddressGroup ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 *	�ּҷ� �׷� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @return
	 */
	public boolean delAddressGroup(int userIndex, int groupIndex) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			// �ش� �׷��� �ּҷ� ����
			String sql = "DELETE FROM address_book WHERE 1 =1 AND" +
					" f_index = ? AND f_group_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// ���� �ε���
			pstmt.setInt(2, groupIndex);					// �׷� �ε���					
			pstmt.executeUpdate();	
			// �ش� �׷� ����
			sql = "DELETE FROM address_book_group WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// ���� �ε���
			pstmt.setInt(2, groupIndex);					// �׷� �ε���			
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delAddressGroup ���� : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * 	�ּҷ� �׷�� ����
	 * @param userIndex
	 * 		���� �ε���
	 * @param groupIndex
	 * 		�׷� �ε���
	 * @param groupName
	 * 		�׷��
	 * @return
	 */
	public boolean modifyAddressGroup(int userIndex, int groupIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE address_book_group SET f_group = ? WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);					// �׷��			
			pstmt.setInt(2, userIndex);						// ���� �ε���
			pstmt.setInt(3, groupIndex);					// �׷� �ε���			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyAddressGroup ���� : " + e.getMessage());
			return false;
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
