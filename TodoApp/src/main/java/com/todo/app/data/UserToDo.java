package com.todo.app.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usertodolist")
public class UserToDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="user_index")
	private int user_index;
	
	@Column(name="task_name")
	private String task_name;
	
	@Column(name="task_label")
	private String task_label;
	
	@Column(name="task_notes")
	private String task_notes;
	
	@Column(name="task_status")
	private String task_status;
	
	@Column(name="is_task_deleted")
	private String is_task_deleted;
	
	@Column(name="task_due_date")
	private String task_due_date;

	@Column(name="user_session")
	private String user_session;
	
	@Column(name="task_priority")
	private int task_priority=3;
	
	public String getUser_session() {
		return user_session;
	}

	public void setUser_session(String user_session) {
		this.user_session = user_session;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_index() {
		return user_index;
	}

	public void setUser_index(int user_index) {
		this.user_index = user_index;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTask_label() {
		return task_label;
	}

	public void setTask_label(String task_label) {
		this.task_label = task_label;
	}

	public String getTask_notes() {
		return task_notes;
	}

	public void setTask_notes(String task_notes) {
		this.task_notes = task_notes;
	}

	public String getTaskStatus() {
		return task_status;
	}

	public void setTaskStatus(String task_status) {
		this.task_status = task_status;
	}

	public String getIs_task_deleted() {
		return is_task_deleted;
	}

	public void setIs_task_deleted(String is_task_deleted) {
		this.is_task_deleted = is_task_deleted;
	}

	public String getTask_due_date() {
		return task_due_date;
	}

	public void setTask_due_date(String task_due_date) {
		this.task_due_date = task_due_date;
	}

	public int getTask_priority() {
		return task_priority;
	}

	public void setTask_priority(int task_priority) {
		this.task_priority = task_priority;
	}
	
	
	
}
