package com.todo.app.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.app.data.LabelData;

public interface LabelRepos extends JpaRepository<LabelData, Integer> {
	@Query(value="select id,label_name,created_by,user_session from label_list where (created_by= :created_by or created_by=-1) and (user_session= :session or user_session=-1) order by label_name asc", nativeQuery=true)
	List<LabelData> getdefaultLabels(@Param("created_by") int created_index, @Param("session") String session);

	@Query(value="select * from label_list where created_index= :created_index", nativeQuery=true)
	List<LabelData> getUserLabels(@Param("created_index") int created_index);

	@Modifying
	@Query(value="insert into label_list (label_name,created_index) VALUES (:label_name,:addedBy)", nativeQuery=true)
	@Transactional
	List<LabelData> createUserLabel(@Param("label_name") String label_name,@Param("addedBy") int addedBy);
	/*
	@Modifying
	@Query(value="update label_list u set u.label_name = :label_name where created_index= :created_index and id= :id", nativeQuery=true)
	@Transactional
	List<LabelData> updatesUserLabel(@Param("label_name") String isDeleted,@Param("created_index") int created_index,@Param("id") int id);*/

	/*@Modifying
	@Query(value="update label_list u set u.is_deleted = :isDeleted where created_index= :created_index and id= :id")
	@Transactional
	String deleteUserLabel(@Param("isDeleted") String isDeleted,@Param("created_index") int created_index,@Param("id") int id);*/
}
