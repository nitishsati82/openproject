package com.todo.app.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userdata")
public class UserData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_password")
	private String user_password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "user_name")
	private String user_name;

	
	@Column(name = "login_success")
	private String login_success="N"; 
	
	@Column(name = "session_index")
	private String session_index="NA";
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getLogin_success() {
		return login_success;
	}

	public void setLogin_success(String login_success) {
		this.login_success = login_success;
	}

	public String getSession_index() {
		return session_index;
	}

	public void setSession_index(String session_index) {
		this.session_index = session_index;
	}
	
}
