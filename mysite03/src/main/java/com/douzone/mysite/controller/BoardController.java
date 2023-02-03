package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(@RequestParam(value = "page",defaultValue="1",required=false)int page,@RequestParam(value = "keyword", required=false)String keyword,Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		 model.addAllAttributes(map);
		// model.addAttribute(page);
		return "board/list";
	}
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String search(@RequestParam(value = "page",defaultValue="1",required=false)int page,@RequestParam(value = "keyword", required=false)String keyword,Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		 model.addAllAttributes(map);
		return "board/list";
	}
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "board/view";
	}

	@RequestMapping(value = "/writeform", method = RequestMethod.GET)
	public String insertView(Model model) {
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String insert(BoardVo vo) {
		if(vo.getgNo()==0) {
			vo.setgNo(boardService.selectGno());	
		}else {
			boardService.updateReply(vo.getgNo(),vo.getoNo());
			vo.setoNo(vo.getoNo()+1);
			vo.setDepth(vo.getDepth()+1);
		}
		boardService.addContents(vo);
		return "redirect:/board?page=1&keyword=";
	}
	@RequestMapping(value = "/write/replyform", method = RequestMethod.POST)
	public String replyView(BoardVo vo,Model model) {
		model.addAttribute("vo",vo);
		return "board/write";
	}
	
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String updateform(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value = "/update/{no}", method = RequestMethod.POST)
	public String update(@PathVariable("no") Long no, BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board?page=1&keyword=";
	}
	
	@RequestMapping(value = "/delete/{no}/{userNo}")
	public String delete(@PathVariable("no") Long no,@PathVariable("userNo") Long userNo, Model model) {
		boardService.deleteContents(no,userNo);
		return "redirect:/board?page=1&keyword=";
	}
}