package com.hotel.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.hotel.app.data.RestaurantData;

public interface ResaturantRepos extends CrudRepository<RestaurantData, Integer> {
	@Query(value="select * from restaurantsadb where id= :id", nativeQuery=true)
	List<RestaurantData> getHotelDetailsById(@Param("id") int id);
	
	@Query(value="select * from restaurantsadb where name= :name", nativeQuery=true)
	List<RestaurantData> getHotelDetailsByName(@Param("name") String name);
	
	@Query(value="select * from restaurantsadb where id is not null", nativeQuery=true)
	List<RestaurantData> getAllHotels();
	
	@Query(value="select * from restaurantsadb where id is not null", nativeQuery=true)
	RestaurantData[] getAllHotel();
}
