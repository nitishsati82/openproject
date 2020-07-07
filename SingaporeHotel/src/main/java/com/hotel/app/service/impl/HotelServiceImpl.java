package com.hotel.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.app.data.HotelData;
import com.hotel.app.repos.HotelRepos;
import com.hotel.app.service.HotelService;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {
	@Autowired
	HotelRepos hotelservice;
	
	@Override
	public List<HotelData> getAllHotels() {
		return hotelservice.getAllHotels();
	}

	@Override
	public List<HotelData> getHotelByName(String hotelName) {
		return hotelservice.getHotelDetailsByName(hotelName);
	}

	@Override
	public List<HotelData> getHotelById(int hotelId) {
		return hotelservice.getHotelDetailsById(hotelId);
	}

}
