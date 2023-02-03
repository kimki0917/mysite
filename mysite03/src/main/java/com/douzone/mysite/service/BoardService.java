package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 10; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 페이지 리스트의 페이지 수

	@Autowired
	private BoardRepository boardRepository;

	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}

	public BoardVo getContents(Long no) {

		return boardRepository.getContents(no);
	}

	public void updateContents(BoardVo vo) {
		boardRepository.updateContents(vo);
	}

	public void deleteContents(Long no, Long userNo) {
		boardRepository.delete(no);
	}

	public Map<String, Object> getContentsList(int page, String keyword) {
		int totalCount = boardRepository.getTotalCount(keyword);
		int totalPage = totalCount % LIST_SIZE == 0 ? totalCount / LIST_SIZE : totalCount / LIST_SIZE + 1;
		// 1.view 에서 게시판 리스트를 렌더링 하기 위한 데이터값 계산
		int prevPage = page - 1;
		int nextPage = page + 1;

		int endPage = 2;
		int beginPage = (page - 2) <= 0 ? 1 : page - 2;

		if (page == 1 || page == 2) {
			endPage = PAGE_SIZE;
			if (endPage > totalPage) {
				endPage = totalPage;
			}
		}

		if (page + 2 > totalPage) {
			endPage = totalPage;
			beginPage = endPage - PAGE_SIZE + 1;
			if (beginPage <= 0) {
				beginPage = 1;
			}
		} else {
			endPage = page + 2;
		}

		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeyWord(page - 1, keyword, LIST_SIZE);

		// 3. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("beginPage", beginPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("endPage", endPage);
		map.put("totalPage", totalPage);
		map.put("page", page);

		return map;
	}

	public int selectGno() {
		return boardRepository.findGno();
	}

	public void updateReply(int getgNo, int getoNo) {
		boardRepository.updateReply(getgNo, getoNo);
	}
}
