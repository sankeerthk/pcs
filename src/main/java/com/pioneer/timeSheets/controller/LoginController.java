package com.pioneer.timeSheets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value={"/login","/"})
	public String loginAction(){
		return "login";
	}
	
	@RequestMapping(value="/user/home",method=RequestMethod.GET)
	public String homeMethod(){
		return "home";
	}

}
