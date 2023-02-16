package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private ServletContext servletContext;
	@RequestMapping("/")
	public String index(Model model) {
		SiteVo site = siteService.getSite();
		servletContext.setAttribute("title", site.getTitle());
		model.addAttribute("site",site);
		return "main/index";
	}
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name) {
		return "안녕"+name;
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		return vo;
	}
}
