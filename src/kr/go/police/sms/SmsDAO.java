package kr.go.police.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.co.police.CommonCon;
import kr.go.police.account.UserBean;
import kr.go.police.board.BoardBean;


/**
 * ���� ���� Dao
 */
public class SmsDAO extends CommonCon {
	DataSource dataSource;
	
	public SmsDAO(){
		dataSource = getDataSource();
		if(dataSource == null){
			return;
		}
	}
	
	
	protected List<Message> getMyMessage(int userIndex, int groupIndex){
		List<Message> list = new ArrayList<Message>();
		Message data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM message WHERE f_user_index =?  ");
			pstmt.setInt(1, userIndex);
			//pstmt.setInt(2, groupIndex);			
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
				// �ε���, ��Ͼ��̵�, ����, ����, �׷��ε���, �׷��
			    data = new Message();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setId(rs.getString("f_id"));	  		
			    data.setTitle(rs.getString("f_message_title"));			    
			    data.setMessage(rs.getString("f_message_text"));
			    data.setGroupIndex(rs.getString("f_group_index"));
			    data.setGroup(rs.getString("f_message_group"));
	
				list.add(data);
			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyMessage ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}
	

	/**
	 * ���� ���� ����Ʈ ��������
	 * @param userIndex
	 * 	���� �ε���
	 * @param pae
	 * 	������
	 * @param limit
	 *  ����������
	 * @return
	 */
	protected List<SMS> getReservedList(int userIndex, int page, int limit){
		List<SMS> list = new ArrayList<SMS>();
		SMS data = null;		
		int startRow = (page -1 ) * 10 +1;		// ���� ��ȣ
		int endRow = startRow + limit -1;		// �� ��ȣ
		try {
			conn = dataSource.getConnection();
			// �ش� ������ ����� ���ڳ����� �����´�.
			pstmt = conn.prepareStatement("SELECT * FROM send_sms_info " +
					"WHERE f_reserve_date != null " +
					"AND f_user_index = ? " +
					"ORDER BY f_index DESC " +
					"LIMIT ?, ?");
			pstmt.setInt(1, userIndex);			
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);			
			rs = pstmt.executeQuery();
			
			while(rs.next())	{
			    data = new SMS();	
			    data.setId(rs.getString("f_id"));
			    data.setMessage(rs.getString("f_message"));
			    data.setToPhone(rs.getString("f_to_phone"));
			    data.setFromPhone(rs.getString("f_from_phone"));			    
			    data.setReserveDate(rs.getString("f_reserve_date"));	
			    data.setUserIndex(rs.getInt("f_user_index"));	
			    
				list.add(data);
  			}		
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getReservedList ���� : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}

	}		
		
	public List getPslist() throws SQLException {

//		 CommonCon�� getConnection Method�� ���Ͽ�, DBCP���� Ŀ�ؼ��� �����´�.
		Connection conn = dataSource.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		// ���ϵ� ������ ��
		List list = new ArrayList();
		
		try {
			// JDBC�� ���� ������ �ۼ�
			StringBuffer strQuery = new StringBuffer();

			strQuery.append("SELECT * FROM t_pscode ORDER BY F_PSCODE");
			
			// Prepared Statment�� ��ӹ��� LoggableStatment�� ���Ͽ� ? ��� ���� ���ڰ��� Ȯ���� �� �ִ�.
			pstmt = conn.prepareStatement(strQuery.toString() );

			System.out.println( strQuery.toString() );
			
			// �������� �����Ű��.
			rs = pstmt.executeQuery();
			
			// ResultSet Metat �����͸� ���Ͽ�, �÷��� ������, �÷��� �̸��� ��������.
			rsmd = rs.getMetaData();

			// �ش� Row�� ��ŭ ������ ����
			while( rs.next() ) {
				// 1���� Row�� �ش��ϴ� �÷����� HashMap�� ���� ��ü
				HashMap hm = new HashMap();
				
				// �÷��� ������ŭ ������ ���鼭, �÷���� �÷����� HashMap ��ü�� ��´�.
				for(int i = 1 ; i <= rsmd.getColumnCount() ; i++){
					hm.put( rsmd.getColumnName(i), rs.getString(i) );
					System.out.println( rsmd.getColumnName(i) + "   "  +  rs.getString(i) );
				}
				
				// HashMap ��ü�� ����� 1���� Row�� List ��ü�� �ٽ� �߰�
				list.add(hm);

			}
					
		}catch(Exception e ) {
			System.out.println("getPslist() Error : " + e.getMessage() );
		}finally {
			// �پ� �ڿ��� ��ȯ����.
			if( rs != null)	try { rs.close(); }catch(Exception e){}
			if( pstmt != null) try { pstmt.close(); }catch(Exception e1){}
			if( conn != null) try { conn.close(); } catch(Exception e2) {}
		}
		
		return list;
	}
	
}
