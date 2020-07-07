package com.hotel.app.service;

import java.util.List;

import com.hotel.app.data.RestaurantData;

public interface RestaurantService {
	public List<RestaurantData> getAllHotels();
	public RestaurantData[] getAllHotel();
	public List<RestaurantData> getHotelByName(String hotelName);
	public List<RestaurantData> getHotelById(int hotelId);
	
	
}
