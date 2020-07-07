package com.todo.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.data.UserData;
import com.todo.app.repos.UserRepos;
import com.todo.app.services.UserServices;
@Service("userServices")
public class UserServicesImpl implements UserServices {
	@Autowired 
	UserRepos userServices;

	@Override
	public UserData getUserDetails(String userIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData userLogin(UserData userData,String sessionnId) {
		UserData user = userServices.getUserDetails(userData.getEmail(),userData.getUser_password());
		if(user==null) {
			user = new UserData();
			user.setLogin_success("Email/password not matched.");
			return user;
		}
		if(user!=null && user.getId()>0) {
			user.setLogin_success("Y");
			int sessionupdate = userServices.updateUserSession(sessionnId, user.getId());
			if(sessionupdate>0) {
				user.setUser_password("");
				user.setSession_index(sessionnId);
			}
		}
		return user;
	}

	@Override
	public UserData userSignUp(UserData userData) {
		String id = "";
		id = userServices.getUserId(userData.getEmail());
		if(id==null)id="";
		if(!id.equals("")) {
			userData.setId(-1);
			return userData;
		}
		return userServices.saveAndFlush(userData);
	}

	@Override
	public UserData updateUserDetails(UserData userData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int userLogOut(int userIndex, String sessionnId) {
		int sessionupdate = userServices.updateUserSessionOut("0",sessionnId, userIndex);
		return sessionupdate;
	}

	@Override
	public String getPassword(String email) {
		String id = userServices.getUserId(email);
		if(id==null) return "N";
		if(!id.equals("")) {
			String Password = userServices.getPassword(email);
			return Password;
		}else {
			return "N";
		}
		
	}

	
}
