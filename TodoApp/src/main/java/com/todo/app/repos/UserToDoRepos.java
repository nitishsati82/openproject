package com.todo.app.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.app.data.UserToDo;

public interface UserToDoRepos extends JpaRepository<UserToDo, Integer> {
	
	@Query(value="select * from usertodolist where user_index= :user_index and id= :toDoId", nativeQuery=true)
	List<UserToDo> getUserLabels(@Param("user_index") int added_by,@Param("toDoId") int toDoId);
	
	@Query(value="select * from usertodolist where user_index= :user_index and task_label= :task_label and user_session= :sessionKey and is_task_deleted='N' order by task_priority desc", nativeQuery=true)
	List<UserToDo> getSelectedLabelsTasks(@Param("user_index") int added_by,@Param("task_label") String toDoId,@Param("sessionKey") String sessionKey);
	
	@Query(value="select * from usertodolist where user_index= :user_index and task_label= :task_label and user_session= :sessionKey and is_task_deleted='N' order by task_priority desc", nativeQuery=true)
	List<UserToDo> getCount(@Param("user_index") int added_by,@Param("task_label") String toDoId,@Param("sessionKey") String sessionKey);
	
	@Query(value="select * from usertodolist where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	UserToDo getTaskDetails(@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
	
	@Query(value="select task_status from usertodolist where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	String getTaskStatus(@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
	
	@Query(value="select task_priority from usertodolist where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	int getTaskPriority(@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
	
	@Modifying
	@Query(value="insert into usertodolist (user_index,task_name,task_due_date,task_label,task_notes) VALUES (:user_index,:task_name,:task_due_date,:task_label,:task_notes)", nativeQuery=true)
	@Transactional
	UserToDo createUserLabel(@Param("user_index") int user_index,@Param("task_name") String task_name,@Param("task_due_date") String task_due_date,@Param("task_label") String task_label,@Param("task_notes") String task_notes);

	@Modifying
	@Query(value="update usertodolist set task_name = :task_name where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesToDoTaskName(@Param("task_name") String task_name,@Param("user_index") int user_index,@Param("id") int id, @Param("sessionKey") String sessionKey);
	
	@Modifying
	@Query(value="update usertodolist set task_notes = :task_notes where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesToDoTaskNotes(@Param("task_notes") String task_notes,@Param("user_index") int user_index,@Param("id") int id, @Param("sessionKey") String sessionKey);
	
	@Modifying
	@Query(value="update usertodolist set task_due_date = :task_due_date where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesToDoTaskDate(@Param("task_due_date") String task_due_date,@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
	
	
	@Modifying
	@Query(value="update usertodolist set task_status = :task_status where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesUserLabel(@Param("task_status") String task_status,@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);

	@Modifying
	@Query(value="update usertodolist set is_task_deleted = 'Y' where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesUserTodoStatus(@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
	
	@Modifying
	@Query(value="update usertodolist set task_priority = :task_priority where user_index= :user_index and id= :id and user_session= :sessionKey", nativeQuery=true)
	@Transactional
	int updatesUserTaskPriority(@Param("task_priority") int task_priority,@Param("user_index") int user_index,@Param("id") int id,@Param("sessionKey") String sessionKey);
}
