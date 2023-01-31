package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		String strNo = request.getParameter("no");
		int no =Integer.parseInt(strNo);
		BoardVo vo = dao.findBoardByNo(no);
		request.setAttribute("vo", vo);
		cookie(request,response,dao,strNo);
		MvcUtil.forward("board/view", request, response);
	}

	private void cookie(HttpServletRequest request,HttpServletResponse response,BoardDao dao, String no) {
		boolean isCookie=false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (no.equals(cookie.getName())) {
					isCookie=true;
				}
			}
		}
		if(!isCookie) {
			createCookie(request,response,dao,no);
		}
	}

	private void createCookie(HttpServletRequest request,HttpServletResponse response,BoardDao dao,String no) {
		Cookie cookie = new Cookie(no, String.valueOf(1));
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(24 * 60 * 60);
		response.addCookie(cookie);
		dao.updateHit(Integer.parseInt(no));
	}
}
