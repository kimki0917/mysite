package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

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

		boardRepository.delete(no,userNo);
	}

	public Map<String, Object> getContentsList(int page, String keyword) {
		int totalCount = boardRepository.getTotalCount(keyword);
		int totalPage = totalCount % LIST_SIZE == 0 ? totalCount / LIST_SIZE : totalCount / LIST_SIZE + 1;
		// 1.view 에서 게시판 리스트를 렌더링 하기 위한 데이터값 계산
		int prevPage = page - 1;
		int nextPage = page + 1;
//		int endPage = ((totalPage < PAGE_SIZE)|| (page+PAGE_SIZE-1>=totalPage)) ? totalPage : page+PAGE_SIZE-1;
//		int beginPage = (prevPage<=0||endPage-PAGE_SIZE+1<=0)?1:(endPage-PAGE_SIZE+1);
		
		//int endPage = ((totalPage < PAGE_SIZE)|| (nextPage>=totalPage)) ? totalPage : beginPage+PAGE_SIZE-1;
//		if((totalPage < PAGE_SIZE)|| (nextPage>=totalPage)){
//			endPage=totalPage;
//			if(nextPage>=totalPage) {
//				beginPage=totalPage-PAGE_SIZE+1;
//			}
//		}

//		int beginPage=page - 2;
//		int endPage = page + 2;
//		beginPage = (totalPage<PAGE_SIZE|| prevPage<=0||beginPage<=0)?1:page-2;
//		endPage = (totalPage<=PAGE_SIZE||nextPage>=totalPage)? totalPage : beginPage+PAGE_SIZE-1;
//		if(totalPage>PAGE_SIZE&&nextPage>=totalPage) {
//			beginPage=endPage-PAGE_SIZE+1;
//		}
		//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
		int beginPage = page-2 <1 ? 1: page-2;
		int endPage = page+2 > totalPage ? totalPage: page+2;
		if(page <=2 && totalPage > PAGE_SIZE ) {
			beginPage =1;
			endPage = PAGE_SIZE;
		}
		if(page >= endPage-2 && totalPage > PAGE_SIZE ) {
			beginPage = endPage-PAGE_SIZE+1;
		}
		//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
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
