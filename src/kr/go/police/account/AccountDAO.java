package kr.go.police.account;

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


/**
 * ���� (login, regsiter etc)���� Dao
 */
public class AccountDAO {
	
	DataSource dataSource;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs1;
	
	public AccountDAO(){
		try{
			
			Context initCtx = new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			dataSource = (DataSource)envCtx.lookup("jdbc/smsConn");
		}catch(Exception ex){
			System.out.println("DB ���� ���� : " + ex);
			return;
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
