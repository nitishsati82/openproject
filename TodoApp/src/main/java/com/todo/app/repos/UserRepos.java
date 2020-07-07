package com.todo.app.repos;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.app.data.UserData;


public interface UserRepos extends JpaRepository<UserData, Integer> {
	@Query(value="select * from userdata where email= :email and user_password= :user_password", nativeQuery=true)
	UserData getUserDetails(@Param("email") String email,@Param("user_password") String user_password);
	
	@Query(value="select id from userdata where email= :email", nativeQuery=true)
	String getUserId(@Param("email") String email);
	
	@Query(value="select user_password from userdata where email= :email", nativeQuery=true)
	String getPassword(@Param("email") String email);
	
	@Modifying
	@Query(value="update userdata set session_index = :session_index where id= :id", nativeQuery=true)
	@Transactional
	int updateUserSession(@Param("session_index") String session_index,@Param("id") int id);
	
	@Modifying
	@Query(value="update userdata set session_index = :defSession, login_success='N' where session_index= :session_index and id= :id", nativeQuery=true)
	@Transactional
	int updateUserSessionOut(@Param("defSession") String defSession,@Param("session_index") String session_index,@Param("id") int id);
	
}
