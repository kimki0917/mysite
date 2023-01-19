package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.web.mvc.Action;

public class GuestBookDelete implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String no = request.getParameter("no");
		String password = request.getParameter("password");

		GuestbookDao dao = new GuestbookDao();

		if(dao.findByNo(no).getPassword().equals(password)){
		dao.deleteByNo(no);
		response.sendRedirect(request.getContextPath() + "/guestbook?a=list");
		}else {
			request.setAttribute("no", no);
			response.sendRedirect(request.getContextPath() + "/guestbook?a=delete");
		}
	}

}
