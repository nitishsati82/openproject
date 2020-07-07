package com.todo.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.data.UserData;
import com.todo.app.services.UserServices;

@RestController
@RequestMapping(path = "/userservice")
public class UserController {
	@Autowired
	UserServices services;
	
	
	@RequestMapping(value = "/login/", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody UserData userLogin(HttpSession session,@RequestBody UserData labelData,@ModelAttribute("user") UserData user) {
		UserData fetchedData  = new UserData();
		if(labelData.getEmail()==null || labelData.getEmail().isEmpty()) {
			fetchedData.setLogin_success("Kindly enter email");
			return fetchedData;
		}else if(labelData.getUser_password()==null || labelData.getUser_password().isEmpty()) {
			fetchedData.setLogin_success("Kindly enter password");
			return fetchedData;
		} 
		String sessionnId = session.getId();
		fetchedData = services.userLogin(labelData,sessionnId);
		session.setAttribute("user", user);
		return fetchedData;
	}
	
	@RequestMapping(value = "/signup/", method = RequestMethod.POST, headers = "Accept=text/plain")
	public @ResponseBody String signUp(@RequestBody UserData labelData) {
		System.out.println("called to save controller.");
		String returnResult = "Sign up fail.";
		if(labelData.getEmail()==null || labelData.getEmail().isEmpty()) {
			returnResult = "Email can't be blank.";
		}else if(labelData.getUser_password()==null || labelData.getUser_password().isEmpty()) {
			returnResult = "Password can't be blank.";
		}
		UserData user = services.userSignUp(labelData);
		if(user!=null && user.getId()>0) {
			returnResult = "Y,"+user.getEmail()+" for login.";
		}else if(user.getId()==-1) {
			returnResult = "N,Email already exist.";
		}else {
			returnResult = "X,Signup Failed.";
		}
		return returnResult;
	}
	
	@RequestMapping(value = "/logout/{userIndex}/{sessionKey}", method = RequestMethod.GET, headers = "Accept=text/plain")
	public @ResponseBody String logout(@PathVariable("userIndex") String userIndex,@PathVariable("sessionKey") String sessionKey,HttpSession session ) {
	    int res = services.userLogOut(Integer.parseInt(userIndex), sessionKey);
	    String result = "N";
	    if(res>0) {
	    	session.invalidate();
	    	result= "Y";
	    }
	    return result;
	} 
	
	@RequestMapping(value = "/getPassowrd/{email}", method = RequestMethod.GET, headers = "Accept=text/plain")
	public @ResponseBody String getPassword(@PathVariable("email") String email) {
	    String res = services.getPassword(email);
	    return res;
	} 
	@RequestMapping(value = "/update/", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String update(@RequestBody UserData labelData) {
		System.out.println("called to save controller.");
		return null;
	}
	
}
