package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	public List<BoardVo> findAllByPageAndKeyWord(int page, String keyword, int size) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("keyword", keyword);
		map.put("size", size);
		map.put("startOffset", page*size);
		
		List<BoardVo> result = sqlSession.selectList("board.findAllByPageAndKeyWord",map);
		
		return sqlSession.selectList("board.findAllByPageAndKeyWord",map);
	}
	public int getTotalCount(String keyword) {
		System.out.println(sqlSession.selectOne("board.getTotalCount", keyword)+"getTotalCount");
		return sqlSession.selectOne("board.getTotalCount", keyword);
	}
	
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}
	public BoardVo getContents(Long no) {
		BoardVo v = sqlSession.selectOne("board.getContents",no);
		return sqlSession.selectOne("board.getContents",no);
	}
	public int findGno() {
		int gno = sqlSession.selectOne("board.getGno");
		
		return gno;
	}
	public void delete(Long no) {
		sqlSession.delete("board.delete",no);
	}
	public void updateReply(int gNo, int oNo) {
		Map<String, Object> map = Map.of("gNo",gNo,"oNo",oNo);
		sqlSession.update("board.updateReply",map);
		
	}
	public void updateContents(BoardVo vo) {
		sqlSession.update("board.updateContent",vo);
	}

}
