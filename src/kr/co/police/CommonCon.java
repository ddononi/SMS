package kr.co.police;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class CommonCon {
	
	
	
	public DataSource getDataSource(){
		try{
			Context initCtx = new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			return (DataSource)envCtx.lookup("jdbc/smsConn");
		}catch(Exception ex){
			System.out.println("DB ���� ���� : " + ex);
			return null;
		}
	}
	
	/**
	 * ���ҽ� ��ȯ
	 * ��ȯ ������� �ݾ��ش�.
	 */
	public void connClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){}
		}
		
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(SQLException e){}
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	} 
	
}
