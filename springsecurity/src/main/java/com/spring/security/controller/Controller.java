package com.spring.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class Controller {
	@RequestMapping("/home")
	public String home() {
		return "hi";
	}
}
