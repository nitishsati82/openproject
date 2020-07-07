package com.hotel.app.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.app.data.HotelData;
import com.hotel.app.service.HotelService;

@RestController
@RequestMapping(path = "/hotelservice")
public class AppController {
	@Autowired
	HotelService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goToHomePage() {
		return "hotelapp";
	}
	
	@RequestMapping(value="/getAllHotels", method = RequestMethod.GET,headers = "Accept=application/json")
	public List<HotelData> getAlHotelList(Model model) {
		System.out.println("called to controller...");
		List<HotelData> hotelList = service.getAllHotels();
		model.addAttribute("hoetl",new HotelData());
		model.addAttribute("hotellist",hotelList);
		return hotelList;
	}
	
	
	@RequestMapping(value = "/getHotelByName/{hotelname}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<HotelData> getHotelByName(@PathVariable("hotelname") String hotelName) {
		System.out.println("called to controller..1.");
		List<HotelData> hotelList = service.getHotelByName(hotelName);
		return hotelList;
	}
}
