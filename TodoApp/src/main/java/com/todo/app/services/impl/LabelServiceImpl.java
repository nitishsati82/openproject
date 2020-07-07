package com.todo.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.data.LabelData;
import com.todo.app.repos.LabelRepos;
import com.todo.app.services.LabelServices;

@Service("labelServices")
public class LabelServiceImpl implements LabelServices {
	@Autowired
	LabelRepos labelServices;

	@Override
	public List<LabelData> getDefaultLabels(int userId, String session) {
		// TODO Auto-generated method stub
		List<LabelData> ok = labelServices.getdefaultLabels(userId,session);
		return ok;
		//return null;
	}

	@Override
	public List<LabelData> getUserLabels(String userIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createNewLabel(LabelData labelData) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateLabel(LabelData labelData) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int deleteLabel(LabelData labelData) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LabelData save(LabelData labelData) {
		return labelServices.saveAndFlush(labelData);
	}
}
