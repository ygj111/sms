package com.hhh.fund.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@RequestMapping(value = "dictlist", method = RequestMethod.GET)
	public String dictList(Model model) {
		return "admin/dict";
	}
}
