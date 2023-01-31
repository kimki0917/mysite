package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		BoardVo vo =dao.findBoardByNo(Integer.parseInt(request.getParameter("no")));
		vo.setTitle(request.getParameter("title"));
		vo.setContents(request.getParameter("content"));
		dao.update(vo);
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
