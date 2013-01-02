package kr.go.police.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.go.police.CommonCon;
import kr.go.police.aria.ARIAEngine;
import kr.go.police.aria.Aria;
import kr.go.police.sms.Group;

/**
 * 주소록 관련 Dao
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
	 * 내 주소록 리스트
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupndex
	 * 		그룹 인덱스
	 * @param start
	 * 		시작번호
	 * @param end
	 * 		끝번호
	 * @param search 
	 * 		검색어
	 * @param what
	 * 		검색종류 
	 * @return
	 */
	public List<AddressBean> getAddressList(int userIndex, int groupIndex, int start, int end, String what, String search) {
		List<AddressBean> list = new ArrayList<AddressBean>();
		AddressBean data = null;		
		try {
			Aria aria = Aria.getInstance();		
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book WHERE " +
					 " f_user_index = ? AND f_group_index = ? ORDER BY f_index DESC LIMIT ?, ? ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, groupIndex);
			pstmt.setInt(3, start -1);	
			pstmt.setInt(4, end);			
			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			// 검색 종류 설정			
			what = what.equals("name")?"f_people":"f_phone";
			
			while(rs.next()){
				
				// 문자함 그룹
			    data = new AddressBean();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setPeople(aria.encryptHexStr2DecryptStr(rs.getString("f_people")));
			    data.setPhone(aria.encryptHexStr2DecryptStr(rs.getString("f_phone")));		
			    data.setGroupIndex(rs.getInt("f_group_index"));
			    
			    // 검색 조건이 있을경우
			    if(search.length() > 0){
					// 매칭되는 검색 결과만
			    	System.out.println(data.getPhone().trim());
					if( data.getPeople().contains(search.trim()) || 
							data.getPhone().contains(search.trim()) ){ 
						list.add(data);
					}		
			    }else{
			    	list.add(data);
			    }
  			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * 	선택 그룹의 전체 주소록 가져오기
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @return
	 */
	public List<AddressBean> getAddressList(int groupIndex	) {
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
				// 문자함 그룹
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
			System.out.println("getAddressList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	

	/**
	 * 	해당 주소록 갯수
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @param search 
	 * @param what 
	 * @return
	 */
	public  int getAddressSize(int userIndex, int groupIndex, String what, String search) {
		int size = 0;	
		List<AddressBean> list = new ArrayList<AddressBean>();
		AddressBean data = null;				
		try {
			Aria aria = Aria.getInstance();			
			/*
			what = what.equals("name")?"f_people":"f_phone";
			String subWhere = "";
			if(search.length() > 0){
				System.out.println(search);
				search = " '%" + aria.encryptByte2HexStr(search.trim()) + "%' ";		
				subWhere = what + " like " + search + " AND ";
			}
			*/
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book WHERE " +
					" f_user_index = ? AND f_group_index = ?  ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, groupIndex);				
			rs = pstmt.executeQuery();
			System.out.println(pstmt.toString());	
			
			while(rs.next()){
			    data = new AddressBean();	
			    data.setPeople(aria.encryptHexStr2DecryptStr(rs.getString("f_people")));
			    data.setPhone(aria.encryptHexStr2DecryptStr(rs.getString("f_phone")));		
			    // 검색 조건이 있을경우
			    if(search.length() > 0){
					// 매칭되는 검색 결과만
					if( data.getPeople().contains(search.trim()) || 
							data.getPhone().contains(search.trim()) ){ 
						++size;
					}
			    }else{
			    	++size;
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getAddressSize 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return size;		
	}	
	
	
	/**
	 *	주소록 삭제
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @return
	 */
	public boolean delAddress(int groupIndex, int addressIndex) {
		int result = 0;
		int subResult = 0;		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM address_book WHERE 1 =1 AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, addressIndex);					// 주소 인덱스			
			// update
			result = pstmt.executeUpdate();
			
			// 해당 그룹에 인원수 감소
			pstmt = conn.prepareStatement("UPDATE address_book_group SET f_size = f_size - 1" +
					" WHERE 1 = 1 AND f_index = ? ORDER BY f_index DESC ");
			pstmt.setInt(1, groupIndex);	
			subResult = pstmt.executeUpdate();	
			
			return result > 0 && subResult > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delAddress 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 내 주소록 추가
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
			pstmt.setInt(1, userIndex);															// 유저 인덱스
			pstmt.setInt(2, data.getGroupIndex());											// 그룹 인덱스
			pstmt.setString(3, aria.encryptByte2HexStr(data.getPeople()));			// 등록이름
			pstmt.setString(4, aria.encryptByte2HexStr(data.getPhone()));			// 메세지	
			pstmt.executeUpdate();
			
			// 해당 그룹에 인원수 증가
			pstmt = conn.prepareStatement("UPDATE address_book_group SET f_size = f_size + 1" +
					" WHERE 1 = 1 AND f_index = ? ORDER BY f_index DESC ");
			pstmt.setInt(1, data.getGroupIndex());	
			result = pstmt.executeUpdate();					
			
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addAddressPeople 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}
	
	/**
	 * 	주소록  수정
	 * @param index
	 * 		인덱스
	 * @param people
	 * 		등록자명
	 * @param phone
	 * 		전화번호
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
			pstmt.setString(1, aria.encryptByte2HexStr(people));			// 등록이름
			pstmt.setString(2, aria.encryptByte2HexStr(phone));			// 메세지	
			pstmt.setInt(3, index);													// 그룹 인덱스			
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyAddress 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}		
	
	/**
	 * 내 주소록 그룹리스트
	 * @param userIndex
	 * 	유저 인덱스
	 * @return
	 */
	public List<Group> getGroupList(int userIndex, int start, int end) {
		List<Group> list = new ArrayList<Group>();
		Group data = null;		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM address_book_group WHERE" +
					" f_user_index = ? ORDER BY f_index ASC, f_size DESC LIMIT ?, ? ");
			pstmt.setInt(1, userIndex);	
			pstmt.setInt(2, start -1);	
			pstmt.setInt(3, end);				
			rs = pstmt.executeQuery();
			while(rs.next()){
				// 문자함 그룹
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
			    data.setCount(rs.getInt("f_size"));			    
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 내 주소록 전체 그룹리스트
	 * @param userIndex
	 * 	유저 인덱스
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
				// 문자함 그룹
			    data = new Group();	
			    data.setIndex(rs.getInt("f_index"));
			    data.setGroup(rs.getString("f_group"));
			    data.setCount(rs.getInt("f_size"));			    
				list.add(data);
  			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getMyGroupList 에러 : " + e.getMessage());
			return null;
		}finally{
			connClose();
		}
	}	
	
	
	/**
	 * 내 주소록 그룹 갯수
	 * @param userIndex
	 * 	유저 인덱스
	 * @return
	 * 	주소록 그룹 갯수
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
			System.out.println("getGroupSize 에러 : " + e.getMessage());
		}finally{
			connClose();
		}
		
		return size;		
	}	

	/**
	 * 	주소록 그룹 추가
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupName
	 * 		그룹명
	 * @return
	 */
	public boolean addAddressGroup(int userIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO address_book_group ( f_user_index, f_group) VALUES (?, ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);							// 유저 인덱스
			pstmt.setString(2, groupName);					// 그룹 인덱스		
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addAddressGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 *	주소록 그룹 삭제
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @return
	 */
	public boolean delAddressGroup(int userIndex, int groupIndex) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			// 해당 그룹의 주소록 삭제
			String sql = "DELETE FROM address_book WHERE 1 =1 AND" +
					" f_user_index = ? AND f_group_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// 유저 인덱스
			pstmt.setInt(2, groupIndex);					// 그룹 인덱스					
			pstmt.executeUpdate();	
			
			// 해당 그룹 삭제
			sql = "DELETE FROM address_book_group WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userIndex);						// 유저 인덱스
			pstmt.setInt(2, groupIndex);					// 그룹 인덱스			
			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delAddressGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}

	/**
	 * 	주소록 그룹명 수정
	 * @param userIndex
	 * 		유저 인덱스
	 * @param groupIndex
	 * 		그룹 인덱스
	 * @param groupName
	 * 		그룹명
	 * @return
	 */
	public boolean modifyAddressGroup(int userIndex, int groupIndex, String groupName) {
		int result = 0;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE address_book_group SET f_group = ? WHERE 1 =1 AND" +
					" f_user_index = ? AND f_index = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);					// 그룹명			
			pstmt.setInt(2, userIndex);						// 유저 인덱스
			pstmt.setInt(3, groupIndex);					// 그룹 인덱스			

			// update
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("modifyAddressGroup 에러 : " + e.getMessage());
			return false;
		}finally{
			connClose();
		}
	}	
	
	/**
	 * 리소스 반환 반환 순서대로 닫아준다.
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
