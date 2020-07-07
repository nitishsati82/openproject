package com.todo.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.data.UserToDo;
import com.todo.app.services.UserToDoServices;

@RestController
@RequestMapping(path = "/todoservice")
public class ToDoController {
	@Autowired
	UserToDoServices todoServices;
	
	@RequestMapping(value = "/loadToDo/{userIndex}/{toDoId}/", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<UserToDo> loadToDoData(@PathVariable("userIndex") String userIndex,@PathVariable("toDoId") String toDoId) {
		List<UserToDo> returnList = todoServices.getUserToDoDetails(Integer.parseInt(userIndex), Integer.parseInt(toDoId));
		return returnList;
	}
	
	@RequestMapping(value = "/getToDoTaks/{userIndex}/{todoLabel}/{sessionKey}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<UserToDo> getToDoData(@PathVariable("userIndex") String userIndex,@PathVariable("todoLabel") String todoLabel,@PathVariable("sessionKey") String sessionKey) {
		List<UserToDo> returnList = todoServices.getLabelsTaksDetails(Integer.parseInt(userIndex), todoLabel,sessionKey);
		return returnList;
	}
	
	@RequestMapping(value = "/createToDo/", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody UserToDo createToDo(@RequestBody UserToDo labelData) {
		UserToDo us = null;
		DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String day = labelData.getTask_due_date();
		LocalDate current = LocalDate.now();
	    String formatedDateTime = current.format(format);   
	    if(day.isEmpty()) {
	    	labelData.setTask_due_date(formatedDateTime);
	    }
		if(labelData.getUser_session()==null || labelData.getUser_session().isEmpty()) {
			us = new UserToDo();
			us.setUser_session("Invalid");
			return us;
		}
		if(labelData.getUser_index()>0) {
			labelData.setIs_task_deleted("N");
			labelData.setTaskStatus("NEW");
			us = todoServices.insertToDoList(labelData);
		}else {
			us = new UserToDo();
			us.setUser_session("Error in adding todo.");
		}
		return us;
	}
	
	@RequestMapping(value = "/updateToDo/", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody UserToDo update(@RequestBody UserToDo labelData) {
		if(labelData.getUser_session()==null || labelData.getUser_session().isEmpty()) {
			labelData.setUser_session("Invalid");
			return labelData;
		}
		labelData.setIs_task_deleted("N");
		String result = todoServices.updateUserToDo(labelData);
		if(result.equals("Y")) {
			return labelData;
		}else {
			labelData.setUser_session("Error in updating task.");
			return labelData;
		}
	}
	
	@RequestMapping(value = "/updateStatus/{operation}/{todoId}/{state}/{userIndex}/{sessionKey}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody UserToDo updateStatus(@PathVariable("operation") String operation,@PathVariable("todoId") String todoId,@PathVariable("state") String state,@PathVariable("userIndex") String userIndex,@PathVariable("sessionKey") String sessionKey) {
		UserToDo user = new UserToDo();
		if(operation.equals("delete")) {
			user =  todoServices.deleteUserToDo(Integer.parseInt(userIndex),Integer.parseInt(todoId),sessionKey);
		}else if(operation.equals("status")) {
			user =  todoServices.updateUserToDo(Integer.parseInt(userIndex), Integer.parseInt(todoId), state,sessionKey);
		}
		return user;
	}
}
