package com.todo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/todoApp/")
public class AppLoadController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goToHomePage() {
		return "todoApp";
	}
}
