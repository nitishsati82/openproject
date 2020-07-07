package com.todo.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.data.UserToDo;
import com.todo.app.repos.UserToDoRepos;
import com.todo.app.services.UserToDoServices;

@Service("userTodoServices")
public class UserToDoListImpl implements UserToDoServices {
	@Autowired
	UserToDoRepos repos;
	@Override
	public UserToDo insertToDoList(UserToDo userToDo) {
		return repos.saveAndFlush(userToDo);
	}

	
	@Override
	public List<UserToDo> getUserToDoDetails(int userIndex, int toDoId) {
		return repos.getUserLabels(userIndex, toDoId);
	}

	@Override
	public List<UserToDo> getLabelsTaksDetails(int userIndex, String toDoLabel,String sessionKey) {
		return repos.getSelectedLabelsTasks(userIndex, toDoLabel,sessionKey);
	}


	@Override
	public UserToDo updateUserToDo(int userIndex, int toDoId, String status,String sessionKey) {
		UserToDo user = new UserToDo();
		updateTaskPriority(userIndex,toDoId,status,sessionKey);
		int update = repos.updatesUserLabel(status, userIndex, toDoId,sessionKey);
		if(update>0) {
			user = repos.getTaskDetails(userIndex, toDoId, sessionKey);
		}else {
			user.setId(-1);
		}
		return user;
	}
	
	private void updateTaskPriority(int userIndex, int toDoId, String status,String sessionKey) {
		String oldStatus = repos.getTaskStatus(userIndex, toDoId, sessionKey);
		int taskPrio = repos.getTaskPriority(userIndex, toDoId, sessionKey);
		int updateP = 0;
		if(oldStatus!=null && oldStatus.equals("NEW") && status.equals("PROGRESS")) {
			if(taskPrio==3) updateP=2;
			else if(taskPrio==2) updateP=1;
			else if(taskPrio==1) updateP=1;
			repos.updatesUserTaskPriority(updateP, userIndex, toDoId,sessionKey);
		}else if(oldStatus!=null && oldStatus.equals("PROGRESS") && status.equals("COMPLETED")) {
			repos.updatesUserTaskPriority(0, userIndex, toDoId,sessionKey);
		}else if(oldStatus!=null && oldStatus.equals("COMPLETED") && status.equals("PROGRESS")) {
			repos.updatesUserTaskPriority(0, userIndex, toDoId,sessionKey);
		}
	}

	@Override
	public UserToDo deleteUserToDo(int userIndex, int toDoId,String session) {
		/*change priority*/
		repos.updatesUserTaskPriority(0, userIndex, toDoId,session);
		int update = repos.updatesUserTodoStatus(userIndex, toDoId,session);
		UserToDo user = new UserToDo();
		if(update>0) {
			user = repos.getTaskDetails(userIndex, toDoId, session);
		}else {
			user.setId(-1);
		}
		return user;
	}


	@Override
	public String updateUserToDo(UserToDo userToDo) {
		String returnRes = "";
		int updateTaskName = repos.updatesToDoTaskName(userToDo.getTask_name(),userToDo.getUser_index(),userToDo.getId(),userToDo.getUser_session());
		int updateDate = repos.updatesToDoTaskDate(userToDo.getTask_due_date(),userToDo.getUser_index(),userToDo.getId(),userToDo.getUser_session());
		int updateTaskNotes = repos.updatesToDoTaskNotes(userToDo.getTask_notes(),userToDo.getUser_index(),userToDo.getId(),userToDo.getUser_session());
		int sum = updateTaskName+updateDate+updateTaskNotes;
		if(sum>0)returnRes="Y";
		return returnRes;
	}

}
