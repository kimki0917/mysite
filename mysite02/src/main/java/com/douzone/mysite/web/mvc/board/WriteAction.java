package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		vo.setUserNo(Integer.parseInt(request.getParameter("userNo")));
		vo.setTitle(request.getParameter("title"));
		vo.setContents(request.getParameter("content"));
		
		if(request.getParameter("re").equals("")) {
			vo.setgNo(dao.selectGno());
			vo.setoNo(1);
			vo.setDepth(0);
				
		}else if(request.getParameter("re").equals("re")) {
			int ono =Integer.parseInt(request.getParameter("oNo"));
			int gno =Integer.parseInt(request.getParameter("gNo"));
			dao.updateReply(gno,ono);
			vo.setgNo(gno);
			vo.setoNo(ono+1);
			vo.setDepth(Integer.parseInt(request.getParameter("depth"))+1);
			
		}
		dao.insert(vo);
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}
}
