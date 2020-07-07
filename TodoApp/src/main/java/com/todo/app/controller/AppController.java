package com.todo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.data.LabelData;
import com.todo.app.services.LabelServices;

@RestController
@RequestMapping(path = "/labelservice")
public class AppController {
	@Autowired
	LabelServices labelServices;
	
	@RequestMapping(value="/getDefaultLabels/{userIndex}/{sessionKey}", method = RequestMethod.GET,headers = "Accept=application/json")
	public List<LabelData> getDefaultLabels(@PathVariable("userIndex") String userIndex,@PathVariable("sessionKey") String sessionKey) {
		System.out.println("called to controller...");
		List<LabelData> hotelList = labelServices.getDefaultLabels(Integer.parseInt(userIndex),sessionKey);
		return hotelList;
	}
	
	@RequestMapping(value = "/createNewLabel/", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody LabelData createNewLabel(@RequestBody LabelData labelData) {
		System.out.println("called to save controller.");
		if(labelData.getUser_session()==null || labelData.getUser_session().isEmpty()) {
			labelData.setUser_session("Invalid");
			return labelData; 
		}
		return labelServices.save(labelData);
	}
}
