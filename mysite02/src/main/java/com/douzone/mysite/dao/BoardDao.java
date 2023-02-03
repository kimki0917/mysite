package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {

	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int test = 0;
		try {
			conn = getConnection();
			String sql;

			System.out.println(test);
			sql = "insert into board values(null, ?, ?, ?, now(),?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getHit());
			// pstmt.setInt(3, vo.getgNo()==0? selectNo():vo.getgNo());
			pstmt.setInt(4, vo.getgNo());
			pstmt.setInt(5, vo.getoNo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setInt(7, vo.getUserNo());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
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

	public List<BoardVo> findAll(int pageNo, int pageSize) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "    select a.no, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y/%m/%d %H:%i:%s'), a.g_no, a.o_no, a.depth, a.user_no, b.name"
					+ "      from board a join user b on a.user_no=b.no order by g_no desc , o_no asc , no LIMIT 0, 10";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				BoardVo vo = new BoardVo();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setRegDate(rs.getString(5));
				vo.setgNo(rs.getInt(6));
				vo.setoNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getInt(9));
				vo.setName(rs.getString(10));
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

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

		return result;
	}

	public BoardVo findBoardByNo(int no) {
		BoardVo vo = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = " select no, title, contents, hit, date_format(reg_date, '%Y/%m/%d %H:%i:%s'), g_no, o_no, depth, user_no "
					+ "      from board " + "  where no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getInt(4));
				vo.setRegDate(rs.getString(5));
				vo.setgNo(rs.getInt(6));
				vo.setoNo(rs.getInt(7));
				vo.setDepth(rs.getInt(8));
				vo.setUserNo(rs.getInt(9));

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

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

		return vo;
	}

	public int selectGno() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int gNo = 0;

		try {
			conn = getConnection();

			String sql = "select ifnull(max(g_no)+1, 1) from board a";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				// System.out.println(rs.getInt(1)+"rs 결과값");
				gNo = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

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
		return gNo;
	}
	
	
	

	public Boolean update(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set title = ?, contents = ?" + " where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getNo());

			int count = pstmt.executeUpdate();

			result = count == 1;

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

		return result;
	}

	public Boolean deleteByNo(int no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();

			result = count == 1;
		} catch (SQLException e) {
			System.out.println("Error:" + e);
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

		return result;
	}

	public Boolean updateHit(int no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int hit = 0;
		try {

			conn = getConnection();

			sql = "select hit from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hit = rs.getInt(1) + 1;
			}

			sql = "update board set hit = ?" + " where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, hit);
			pstmt.setInt(2, no);

			int count = pstmt.executeUpdate();

			result = count == 1;

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

		return result;
	}

	public void updateReply(int gno, int ono) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set o_no = o_no+1 " + " where g_no = ? and o_no > ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, gno);
			pstmt.setInt(2, ono);

			int count = pstmt.executeUpdate();

			result = count == 1;

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