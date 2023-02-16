package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list",list);
		return "guestbook/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete() {
		return "guestbook/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(GuestbookVo vo) {

		guestbookService.deleteMessage(vo.getNo(), vo.getPassword());
		return "redirect:/guestbook";
	}
}
