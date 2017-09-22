package com.libertymutual.goforcode.spark.app.models;

import org.javalite.activejdbc.Model;

public class ApartmentsUsers extends Model{

	public ApartmentsUsers() {}
	
	public ApartmentsUsers(int apartmentId, int userId) {
		setApartmentId(apartmentId);
		setUserId(userId);
	}

	public void setUserId(int userId) {
		set("user_id", userId);
	}
	
	public int getUserId() {
		return getInteger("user_id");
	}

	public void setApartmentId(int apartmentId) {
		set("apartment_id", apartmentId);
	}
	
	public int getApartmentId() {
		return getInteger("apartment_id");
	}
}
