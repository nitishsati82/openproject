package com.hotel.app.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurantsdb")
public class RestaurantData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "Restaurant_ID")
	private String Restaurant_ID;
	
	@Column(name = "Restaurant_Name")
	private String Restaurant_Name;
	
	@Column(name = "Cuisines")
	private String Cuisines;
	
	@Column(name = "Average_Cost")
	private String Average_Cost;
	
	@Column(name = "Currency")
	private String Currency;
	
	@Column(name = "Has_Table_book")
	private String Has_Table_book;
	
	@Column(name = "Has_Online_del")
	private String Has_Online_del;
	
	@Column(name = "Aggregate_rating")
	private String Aggregate_rating;
	
	@Column(name = "Rating_color")
	private String Rating_color;
	
	@Column(name = "Rating_text")
	private String Rating_text;
	
	@Column(name = "Votes")
	private String Votes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRestaurant_ID() {
		return Restaurant_ID;
	}

	public void setRestaurant_ID(String restaurant_ID) {
		Restaurant_ID = restaurant_ID;
	}

	public String getRestaurant_Name() {
		return Restaurant_Name;
	}

	public void setRestaurant_Name(String restaurant_Name) {
		Restaurant_Name = restaurant_Name;
	}

	public String getCuisines() {
		return Cuisines;
	}

	public void setCuisines(String cuisines) {
		Cuisines = cuisines;
	}

	public String getAverage_Cost() {
		return Average_Cost;
	}

	public void setAverage_Cost(String average_Cost) {
		Average_Cost = average_Cost;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getHas_Table_book() {
		return Has_Table_book;
	}

	public void setHas_Table_book(String has_Table_book) {
		Has_Table_book = has_Table_book;
	}

	public String getHas_Online_del() {
		return Has_Online_del;
	}

	public void setHas_Online_del(String has_Online_del) {
		Has_Online_del = has_Online_del;
	}

	public String getAggregate_rating() {
		return Aggregate_rating;
	}

	public void setAggregate_rating(String aggregate_rating) {
		Aggregate_rating = aggregate_rating;
	}

	public String getRating_color() {
		return Rating_color;
	}

	public void setRating_color(String rating_color) {
		Rating_color = rating_color;
	}

	public String getRating_text() {
		return Rating_text;
	}

	public void setRating_text(String rating_text) {
		Rating_text = rating_text;
	}

	public String getVotes() {
		return Votes;
	}

	public void setVotes(String votes) {
		Votes = votes;
	}
	
}
