package com.todo.app.services;

import java.util.List;

import com.todo.app.data.UserToDo;

public interface UserToDoServices {
	public List<UserToDo> getUserToDoDetails(int userIndex,int toDoId);
	public List<UserToDo> getLabelsTaksDetails(int userIndex,String toDoLabel,String sessionKey);
	public UserToDo insertToDoList(UserToDo userToDo);
	public UserToDo updateUserToDo(int userIndex, int toDoId,String status,String sessionKey);
	public UserToDo deleteUserToDo(int userIndex, int toDoId,String session);
	public String updateUserToDo(UserToDo userToDo);
}
