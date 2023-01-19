package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douzone.mysite.vo.UserVo;

public class UserDao {

//	 public List<UserVo> findAll() {
//		    List<UserVo> result = new ArrayList<>();
//
//		    Connection conn = null;
//		    PreparedStatement pstmt = null;
//		    ResultSet rs = null;
//		    try {
//		      conn = getConnection();
//
//		      String sql = "select no, name, password, message, reg_date from guestbook order by no desc";
//		      pstmt = conn.prepareStatement(sql);
//
//		      rs = pstmt.executeQuery();
//		      while (rs.next()) {
//		    	  UserVo vo = new UserVo();
//		        vo.setNo(rs.getLong(1));
//		        vo.setName(rs.getString(2));
//		        vo.setPassword(rs.getString(3));
//		        vo.setMessage(rs.getString(4));
//		        vo.setRegDate(rs.getString(5));
//
//		        result.add(vo);
//		      }
//
//		    } catch (SQLException e) {
//		      System.out.println("error:" + e);
//		    } finally {
//		      try {
//		        if (rs != null) {
//		          rs.close();
//		        }
//
//		        if (pstmt != null) {
//		          pstmt.close();
//		        }
//
//		        if (conn != null) {
//		          conn.close();
//		        }
//		      } catch (SQLException e) {
//		        e.printStackTrace();
//		      }
//		    }
//
//		    return result;
//		  }

	public void insert(UserVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into user values(null, ?, ?, password(?), ?,now())";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.122:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
}
