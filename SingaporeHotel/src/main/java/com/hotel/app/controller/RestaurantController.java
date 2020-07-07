package com.hotel.app.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.app.data.HotelData;
import com.hotel.app.data.RestaurantData;
import com.hotel.app.service.HotelService;
import com.hotel.app.service.RestaurantService;

@RestController
@RequestMapping(path = "/restaurantservice")
public class RestaurantController {
	@Autowired
	RestaurantService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goToHomePage() {
		return "hotelapp";
	}
	
	@RequestMapping(value="/getAllRestaurant", method = RequestMethod.GET,headers = "Accept=application/json")
	public List<RestaurantData> getAlHotelList(Model model) {
		System.out.println("called to controller...");
		List<RestaurantData> hotelList = service.getAllHotels();
		model.addAttribute("hoetl",new HotelData());
		model.addAttribute("hotellist",hotelList);
		return hotelList;
	}
	
	@RequestMapping(value="/getAllRestaurants", method = RequestMethod.GET,headers = "Accept=application/json")
	public RestaurantData[] getAlHotelListArray(Model model) {
		System.out.println("called to controller...");
		RestaurantData[] hotelArray = service.getAllHotel();
		model.addAttribute("hoetl",new RestaurantData());
		model.addAttribute("hotellist",hotelArray);
		return hotelArray;
	}
	
	@RequestMapping(value = "/getRestaurantName/{hotelname}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RestaurantData> getHotelByName(@PathVariable("hotelname") String hotelName) {
		System.out.println("called to controller..1.");
		List<RestaurantData> hotelList = service.getHotelByName(hotelName);
		return hotelList;
	}
}
