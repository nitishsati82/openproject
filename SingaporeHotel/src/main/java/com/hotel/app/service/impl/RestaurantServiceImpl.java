package com.hotel.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.app.data.RestaurantData;
import com.hotel.app.repos.ResaturantRepos;
import com.hotel.app.service.RestaurantService;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	ResaturantRepos hotelservice;
	
	@Override
	public List<RestaurantData> getAllHotels() {
		return hotelservice.getAllHotels();
	}

	@Override
	public List<RestaurantData> getHotelByName(String hotelName) {
		return hotelservice.getHotelDetailsByName(hotelName);
	}

	@Override
	public List<RestaurantData> getHotelById(int hotelId) {
		return hotelservice.getHotelDetailsById(hotelId);
	}

	@Override
	public RestaurantData[] getAllHotel() {
		return hotelservice.getAllHotel();
	}

}
