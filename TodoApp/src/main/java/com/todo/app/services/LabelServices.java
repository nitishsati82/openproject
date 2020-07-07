package com.todo.app.services;

import java.util.List;

import com.todo.app.data.LabelData;

public interface LabelServices {
	public List<LabelData> getDefaultLabels(int userId, String session);
	public List<LabelData> getUserLabels(String userIndex);
	public int createNewLabel(LabelData labelData);
	public LabelData save(LabelData labelData);
	public int updateLabel(LabelData labelData);
	public int deleteLabel(LabelData labelData);
}
