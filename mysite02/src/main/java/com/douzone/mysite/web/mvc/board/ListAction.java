package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		int all = dao.count();
		int size = 3;
		int pageSize=5;
		int first = 0;
		int last= 0;
		int max= (all-1)/size + 1;
		
		
		String pageNoStr = request.getParameter("pageNo");
		if(pageNoStr==null || pageNoStr.equals("")) {
			pageNoStr="1";
		}
		int thisPage =Integer.parseInt(pageNoStr);
		
		
		first = (thisPage-1)/pageSize * pageSize + 1 ;		
		
		if(thisPage > 3 && thisPage < max -1) {
			first = thisPage -2;
		} 
		last = first + pageSize -1;
		last = max - 2 >= last? last : max;
		
		if(thisPage >= max -1) {
			first = max - pageSize +1; 
		}
		
		List<BoardVo> list = dao.findAll(thisPage-1,size);
		
		request.setAttribute("list", list);
		request.setAttribute("size", size);
		request.setAttribute("first", first);
		request.setAttribute("last", last);
		request.setAttribute("max", max);
		request.setAttribute("thisPage", thisPage);
		request.setAttribute("all", all);
		
		MvcUtil.forward("board/list", request, response);
	}

}