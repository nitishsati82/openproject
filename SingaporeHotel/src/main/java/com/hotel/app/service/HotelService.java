package com.hotel.app.service;

import java.util.List;

import com.hotel.app.data.HotelData;

public interface HotelService {
	public List<HotelData> getAllHotels();
	public List<HotelData> getHotelByName(String hotelName);
	public List<HotelData> getHotelById(int hotelId);
	
	
}
