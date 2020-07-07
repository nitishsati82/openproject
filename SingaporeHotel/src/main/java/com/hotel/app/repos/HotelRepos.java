package com.hotel.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hotel.app.data.HotelData;

public interface HotelRepos extends CrudRepository<HotelData, Integer> {
	@Query(value="select * from restaurantsadb where id= :id", nativeQuery=true)
	List<HotelData> getHotelDetailsById(@Param("id") int id);
	
	@Query(value="select * from restaurantsadb where name= :name", nativeQuery=true)
	List<HotelData> getHotelDetailsByName(@Param("name") String name);
	
	@Query(value="select * from restaurantsadb where id is not null", nativeQuery=true)
	List<HotelData> getAllHotels();
}
